package com.maxt.system.hospital.entity.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Maxt
 * @Date 2022/3/30 下午12:00
 * @Version 1.0
 * @Description  OrderCountVo
 */

@Data
@ApiModel(description = "OrderCountVo")
public class OrderCountVo {
	
	@ApiModelProperty(value = "安排日期")
	private String reserveDate;

	@ApiModelProperty(value = "预约单数")
	private Integer count;

}

