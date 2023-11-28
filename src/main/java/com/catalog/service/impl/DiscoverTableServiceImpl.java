package com.catalog.service.impl;


import com.catalog.dao.*;
import com.catalog.dto.*;
import com.catalog.mapper.DiscoverTableMapper;
import com.catalog.service.DiscoverTableService;
import com.catalog.util.Md5Utils;
import com.catalog.util.PageUtil;
import com.catalog.vo.*;
import com.definesys.mpaas.query.db.PageQueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @Author: miaowei
 * @Since: 2023/03/27
 */
@Service
public class DiscoverTableServiceImpl implements DiscoverTableService {

    @Autowired
    private DiscoverTableMapper discoverTableMapper;
    @Autowired
    private DataTableDao dataTableDao;
    @Autowired
    private DataFieldDao dataFieldDao;
    @Autowired
    private TableCheckDao tableCheckDao;
    @Autowired
    private FieldCheckDao fieldCheckDao;
    @Autowired
    private TableOrigin2Dao tableOrigin2Dao;

    @Override
    public PageQueryResult<DiscoverTable> getPageDiscoverTable(PageRequestVo<DataTableVoRequest> pageRequestVo) {
        DataTableVoRequest dataTableVoRequest = (DataTableVoRequest) pageRequestVo.getCondition();
        String tableName = dataTableVoRequest.getTableName();
        String dataBase = dataTableVoRequest.getDataBase();
        int pageNum = pageRequestVo.getPageNum();
        int pageSize = pageRequestVo.getPageSize();

        PageHelper.startPage(pageNum,pageSize);
        List<DiscoverTable> orderList = discoverTableMapper.selectDiscoverTable(tableName,dataBase);

        return PageUtil.getPageQueryResult(pageRequestVo, new PageInfo<DiscoverTable>(orderList));
    }


