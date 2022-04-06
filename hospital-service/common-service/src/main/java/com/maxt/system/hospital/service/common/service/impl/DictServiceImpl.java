package com.maxt.system.hospital.service.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maxt.system.hospital.entity.model.common.Dict;
import com.maxt.system.hospital.service.common.mapper.DictMapper;
import com.maxt.system.hospital.service.common.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author Maxt
 * @Date 2022/4/7 07:31
 * @Version 1.0
 * @Description
 */
@Service
public class DictServiceImpl implements IDictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<Dict> findChildData(Long id) {

        Assert.notNull(id, "id不能为空");

        List<Dict> dictList = dictMapper.selectList(new LambdaQueryWrapper<Dict>().eq(!StringUtils.isEmpty(id), Dict::getParentId, id));

        //向list集合每个dict对象中设置hashChildren
        for (Dict dict : dictList) {
            Long dictId = dict.getId();
            boolean hasChildren = this.isChildren(dictId);
            dict.setHasChildren(hasChildren);
        }
        return dictList;
    }

    /**
     * 判断id下面是否有节点
     * @param id
     * @return
     */
    private boolean isChildren(Long id){

        Integer count = Math.toIntExact(dictMapper.selectCount(new LambdaQueryWrapper<Dict>().eq(Dict::getParentId, id)));

        return count > 0;
    }
}
