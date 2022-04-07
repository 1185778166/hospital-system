package com.maxt.system.hospital.service.common.controller;

import com.maxt.system.hospital.common.common.util.result.Result;
import com.maxt.system.hospital.entity.model.common.Dict;
import com.maxt.system.hospital.service.common.service.IDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Maxt
 * @Date 2022/4/7 07:45
 * @Version 1.0
 * @Description
 */
@Api("数据字典接口")
@RestController
@RequestMapping("/admin/common/dict")
public class DictController {

    @Autowired
    private IDictService dictService;

    @ApiOperation("根据数据id查询子数据列表")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id){
        List<Dict> childData = dictService.findChildData(id);
        return Result.ok(childData);
    }

    @ApiOperation("导出")
    @GetMapping(value = "exportData")
    public Result exportData(HttpServletResponse response){
        dictService.exportData(response);
        return Result.ok();
    }

    @ApiOperation("导入")
    @PostMapping("importData")
    public Result importData(MultipartFile file){
        dictService.importDictData(file);
        return Result.ok();
    }
}
