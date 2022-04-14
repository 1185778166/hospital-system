package com.maxt.system.hospital.service.appointment.reposity;

import com.maxt.system.hospital.entity.model.hospital.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Maxt
 * @Date 2022/4/14 21:15
 * @Version 1.0
 * @Description
 */
@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {
    /**
     * 通过hosCode和depCode获取Department信息
     * @param hosCode
     * @param depCode
     */
    Department getDepartmentByHosCodeAndDepCode(String hosCode, String depCode);
}
