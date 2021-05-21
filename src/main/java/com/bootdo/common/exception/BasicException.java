package com.bootdo.common.exception;

import com.bootdo.common.constant.ResponseCodeEnum;
import lombok.Data;

@Data
public class BasicException extends Exception {

    protected Integer errorCode;

    protected String errorMsg;

    public BasicException(Integer errorCode, String errorMsg) {
        super(errorCode + "_" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BasicException(ResponseCodeEnum codeEnum) {
        this(codeEnum.getCode(), codeEnum.getMsg());
    }

}
