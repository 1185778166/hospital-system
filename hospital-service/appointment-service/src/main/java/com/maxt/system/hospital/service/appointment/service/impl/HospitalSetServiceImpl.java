package com.maxt.system.hospital.service.appointment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maxt.system.hospital.common.common.util.exception.HospitalException;
import com.maxt.system.hospital.common.common.util.result.ResultCodeEnum;
import com.maxt.system.hospital.common.common.util.util.MD5Utils;
import com.maxt.system.hospital.entity.model.hospital.HospitalSet;
import com.maxt.system.hospital.entity.vo.hospital.HospitalQueryVo;
import com.maxt.system.hospital.service.appointment.mapper.HospitalSetMapper;
import com.maxt.system.hospital.service.appointment.service.IHospitalSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Random;

/**
 * @Author Maxt
 * @Date 2022/4/6 19:21
 * @Version 1.0
 * @Description
 */
@Service
@Slf4j
public class HospitalSetServiceImpl implements IHospitalSetService {

    @Autowired
    private HospitalSetMapper hospitalSetMapper;

    @Override
    public List<HospitalSet> listAll() {
        return hospitalSetMapper.selectList(null);
    }

    @Override
    public boolean removeById(Long id) {
        Assert.notNull(id, "id不能为空");
        int count = hospitalSetMapper.deleteById(id);
        return result(count, "删除失败");
    }

    @Override
    public Page<HospitalSet> selectByProperties(HospitalQueryVo hospitalQueryVo) {
        Page<HospitalSet> page = new Page<>(hospitalQueryVo.getPageNo(), hospitalQueryVo.getPageSize());
        LambdaQueryWrapper<HospitalSet> queryWrapper = new LambdaQueryWrapper<HospitalSet>()
                .eq(!StringUtils.isEmpty(hospitalQueryVo.getHosName()), HospitalSet::getHosName, hospitalQueryVo.getHosName())
                .eq(!StringUtils.isEmpty(hospitalQueryVo.getHosCode()), HospitalSet::getHosCode, hospitalQueryVo.getHosCode());
        Page<HospitalSet> hospitalSetPage = hospitalSetMapper.selectPage(page, queryWrapper);
        return hospitalSetPage;
    }

    @Override
    public boolean saveHospitalSet(HospitalSet hospitalSet) {
        //设置状态，1可用，0不可类
        //todo 将状态封装为枚举累
        hospitalSet.setStatus(1);
        //签名密钥
        hospitalSet.setSignKey(MD5Utils.encrypt(String.valueOf(System.currentTimeMillis() + new Random().nextInt(1000))));
        int count = hospitalSetMapper.insert(hospitalSet);
        return result(count, "插入失败");
    }

    @Override
    public HospitalSet getById(Long id) {
        Assert.notNull(id, "id不能为空");
        return hospitalSetMapper.selectById(id);
    }

    @Override
    public boolean updateHospitalSet(HospitalSet hospitalSet) {
        Assert.notNull(hospitalSet.getId(), "id不能为空");
        int count = hospitalSetMapper.updateById(hospitalSet);
        return result(count, "更新失败");
    }

    @Override
    public boolean removeByIds(List<Long> idList) {
        int count = hospitalSetMapper.deleteBatchIds(idList);
        return result(count, "删除失败");
    }

    @Override
    public boolean lockOrUnLockHospitalSet(Long id, Integer status) {
        Assert.notNull(id, "id不能为空");
        Assert.notNull(status, "status不能为空");
        //1. 根据id查询出医院设置信息
        HospitalSet hospitalSet = hospitalSetMapper.selectById(id);
        //2. 设置状态
        hospitalSet.setStatus(status);
        //3. 更新信息
        int count = hospitalSetMapper.updateById(hospitalSet);

        return result(count, "更新失败");
    }

    @Override
    public boolean sendKey(Long id) {
        Assert.notNull(id, "id不能为空");
        HospitalSet hospitalSet = hospitalSetMapper.selectById(id);
        String signKey = hospitalSet.getSignKey();
        String hosCode = hospitalSet.getHosCode();
        //todo 发送短信
        return true;
    }

    @Override
    public String getSignKey(String hosCode) {
        HospitalSet hospitalSet = this.getByHosCode(hosCode);
        if (null == hospitalSet){
            throw new HospitalException(ResultCodeEnum.HOSPITAL_OPEN);
        }
        if (hospitalSet.getStatus().intValue() == 0){
            throw new HospitalException(ResultCodeEnum.HOSPITAL_LOCK);
        }
        return hospitalSet.getSignKey();
    }

    private HospitalSet getByHosCode(String hosCode) {
        return hospitalSetMapper.selectOne(new LambdaQueryWrapper<HospitalSet>().eq(HospitalSet::getHosCode, hosCode));
    }

    private boolean result(long count, String message){
        if (count <= 0){
            throw new RuntimeException(message);
        }else {
            return true;
        }
    }
}
