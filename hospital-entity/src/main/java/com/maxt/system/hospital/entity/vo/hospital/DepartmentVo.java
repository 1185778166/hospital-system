package com.maxt.system.hospital.entity.vo.hospital;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author Maxt
 * @Date 2022/3/30 下午12:00
 * @Version 1.0
 * @Description
 */

@Data
@ApiModel(description = "Department")
public class DepartmentVo {

	@ApiModelProperty(value = "科室编号")
	private String depCode;

	@ApiModelProperty(value = "科室名称")
	private String depName;

	@ApiModelProperty(value = "下级节点")
	private List<DepartmentVo> children;

}