    @Override
    public int addDiscoverTable(List<DiscoverTableVoRequest> RequestVos,String username){
        int count=0;
        Date date = new Date();
        for (DiscoverTableVoRequest RequestVo:RequestVos){
            TableOrigin2 tableOrigin = discoverTableMapper.selectOneTableOrigin(RequestVo.getId());
            // 单条插入DataTable表
            String uuid = Md5Utils.generateMD5(
                    tableOrigin.getDatasource()+
                            tableOrigin.getDatabaseName()+
                            tableOrigin.getSchemaName()+
                            tableOrigin.getTableName());
            DataTable dataTable = new DataTable();
            dataTable.setId(uuid);
            dataTable.setTableName(tableOrigin.getTableName());
            dataTable.setTableComment(RequestVo.getTableComment());
            dataTable.setTableCommentEng(RequestVo.getTableCommentEng());
            dataTable.setDatasource(tableOrigin.getDatasource());
            dataTable.setDatabaseName(tableOrigin.getDatabaseName());
            dataTable.setSchemaName(tableOrigin.getSchemaName());
            dataTable.setCategory(RequestVo.getCategory());
            dataTable.setUpdateFrequency(RequestVo.getUpdateFrequency());
            dataTable.setUpdateTime(date);
            dataTable.setOperatorUser(username);
            int deleteDataTableRes = dataTableDao.deleteDataTableById(uuid);
            dataTableDao.insertDataTable(dataTable);
            // check
            TableCheck tableCheck = new TableCheck();
            tableCheck.setOid(tableOrigin.getId());
            tableCheck.setImportId(uuid);
            tableCheck.setDatasource(tableOrigin.getDatasource());
            tableCheck.setDatabaseName(tableOrigin.getDatabaseName());
            tableCheck.setSchemaName(tableOrigin.getSchemaName());
            tableCheck.setTablename(tableOrigin.getTableName());
            tableCheck.setBusinessTerm(tableOrigin.getBusiness_term());
            tableCheck.setFrequency(tableOrigin.getFrequency());
            tableCheck.setRows(tableOrigin.getRows());
            tableCheck.setTableComment(RequestVo.getTableComment());
            tableCheck.setTableCommentEng(RequestVo.getTableCommentEng());
            tableCheck.setOriginFlag("0");
            tableCheck.setType(tableOrigin.getType());
            tableCheck.setUpdateTime(date.toString());
            tableCheck.setModifyTime(tableOrigin.getUpdate_time());
            tableCheck.setSize(tableOrigin.getSize());
            tableCheck.setCategory(RequestVo.getCategory());
            tableCheck.setUpdateFrequency(RequestVo.getUpdateFrequency());
            tableCheck.setOperator(username);
            int deleteCheckRes = tableCheckDao.deleteTableCheckByOid(tableOrigin.getId());
            tableCheckDao.insertTableCheck(tableCheck);
            List<DiscoverFieldVo> fieldVoList = discoverTableMapper.selectDiscoverField(uuid,null);
            for (DiscoverFieldVo fieldVo: fieldVoList){
                String field_uuid = Md5Utils.generateMD5(
                        tableOrigin.getDatasource()+
                                tableOrigin.getDatabaseName()+
                                tableOrigin.getSchemaName()+
                                tableOrigin.getTableName()+
                                fieldVo.getField()
                        );
                DataField dataField = new DataField();
                dataField.setId(field_uuid);
                dataField.setTableId(uuid);
                dataField.setField(fieldVo.getField());
                dataField.setFieldType(fieldVo.getFieldType());
                dataField.setUpdateTime(date);
                dataField.setBusinessTerms(fieldVo.getBusinessTerm());
                dataField.setOperatorUser(username);
                dataFieldDao.deleteDataFieldById(field_uuid);
                dataFieldDao.insertDataField(dataField);
                FieldCheck fieldCheck = new FieldCheck();
                fieldCheck.setField(fieldVo.getField());
                fieldCheck.setFieldType(fieldVo.getFieldType());
                fieldCheck.setFieldLength(fieldVo.getFieldLength());
                fieldCheck.setFieldDemo(fieldVo.getFieldDemo());
                fieldCheck.setFieldDemo2(fieldVo.getFieldDemo2());
                fieldCheck.setFieldDemo3(fieldVo.getFieldDemo3());
                fieldCheck.setBusinessTerm(fieldVo.getBusinessTerm());
                fieldCheck.setTid(tableOrigin.getId());
                fieldCheck.setImportTid(uuid);
                fieldCheck.setFid(fieldVo.getId());
                fieldCheck.setImportFid(field_uuid);
                fieldCheck.setDatasource(tableOrigin.getDatasource());
                fieldCheck.setDatabaseName(tableOrigin.getDatabaseName());
                fieldCheck.setSchemaName(tableOrigin.getSchemaName());
                fieldCheck.setTablename(tableOrigin.getTableName());
                fieldCheck.setImportType(fieldVo.getFieldType());
                fieldCheck.setOriginFlag("0");
                fieldCheck.setUpdateTime(date.toString());
                fieldCheck.setModifyTime(fieldVo.getUpdateTime());
                fieldCheckDao.deleteFieldCheckByfid(fieldVo.getId());
                fieldCheckDao.insertFieldCheck(fieldCheck);
            }
            count+=1;
        }
        return count;
    }




    @Override
    public int updateSkip(String id,boolean skip){
        int updateRes = tableOrigin2Dao.updateTableOriginSkip(id, skip);
        return updateRes;
    }

    @Override
    public int batchUpdateSkip(String[] ids,boolean skip){
        int updateRes = tableOrigin2Dao.batchUpdateTableOriginSkip(ids, skip);
        return updateRes;
    }

    @Override
    public PageQueryResult<DiscoverFieldVo> getPageDiscoverField(PageRequestVo<DiscoverFieldVoRequest> pageRequestVo){
        DiscoverFieldVoRequest discoverFieldVoRequest = (DiscoverFieldVoRequest) pageRequestVo.getCondition();
        String tid = discoverFieldVoRequest.getTid();
        String field = discoverFieldVoRequest.getField();
        int pageNum = pageRequestVo.getPageNum();
        int pageSize = pageRequestVo.getPageSize();

        PageHelper.startPage(pageNum,pageSize);
        List<DiscoverFieldVo> orderList = discoverTableMapper.selectDiscoverField(tid,field);

        return PageUtil.getPageQueryResult(pageRequestVo, new PageInfo<DiscoverFieldVo>(orderList));
    }
}
