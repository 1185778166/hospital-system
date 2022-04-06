package com.maxt.system.hospital.service.appointment.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maxt.system.hospital.common.common.util.result.Result;
import com.maxt.system.hospital.entity.model.hospital.HospitalSet;
import com.maxt.system.hospital.entity.vo.hospital.HospitalQueryVo;
import com.maxt.system.hospital.service.appointment.service.IHospitalSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Maxt
 * @Date 2022/4/6 19:24
 * @Version 1.0
 * @Description
 */
@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hospital/hospitalSet")
public class HospitalSetController {


    @Autowired
    private IHospitalSetService hospitalSetService;

    /**
     * 获取所有医院设置
     * @return
     */
    @ApiOperation(value = "获取所有医院设置")
    @GetMapping("findAll")
    public Result findAllHospitalSet(){
        int i = 1/0;
        List<HospitalSet> list = hospitalSetService.listAll();
        return Result.ok(list);
    }

    /**
     * 逻辑删除医院设置
     * @param id
     * @return
     */
    @ApiOperation(value = "逻辑删除医院设置")
    @DeleteMapping("{id}")
    public Result removeHospitalSet(@PathVariable Long id){
        boolean flag = hospitalSetService.removeById(id);
        return Result.result(flag);
    }

    /**
     * 条件查询带分页
     * @param hospitalQueryVo
     * @return
     */
    @ApiOperation("条件查询带分页")
    @PostMapping("findHospitalByPage")
    public Result findHospitalSetByPage(@RequestBody(required = false)HospitalQueryVo hospitalQueryVo){
        Page<HospitalSet> pageHospital = hospitalSetService.selectByProperties(hospitalQueryVo);
        return Result.ok(pageHospital);
    }

    /**
     * 添加医院设置
     * @param hospitalSet
     * @return
     */
    @ApiOperation("添加医院设置")
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet){
       boolean save = hospitalSetService.saveHospitalSet(hospitalSet);
        return Result.result(save);
    }

    /**
     * 根据id获取医院设置
     * @param id
     * @return
     */
    @ApiOperation("根据id获取医院设置")
    @GetMapping("getHospital/{id}")
    public Result getHospitalSetById(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    /**
     * 更新医院设置
     * @param hospitalSet
     * @return
     */
    @ApiOperation("更新医院设置")
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet){
        boolean update = hospitalSetService.updateHospitalSet(hospitalSet);
        return Result.result(update);
    }

    /**
     * 批量删除医院设置
     * @param idList
     * @return
     */
    @ApiOperation("批量删除医院设置")
    @DeleteMapping("batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList){
        boolean batchDelete = hospitalSetService.removeByIds(idList);
        return Result.result(batchDelete);
    }

    /**
     * 医院设置锁定和解锁
     * @param id
     * @param status
     * @return
     */
    @ApiOperation("医院设置锁定和解锁")
    @PostMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id, @PathVariable Integer status){
        boolean lock = hospitalSetService.lockOrUnLockHospitalSet(id, status);
        return Result.result(lock);
    }

    /**
     * 发送签名密钥
     * @param id
     * @return
     */
    @ApiOperation("发送签名密钥")
    @PutMapping("sendKey/{id}")
    public Result sendKey(@PathVariable Long id){
       boolean send = hospitalSetService.sendKey(id);
        return Result.result(send);
    }

}
