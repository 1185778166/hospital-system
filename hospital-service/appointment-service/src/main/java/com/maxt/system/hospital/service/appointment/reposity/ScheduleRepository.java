package com.maxt.system.hospital.service.appointment.reposity;

import com.maxt.system.hospital.entity.model.hospital.Schedule;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Maxt
 * @Date 2022/4/14 21:53
 * @Version 1.0
 * @Description
 */
@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    /**
     * 通过hosCode和hosScheduleId查询Schedule信息
     * @param hosCode
     * @param hosScheduleId
     * @return
     */
    Schedule getScheduleByHosCodeAndHosScheduleId(String hosCode, String hosScheduleId);

    /**
     * 根据医院编号、科室编号和工作日期，查询排班详细信息
     * @param hosCode
     * @param depCode
     * @param dateTime
     * @return
     */
    List<Schedule> findScheduleByHosCodeAndDepCodeAndWorkDare(String hosCode, String depCode, DateTime dateTime);
}
