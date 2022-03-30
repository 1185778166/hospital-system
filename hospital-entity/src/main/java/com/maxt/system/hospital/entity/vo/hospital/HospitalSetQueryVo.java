package com.maxt.system.hospital.entity.vo.hospital;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Maxt
 * @Date 2022/3/30 下午12:00
 * @Version 1.0
 * @Description
 */

@Data
public class HospitalSetQueryVo {

    @ApiModelProperty(value = "医院名称")
    private String hosName;

    @ApiModelProperty(value = "医院编号")
    private String hosCode;
}
