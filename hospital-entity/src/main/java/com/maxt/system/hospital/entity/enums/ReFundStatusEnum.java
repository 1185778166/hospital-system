package com.maxt.system.hospital.entity.enums;

/**
 * @Author Maxt
 * @Date 2022/3/23 下午5:42
 * @Version 1.0
 * @Description
 */
public enum ReFundStatusEnum {

    UNREFUND(1, "退款中"),
    REFUND(2,"已退款");

    private Integer status;
    private String name;

    ReFundStatusEnum(Integer status, String name) {
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
