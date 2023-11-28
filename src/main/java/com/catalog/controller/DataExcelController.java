package com.catalog.controller;

import com.catalog.dto.DataField;
import com.catalog.dto.DataTable;
import com.catalog.dto.FieldOrigin;
import com.catalog.dto.SysLog;
import com.catalog.service.DataFieldService;
import com.catalog.service.DataTableService;
import com.catalog.service.FieldOriginService;
import com.catalog.service.SysLogService;
import com.catalog.util.Md5Utils;
import com.catalog.util.MyExcelUtil;
import com.catalog.vo.*;
import com.definesys.mpaas.common.exception.MpaasBusinessException;
import com.definesys.mpaas.common.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/dictDataExcel")
public class DataExcelController {

    @Autowired
    private DataTableService dataTableService;

    @Autowired
    private DataFieldService dataFieldService;

    @Autowired
    private SysLogService sysLogService;

    @PostMapping(value = "/import")
    public Response importTest(@RequestParam("file") MultipartFile file,
                               @RequestParam("username") String username) {


        Date startTime = new Date();

        List<DataTableExcelVo> tableExcelVoList = MyExcelUtil.importExcel(file, 1, 2, DataTableExcelVo.class);

        for(DataTableExcelVo dataTableExcelVo : tableExcelVoList){
            String tableName =dataTableExcelVo.getTableName()!=null?dataTableExcelVo.getTableName().trim():"";
            String schema =dataTableExcelVo.getSchemaName()!=null?dataTableExcelVo.getSchemaName().trim():"";
            String uuid = Md5Utils.generateMD5(dataTableExcelVo.getDatasource()+dataTableExcelVo.getDatabaseName()+dataTableExcelVo.getSchemaName()+dataTableExcelVo.getTableName());
            //根据ID查询数据库数据
            List<DataTable> tableList = dataTableService.findById(uuid);
            if(tableList.size()==0){
                DataTable dataTable = new DataTable();
                dataTable.setId(uuid);
                dataTable.setTableName(tableName);
                dataTable.setTableComment(dataTableExcelVo.getTableComment()!=null?dataTableExcelVo.getTableComment().trim():"");
                dataTable.setTableCommentEng(dataTableExcelVo.getTableNameEng()!=null?dataTableExcelVo.getTableNameEng().trim():"");
                dataTable.setDatasource(dataTableExcelVo.getDatasource()!=null?dataTableExcelVo.getDatasource().trim():"");
                dataTable.setDatabaseName(dataTableExcelVo.getDatabaseName()!=null?dataTableExcelVo.getDatabaseName().trim():"");
                dataTable.setSchemaName(schema);
                dataTable.setCategory(dataTableExcelVo.getCategory()!=null?dataTableExcelVo.getCategory().trim():"");
                dataTable.setUpdateFrequency(dataTableExcelVo.getUpdateFrequency()!=null?dataTableExcelVo.getUpdateFrequency().trim():"");
                dataTable.setUpdateTime(new Date());
                dataTable.setOperatorUser(username);
                dataTableService.addDataTable(dataTable);
                if(dataTableExcelVo.getFields().size()!=0){
                    for(DataFieldExcelVo fieldExcelVo : dataTableExcelVo.getFields()){
                        String id = Md5Utils.generateMD5(dataTable.getId()+fieldExcelVo.getFieldName());
                        //根据ID查询数据字段
                        List<DataField> fieldList = dataFieldService.getById(id);
                        if(fieldList.size()==0){
                            DataField dataField = new DataField();
                            dataField.setId(id);
                            dataField.setFieldComment(fieldExcelVo.getFieldComment()!=null?fieldExcelVo.getFieldComment().trim():"");
                            dataField.setFieldCommentEng(fieldExcelVo.getFieldCommentEng()!=null?fieldExcelVo.getFieldCommentEng().trim():"");
                            dataField.setField(fieldExcelVo.getFieldName()!=null?fieldExcelVo.getFieldName().trim():"");
                            dataField.setFieldType(fieldExcelVo.getFieldType()!=null?fieldExcelVo.getFieldType().trim():"");
                            dataField.setClassfication(fieldExcelVo.getClassfication()!=null?fieldExcelVo.getClassfication().trim():"");
                            dataField.setBusinessTerms(fieldExcelVo.getBusinessTerms()!=null?fieldExcelVo.getBusinessTerms().trim():"");
                            dataField.setSecurityLevel(fieldExcelVo.getSecurityLevel()!=null?fieldExcelVo.getSecurityLevel().trim():"");
                            dataField.setTableId(dataTable.getId().trim());
                            dataField.setUpdateTime(new Date());
                            dataField.setOperatorUser(username);
                            dataFieldService.addDataField(dataField);
                        }
                    }
                }
            }
        }
        Date endTime = new Date();
        Long consunTime = endTime.getTime() - startTime.getTime();
        SysLog sysLog = new SysLog();
        sysLog.setStartTime(startTime);
        sysLog.setConsumTime(consunTime.intValue());
        sysLog.setEndTime(endTime);
        sysLog.setModel("dataTable");
        sysLog.setOperationDate(new Date());
        sysLog.setOperationType("import");
        sysLog.setOperatorUser(username);
        sysLog.setRequestParam("fileName："+file.getName());
        sysLogService.addSysLog(sysLog);
        return Response.ok();
    }

    /**
     * 导出
     * @param response
     */
    @Transactional
    @PostMapping("/export")
    public Response exportExcel(HttpServletResponse response,@RequestParam("username") String username) {
        try {
            Date startTime = new Date();
            List<DataTableExcelVo> tableExcelVoList  = dataTableService.getExcelDataTable();
            MyExcelUtil.exportExcel(tableExcelVoList, "dataTables","dataTables",DataTableExcelVo.class,"dataTables.xls",response);
            Date endTime = new Date();
            Long consunTime = endTime.getTime() - startTime.getTime();
            SysLog sysLog = new SysLog();
            sysLog.setStartTime(startTime);
            sysLog.setConsumTime(consunTime.intValue());
            sysLog.setEndTime(endTime);
            sysLog.setModel("dataTable");
            sysLog.setOperationDate(new Date());
            sysLog.setOperationType("export");
            sysLog.setOperatorUser(username);
            sysLogService.addSysLog(sysLog);
        } catch (Exception e) {
            throw new MpaasBusinessException("导出失败,请核对后重试或联系管理员");
        }
        return Response.ok();
    }

}