package com.bootdo.common.config;

import com.bootdo.api.entity.res.common.Response;
import com.bootdo.common.exception.BasicException;
import com.bootdo.common.utils.ResponseUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author rory.chen
 * @date 2021-02-25 18:30
 */
@ControllerAdvice(basePackages = "com.bootdo.api.controller")
@ResponseBody
public class VaildExceptionHandler {

    /**
     * 自定义异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BasicException.class)
    public Response handleWxbaseException(BasicException e) {
        return ResponseUtil.getResult(e.getErrorCode(), e.getErrorMsg());
    }

}
