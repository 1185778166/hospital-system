package com.maxt.system.hospital.entity.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Maxt
 * @Date 2022/3/23 下午5:15
 * @Version 1.0
 * @Description
 */
public enum OrderStatusEnum {

    UNPAID(0, "预约成功，待支付"),
    PAID(1, "已支付"),
    GET_NUMBER(2, "已取号"),
    CANCEL(-1, "取消预约")
    ;


    private Integer status;
    private String comment;

    OrderStatusEnum(Integer status, String comment) {
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

    public static String getStatusNameByStatus(Integer status){
        OrderStatusEnum[] values = OrderStatusEnum.values();
        for (OrderStatusEnum value : values) {
            if (status.intValue() == value.getStatus().intValue()){
                return value.getComment();
            }
        }
        return "";
    }

    public static List<Map<String, Object>> getStatusList(){
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        OrderStatusEnum[] values = OrderStatusEnum.values();
        for (OrderStatusEnum value : values) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("status", value.getStatus());
            map.put("comment", value.getComment());
            list.add(map);
        }
        return list;
    }
}
