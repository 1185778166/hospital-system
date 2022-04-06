package com.maxt.system.hospital.service.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maxt.system.hospital.entity.model.common.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author Maxt
 * @Date 2022/4/7 07:33
 * @Version 1.0
 * @Description
 */
@Mapper
public interface DictMapper extends BaseMapper<Dict> {
}
