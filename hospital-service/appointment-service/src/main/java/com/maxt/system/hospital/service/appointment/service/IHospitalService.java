package com.maxt.system.hospital.service.appointment.service;

import com.maxt.system.hospital.entity.model.hospital.Hospital;

import java.util.Map;

/**
 * @Author Maxt
 * @Date 2022/4/13 12:12
 * @Version 1.0
 * @Description
 */
public interface IHospitalService {

    /**
     * 上传医院信息
     * @param paramMap
     */
    void save(Map<String, Object> paramMap);

    /**
     * 查询医院
     * @param hosCode
     * @return
     */
    Hospital getByHosCode(String hosCode);
}
