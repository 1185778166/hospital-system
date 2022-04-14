package com.maxt.system.hospital.common.servicce.util.handler;

import com.maxt.system.hospital.common.common.util.exception.HospitalException;
import com.maxt.system.hospital.common.common.util.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author Maxt
 * @Date 2022/3/23 下午2:36
 * @Version 1.0
 * @Description
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(HospitalException.class)
    public Result error(HospitalException e){
        e.printStackTrace();
        return Result.build(e.getCode(), e.getMessage());
    }
}
