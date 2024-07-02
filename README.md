# 简介

一个博客项目，管理用户上传的文档，项目采用单体架构，使用了springboot、mysql、mybatis-plus、redis、rocketMQ、jwt

基本功能：文章列表、文章分类、文章标签、发布文章、评论、用户（登录、注册）、图片的上传下载



# 技术点

## 跨域配置

博客项目的前端是在8080端口上的，后端是在8888端口上的，所以后端需要提供跨域配置

这里选择自定义拦截器来处理跨域请求：

```java
// 第一步：自定义拦截器
@Component
public class CrossDomainInterceptor  implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request
            , HttpServletResponse response, Object handler) throws Exception {
        // 处理OPTIONS请求
        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.addHeader("Vary", "Origin");
            response.addHeader("Vary", "Access-Control-Request-Method");
            response.addHeader("Vary", "Access-Control-Request-Headers");
            response.setHeader("Access-control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,POST");
            response.setHeader("Access-Control-Allow-Credentials", "false");
            response.setHeader("Access-Control-Allow-Headers", "authorization, content-type");
            response.setHeader("Access-Control-Max-Age", "1800");
            response.setHeader("Allow", "GET, HEAD, POST, PUT, DELETE, OPTIONS, PATCH");
            return false;
        }
        // 为所有的响应加上一个统一的响应头
        response.setHeader("Access-control-Allow-Origin", "*");
        return true;
    }
}
      
// 第二步：配置拦截器
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Autowired
    private CrossDomainInterceptor crossDomainInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 自定义跨域配置拦截器，它在所有的拦截器之前调用
        registry.addInterceptor(crossDomainInterceptor)
                .addPathPatterns("/**");
    }
}
```



## 使用log4j2来记录日志

实现的功能：

- 将日志打印到控制台，同时配置日志中各个部分的颜色
- 打印debug、info、error三份日志
- 为一些重点关注的组件单独生成日志文件，例如mybatis-plus，在这里会把sql操作单独打印到一个文件中
- 按天来组织日志文件

