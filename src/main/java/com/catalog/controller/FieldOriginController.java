package com.catalog.controller;

import com.alibaba.fastjson.JSON;
import com.catalog.dto.FieldOrigin;
import com.catalog.dto.SysLog;
import com.catalog.dto.TableOrigin;
import com.catalog.service.FieldOriginService;
import com.catalog.service.SysLogService;
import com.catalog.service.TableOriginService;
import com.catalog.vo.FieldOriginVoRequest;
import com.catalog.vo.PageRequestVo;
import com.catalog.vo.TableOriginVoRequest;
import com.definesys.mpaas.common.http.Response;
import com.definesys.mpaas.query.db.PageQueryResult;
import com.definesys.mpaas.swagger.DataResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/fieldOrigin")
public class FieldOriginController {
    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private FieldOriginService fieldOriginService;

    @RequestMapping(value = "/getFieldOriginPageList")
    @ApiOperation("分页列表")
    public Response getFieldOriginPageList(@RequestBody PageRequestVo<FieldOriginVoRequest> pageRequestVo) {
        PageQueryResult<FieldOrigin> list =fieldOriginService.getPageFieldOrigin(pageRequestVo);
        return Response.ok().data(list);
    }

    @RequestMapping(value = "/addFieldOrigin")
    @ApiOperation("新增")
    public Response add(@RequestBody FieldOrigin fieldOrigin) {
        fieldOriginService.addFieldOrigin(fieldOrigin);
        return Response.ok();
    }

    @RequestMapping(value = "/update")
    @ApiOperation("修改")
    public Response update(@RequestBody FieldOrigin fieldOrigin) {
        fieldOriginService.updateFieldOrigin(fieldOrigin);
        return Response.ok();
    }

    @RequestMapping(value = "/delFieldOrigin", method = RequestMethod.GET)
    @ApiOperation("删除")
    public DataResponse delFieldOrigin(@RequestParam String id,@RequestParam("username") String username) {
        Date startTime = new Date();
        fieldOriginService.delFieldOrigin(id);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("fieldOrigin");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("delete");
        sysLog.setOperatorUser(username);
        sysLog.setRequestParam("delete："+id);
        sysLogService.addSysLog(sysLog);
        return DataResponse.ok();
    }

}