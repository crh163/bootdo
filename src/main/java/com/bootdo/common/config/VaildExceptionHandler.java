package com.bootdo.common.config;

import com.bootdo.api.entity.res.common.Response;
import com.bootdo.common.exception.BasicException;
import com.bootdo.common.utils.ResponseUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author rory.chen
 * @date 2021-02-25 18:30
 */
@ControllerAdvice(basePackages = "com.bootdo.api.controller")
@ResponseBody
@Slf4j
public class VaildExceptionHandler {

    @Autowired
    private Gson gson;

    /**
     * 自定义异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BasicException.class)
    public Response handleWxbaseException(BasicException e) {
        Response result = ResponseUtil.getResult(e.getErrorCode(), e.getErrorMsg());
        logResult(result);
        return result;
    }

    private void logResult(Object result){
        //对每个请求的结果打印的字符串最大长度
        int maxLength = 5000;
        // 处理完请求，返回内容
        String resultJson = gson.toJson(result);
        if (resultJson.length() <= maxLength) {
            log.info("result : {}", resultJson);
        } else {
            log.info("result : {} (truncated)...", resultJson.substring(0, maxLength));
        }
    }

}
