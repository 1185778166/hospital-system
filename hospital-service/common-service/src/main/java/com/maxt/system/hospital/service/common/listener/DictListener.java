package com.maxt.system.hospital.service.common.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.maxt.system.hospital.entity.model.common.Dict;
import com.maxt.system.hospital.entity.vo.common.DictEeVo;
import com.maxt.system.hospital.service.common.mapper.DictMapper;
import org.springframework.beans.BeanUtils;

/**
 * @Author Maxt
 * @Date 2022/4/7 09:17
 * @Version 1.0
 * @Description
 */
public class DictListener extends AnalysisEventListener<DictEeVo> {

    private DictMapper dictMapper;
    public DictListener(DictMapper dictMapper){
        this.dictMapper = dictMapper;
    }

    /**
     * 一行一行读取
     * @param dictEeVo
     * @param analysisContext
     */
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        //调用方法添加数据库
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo, dict);
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
