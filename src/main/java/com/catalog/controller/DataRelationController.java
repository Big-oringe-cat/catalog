package com.catalog.controller;

import com.catalog.dto.DataField;
import com.catalog.dto.DataRelation;
import com.catalog.dto.DataTable;
import com.catalog.dto.SysLog;
import com.catalog.service.DataFieldService;
import com.catalog.service.DataRelationService;
import com.catalog.service.DataTableService;
import com.catalog.service.SysLogService;
import com.catalog.util.ExcelUtil;
import com.catalog.util.MyExcelUtil;
import com.catalog.vo.*;
import com.definesys.mpaas.common.exception.MpaasBusinessException;
import com.definesys.mpaas.common.http.Response;
import com.definesys.mpaas.query.db.PageQueryResult;
import com.definesys.mpaas.swagger.DataResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/dataRelation")
public class DataRelationController {
    @Autowired
    private DataRelationService dataRelationService;
    @Autowired
    private SysLogService sysLogService;

    @PostMapping(value = "/import")
    public Response importTest( @RequestParam("file") MultipartFile file,
                                @RequestParam("username") String username) {
        Date startTime = new Date();
        List<DataRelation> tableExcelVoList = ExcelUtil.importExcel(file,DataRelation.class,0,1);
        for(DataRelation dataRelation : tableExcelVoList){
            dataRelation.setUpdateTime(new Date());
            dataRelation.setOperatorUser(username);
            dataRelationService.addDataRelation(dataRelation);
        }

        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("dataRelation");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("import");
        sysLog.setOperatorUser(username);
        sysLog.setRequestParam("fileName："+file.getName());
        sysLogService.addSysLog(sysLog);
        return Response.ok();
    }

    /**
     * 导出
     * @param pageRequestVo
     * @param response
     */
    @Transactional
    @PostMapping("/export")
    public Response exportExcel(@RequestBody PageRequestVo<DataRelationVoRequest> pageRequestVo, HttpServletResponse response,@RequestParam("username") String username) {
        try {
            Date startTime = new Date();

            dataRelationService.exportExcelOms(pageRequestVo, response);

            Date endTime = new Date();
            Long consunTime = endTime.getTime() - startTime.getTime();
            SysLog sysLog = new SysLog();
            sysLog.setStartTime(startTime);
            sysLog.setConsumTime(consunTime.intValue());
            sysLog.setEndTime(endTime);
            sysLog.setModel("dataRelation");
            sysLog.setOperationDate(new Date());
            sysLog.setOperationType("export");
            sysLog.setOperatorUser(username);
            sysLogService.addSysLog(sysLog);
        } catch (Exception e) {
            throw new MpaasBusinessException("导出失败,请核对后重试或联系管理员");
        }
        return Response.ok();
    }

    @RequestMapping(value = "/getDataRelationPageList")
    @ApiOperation("分页列表")
    public Response getDataRelationPageList(@RequestBody PageRequestVo<DataRelationVoRequest> pageRequestVo) {
        PageQueryResult<DataRelation> list =dataRelationService.getPageDataRelation(pageRequestVo);
        return Response.ok().data(list);
    }

    @RequestMapping(value = "/addDataRelation")
    @ApiOperation("新增")
    public Response add(@RequestBody DataRelation dataRelation,@RequestParam("username") String username) {
        Date startTime = new Date();
        dataRelationService.addDataRelation(dataRelation);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("dataRelation");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("add");
        sysLog.setOperatorUser(username);
        sysLogService.addSysLog(sysLog);
        return Response.ok();
    }

    @RequestMapping(value = "/update")
    @ApiOperation("修改")
    public Response update(@RequestBody DataRelation dataRelation,@RequestParam("username") String username) {
        Date startTime = new Date();
        dataRelationService.updateDataRelation(dataRelation);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("dataRelation");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("update");
        sysLog.setOperatorUser(username);
        sysLogService.addSysLog(sysLog);
        return Response.ok();
    }

    @PostMapping(value = "/batchupdate")
    @ApiOperation("批量修改")
    public Response batchupdate(@RequestBody List<DataRelation> dataRelations,@RequestParam("username") String username) {
        Date startTime = new Date();
        for(DataRelation dataRelation : dataRelations){
            dataRelationService.updateDataRelation(dataRelation);
        }
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("dataRelation");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("update");
        sysLog.setOperatorUser(username);
        sysLogService.addSysLog(sysLog);
        return Response.ok();
    }

    @RequestMapping(value = "/delDataRelation", method = RequestMethod.GET)
    @ApiOperation("删除")
    public DataResponse delDataTable(@RequestParam String id,@RequestParam("username") String username) {
        Date startTime = new Date();
        dataRelationService.delDataRelation(id);
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("dataRelation");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("delete");
        sysLog.setOperatorUser(username);
        sysLogService.addSysLog(sysLog);
        return DataResponse.ok();
    }

}