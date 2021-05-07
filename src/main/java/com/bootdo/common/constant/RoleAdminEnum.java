package com.bootdo.common.constant;

public enum RoleAdminEnum {

    /**
     * admin 对应数据库信息（不可变）
     */
    ADMIN(1L, "admin");

    RoleAdminEnum(Long roleId, String roleSign){
        this.roleId = roleId;
        this.roleSign = roleSign;
    }

    private Long roleId;

    private String roleSign;

    public Long getRoleId() {
        return roleId;
    }

    public String getRoleSign() {
        return roleSign;
    }
}