```xml
<?xml version="1.0" encoding="UTF-8"?>

<configuration>

    <!--变量配置-->
    <Properties>
        <!-- 定义日志存储的路径-->
        <property name="LOG_CONTEXT_NAME">blog-api</property>
        <!--定义日志文件的存储地址 勿在LogBack的配置中使用相对路径-->
        <property name="LOG_HOME">logs/${LOG_CONTEXT_NAME}</property>
        <property name="DAY_INFO_FILE">${LOG_HOME}/$${date:yyyy-MM-dd}/info.%d{yyyy-MM-dd}.%i.log</property>
        <property name="DAY_BUSINESS_INFO_FILE">${LOG_HOME}/$${date:yyyy-MM-dd}/business.%d{yyyy-MM-dd}.%i.log</property>
        <property name="DAY_SQL_INFO_FILE">${LOG_HOME}/$${date:yyyy-MM-dd}/sql.%d{yyyy-MM-dd}.%i.log</property>
        <property name="DAY_DEBUG_FILE">${LOG_HOME}/$${date:yyyy-MM-dd}/debug.%d{yyyy-MM-dd}.%i.log</property>
        <property name="DAY_ERROR_FILE">${LOG_HOME}/$${date:yyyy-MM-dd}/error.%d{yyyy-MM-dd}.%i.log</property>

        <!-- 格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度，
          %method 方法名 ，%L 行数，%msg：日志消息，%n是换行符-->
        <property name="LOG_PATTERN">
            [%d{yyyy-MM-dd HH:mm:ss.SSS}] %-5level [%thread] %logger.%method:%L : %msg%n
        </property>
        <property name="COLOR_LOG_PATTERN">
            %green{[%date{yyyy-MM-dd HH:mm:ss}]} %blue{%-5level} %cyan{[%thread]} %magenta{%logger} : %black{%msg%n}
        </property>
        <property name="BUSINESS_LOG_PATTERN">
            [%d{yyyy-MM-dd HH:mm:ss}]: %msg%n
        </property>
    </Properties>

    <appenders>
        <!-- 输出到控制台的追加器-->
        <Console name="console" target="SYSTEM_OUT">
            <!-- 过滤器，表示当前appender只打印info及以上级别的信息 -->
            <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY" />
            <PatternLayout pattern="${COLOR_LOG_PATTERN}" />
        </Console>

        <!--存储info信息-->
        <RollingFile name="rollingFileInfo" fileName="${LOG_HOME}/info.log"
                     filePattern="${DAY_INFO_FILE}">
            <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY" />
            <PatternLayout pattern="${LOG_PATTERN}"/>
            <Policies>
                <OnStartupTriggeringPolicy />
                <TimeBasedTriggeringPolicy />
                <SizeBasedTriggeringPolicy size="10MB"/>
            </Policies>
            <DefaultRolloverStrategy max="50"/>
        </RollingFile>

        <!--存储debug信息-->
        <RollingFile name="rollingFileDebug" fileName="${LOG_HOME}/debug.log"
                     filePattern="${DAY_DEBUG_FILE}">
            <PatternLayout pattern="${LOG_PATTERN}"/>
            <Policies>
                <OnStartupTriggeringPolicy />
                <TimeBasedTriggeringPolicy />
                <SizeBasedTriggeringPolicy size="30MB"/>
            </Policies>
            <DefaultRolloverStrategy max="50"/>
        </RollingFile>

        <!--存储error信息-->
        <RollingFile name="rollingFileError" fileName="${LOG_HOME}/error.log"
                     filePattern="${DAY_ERROR_FILE}">
            <ThresholdFilter level="ERROR" onMatch="ACCEPT" onMismatch="DENY" />
            <PatternLayout pattern="${LOG_PATTERN}"/>
            <Policies>
                <OnStartupTriggeringPolicy />
                <TimeBasedTriggeringPolicy />
                <SizeBasedTriggeringPolicy size="10MB"/>
            </Policies>
            <DefaultRolloverStrategy max="50"/>
        </RollingFile>

        <!-- 记录业务相关的信息 -->
        <RollingFile name="BUSINESS_APPENDER" fileName="${LOG_HOME}/business.log"
                     filePattern="${DAY_BUSINESS_INFO_FILE}">
            <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY" />
            <PatternLayout pattern="${BUSINESS_LOG_PATTERN}" charset="utf-8"/>
            <Policies>
                <OnStartupTriggeringPolicy />
                <TimeBasedTriggeringPolicy />
                <SizeBasedTriggeringPolicy size="10MB"/>
            </Policies>
            <DefaultRolloverStrategy max="50"/>
        </RollingFile>

        <!-- 记录sql相关的信息 -->
        <RollingFile name="SQL_APPENDER" fileName="${LOG_HOME}/sql.log"
                     filePattern="${DAY_SQL_INFO_FILE}">
            <ThresholdFilter level="DEBUG" onMatch="ACCEPT" onMismatch="DENY" />
            <PatternLayout pattern="${LOG_PATTERN}" charset="utf-8"/>
            <Policies>
                <OnStartupTriggeringPolicy />
                <TimeBasedTriggeringPolicy />
                <SizeBasedTriggeringPolicy size="10MB"/>
            </Policies>
            <DefaultRolloverStrategy max="50"/>
        </RollingFile>
    </appenders>

    <loggers>
        <!--定义根日志记录器-->
        <root level="DEBUG">
            <appender-ref ref="console" />
            <appender-ref ref="rollingFileInfo"/>
            <appender-ref ref="rollingFileDebug"/>
            <appender-ref ref="rollingFileError"/>
        </root>

        <!--打印业务相关的日志-->
        <Logger name="org.wyj.blog.aop.log" level="DEBUG" additivity="true">
            <appender-ref ref="BUSINESS_APPENDER" />
        </Logger>

        <!--打印sql相关的日志-->
        <Logger name="org.wyj.blog.mapper" level="DEBUG" additivity="false">
            <appender-ref ref="SQL_APPENDER" />
        </Logger>
    </loggers>
</configuration>
```



## 使用rocketMQ来解决缓存一致性的问题

本项目会把首页的文章列表放到redis中，文章列表是分页、按照创建时间倒序排序的。当用户发布新文章之后，原有的文章列表就需要重新计算，但是它们在redis中还被缓存了一份，如果只是数据库重新计算，就会造成缓存不一致的问题。

在这里，为了解决缓存不一致的问题，本项目的设计方案是，当用户发布新文章后，删除redis中所有关于文章列表的缓存。  // 这种方案比较粗糙，但是考虑到这是一个用于学习的项目，而且主要是为了在学习中接触缓存一致性问题。

代码方案：使用消息队列，在这里选择rocketMQ，当用户发布文章后，发送一个消息到消息队列中，然后再启动一个监听器，监听消息队列，监听到消息后，删除redis中所有关于文章列表的缓存



# 性能测试

使用jmeter对当前程序进行性能测试。

当前程序中涉及到的所有组件，redis、rocketMQ、springboot程序、mysql，都是单机部署。

所有的测试脚本都在当前目录下

