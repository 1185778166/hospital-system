package com.maxt.system.hospital.service.appointment.controller;

import com.maxt.system.hospital.common.common.util.exception.HospitalException;
import com.maxt.system.hospital.common.common.util.result.Result;
import com.maxt.system.hospital.common.common.util.result.ResultCodeEnum;
import com.maxt.system.hospital.common.servicce.util.helper.HttpRequestHelper;
import com.maxt.system.hospital.entity.model.hospital.Department;
import com.maxt.system.hospital.entity.model.hospital.Schedule;
import com.maxt.system.hospital.entity.vo.hospital.DepartmentQueryVo;
import com.maxt.system.hospital.entity.vo.hospital.ScheduleQueryVo;
import com.maxt.system.hospital.service.appointment.service.IDepartmentService;
import com.maxt.system.hospital.service.appointment.service.IHospitalService;
import com.maxt.system.hospital.service.appointment.service.IHospitalSetService;
import com.maxt.system.hospital.service.appointment.service.IScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author Maxt
 * @Date 2022/4/13 12:16
 * @Version 1.0
 * @Description
 */
@Api(tags = "医院管理API接口")
@RestController
@RequestMapping("/api/hospital")
public class HospitalApiController {

    @Autowired
    IHospitalService hospitalService;

    @Autowired
    IHospitalSetService hospitalSetService;

    @Autowired
    IDepartmentService departmentService;

    @Autowired
    IScheduleService scheduleService;

    @ApiOperation(value = "上传医院")
    @RequestMapping("saveHospital")
    public Result saveHospital(HttpServletRequest request){
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //todo 参数校验
        String hosCode = (String)paramMap.get("hosCode");
        verification(paramMap, hosCode);
        //传输过程中"+"转换为了""，因此此处需要转换回来
        String logoDataString = (String)paramMap.get("logoData");
        if (!StringUtils.isEmpty(logoDataString)){
            String logoData = logoDataString.replaceAll("", "+");
            paramMap.put("logoData", logoData);
        }

        hospitalService.save(paramMap);
        return Result.ok();
    }

    @ApiOperation(value = "获取医院信息")
    @PostMapping("hospital/show")
    public Result hospital(HttpServletRequest request){
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //todo 参数校验
        String hasCode = (String)paramMap.get("hasCode");
        verification(paramMap, hasCode);
        return Result.ok(hospitalService.getByHosCode(hasCode));
    }

    @ApiOperation(value = "上传科室")
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request){
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //todo 参数校验
        String hasCode = (String)paramMap.get("hasCode");
        verification(paramMap, hasCode);
        departmentService.save(paramMap);
        return Result.ok();
    }

    @ApiOperation(value = "获取分页列表")
    @PostMapping("department/list")
    public Result department(HttpServletRequest request){
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //todo 参数校验
        String hasCode = (String)paramMap.get("hasCode");
        String depCode = (String)paramMap.get("depCode");
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 10 : Integer.parseInt((String)paramMap.get("limit"));
        verification(paramMap, hasCode, depCode);
        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHosCode(hasCode);
        departmentQueryVo.setDepCode(depCode);
        Page<Department> departments = departmentService.selectPage(page, limit, departmentQueryVo);
        return Result.ok(departments);
    }

    @ApiOperation(value = "删除科室")
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request){
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        String hasCode = (String)paramMap.get("hasCode");
        String depCode = (String)paramMap.get("depCode");
        verification(paramMap, hasCode, depCode);
        departmentService.remove(hasCode, depCode);
        return Result.ok();
    }

    @ApiOperation(value = "上传排班")
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        String hasCode = (String)paramMap.get("hasCode");
        verification(paramMap, hasCode);
        scheduleService.save(paramMap);
        return Result.ok();
    }

    @ApiOperation(value = "获取排班分页列表")
    @RequestMapping("schedule/list")
    public Result schedule(HttpServletRequest request){
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        String hasCode = (String)paramMap.get("hasCode");
        String depCode = (String)paramMap.get("depCode");
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 10 : Integer.parseInt((String)paramMap.get("limit"));
        verification(paramMap, hasCode, depCode);
        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHosCode(hasCode);
        scheduleQueryVo.setDepCode(depCode);
        Page<Schedule> schedules = scheduleService.selectPage(page, limit, scheduleQueryVo);
        return Result.ok(schedules);
    }
    @ApiOperation(value = "删除科室")
    @PostMapping("schedule/remove")
    public Result removeSchedule(HttpServletRequest request){
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        String hasCode = (String)paramMap.get("hasCode");
        String hosScheduleId = (String)paramMap.get("hosScheduleId");
        verification(paramMap, hasCode, hosScheduleId);
        scheduleService.remove(hasCode, hosScheduleId);
        return Result.ok();
    }

    private void verification(Map<String, Object> paramMap, String hosCode, String... param){
        if (StringUtils.isEmpty(hosCode) | StringUtils.isEmpty(param)){
            throw new HospitalException(ResultCodeEnum.PARAM_ERROR);
        }
        //签名校验
        if (!HttpRequestHelper.isSignEquals(paramMap, hospitalSetService.getSignKey(hosCode))){
            throw new HospitalException(ResultCodeEnum.SIGN_ERROR);
        }
    }

}
