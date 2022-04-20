package com.maxt.system.hospital.service.common.service;

import com.maxt.system.hospital.entity.model.common.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    /**
     * 导出
     * @param response
     */
    void exportData(HttpServletResponse response);

    /**
     * 导入
     * @param file
     */
    void importDictData(MultipartFile file);

    /**
     * 根据上级编号与值获取数据字典名称
     * @param parentDictCode
     * @param value
     * @return
     */
    String getNameByParentDictCodeAndValue(String parentDictCode, String value);

    /**
     * 根据dictCode获取下级节点
     * @param dictCode
     * @return
     */
    List<Dict> findByDictCode(String dictCode);
}
