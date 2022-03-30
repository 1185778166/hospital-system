package com.maxt.system.hospital.entity.vo.acl;

import lombok.Data;

/**
 * @Author Maxt
 * @Date 2022/3/30 下午12:47
 * @Version 1.0
 * @Description
 */

@Data
public class AssignVo {

    private Long roleId;

    private Long[] permissionId;
}
