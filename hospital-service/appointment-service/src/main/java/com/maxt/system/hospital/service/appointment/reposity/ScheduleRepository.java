package com.maxt.system.hospital.service.appointment.reposity;

import com.maxt.system.hospital.entity.model.hospital.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

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
}
