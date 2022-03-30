package com.maxt.system.hospital.entity.model.hospital;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author Maxt
 * @Date 2022/3/23 下午10:17
 * @Version 1.0
 * @Description
 */
@Data
@ApiModel(description = "预约规则")
@Document("BookingRule")
public class BookingRule {

    @ApiModelProperty(value = "预约周期")
    private Integer cycle;

    @ApiModelProperty(value = "放号时间")
    private String releaseTime;

    @ApiModelProperty(value = "停挂时间")
    private String stopTime;

    @ApiModelProperty(value = "推号截止天数（如：就诊前一天为-1，当天为0）")
    private Integer quitDay;

    @ApiModelProperty(value = "推号时间")
    private String quitTime;

    @ApiModelProperty(value = "预约规则")
    private List<String> role;

    /**
     *
     * @param role
     */
    public void setRole(String role){
        if (StringUtils.hasLength(role)){
            this.role = JSONArray.parseArray(role, String.class);
        }
    }
}
