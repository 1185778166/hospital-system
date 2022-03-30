package com.maxt.system.hospital.entity.vo.hospital;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author Maxt
 * @Date 2022/3/30 下午12:47
 * @Version 1.0
 * @Description
 */
@Data
@ApiModel(description = "可预约排班规则数据")
public class BookingScheduleRuleVo {
	
	@ApiModelProperty(value = "可预约日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime workDate;

	@ApiModelProperty(value = "可预约日期")
	@JsonFormat(pattern = "MM月dd日")
	//方便页面使用
	private LocalDateTime workDateMd;

	@ApiModelProperty(value = "周几")
	private String dayOfWeek;

	@ApiModelProperty(value = "就诊医生人数")
	private Integer docCount;

	@ApiModelProperty(value = "科室可预约数")
	private Integer reservedNumber;

	@ApiModelProperty(value = "科室剩余预约数")
	private Integer availableNumber;

	@ApiModelProperty(value = "状态 0：正常 1：即将放号 -1：当天已停止挂号")
	private Integer status;
}

