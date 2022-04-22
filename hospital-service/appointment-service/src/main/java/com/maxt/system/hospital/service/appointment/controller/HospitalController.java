package com.maxt.system.hospital.service.appointment.controller;

import com.maxt.system.hospital.common.common.util.result.Result;
import com.maxt.system.hospital.entity.vo.hospital.HospitalQueryVo;
import com.maxt.system.hospital.service.appointment.service.IHospitalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Maxt
 * @Date 2022/4/20 16:49
 * @Version 1.0
 * @Description
 */
@Api("医院管理接口")
@RestController
@RequestMapping("/admin/hospital/hospital")
public class HospitalController {

    @Autowired
    IHospitalService hospitalService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Integer page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Integer limit,
                        @ApiParam(name = "hospitalQueryVo", value = "查询对象")HospitalQueryVo hospitalQueryVo){
        return Result.ok(hospitalService.selectPage(page, limit, hospitalQueryVo));
    }

    @ApiOperation("更新医院状态")
    @GetMapping("updateStatus/{id}/{status}")
    public Result lock(@ApiParam(name = "id", value = "医院id", required = true) @PathVariable String id,
                       @ApiParam(name = "status", value = "状态（0：未上线 1：已上线）", required = true) @PathVariable Integer status){
        hospitalService.updateStatus(id, status);
        return Result.ok();
    }

    @ApiOperation("获取医院详情")
    @GetMapping("show/{id}")
    public Result show(@ApiParam(name = "id", value = "医院id", required = true) @PathVariable String id){
        return Result.ok(hospitalService.show(id));
    }

    @ApiOperation("根据医院名称获取医院列表")
    @GetMapping("findByHosName/{hosName}")
    public Result findByHosName(@ApiParam(name = "hosName", value = "医院名称", required = true)
                                    @PathVariable String hosName){
        return Result.ok(hospitalService.findByHosName(hosName));
    }
}
