package com.maxt.system.hospital.service.common.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maxt.system.hospital.entity.model.common.Dict;
import com.maxt.system.hospital.entity.vo.common.DictEeVo;
import com.maxt.system.hospital.service.common.listener.DictListener;
import com.maxt.system.hospital.service.common.mapper.DictMapper;
import com.maxt.system.hospital.service.common.service.IDictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
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

    @Override
    public void exportData(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("数据字典", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");
            List<Dict> dictList = dictMapper.selectList(null);
            List<DictEeVo> dictEeVoList = new ArrayList<>(dictList.size());
            for (Dict dict : dictList) {
                DictEeVo dictEeVo = new DictEeVo();
                BeanUtils.copyProperties(dict, dictEeVo, DictEeVo.class);
                dictEeVoList.add(dictEeVo);
            }
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("数据字典").doWrite(dictEeVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void importDictData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class,
                    new DictListener(dictMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
