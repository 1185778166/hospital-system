package com.maxt.system.hospital.service.appointment.service;

import com.maxt.system.hospital.entity.model.hospital.Hospital;
import com.maxt.system.hospital.entity.vo.hospital.HospitalQueryVo;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;

import java.util.List;
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

    /**
     * 分页查询
     * @param page  当前页码
     * @param limit 每页记录数
     * @param hospitalQueryVo 查询条件
     * @return
     */
    Page<Hospital> selectPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    /**
     * 更新线上状态
     * @param id
     * @param status
     */
    void updateStatus(String id, Integer status);

    /**
     * 医院详情
     * @param id
     * @return
     */
    Map<String, Object> show(String id);

    /**
     * 根据医院名称获取医院列表
     * @param hosName
     * @return
     */
    List<Hospital> findByHosName(String hosName);

    /**
     * 医院预约挂号详情
     * @param hosCode
     * @return
     */
    Map<String, Object> item(String hosCode);

}
