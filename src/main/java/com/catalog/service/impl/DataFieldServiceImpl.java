package com.catalog.service.impl;


import com.catalog.dao.DataFieldDao;
import com.catalog.dao.DataTableDao;
import com.catalog.dto.DataField;
import com.catalog.dto.DataTable;
import com.catalog.mapper.DataFieldMapper;
import com.catalog.mapper.DataTableMapper;
import com.catalog.service.DataFieldService;
import com.catalog.service.DataTableService;
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

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @Author: wangxilu
 * @Since: 2019/12/12
 */
@Service
public class DataFieldServiceImpl implements DataFieldService {


    @Autowired
    private DataFieldDao dataFieldDao;

    @Resource
    private DataFieldMapper dataFieldMapper;

    @Resource
    private DataTableMapper dataTableMapper;


    @Override
    public void addDataField(DataField dataField) {
        dataFieldDao.insertDataField(dataField);
    }



    @Override
    public void updateDataField(DataField dataField) {
        dataFieldDao.updateDataField(dataField);
    }

    @Override
    public void updateDataFieldDictionary(DataField dataField) {
        dataFieldDao.updateDataFieldDictionary(dataField);
    }

    @Override
    public void delDataField(String id) {
        dataFieldDao.deleteDataFieldById(id);
    }

    @Override
    public List<DataField> getByTableId(String tableId) {
        return dataFieldMapper.selectByTableId(tableId);
    }

    @Override
    public List<DataField> getById(String fieldId) {
        return dataFieldMapper.selectByFieldId(fieldId);
    }

    @Override
    public PageQueryResult<DataField> getPageDataField(PageRequestVo<DataFieldVoRequest> pageRequestVo) {
        DataFieldVoRequest fieldOriginVoRequest = (DataFieldVoRequest) pageRequestVo.getCondition();
        String field = fieldOriginVoRequest.getField();
        String dataBase = fieldOriginVoRequest.getDataBase();
        String checkFlag = fieldOriginVoRequest.getCheckFlag();
        String fieldComment = fieldOriginVoRequest.getFieldComment();
        String fieldCommentEng = fieldOriginVoRequest.getFieldCommentEng();
        int pageNum = pageRequestVo.getPageNum();
        int pageSize = pageRequestVo.getPageSize();

        //根据dataBase查询tableIds
        List<String> list = new ArrayList<>();
        if(dataBase!=null&&"".equals(dataBase)==false){
            list = dataTableMapper.selectByDataBase(dataBase);
        }
        if("".equals(dataBase)==false&&dataBase!=null){
            List<DataField> orderList = new ArrayList<>();
            if(list.size()!=0){
                String[] tableIds = list.toArray(new String[0]);
                PageHelper.startPage(pageNum,pageSize);
                orderList = dataFieldMapper.selectDataFieldByTableIds(tableIds,checkFlag,fieldComment,fieldCommentEng);
            }
            return PageUtil.getPageQueryResult(pageRequestVo, new PageInfo<DataField>(orderList));
        }else{
            PageHelper.startPage(pageNum,pageSize);
            List<DataField> orderList = dataFieldMapper.selectDataField(field,checkFlag,fieldComment,fieldCommentEng);
            return PageUtil.getPageQueryResult(pageRequestVo, new PageInfo<DataField>(orderList));
        }
    }

    @Override
    public CategoryNumVo findDsEmpty() {
        return dataFieldMapper.findDsEmpty();
    }

    @Override
    public PageQueryResult<DataField> getFieldDataByTableName(PageRequestVo<DataTableVoRequest> pageRequestVo) {
        DataTableVoRequest dataTableVoRequest = (DataTableVoRequest) pageRequestVo.getCondition();
        String tableId = dataTableVoRequest.getTableName();
        int pageNum = pageRequestVo.getPageNum();
        int pageSize = pageRequestVo.getPageSize();
        PageHelper.startPage(pageNum,pageSize);
        List<DataField> orderList = dataFieldMapper.selectByTableId(tableId);
        return PageUtil.getPageQueryResult(pageRequestVo, new PageInfo<DataField>(orderList));
    }

}
