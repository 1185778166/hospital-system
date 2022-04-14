package com.maxt.system.hospital.entity.model.hospital;

import com.maxt.system.hospital.entity.model.base.BaseMongoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author Maxt
 * @Date 2022/3/23 下午10:23
 * @Version 1.0
 * @Description
 */
@Data
@ApiModel(description = "")
@Document("Department")
public class Department extends BaseMongoEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "医院编号")
    //普通索引
    @Indexed
    private String hosCode;

    @ApiModelProperty(value = "科室编号")
    //唯一索引
    @Indexed(unique = true)
    private String depCode;

    @ApiModelProperty(value = "科室名称")
    private String depName;

    @ApiModelProperty(value = "科室描述")
    private String intro;

    @ApiModelProperty(value = "大科室编号")
    private String bigCode;

    @ApiModelProperty(value = "大科室名称")
    private String bigName;
}
