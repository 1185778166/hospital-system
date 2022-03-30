package com.maxt.system.hospital.entity.enums;

/**
 * @Author Maxt
 * @Date 2022/3/23 下午5:40
 * @Version 1.0
 * @Description
 */
public enum PaymentTypeEnum {
    ALIPAY(1, "支付宝"),
    WEIXIN(2, "微信");

    private Integer status;
    private String comment;

    PaymentTypeEnum(Integer status, String comment) {
        this.status = status;
        this.comment = comment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
