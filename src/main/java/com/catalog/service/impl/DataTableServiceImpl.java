package com.catalog.service.impl;


import com.catalog.dao.DataFieldDao;
import com.catalog.dao.DataTableDao;
import com.catalog.dao.FieldOriginDao;
import com.catalog.dto.DataField;
import com.catalog.dto.DataTable;
import com.catalog.dto.FieldOrigin;
import com.catalog.mapper.DataFieldMapper;
import com.catalog.mapper.DataTableMapper;
import com.catalog.mapper.FieldOriginMapper;
import com.catalog.service.DataTableService;
import com.catalog.service.FieldOriginService;
import com.catalog.util.PageUtil;
import com.catalog.vo.*;
import com.definesys.mpaas.query.db.PageQueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @Author: wangxilu
 * @Since: 2019/12/12
 */
@Service
public class DataTableServiceImpl implements DataTableService {


    @Autowired
    private DataTableDao dataTableDao;

    @Resource
    private DataTableMapper dataTableMapper;

    @Autowired
    private DataFieldMapper dataFieldMapper;


    @Override
    public void addDataTable(DataTable dataTable) {
        dataTableDao.insertDataTable(dataTable);
    }



    @Override
    public void updateDataTable(DataTable dataTable) {
        dataTableDao.updateDataTable(dataTable);
    }

    @Override
    public void delDataTable(String id) {
        dataTableDao.deleteDataTableById(id);
    }

    @Override
    public PageQueryResult<DataTable> getPageDataTable(PageRequestVo<DataTableVoRequest> pageRequestVo) {
        DataTableVoRequest dataTableVoRequest = (DataTableVoRequest) pageRequestVo.getCondition();
        String tableName = dataTableVoRequest.getTableName();
        String checkFlag = dataTableVoRequest.getChenkFlag();
        String dataBase = dataTableVoRequest.getDataBase();
        String tableComment = dataTableVoRequest.getTableComment();
        String tableCommentEng = dataTableVoRequest.getTableCommentEng();
        String category = dataTableVoRequest.getCategory();
        int pageNum = pageRequestVo.getPageNum();
        int pageSize = pageRequestVo.getPageSize();

        PageHelper.startPage(pageNum,pageSize);
        List<DataTable> orderList = dataTableMapper.selectDataTable(tableName,checkFlag,dataBase,tableComment,tableCommentEng,category);

        return PageUtil.getPageQueryResult(pageRequestVo, new PageInfo<DataTable>(orderList));
    }

    @Override
    public List<DataTableExcelVo> getExcelDataTable() {
        List<DataTableExcelVo> list = new ArrayList();
        List<DataTable> orderList = dataTableMapper.selectDataTable("","","","","","");
        for(DataTable dataTable : orderList){
            DataTableExcelVo dataTableExcelVo = new DataTableExcelVo();
            dataTableExcelVo.setDatabaseName(dataTable.getDatabaseName());
            dataTableExcelVo.setDatasource(dataTable.getDatasource());
            dataTableExcelVo.setSchemaName(dataTable.getSchemaName());
            dataTableExcelVo.setTableComment(dataTable.getTableComment());
            dataTableExcelVo.setTableName(dataTable.getTableName());
            dataTableExcelVo.setTableNameEng(dataTable.getTableCommentEng());
            //根据tableId查询field
            List<DataField> filedList = dataFieldMapper.selectByTableId(dataTable.getId());
            List<DataFieldExcelVo> fieldExcelVoList =  new ArrayList<>();
            for(DataField dataField : filedList){
                DataFieldExcelVo dataFieldExcelVo = new DataFieldExcelVo();
                dataFieldExcelVo.setClassfication(dataField.getClassfication());
                dataFieldExcelVo.setFieldComment(dataField.getFieldComment());
                dataFieldExcelVo.setFieldCommentEng(dataField.getFieldCommentEng());
                dataFieldExcelVo.setFieldName(dataField.getField());
                dataFieldExcelVo.setFieldType(dataField.getFieldType());
                fieldExcelVoList.add(dataFieldExcelVo);
            }
            dataTableExcelVo.setFields(fieldExcelVoList);

            list.add(dataTableExcelVo);
        }
        return list;
    }

    @Override
    public DataStatisticsVo statisticsData() {
        Integer dataSourceNum = dataTableMapper.findDataSourceCount();
        Integer dataBaseNum = dataTableMapper.findProjectCount();
        Integer tableNum = dataTableMapper.findTableCount();
        Integer fieldNum = dataTableMapper.findFieldCount();
        DataStatisticsVo dataStatisticsVo = new DataStatisticsVo();
        dataStatisticsVo.setDataBaseNum(dataBaseNum);
        dataStatisticsVo.setDataSourceNum(dataSourceNum);
        dataStatisticsVo.setFieldNum(fieldNum);
        dataStatisticsVo.setTableNum(tableNum);
        return dataStatisticsVo;
    }

    @Override
    public List<DataTable> findById(String id) {
        return dataTableMapper.selectById(id);
    }

    @Override
    public List<String> getDataBase() {
        return dataTableMapper.selectDataBase();
    }

    @Override
    public CategoryNumVo findCountByCategory(String category) {
        return dataTableMapper.findCountByCategory(category);
    }

    @Override
    public CategoryNumVo findCategoryEmpty() {
        return dataTableMapper.findCategoryEmpty();
    }

    @Override
    public CategoryNumVo findDsEmpty() {
        return dataTableMapper.findDsEmpty();
    }

}
