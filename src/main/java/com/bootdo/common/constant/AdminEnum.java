package com.bootdo.common.constant;

public enum AdminEnum {

    /**
     * role admin 对应数据库 role_id,role_sign
     */
    ROLE_ADMIN(1L, "admin"),

    /**
     * user admin 对应数据库 id,username
     */
    USER_ADMIN(1L, "admin");

    AdminEnum(Long id, String sign){
        this.id = id;
        this.sign = sign;
    }

    private Long id;

    private String sign;

    public Long getId() {
        return id;
    }

    public String getSign() {
        return sign;
    }
}
