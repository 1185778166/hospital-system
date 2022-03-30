package com.maxt.system.hospital.common.common.util.exception;

import com.maxt.system.hospital.common.common.util.result.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Maxt
 * @Date 2022/3/23 下午2:01
 * @Version 1.0
 * @Description  自定义全局异常
 */
@Data
@ApiModel(value = "自定义全局异常类")
public class HospitalException extends RuntimeException{
    @ApiModelProperty(value = "异常状态码")
    private Integer code;

    /**
     * 通过状态码和错误信息创建异常对象
     * @param message
     * @param code
     */
    public HospitalException(String message, Integer code){
        super(message);
        this.code = code;
    }

    /**
     * 接受枚举类型对象
     * @param resultCodeEnum
     */
    public HospitalException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "HospitalException{" +
                "code=" + code +
                ", message" + this.getMessage() +
                '}';
    }
}
