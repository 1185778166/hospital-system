package com.maxt.system.hospital.service.appointment.service;

import com.maxt.system.hospital.entity.model.hospital.Schedule;
import com.maxt.system.hospital.entity.vo.hospital.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Maxt
 * @Date 2022/4/14 21:54
 * @Version 1.0
 * @Description
 */
public interface IScheduleService {

    /**
     * 上传排班信息
     * @param paramMap
     */
    void save(Map<String, Object> paramMap);

    /**
     * 分页查询
     * @param page 当前页码
     * @param limit 每页记录数
     * @param scheduleQueryVo 查询条件
     * @return
     */
    Page<Schedule> selectPage(Integer page, Integer limit, ScheduleQueryVo scheduleQueryVo);

    /**
     * 删除排班信息
     * @param hosCode
     * @param hosScheduleId
     */
    void remove(String hosCode, String hosScheduleId);

    /**
     * 根据医院编号和科室编号，查询排班规则数据
     * @param page
     * @param limit
     * @param hosCode
     * @param depCode
     * @return
     */
    Map<String, Object> getRuleSchedule(long page, long limit, String hosCode, String depCode);

    /**
     * 根据医院编号、科室编号和工作日期，查询排班详细信息
     * @param hosCode
     * @param depCode
     * @param dateTime
     * @return
     */
    List<Schedule> getDetailSchedule(String hosCode, String depCode, String dateTime);
}
