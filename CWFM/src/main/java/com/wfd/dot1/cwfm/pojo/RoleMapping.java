package com.wfd.dot1.cwfm.pojo;

public class RoleMapping {
    private int mappingId;
    private String userId;
    private int roleId;

    public int getMappingId() {
        return mappingId;
    }

    public void setMappingId(int mappingId) {
        this.mappingId = mappingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public RoleMapping(String userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public RoleMapping() {
    }
}
