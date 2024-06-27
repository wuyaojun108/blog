package org.wyj.blog.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wyj.blog.entity.vo.ResultVO;

@ControllerAdvice
public class AllExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(AllExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String doException(Exception ex) {
        // 打印堆栈信息
        LOG.error("发生错误", ex);
        return ResultVO.fail("系统异常").toJson();
    }
}
