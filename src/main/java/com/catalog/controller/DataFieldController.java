package com.catalog.controller;

import com.alibaba.fastjson.JSON;
import com.catalog.dto.DataField;
import com.catalog.dto.DataTable;
import com.catalog.dto.SysLog;
import com.catalog.service.DataFieldService;
import com.catalog.service.DataTableService;
import com.catalog.service.SysLogService;
import com.catalog.vo.DataFieldVoRequest;
import com.catalog.vo.DataTableVoRequest;
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
@RequestMapping("/dataField")
public class DataFieldController {

    @Autowired
    private DataFieldService dataFieldService;

    @Autowired
    private SysLogService sysLogService;

    @RequestMapping(value = "/getDataFieldPageList")
    @ApiOperation("分页列表")
    public Response getDataTablePageList(@RequestBody PageRequestVo<DataFieldVoRequest> pageRequestVo) {
        PageQueryResult<DataField> list =dataFieldService.getPageDataField(pageRequestVo);
        return Response.ok().data(list);
    }

    @RequestMapping(value = "/update")
    @ApiOperation("修改")
    public Response update(@RequestBody DataField dataField,@RequestParam("username") String username) {
        Date startTime = new Date();
        dataFieldService.updateDataField(dataField);
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
        sysLog.setRequestParam(JSON.toJSONString(dataField));
        sysLogService.addSysLog(sysLog);
        return Response.ok();
    }
    @PostMapping(value = "/batchupdate")
    @ApiOperation("批量修改")
    public Response batchupdate(@RequestBody List<DataField> dataFields,@RequestParam("username") String username) {
        Date startTime = new Date();
        for(DataField dataField : dataFields){
            dataFieldService.updateDataField(dataField);
        }
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
        sysLog.setRequestParam(JSON.toJSONString(dataFields));
        sysLogService.addSysLog(sysLog);
        return Response.ok();
    }

    @PostMapping(value = "/updateDataFieldDictionary")
    @ApiOperation("批量修改")
    public Response updateDataFieldDictionary(@RequestBody List<DataField> dataFields,@RequestParam("username") String username) {
        Date startTime = new Date();
        for(DataField dataField : dataFields){
            dataFieldService.updateDataFieldDictionary(dataField);
        }
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
        sysLog.setRequestParam(JSON.toJSONString(dataFields));
        sysLogService.addSysLog(sysLog);
        return Response.ok();
    }

    @RequestMapping(value = "/delDataField", method = RequestMethod.GET)
    @ApiOperation("删除")
    public DataResponse delDataField(@RequestParam String id,@RequestParam("username") String username) {
        Date startTime = new Date();
        dataFieldService.delDataField(id);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("dataFile");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("delete");
        sysLog.setOperatorUser(username);
        sysLog.setRequestParam("delete："+id);
        sysLogService.addSysLog(sysLog);
        return DataResponse.ok();
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiOperation("查询详情")
    public Response detail(@RequestParam String id) {
        List<DataField> dataFields = dataFieldService.getById(id);
        List<DataField> list = new ArrayList<>();
        if(dataFields.size()!=0){
            list = dataFieldService.getByTableId(dataFields.get(0).getTableId());
        }
        return Response.ok().data(list);
    }
}