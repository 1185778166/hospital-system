package com.maxt.system.hospital.entity.model.order;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.maxt.system.hospital.entity.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author Maxt
 * @Date 2022/3/30 下午12:47
 * @Version 1.0
 * @Description
 */
@Data
@ApiModel(description = "Order")
@TableName("order_info")
public class OrderInfo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "userId")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "订单交易号")
	@TableField("out_trade_no")
	private String outTradeNo;

	@ApiModelProperty(value = "医院编号")
	@TableField("hos_code")
	private String hosCode;

	@ApiModelProperty(value = "医院名称")
	@TableField("hos_name")
	private String hosName;

	@ApiModelProperty(value = "科室编号")
	@TableField("dep_code")
	private String depCode;

	@ApiModelProperty(value = "科室名称")
	@TableField("dep_name")
	private String depName;

	@ApiModelProperty(value = "排班id")
	@TableField("schedule_id")
	private String scheduleId;

	@ApiModelProperty(value = "医生职称")
	@TableField("title")
	private String title;

	@ApiModelProperty(value = "安排日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@TableField("reserve_date")
	private LocalDateTime reserveDate;

	@ApiModelProperty(value = "安排时间（0：上午 1：下午）")
	@TableField("reserve_time")
	private Integer reserveTime;

	@ApiModelProperty(value = "就诊人id")
	@TableField("patient_id")
	private Long patientId;

	@ApiModelProperty(value = "就诊人名称")
	@TableField("patient_name")
	private String patientName;

	@ApiModelProperty(value = "就诊人手机")
	@TableField("patient_phone")
	private String patientPhone;

	@ApiModelProperty(value = "预约记录唯一标识（医院预约记录主键）")
	@TableField("hos_record_id")
	private String hosRecordId;

	@ApiModelProperty(value = "预约号序")
	@TableField("number")
	private Integer number;

	@ApiModelProperty(value = "建议取号时间")
	@TableField("fetch_time")
	private String fetchTime;

	@ApiModelProperty(value = "取号地点")
	@TableField("fetch_address")
	private String fetchAddress;

	@ApiModelProperty(value = "医事服务费")
	@TableField("amount")
	private BigDecimal amount;

	@ApiModelProperty(value = "退号时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@TableField("quit_time")
	private LocalDateTime quitTime;

	@ApiModelProperty(value = "订单状态")
	@TableField("order_status")
	private Integer orderStatus;

}

