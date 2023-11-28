package com.catalog.controller;

import com.alibaba.fastjson.JSON;
import com.catalog.dto.*;
import com.catalog.service.DictService;
import com.catalog.service.SysLogService;
import com.catalog.vo.CategoryVoRequest;
import com.catalog.vo.ClassificationVoRequest;
import com.catalog.vo.DataTableVoRequest;
import com.catalog.vo.PageRequestVo;
import com.definesys.mpaas.common.http.Response;
import com.definesys.mpaas.query.db.PageQueryResult;
import com.definesys.mpaas.swagger.DataResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;
    @Autowired
    private SysLogService sysLogService;

    @RequestMapping(value = "/getCategoryList")
    @ApiOperation("查询表分类")
    public Response getCategoryList() {
        List<Category> list =dictService.getCategory();
        return Response.ok().data(list);
    }

    @RequestMapping(value = "/getCategoryPageList")
    @ApiOperation("查询表分类分页列表")
    public Response getCategoryPageList(@RequestBody PageRequestVo<CategoryVoRequest> pageRequestVo) {
        PageQueryResult<Category> list =dictService.getPageCategory(pageRequestVo);
        return Response.ok().data(list);
    }

    @RequestMapping(value = "/addCategory")
    @ApiOperation("新增Category")
    public Response addCategory(@RequestBody Category category, @RequestParam("username") String username) {
        Date startTime = new Date();
        dictService.addCategory(category);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("category");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("add");
        sysLog.setOperatorUser(username);
        sysLog.setRequestParam(JSON.toJSONString(category));
        sysLogService.addSysLog(sysLog);
        return Response.ok();
    }

    @PostMapping(value = "/updateCategory")
    @ApiOperation("修改category")
    public Response updateCategory(@RequestBody Category category,@RequestParam("username") String username) {
        Date startTime = new Date();
        dictService.updateCategory(category);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("category");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("update");
        sysLog.setOperatorUser(username);
        sysLog.setRequestParam(JSON.toJSONString(category));
        sysLogService.addSysLog(sysLog);
        return Response.ok();
    }

    @RequestMapping(value = "/delCategory", method = RequestMethod.GET)
    @ApiOperation("删除Category")
    public DataResponse delCategory(@RequestParam String id, @RequestParam("username") String username) {
        Date startTime = new Date();
        dictService.delCategory(id);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("category");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("delete");
        sysLog.setRequestParam("delete："+id);
        sysLog.setOperatorUser(username);
        sysLogService.addSysLog(sysLog);
        return DataResponse.ok();
    }

    @RequestMapping(value = "/addClassification")
    @ApiOperation("新增Classification")
    public Response addClassification(@RequestBody Classification classification, @RequestParam("username") String username) {
        Date startTime = new Date();
        dictService.addClassification(classification);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("classification");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("add");
        sysLog.setRequestParam(JSON.toJSONString(classification));
        sysLog.setOperatorUser(username);
        sysLogService.addSysLog(sysLog);
        return Response.ok();
    }

    @PostMapping(value = "/updateClassification")
    @ApiOperation("修改classification")
    public Response updateCategory(@RequestBody Classification classification,@RequestParam("username") String username) {
        Date startTime = new Date();
        dictService.updateClassification(classification);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("classification");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("update");
        sysLog.setRequestParam(JSON.toJSONString(classification));
        sysLog.setOperatorUser(username);
        sysLogService.addSysLog(sysLog);
        return Response.ok();
    }

    @RequestMapping(value = "/delClassification", method = RequestMethod.GET)
    @ApiOperation("删除delClassification")
    public DataResponse delClassification(@RequestParam String id, @RequestParam("username") String username) {
        Date startTime = new Date();
        dictService.delClassification(id);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("classification");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("delete");
        sysLog.setOperatorUser(username);
        sysLog.setRequestParam("delete："+id);
        sysLogService.addSysLog(sysLog);
        return DataResponse.ok();
    }

    @RequestMapping(value = "/getClassificationList")
    @ApiOperation("查询字段分类")
    public Response getClassificationList() {
        List<Classification> list =dictService.getClassification();
        return Response.ok().data(list);
    }

    @RequestMapping(value = "/getClassificationPageList")
    @ApiOperation("查询表分类分页列表")
    public Response getClassificationPageList(@RequestBody PageRequestVo<ClassificationVoRequest> pageRequestVo) {
        PageQueryResult<Classification> list =dictService.getPageClassification(pageRequestVo);
        return Response.ok().data(list);
    }
}