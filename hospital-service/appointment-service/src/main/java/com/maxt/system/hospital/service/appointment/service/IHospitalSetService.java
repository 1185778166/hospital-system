package com.maxt.system.hospital.service.appointment.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maxt.system.hospital.entity.model.hospital.HospitalSet;
import com.maxt.system.hospital.entity.vo.hospital.HospitalQueryVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Maxt
 * @Date 2022/4/6 19:20
 * @Version 1.0
 * @Description
 */
public interface IHospitalSetService {
    /**
     *
     * @return
     */
    List<HospitalSet> listAll();

    /**
     * 通过id删除
     * @param id
     * @return
     */
    boolean removeById(Long id);

    /**
     * 条件查询带分页
     * @param hospitalQueryVo
     * @return
     */
    Page<HospitalSet> selectByProperties(HospitalQueryVo hospitalQueryVo);

    /**
     * 添加医院设置
     * @param hospitalSet
     * @return
     */
    boolean saveHospitalSet(HospitalSet hospitalSet);

    /**
     * 根据id获取医院设置
     * @param id
     * @return
     */
    HospitalSet getById(Long id);

    /**
     * 更新医院设置
     * @param hospitalSet
     * @return
     */
    boolean updateHospitalSet(HospitalSet hospitalSet);

    /**
     * 批量删除医院设置
     * @param idList
     * @return
     */
    boolean removeByIds(List<Long> idList);

    /**
     * 医院设置锁定与解锁
     * @param id
     * @param status
     * @return
     */
    boolean lockOrUnLockHospitalSet(Long id, Integer status);

    /**
     * 发送签名密钥
     * @param id
     * @return
     */
    boolean sendKey(Long id);


    /**
     * 获取签名key
     * @param hosCode
     * @return
     */
    String getSignKey(String hosCode);
}
