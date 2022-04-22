package com.maxt.system.hospital.service.appointment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.maxt.system.hospital.entity.model.hospital.Schedule;
import com.maxt.system.hospital.entity.vo.hospital.BookingScheduleRuleVo;
import com.maxt.system.hospital.entity.vo.hospital.ScheduleQueryVo;
import com.maxt.system.hospital.service.appointment.reposity.ScheduleRepository;
import com.maxt.system.hospital.service.appointment.service.IDepartmentService;
import com.maxt.system.hospital.service.appointment.service.IHospitalService;
import com.maxt.system.hospital.service.appointment.service.IScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    IHospitalService hospitalService;

    @Autowired
    IDepartmentService departmentService;

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

    /**
     * 根据医院编号和科室编号，查询排班规则数据
     * @param page
     * @param limit
     * @param hosCode
     * @param depCode
     * @return
     */
    @Override
    public Map<String, Object> getRuleSchedule(long page, long limit, String hosCode, String depCode) {
        //1. 根据医院编号和科室编号查询
        Criteria criteria = Criteria.where("hosCode").is(hosCode).and("depCode").is(depCode);
        //2. 根据工作日workDate进行分组
        Aggregation aggregation = Aggregation.newAggregation(
                //匹配条件
                Aggregation.match(criteria),
                //分组字段
                Aggregation.group("workDate")
                        //3. 统计号源数量
                        .count().as("docCount")
                        .sum("reservedNumber").as("reservedNumber")
                        .sum("availableNumber").as("availableNumber"),
                //排序
                Aggregation.sort(Sort.Direction.DESC, "workDate"),
                //4. 实现分页
                Aggregation.skip((page - 1) * limit),
                Aggregation.limit(limit)
        );

        //调用方法，最终执行
        AggregationResults<BookingScheduleRuleVo> aggregate =
                mongoTemplate.aggregate(aggregation, Schedule.class, BookingScheduleRuleVo.class);
        List<BookingScheduleRuleVo> bookingScheduleResults = aggregate.getMappedResults();
        //分组查询的总记录数
        Aggregation totalAgg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("workDate")
        );
        AggregationResults<BookingScheduleRuleVo> totalResults =
                mongoTemplate.aggregate(totalAgg, Schedule.class, BookingScheduleRuleVo.class);
        int total = totalResults.getMappedResults().size();
        //把日期对应星期获取
        for (BookingScheduleRuleVo bookingScheduleResult : bookingScheduleResults) {
            LocalDateTime workDate = bookingScheduleResult.getWorkDate();
            String dayOfWeek = this.getDayOfWeek(new DateTime(workDate));
            bookingScheduleResult.setDayOfWeek(dayOfWeek);
        }
        //设置最终数据，进行返回
        Map<String, Object> result = new HashMap<>();
        result.put("bookingScheduleRuleList", bookingScheduleResults);
        result.put("total", total);
        //获取医院名称
        String hosName = hospitalService.getByHosCode(hosCode).getHosName();
        //其他基础数据
        Map<String, Object> baseMap = new HashMap<>();
        baseMap.put("hosName", hosName);
        result.put("baseMap", baseMap);
        return result;
    }

    /**
     * 根据医院编号、科室编号和工作日期，查询排班信息
     * @param hosCode
     * @param depCode
     * @param dateTime
     * @return
     */
    @Override
    public List<Schedule> getDetailSchedule(String hosCode, String depCode, String dateTime) {
        //根据参数查询mongodb
        List<Schedule> scheduleList =
                scheduleRepository.findScheduleByHosCodeAndDepCodeAndWorkDare(hosCode, depCode, new DateTime(dateTime).toDateTime());
        //遍历得到的list集合，设置其他值：医院名称、科室名称、日期和对应星期
        scheduleList.stream().forEach(item -> this.packageSchedule(item));
        return scheduleList;
    }

    /**
     * 封装排班详情其他值：医院名称、科室名称、日期对应星期
     * @param schedule
     */
    private void packageSchedule(Schedule schedule) {
        //设置医院名称
        schedule.getParam().put("hosName", hospitalService.getByHosCode(schedule.getHosCode()).getHosName());
        //设置科室名称
        schedule.getParam().put("depName", departmentService.getDepName(schedule.getHosCode(), schedule.getDepCode()));
        //设置日期对应星期
        schedule.getParam().put("dayOfWeek", this.getDayOfWeek(new DateTime(schedule.getWorkDate())));
    }

    /**
     * 根据日期获取周几数据
     * @param dateTime
     * @return
     */
    private String getDayOfWeek(DateTime dateTime) {
        String datOfWeek = "";
        switch (dateTime.getDayOfWeek()){
            case DateTimeConstants.SUNDAY:
                datOfWeek = "周日";
                break;
            case DateTimeConstants.MONDAY:
                datOfWeek = "周一";
                break;
            case DateTimeConstants.TUESDAY:
                datOfWeek = "周二";
                break;
            case DateTimeConstants.WEDNESDAY:
                datOfWeek = "周三";
                break;
            case DateTimeConstants.THURSDAY:
                datOfWeek = "周四";
                break;
            case DateTimeConstants.FRIDAY:
                datOfWeek = "周五";
                break;
            case DateTimeConstants.SATURDAY:
                datOfWeek = "周六";
                break;
            default:
                break;
        }
        return datOfWeek;
    }
}
