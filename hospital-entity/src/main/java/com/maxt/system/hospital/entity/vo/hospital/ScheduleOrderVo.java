package com.maxt.system.hospital.entity.vo.hospital;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author Maxt
 * @Date 2022/3/30 下午12:00
 * @Version 1.0
 * @Description  Schedule
 */

@Data
@ApiModel(description = "Schedule")
public class ScheduleOrderVo {

	@ApiModelProperty(value = "医院编号")
	private String hosCode;

	@ApiModelProperty(value = "医院名称")
	private String hosName;

	@ApiModelProperty(value = "科室编号")
	private String depCode;

	@ApiModelProperty(value = "科室名称")
	private String depName;

	@ApiModelProperty(value = "排班编号（医院自己的排班主键）")
	private String hosScheduleId;

	@ApiModelProperty(value = "医生职称")
	private String title;

	@ApiModelProperty(value = "安排日期")
	private LocalDateTime reserveDate;

	@ApiModelProperty(value = "剩余预约数")
	private Integer availableNumber;

	@ApiModelProperty(value = "安排时间（0：上午 1：下午）")
	private Integer reserveTime;

	@ApiModelProperty(value = "医事服务费")
	private BigDecimal amount;

	@ApiModelProperty(value = "退号时间")
	private LocalDateTime quitTime;

	@ApiModelProperty(value = "挂号开始时间")
	private LocalDateTime startTime;

	@ApiModelProperty(value = "挂号结束时间")
	private LocalDateTime endTime;

	@ApiModelProperty(value = "当天停止挂号时间")
	private LocalDateTime stopTime;
}
