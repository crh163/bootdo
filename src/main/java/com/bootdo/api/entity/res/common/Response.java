package com.bootdo.api.entity.res.common;

import lombok.Data;

@Data
public class Response {

    private Integer code;

    private String msg;

    private Object data;

    public Response(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Response(){}

}
