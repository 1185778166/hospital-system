package com.maxt.system.hospital.service.appointment.controller;

import com.maxt.system.hospital.common.common.util.result.Result;
import com.maxt.system.hospital.entity.model.hospital.Schedule;
import com.maxt.system.hospital.service.appointment.service.IScheduleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author Maxt
 * @Date 2022/4/20 22:17
 * @Version 1.0
 * @Description
 */
@RestController
@RequestMapping("/admin/hospital/schedule")
public class ScheduleController {

    @Autowired
    IScheduleService scheduleService;

    @ApiOperation(value = "查询排班规则数据")
    @GetMapping("getScheduleRule/{page}/{limit}/{hosCode}/{depCode}")
    public Result getScheduleRule(@PathVariable long page,
                                  @PathVariable long limit,
                                  @PathVariable String hosCode,
                                  @PathVariable String depCode){
        Map<String, Object> ruleSchedule = scheduleService.getRuleSchedule(page, limit, hosCode, depCode);
        return Result.ok(ruleSchedule);
    }

    @ApiOperation("查询排班详细信息")
    @GetMapping("getScheduleDetail/{hosCode}/{depCode}/{workDate}")
    public Result getScheduleDetail(@PathVariable String hosCode,
                                    @PathVariable String depCode,
                                    @PathVariable String workDate){
        List<Schedule> detailSchedule = scheduleService.getDetailSchedule(hosCode, depCode, workDate);
        return Result.ok(detailSchedule);
    }

}
