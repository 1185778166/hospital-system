package com.maxt.system.hospital.service.appointment.reposity;

import com.maxt.system.hospital.entity.model.hospital.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author Maxt
 * @Date 2022/4/13 12:10
 * @Version 1.0
 * @Description
 */
public interface HospitalRepository extends MongoRepository<Hospital, String> {
    /**
     * 根据HosCode获取Hospital
     * @param hosCode
     * @return
     */
    Hospital getHospitalByHosCode(String hosCode);
}
