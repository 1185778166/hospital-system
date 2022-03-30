package com.maxt.system.hospital.entity.vo.acl;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Maxt
 * @Date 2022/3/30 下午12:47
 * @Version 1.0
 * @Description  角色查询实体
 */
@Data
@ApiModel(description = "角色查询实体")
public class RoleQueryVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "角色名称")
	private String roleName;

}

