package com.maxt.system.hospital.service.common.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maxt.system.hospital.entity.model.common.Dict;
import com.maxt.system.hospital.entity.vo.common.DictEeVo;
import com.maxt.system.hospital.service.common.listener.DictListener;
import com.maxt.system.hospital.service.common.mapper.DictMapper;
import com.maxt.system.hospital.service.common.service.IDictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /**
     * @Cacheable 将方法的返回结果进行缓存，下次请求时，如果缓存存在，则直接读取缓存数据，
     * 如果缓存不存在，则执行方法，并把返回结果存入缓存中
     * value：缓存名，必填，指定了缓存存放在那块命名空间
     * @CachePut 使用该注解标记的方法，每次都会执行，并将结果存入指定的缓存中，其他方法可以直接从响应
     * 的缓存中读取数据，而不需要再去查询数据库。一般用在新增方法上
     * value：缓存名，必填，指定了缓存存放在那块命名空间
     * @CacheEvit 使用该注解标志的方法，会清空指定的缓存。一般用在更新或者删除方法上
     * value：缓存名，必填，指定了缓存存放在那块命名空间
     * allEntries：是否清空所有缓存，默认为false
     * beforeInvocation：是否在方法执行前就清空，默认为false
     * @param id
     * @return
     */
    @Cacheable(value = "dict",keyGenerator = "keyGenerator")
    @Override
    public List<Dict> findChildData(Long id) {

        Assert.notNull(id, "id不能为空");

        List<Dict> dictList = dictMapper.selectList(new LambdaQueryWrapper<Dict>().eq(!StringUtils.isEmpty(id), Dict::getParentId, id));

        //向list集合每个dict对象中设置hashChildren
        dictList.stream().forEach(dict -> {
            boolean hasChildren = this.isChildren(dict.getId());
            dict.setHasChildren(hasChildren);
        });
        /*for (Dict dict : dictList) {
            Long dictId = dict.getId();
            boolean hasChildren = this.isChildren(dictId);
            dict.setHasChildren(hasChildren);
        }*/
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

    /**
     * allEntries = true 方法调用后清空所有缓存
     * @param file
     */
    @CacheEvict(value = "dict", allEntries = true)
    @Override
    public void importDictData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class,
                    new DictListener(dictMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Cacheable(value = "dict", keyGenerator = "keyGenerator")
    public String getNameByParentDictCodeAndValue(String parentDictCode, String value) {
        //如果value能唯一定位数据字典，parentDictCode可以传空，例如省市区的value值能够唯一确定
        if (StringUtils.isEmpty(parentDictCode)) {
            Dict dict = dictMapper.selectOne(new QueryWrapper<Dict>().eq("value", value));
            if (null != dict) {
                return dict.getName();
            }
        } else {
            Dict parentDict = dictMapper.selectOne(new LambdaQueryWrapper<Dict>().eq(Dict::getDictCode, parentDictCode));
            if (null == parentDict) {
                return "";
            }
            Dict dict = dictMapper.selectOne(new LambdaQueryWrapper<Dict>().eq(Dict::getParentId, parentDict.getParentId())
                    .eq(Dict::getValue, value));
            if (null != dict) {
                return dict.getName();
            }
        }
        return "";
    }

    @Override
    public List<Dict> findByDictCode(String dictCode) {
        Dict dict = dictMapper.selectOne(new LambdaQueryWrapper<Dict>().eq(Dict::getDictCode, dictCode));
        if (!Optional.of(dict).isPresent()){
            return null;
        }
        return this.findChildData(dict.getId());
    }
}
