package com.maxt.system.hospital.entity.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Maxt
 * @Date 2022/3/23 下午9:37
 * @Version 1.0
 * @Description
 */
@Data
public class BaseMongoEntity implements Serializable {

    @ApiModelProperty(value = "id")
    @Id
    private String id;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除（1：已删除， 0：未删除）")
    private Integer isDeleted;

    @ApiModelProperty(value = "其他参数")
    //被该注解标注的，将不会被录入数据库，只能作为普通的JavaBean属性
    @Transient
    private Map<String, Object> param = new HashMap<>(16);
}
