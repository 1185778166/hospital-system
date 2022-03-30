package com.maxt.system.hospital.entity.model.hospital;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author Maxt
 * @Date 2022/3/30 下午12:00
 * @Version 1.0
 * @Description  医院类
 */
@Data
@ApiModel(description = "Hospital")
@Document("Hospital")
public class Hospital {


    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "医院编号")
    //唯一索引
    @Indexed(unique = true)
    private String hosCode;

    @ApiModelProperty(value = "医院名称")
    @Indexed //普通索引
    private String hosName;

    @ApiModelProperty(value = "医院类型")
    private String hosType;

    @ApiModelProperty(value = "省code")
    private String provinceCode;

    @ApiModelProperty(value = "市code")
    private String cityCode;

    @ApiModelProperty(value = "区code")
    private String districtCode;

    @ApiModelProperty(value = "详情地址")
    private String address;

    @ApiModelProperty(value = "医院logo")
    private String logoData;

    @ApiModelProperty(value = "医院简介")
    private String intro;

    @ApiModelProperty(value = "坐车路线")
    private String route;

    @ApiModelProperty(value = "状态 0：未上线 1：已上线")
    private Integer status;

    //预约规则
    @ApiModelProperty(value = "预约规则")
    private BookingRule bookingRule;

    public void setBookingRule(String bookingRule) {
        this.bookingRule = JSONObject.parseObject(bookingRule, BookingRule.class);
    }
}