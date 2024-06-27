package org.wyj.blog.config.page;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther 武耀君
 * @date 2024/4/14
 * <p>
 * mybatis的分页拦截器
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare",
        args = {Connection.class, Integer.class})})
public class PaginationInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(PaginationInterceptor.class);

    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

    @Override
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获得拦截的对象
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        // 待执行的sql的包装对象
        BoundSql boundSql = statementHandler.getBoundSql();
        // 判断是否是查询语句
        if (isSelect(boundSql.getSql())) {
            // 获得参数集合
            Object params = boundSql.getParameterObject();
            if (params instanceof BasicPageDTO) {
                // 单个参数且为Page，则表示该操作需要进行分页处理
                return simpleParamHandler(invocation, boundSql, (BasicPageDTO) params);
            }
        }
        return invocation.proceed();
    }

    private boolean isSelect(String sql) {
        return !"".equalsIgnoreCase(sql) && sql.toUpperCase().trim().startsWith("SELECT");
    }

    private Object simpleParamHandler(Invocation invocation, BoundSql boundSql, BasicPageDTO pageDTO) throws Throwable {
        return pageHandlerExecutor(invocation, boundSql, pageDTO);
    }

    private Object pageHandlerExecutor(Invocation invocation, BoundSql boundSql, BasicPageDTO pageDTO) throws Throwable {
        // 获得数据库连接
        Connection connection = (Connection) invocation.getArgs()[0];

        // 使用Mybatis提供的MetaObject，该对象主要用于获取包装对象的属性值
        MetaObject statementHandler = MetaObject.forObject(invocation.getTarget(), DEFAULT_OBJECT_FACTORY,
                DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);

        // 获取该sql执行的结果集总数
        int maxSize = getTotalSize(connection, (MappedStatement) statementHandler
                .getValue("delegate.mappedStatement"), boundSql);

        // 生成分页sql
        pageDTO.setTotal(maxSize);
        String wrapperSql = getPageSql(boundSql.getSql(), pageDTO);

        MetaObject boundSqlMeta = MetaObject.forObject(boundSql, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY,
                DEFAULT_REFLECTOR_FACTORY);
        // 修改boundSql的sql
        boundSqlMeta.setValue("sql", wrapperSql);
        return invocation.proceed();
    }

    private int getTotalSize(Connection connection, MappedStatement mappedStatement, BoundSql boundSql) {
        String countSql = getCountSql(boundSql.getSql());
        PreparedStatement countStmt;
        ResultSet rs;
        List<AutoCloseable> closeableList = new ArrayList<>();

        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            closeableList.add(countStmt);
            closeableList.add(rs);
        } catch (SQLException e) {
            logger.error("append an exception[{}] when execute sql[{}] with {}", e, countSql,
                    boundSql.getParameterObject());
        } finally {
            for (AutoCloseable closeable : closeableList) {
                try {
                    if (closeable != null)
                        closeable.close();
                } catch (Exception e) {
                    logger.error("append an exception[{}] when close resource[{}] ", e, closeable);
                }
            }
        }
        return 0;
    }

    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

    public String getCountSql(String sql) {
        return "SELECT count(1) FROM (" + sql + ") as total";
    }

    public String getPageSql(String sql, BasicPageDTO pageDTO) {
        if (pageDTO.getPageNo() <= 0) {
            pageDTO.setPageNo(1);
        }
        if (pageDTO.getPageSize() <= 0) {
            pageDTO.setPageSize(10);
        }
        int offset = (pageDTO.getPageNo() - 1) * pageDTO.getPageSize();

        return sql + " LIMIT " + offset + ", " + pageDTO.getPageSize();
    }

}
