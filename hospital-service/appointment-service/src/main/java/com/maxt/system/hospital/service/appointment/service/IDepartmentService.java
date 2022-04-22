package com.maxt.system.hospital.service.appointment.service;

import com.maxt.system.hospital.entity.model.hospital.Department;
import com.maxt.system.hospital.entity.vo.hospital.DepartmentQueryVo;
import com.maxt.system.hospital.entity.vo.hospital.DepartmentVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author Maxt
 * @Date 2022/4/14 21:16
 * @Version 1.0
 * @Description
 */
public interface IDepartmentService {
    /**
     * 上传科室信息
     * @param paramMap
     */
    void save(Map<String, Object> paramMap);


    /**
     * 分页查询
     * @param page  当前页码
     * @param limit 每页记录数
     * @param departmentQueryVo 查询条件
     * @return
     */
    Page<Department> selectPage(Integer page, Integer limit, DepartmentQueryVo departmentQueryVo);

    /**
     * 删除科室
     * @param hosCode
     * @param depCode
     */
    void remove(String hosCode, String depCode);

    /**
     * 根据医院编号，查询医院所有科室列表
     * @param hosCode
     * @return
     */
    List<DepartmentVo> findDeptTree(String hosCode);

    /**
     * 根据科室编号和医院编号，查询科室名称
     * @param hosCode
     * @param depCode
     * @return
     */
    String getDepName(String hosCode, String depCode);
}
