package com.bootdo.common.exception;

import com.bootdo.common.constant.ResponseCodeEnum;
import lombok.Data;

@Data
public class WxbaseException extends Exception {

    protected Integer errorCode;

    protected String errorMsg;

    public WxbaseException(Integer errorCode, String errorMsg) {
        super(errorCode + "_" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public WxbaseException(ResponseCodeEnum codeEnum) {
        this(codeEnum.getCode(), codeEnum.getMsg());
    }

}
