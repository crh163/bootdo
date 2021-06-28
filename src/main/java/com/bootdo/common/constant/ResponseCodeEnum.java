package com.bootdo.common.constant;


public enum ResponseCodeEnum {

    /**
     * 响应状态码
     */
    SUCCESS(200, "请求成功"),
    FAIL(500, "请求异常"),
    FAIL_NO_LOGIN(501, "请先登录账号！"),
    FAIL_IDEMPOTENT(502, "请勿重复发起请求！"),

    NOT_EXIST_USER(601, "用户不存在！"),
    NOT_EXIST_QUESTION(701, "问卷不存在！"),
    FAIL_SUBMIT_QUESTION(702, "提交的问卷信息参数有误！"),
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
