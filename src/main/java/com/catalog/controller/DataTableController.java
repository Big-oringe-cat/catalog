package com.catalog.controller;

import com.alibaba.fastjson.JSON;
import com.catalog.dto.DataField;
import com.catalog.dto.DataTable;
import com.catalog.dto.SysLog;
import com.catalog.service.DataFieldService;
import com.catalog.service.DataTableService;
import com.catalog.service.SysLogService;
import com.catalog.vo.DataStatisticsVo;
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
@RequestMapping("/dataTable")
public class DataTableController {

    @Autowired
    private DataTableService dataTableService;
    @Autowired
    private DataFieldService dataFieldService;
    @Autowired
    private SysLogService sysLogService;

    @RequestMapping(value = "/getDataTablePageList")
    @ApiOperation("分页列表")
    public Response getDataTablePageList(@RequestBody PageRequestVo<DataTableVoRequest> pageRequestVo) {
        PageQueryResult<DataTable> list =dataTableService.getPageDataTable(pageRequestVo);
        return Response.ok().data(list);
    }

    @RequestMapping(value = "/addDataTable")
    @ApiOperation("新增")
    public Response add(@RequestBody DataTable dataTable,@RequestParam("username") String username) {
        Date startTime = new Date();
        dataTableService.addDataTable(dataTable);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("dataTable");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("add");
        sysLog.setOperatorUser(username);
        sysLog.setRequestParam(JSON.toJSONString(dataTable));
        sysLogService.addSysLog(sysLog);
        return Response.ok();
    }

    @PostMapping(value = "/update")
    @ApiOperation("修改")
    public Response update(@RequestBody DataTable dataTable,@RequestParam("username") String username) {
        Date startTime = new Date();
        dataTableService.updateDataTable(dataTable);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("dataTable");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("update");
        sysLog.setOperatorUser(username);
        sysLog.setRequestParam(JSON.toJSONString(dataTable));
        sysLogService.addSysLog(sysLog);
        return Response.ok();
    }

    @PostMapping(value = "/batchupdate")
    @ApiOperation("批量修改")
    public Response batchupdate(@RequestBody List<DataTable> dataTables,@RequestParam("username") String username) {
        Date startTime = new Date();
        for(DataTable dataTable : dataTables){
            dataTable.setUpdateTime(new Date());
            dataTableService.updateDataTable(dataTable);
        }
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("dataTable");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("update");
        sysLog.setOperatorUser(username);
        sysLog.setRequestParam(JSON.toJSONString(dataTables));
        sysLogService.addSysLog(sysLog);
        return Response.ok();
    }

    @RequestMapping(value = "/delDataTable", method = RequestMethod.GET)
    @ApiOperation("删除")
    public DataResponse delDataTable(@RequestParam String id,@RequestParam("username") String username) {
        Date startTime = new Date();
        dataTableService.delDataTable(id);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("dataTable");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("delete");
        sysLog.setOperatorUser(username);
        sysLog.setRequestParam("delete："+id);
        sysLogService.addSysLog(sysLog);
        return DataResponse.ok();
    }

    @RequestMapping(value = "/getByTableId", method = RequestMethod.POST)
    @ApiOperation("根据Id查询字段")
    public Response getByTableId(@RequestBody PageRequestVo<DataTableVoRequest> pageRequestVo) {
        PageQueryResult<DataField> list = dataFieldService.getFieldDataByTableName(pageRequestVo);
        return Response.ok().data(list);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiOperation("查询详情")
    public Response detail(@RequestParam String id) {
        List<DataTable> dataTable = dataTableService.findById(id);
        return Response.ok().data(dataTable);
    }

    @RequestMapping(value = "/statisticsData", method = RequestMethod.POST)
    @ApiOperation("统计首页数据")
    public Response statisticsData() {
        DataStatisticsVo dataStatisticsVo = dataTableService.statisticsData();
        return Response.ok().data(dataStatisticsVo);
    }

    @RequestMapping(value = "/getDataBase", method = RequestMethod.GET)
    @ApiOperation("获取数据库")
    public Response getDataBase() {
        List<String> dataTable = dataTableService.getDataBase();
        dataTable.remove("");
        return Response.ok().data(dataTable);
    }


}