package com.maxt.system.hospital.entity.enums;

/**
 * @Author Maxt
 * @Date 2022/3/23 下午5:03
 * @Version 1.0
 * @Description
 */
public enum AuthStatusEnum {

    NO_AUTH(0, "未认证"),
    AUTH_RUN(1, "认证中"),
    AUTH_SUCCESS(2, "认证成功"),
    AUTH_FAIL(-1, "认证失败");

    private Integer status;
    private String name;

    AuthStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
