package com.maxt.system.hospital.service.appointment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.maxt.system.hospital.entity.model.hospital.Hospital;
import com.maxt.system.hospital.service.appointment.mapper.HospitalSetMapper;
import com.maxt.system.hospital.service.appointment.reposity.HospitalRepository;
import com.maxt.system.hospital.service.appointment.service.IHospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Author Maxt
 * @Date 2022/4/13 12:13
 * @Version 1.0
 * @Description
 */
@Service
@Slf4j
public class HospitalServiceImpl implements IHospitalService {

    @Autowired
    HospitalRepository hospitalRepository;
    @Autowired
    HospitalSetMapper hospitalSetMapper;

    @Override
    public void save(Map<String, Object> paramMap) {
        //Map转换为Hospital对象时，预约规则bookingRule为一个对象属性，rule为一个数组属性
        //在转换时要重新对应set方法，不然会转换不成功
        Hospital hospital = JSONObject.parseObject(JSONObject.toJSONString(paramMap), Hospital.class);
        //判断是否存在
        Hospital targetHospital = hospitalRepository.getHospitalByHosCode(hospital.getHosCode());
        if (null != targetHospital){
            hospital.setStatus(targetHospital.getStatus());
            hospital.setCreateTime(targetHospital.getCreateTime());
            hospital.setUpdateTime(targetHospital.getUpdateTime());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }else {
            //0未上线；1已上线
            hospital.setStatus(0);
            hospital.setCreateTime(LocalDateTime.now());
            hospital.setUpdateTime(LocalDateTime.now());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }
    }

    @Override
    public Hospital getByHosCode(String hosCode) {
        return hospitalRepository.getHospitalByHosCode(hosCode);
    }
}
