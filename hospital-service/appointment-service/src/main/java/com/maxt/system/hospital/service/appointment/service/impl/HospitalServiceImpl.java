package com.maxt.system.hospital.service.appointment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.maxt.system.hospital.entity.enums.DictEnum;
import com.maxt.system.hospital.entity.model.hospital.Hospital;
import com.maxt.system.hospital.entity.vo.hospital.HospitalQueryVo;
import com.maxt.system.hospital.feign.common.DictFeignClient;
import com.maxt.system.hospital.service.appointment.mapper.HospitalSetMapper;
import com.maxt.system.hospital.service.appointment.reposity.HospitalRepository;
import com.maxt.system.hospital.service.appointment.service.IHospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    @Autowired
    DictFeignClient dictFeignClient;

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

    @Override
    public Page<Hospital> selectPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        //0为第一页
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo, hospital);
        //创建匹配器，即如何使用查询条件
        //构建对象
        ExampleMatcher matcher = ExampleMatcher.matching()
                //改变默认字符串匹配方式：模糊查询
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                //改变默认大小写忽略方式：忽略大小写
                .withIgnoreCase(true);
        Example<Hospital> example = Example.of(hospital, matcher);
        Page<Hospital> pages = hospitalRepository.findAll(example, pageable);
        pages.getContent().stream().forEach(item -> this.packHospital(item));
        return pages;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        if (status.intValue() == 0 || status.intValue() == 1){
            Hospital hospital = hospitalRepository.findById(id).get();
            hospital.setStatus(status);
            hospital.setUpdateTime(LocalDateTime.now());
            hospitalRepository.save(hospital);
        }
    }

    @Override
    public Map<String, Object> show(String id) {
        Map<String, Object> result = new HashMap<>(16);
        Hospital hospital = hospitalRepository.findById(id).get();
        result.put("hospital", hospital);
        //单独处理更直观
        result.put("bookingRule", hospital.getBookingRule());
        //不需要重复返回
        hospital.setBookingRule(null);
        return result;
    }

    private Hospital packHospital(Hospital hospital) {
        String hosTypeString = dictFeignClient.getName(DictEnum.HOSPITAL.getDictCode(), hospital.getHosType());
        String provinceString = dictFeignClient.getName(hospital.getProvinceCode());
        String cityString = dictFeignClient.getName(hospital.getCityCode());
        String districtString = dictFeignClient.getName(hospital.getDistrictCode());
        hospital.getParam().put("hosTypeString", hosTypeString);
        hospital.getParam().put("fullAddress", provinceString + cityString + districtString + hospital.getAddress());
        return hospital;
    }
}
