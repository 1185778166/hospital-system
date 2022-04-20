package com.maxt.system.hospital.service.common.controller;

import com.maxt.system.hospital.common.common.util.result.Result;
import com.maxt.system.hospital.entity.model.common.Dict;
import com.maxt.system.hospital.service.common.service.IDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation("获取数据字典名称")
    @GetMapping("/getName/{parentDictCode}/{value}")
    public Result getName(@ApiParam(name = "parentDictCode", value = "上级编码", required = true) @PathVariable String parentDictCode,
                          @ApiParam(name = "value", value = "值", required = true) @PathVariable String value){
        return Result.ok(dictService.getNameByParentDictCodeAndValue(parentDictCode, value));
    }

    @ApiOperation("获取数据字典名称")
    @ApiImplicitParam(name = "value", value = "值", required = true, dataType = "Long", paramType = "path")
    @GetMapping(value = "/getName/{value}")
    public Result getName(@ApiParam(name = "value", value = "值") @PathVariable(value = "value") String value){
        return Result.ok(dictService.getNameByParentDictCodeAndValue("", value));
    }

    @ApiOperation(value = "根据dictCode获取下级节点")
    @GetMapping("/findByDictCode/{dictCode}")
    public Result<List<Dict>> findByDictCode(@ApiParam(name = "dictCode", value = "节点编码", required = true) @PathVariable String dictCode){
        List<Dict> list = dictService.findByDictCode(dictCode);
        return Result.ok(list);
    }
}
