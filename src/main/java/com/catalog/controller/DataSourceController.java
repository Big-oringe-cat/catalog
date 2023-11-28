package com.catalog.controller;

import com.alibaba.fastjson.JSON;
import com.catalog.dto.Category;
import com.catalog.dto.DataField;
import com.catalog.dto.DataSource;
import com.catalog.dto.SysLog;
import com.catalog.service.DataFieldService;
import com.catalog.service.DataSourceService;
import com.catalog.service.SysLogService;
import com.catalog.vo.DataFieldVoRequest;
import com.catalog.vo.DataSourceVoRequest;
import com.catalog.vo.PageRequestVo;
import com.definesys.mpaas.common.http.Response;
import com.definesys.mpaas.query.db.PageQueryResult;
import com.definesys.mpaas.swagger.DataResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/dataSource")
public class DataSourceController {

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private SysLogService sysLogService;

    @RequestMapping(value = "/getDataSourcePageList")
    @ApiOperation("分页列表")
    public Response getDataSourcePageList(@RequestBody PageRequestVo<DataSourceVoRequest> pageRequestVo) {
        PageQueryResult<DataSource> list =dataSourceService.getPageDataSource(pageRequestVo);
        return Response.ok().data(list);
    }

    @RequestMapping(value = "/addDataSource")
    @ApiOperation("新增DataSource")
    public Response addDataSource(@RequestBody DataSource dataSource, @RequestParam("username") String username) {
        Date startTime = new Date();
        dataSourceService.addDataSource(dataSource);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("dataSource");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("add");
        sysLog.setOperatorUser(username);
        sysLog.setRequestParam(JSON.toJSONString(dataSource));
        sysLogService.addSysLog(sysLog);
        return Response.ok();
    }

    @PostMapping(value = "/updateDataSource")
    @ApiOperation("修改updateDataSource")
    public Response updateDataSource(@RequestBody DataSource dataSource,@RequestParam("username") String username) {
        Date startTime = new Date();
        dataSourceService.updateDataSource(dataSource);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("dataSource");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("update");
        sysLog.setOperatorUser(username);
        sysLog.setRequestParam(JSON.toJSONString(dataSource));
        sysLogService.addSysLog(sysLog);
        return Response.ok();
    }

    @RequestMapping(value = "/delDataSource", method = RequestMethod.GET)
    @ApiOperation("删除DataSource")
    public DataResponse delDataSource(@RequestParam String id, @RequestParam("username") String username) {
        Date startTime = new Date();
        dataSourceService.delDataSource(id);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("dataSource");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("delete");
        sysLog.setOperatorUser(username);
        sysLog.setRequestParam("delete："+id);
        sysLogService.addSysLog(sysLog);
        return DataResponse.ok();
    }
}