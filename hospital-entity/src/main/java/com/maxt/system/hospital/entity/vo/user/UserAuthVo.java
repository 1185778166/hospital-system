package com.maxt.system.hospital.entity.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Maxt
 * @Date 2022/3/30 下午12:00
 * @Version 1.0
 * @Description  会员认证对象
 */

@Data
@ApiModel(description="会员认证对象")
public class UserAuthVo {

    @ApiModelProperty(value = "用户姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "证件类型")
    @TableField("certificates_type")
    private String certificatesType;

    @ApiModelProperty(value = "证件编号")
    @TableField("certificates_no")
    private String certificatesNo;

    @ApiModelProperty(value = "证件路径")
    @TableField("certificates_url")
    private String certificatesUrl;

}
