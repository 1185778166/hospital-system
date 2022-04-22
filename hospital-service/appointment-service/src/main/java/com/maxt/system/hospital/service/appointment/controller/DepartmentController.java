package com.maxt.system.hospital.service.appointment.controller;

import com.maxt.system.hospital.common.common.util.result.Result;
import com.maxt.system.hospital.entity.vo.hospital.DepartmentVo;
import com.maxt.system.hospital.service.appointment.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Maxt
 * @Date 2022/4/20 21:42
 * @Version 1.0
 * @Description
 */
@RestController
@RequestMapping("/admin/hospital/department")
public class DepartmentController {

    @Autowired
    IDepartmentService departmentService;

    @ApiOperation("查询医院所有科室列表")
    @RequestMapping("getDeptList/{hosCode}")
    public Result getDeptList(@PathVariable String hosCode){
        List<DepartmentVo> deptTree = departmentService.findDeptTree(hosCode);
        return Result.ok(deptTree);
    }
}
