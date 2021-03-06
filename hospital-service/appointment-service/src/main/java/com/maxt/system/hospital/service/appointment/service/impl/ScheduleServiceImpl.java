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
            //todo copy??????null??????
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
        //0????????????
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleQueryVo, schedule);
        schedule.setIsDeleted(0);
        //?????????????????????????????????????????????
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
     * ????????????????????????????????????????????????????????????
     * @param page
     * @param limit
     * @param hosCode
     * @param depCode
     * @return
     */
    @Override
    public Map<String, Object> getRuleSchedule(long page, long limit, String hosCode, String depCode) {
        //1. ???????????????????????????????????????
        Criteria criteria = Criteria.where("hosCode").is(hosCode).and("depCode").is(depCode);
        //2. ???????????????workDate????????????
        Aggregation aggregation = Aggregation.newAggregation(
                //????????????
                Aggregation.match(criteria),
                //????????????
                Aggregation.group("workDate")
                        //3. ??????????????????
                        .count().as("docCount")
                        .sum("reservedNumber").as("reservedNumber")
                        .sum("availableNumber").as("availableNumber"),
                //??????
                Aggregation.sort(Sort.Direction.DESC, "workDate"),
                //4. ????????????
                Aggregation.skip((page - 1) * limit),
                Aggregation.limit(limit)
        );

        //???????????????????????????
        AggregationResults<BookingScheduleRuleVo> aggregate =
                mongoTemplate.aggregate(aggregation, Schedule.class, BookingScheduleRuleVo.class);
        List<BookingScheduleRuleVo> bookingScheduleResults = aggregate.getMappedResults();
        //???????????????????????????
        Aggregation totalAgg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("workDate")
        );
        AggregationResults<BookingScheduleRuleVo> totalResults =
                mongoTemplate.aggregate(totalAgg, Schedule.class, BookingScheduleRuleVo.class);
        int total = totalResults.getMappedResults().size();
        //???????????????????????????
        for (BookingScheduleRuleVo bookingScheduleResult : bookingScheduleResults) {
            LocalDateTime workDate = bookingScheduleResult.getWorkDate();
            String dayOfWeek = this.getDayOfWeek(new DateTime(workDate));
            bookingScheduleResult.setDayOfWeek(dayOfWeek);
        }
        //?????????????????????????????????
        Map<String, Object> result = new HashMap<>();
        result.put("bookingScheduleRuleList", bookingScheduleResults);
        result.put("total", total);
        //??????????????????
        String hosName = hospitalService.getByHosCode(hosCode).getHosName();
        //??????????????????
        Map<String, Object> baseMap = new HashMap<>();
        baseMap.put("hosName", hosName);
        result.put("baseMap", baseMap);
        return result;
    }

    /**
     * ?????????????????????????????????????????????????????????????????????
     * @param hosCode
     * @param depCode
     * @param dateTime
     * @return
     */
    @Override
    public List<Schedule> getDetailSchedule(String hosCode, String depCode, String dateTime) {
        //??????????????????mongodb
        List<Schedule> scheduleList =
                scheduleRepository.findScheduleByHosCodeAndDepCodeAndWorkDare(hosCode, depCode, new DateTime(dateTime).toDateTime());
        //???????????????list??????????????????????????????????????????????????????????????????????????????
        scheduleList.stream().forEach(item -> this.packageSchedule(item));
        return scheduleList;
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????
     * @param schedule
     */
    private void packageSchedule(Schedule schedule) {
        //??????????????????
        schedule.getParam().put("hosName", hospitalService.getByHosCode(schedule.getHosCode()).getHosName());
        //??????????????????
        schedule.getParam().put("depName", departmentService.getDepName(schedule.getHosCode(), schedule.getDepCode()));
        //????????????????????????
        schedule.getParam().put("dayOfWeek", this.getDayOfWeek(new DateTime(schedule.getWorkDate())));
    }

    /**
     * ??????????????????????????????
     * @param dateTime
     * @return
     */
    private String getDayOfWeek(DateTime dateTime) {
        String datOfWeek = "";
        switch (dateTime.getDayOfWeek()){
            case DateTimeConstants.SUNDAY:
                datOfWeek = "??????";
                break;
            case DateTimeConstants.MONDAY:
                datOfWeek = "??????";
                break;
            case DateTimeConstants.TUESDAY:
                datOfWeek = "??????";
                break;
            case DateTimeConstants.WEDNESDAY:
                datOfWeek = "??????";
                break;
            case DateTimeConstants.THURSDAY:
                datOfWeek = "??????";
                break;
            case DateTimeConstants.FRIDAY:
                datOfWeek = "??????";
                break;
            case DateTimeConstants.SATURDAY:
                datOfWeek = "??????";
                break;
            default:
                break;
        }
        return datOfWeek;
    }
}
