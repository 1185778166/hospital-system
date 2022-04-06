package com.maxt.system.hospital.entity.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Maxt
 * @Date 2021/12/21 0021 14:55
 * @Version 1.0
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {

    /**
     * 开始页数
     */
    long pageNo;
    /**
     * 当前页条数
     */
    long pageSize;

    /**
     * 总条数
     */
    long totalSize;

    /**
     * 总页数
     */
    long totalPage;
}
