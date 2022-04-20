package com.maxt.system.hospital.feign.common;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author Maxt
 * @Date 2022/4/20 17:19
 * @Version 1.0
 * @Description
 */
@FeignClient("common-feign")
public interface DictFeignClient {

    /**
     * 获取数据字典名称
     * @param parentDictCode
     * @param value
     * @return
     */
    @GetMapping("/admin/common/dict/getName/{parentDictCode}/{value}")
    String getName(@PathVariable("parentDictCode") String parentDictCode,
                   @PathVariable("value") String value);

    /**
     * 获取数据字典名称
     * @param value
     * @return
     */
    @GetMapping("/admin/common/dict/getName/{value}")
    String getName(@PathVariable("value") String value);
}
