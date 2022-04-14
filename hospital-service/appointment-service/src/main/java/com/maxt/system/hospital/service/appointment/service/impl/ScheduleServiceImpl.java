package com.maxt.system.hospital.service.appointment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.maxt.system.hospital.entity.model.hospital.Schedule;
import com.maxt.system.hospital.entity.vo.hospital.ScheduleQueryVo;
import com.maxt.system.hospital.service.appointment.reposity.ScheduleRepository;
import com.maxt.system.hospital.service.appointment.service.IScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

/**
 * @Author Maxt
 * @Date 2022/4/14 21:54
 * @Version 1.0
 * @Description
 */
@Service
@Slf4j
public class ScheduleServiceImpl implements IScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Override
    public void save(Map<String, Object> paramMap) {
        Schedule schedule = JSONObject.parseObject(JSONObject.toJSONString(paramMap), Schedule.class);
        Schedule targetSchedule = scheduleRepository.getScheduleByHosCodeAndHosScheduleId(schedule.getHosCode(),
                schedule.getHosScheduleId());
        if (null != targetSchedule){
            //todo copy不为null的值
            BeanUtils.copyProperties(schedule, targetSchedule, Schedule.class);
            scheduleRepository.save(targetSchedule);
        }else {
            schedule.setCreateTime(LocalDateTime.now());
            schedule.setUpdateTime(LocalDateTime.now());
            schedule.setIsDeleted(0);
            scheduleRepository.save(schedule);
        }
    }

    @Override
    public Page<Schedule> selectPage(Integer page, Integer limit, ScheduleQueryVo scheduleQueryVo) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        //0为第一页
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleQueryVo, schedule);
        schedule.setIsDeleted(0);
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example<Schedule> example = Example.of(schedule, matcher);
        Page<Schedule> pages = scheduleRepository.findAll(example, pageable);
        return pages;
    }

    @Override
    public void remove(String hosCode, String hosScheduleId) {
        Schedule schedule = scheduleRepository.getScheduleByHosCodeAndHosScheduleId(hosCode, hosScheduleId);
        if (Optional.of(schedule).isPresent()){
            scheduleRepository.deleteById(schedule.getId());
        }
    }
}
