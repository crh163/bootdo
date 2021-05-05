package com.bootdo.common.constant;


public enum ResponseCodeEnum {

    /**
     * 响应状态码
     */
    SUCCESS(200, "请求成功"),
    FAIL(500, "请求异常"),

    DEPT_COMMON_PARENT_AND_NAME(601, "");

    private Integer code;

    private String msg;

    ResponseCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
