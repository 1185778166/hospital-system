package com.maxt.system.hospital.service.common.service;

import com.maxt.system.hospital.entity.model.common.Dict;

import java.util.List;

/**
 * @Author Maxt
 * @Date 2022/4/7 07:29
 * @Version 1.0
 * @Description
 */
public interface IDictService {

    /**
     * 根据数据id查询子数据列表
     * @param id
     * @return
     */
    List<Dict> findChildData(Long id);
}
