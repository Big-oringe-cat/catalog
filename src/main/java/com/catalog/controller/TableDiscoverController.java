package com.catalog.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.catalog.dto.DiscoverTable;

import com.catalog.dto.SysLog;
import com.catalog.service.DiscoverTableService;

import com.catalog.service.SysLogService;
import com.catalog.vo.*;
import com.definesys.mpaas.common.http.Response;
import com.definesys.mpaas.query.db.PageQueryResult;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description: 自动发现table
 * @Author: miaowei
 * @Since: 2023/03/27
 */
@RestController
@RequestMapping("/dataDiscover")
public class TableDiscoverController {

    @Autowired
    private DiscoverTableService discoverTableService;
    @Autowired
    private SysLogService sysLogService;

    @RequestMapping(value = "/getDiscoverTablePageList")
    @ApiOperation("分页列表")
    public Response getDataTablePageList(@RequestBody PageRequestVo<DataTableVoRequest> pageRequestVo) {
        PageQueryResult<DiscoverTable> list =discoverTableService.getPageDiscoverTable(pageRequestVo);
        return Response.ok().data(list);
    }

    @PostMapping(value = "/addDiscoverTable")
    public Response addDiscoverTable(@RequestBody List<DiscoverTableVoRequest> RequestVos,
                                     @RequestParam(value = "username",required = false) String username) {
        Date startTime = new Date();
        int res=discoverTableService.addDiscoverTable(RequestVos,username);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("dataFile");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("update");
        sysLog.setOperatorUser(username);
        sysLog.setRequestParam(JSON.toJSONString(RequestVos));
        sysLogService.addSysLog(sysLog);
        return Response.ok().data(String.format("%d add success", res));
    }


    @PostMapping(value = "/updateSkip")
    public Response updateSkip(@RequestParam("id") String id,
                               @RequestParam("skip") boolean skip,
                               @RequestParam(value = "username",required = false) String username) {
        Date startTime = new Date();
        int res = discoverTableService.updateSkip(id,skip);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("tableOrigin");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("update");
        sysLog.setOperatorUser(username);
        sysLog.setRequestParam("update："+id);
        sysLogService.addSysLog(sysLog);
        return Response.ok().data(String.format("%d update success", res));
    }

    @PostMapping(value = "/batchUpdateSkip")
    public Response batchUpdateSkip(@RequestParam(value = "ids") String[] ids,
                                    @RequestParam("skip") boolean skip,
                                    @RequestParam(value = "username",required = false) String username) {
        Date startTime = new Date();
        int res = discoverTableService.batchUpdateSkip(ids,skip);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("tableOrigin");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("update");
        sysLog.setOperatorUser(username);
        sysLog.setRequestParam("update："+ids);
        sysLogService.addSysLog(sysLog);
        return Response.ok().data(String.format("%d update success", res));
    }
    @RequestMapping(value = "/getDiscoverFieldPageList")
    @ApiOperation("分页列表")
    public Response getDataFieldPageList(@RequestBody PageRequestVo<DiscoverFieldVoRequest> pageRequestVo) {
        PageQueryResult<DiscoverFieldVo> list =discoverTableService.getPageDiscoverField(pageRequestVo);
        return Response.ok().data(list);
    }
}