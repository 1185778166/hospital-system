package com.maxt.system.hospital.entity.model.common;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Maxt
 * @Date 2022/3/23 下午10:06
 * @Version 1.0
 * @Description 数据字典
 */
@Data
@ApiModel(description = "数据字典")
@TableName("dict")
public class Dict {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableField("id")
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除（1：已删除，0：未删除）")
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    @ApiModelProperty(value = "其他参数")
    @TableField(exist = false)
    private Map<String, Object> map = new HashMap<>(16);

    @ApiModelProperty(value = "上级id")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "值")
    @TableField("value")
    private String value;

    @ApiModelProperty(value = "编码")
    @TableField("dict_code")
    private String dictCode;

    @ApiModelProperty(value = "是否包含子节点")
    //为树形组件所需字典，标识为数据库不存在
    @TableField(exist = false)
    private boolean hasChildren;
}
