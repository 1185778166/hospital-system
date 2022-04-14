package com.maxt.system.hospital.service.appointment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.maxt.system.hospital.entity.model.hospital.Department;
import com.maxt.system.hospital.entity.vo.hospital.DepartmentQueryVo;
import com.maxt.system.hospital.service.appointment.reposity.DepartmentRepository;
import com.maxt.system.hospital.service.appointment.service.IDepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

/**
 * @Author Maxt
 * @Date 2022/4/14 21:16
 * @Version 1.0
 * @Description
 */
@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public void save(Map<String, Object> paramMap) {
        Department department = JSONObject.parseObject(JSONObject.toJSONString(paramMap), Department.class);
        Department targetDepartment = departmentRepository.getDepartmentByHosCodeAndDepCode(department.getHosCode(),
                department.getDepCode());
        if (null != targetDepartment){
            //todo 验证copy不为空的值
            BeanUtils.copyProperties(department, targetDepartment, Department.class);
            departmentRepository.save(targetDepartment);
        }else {
            department.setCreateTime(LocalDateTime.now());
            department.setUpdateTime(LocalDateTime.now());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }

    @Override
    public Page<Department> selectPage(Integer page, Integer limit, DepartmentQueryVo departmentQueryVo) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        //0为第一页
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo, department);
        department.setIsDeleted(0);
        //创建匹配器，即如何使用查询条件
                                    //构建对象
        ExampleMatcher matcher = ExampleMatcher.matching()
                //改变默认字符串匹配方式：模糊匹配
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                //改变默认大小写忽略方式：忽略大小写
                .withIgnoreCase(true);
        Example<Department> example = Example.of(department, matcher);
        Page<Department> pages = departmentRepository.findAll(example, pageable);
        return pages;
    }

    @Override
    public void remove(String hosCode, String depCode) {
        Department department = departmentRepository.getDepartmentByHosCodeAndDepCode(hosCode, depCode);
        if (Optional.of(department).isPresent()){
            departmentRepository.deleteById(department.getId());
        }
    }
}
