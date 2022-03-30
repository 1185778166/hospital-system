package com.maxt.system.hospital.entity.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Maxt
 * @Date 2022/3/30 下午12:00
 * @Version 1.0
 * @Description 签名信息
 */
@Data
@ApiModel(description = "签名信息")
public class SignInfoVo  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "api基础路径")
	private String apiUrl;

	@ApiModelProperty(value = "签名秘钥")
	private String signKey;

}

