package com.catalog.service.impl;


import com.catalog.dao.DataFieldDao;
import com.catalog.dao.DataRelationDao;
import com.catalog.dto.DataField;
import com.catalog.dto.DataRelation;
import com.catalog.mapper.DataFieldMapper;
import com.catalog.mapper.DataRelationMapper;
import com.catalog.service.DataFieldService;
import com.catalog.service.DataRelationService;
import com.catalog.util.ExcelUtil;
import com.catalog.util.Md5Utils;
import com.catalog.util.PageUtil;
import com.catalog.vo.DataFieldVoRequest;
import com.catalog.vo.DataRelationVoRequest;
import com.catalog.vo.PageRequestVo;
import com.definesys.mpaas.query.db.PageQueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @Author: wangxilu
 * @Since: 2019/12/12
 */
@Service
public class DataRelationServiceImpl implements DataRelationService {


    @Autowired
    private DataRelationDao dataRelationDao;

    @Resource
    private DataRelationMapper dataRelationMapper;


    @Override
    public void addDataRelation(DataRelation dataRelation) {
        String srcDatasource = dataRelation.getSrcDatasource()!=null?dataRelation.getSrcDatasource().trim():"";
        String srcDatabase = dataRelation.getSrcDatabase()!=null?dataRelation.getSrcDatabase().trim():"";
        String srcSchema = dataRelation.getSrcSchema()!=null?dataRelation.getSrcSchema().trim():"";
        String srcTable = dataRelation.getSrcTable()!=null?dataRelation.getSrcTable().trim():"";
        String srcField = dataRelation.getSrcField()!=null?dataRelation.getSrcField().trim():"";
        String dstDatasource = dataRelation.getDstDatasource()!=null?dataRelation.getDstDatasource().trim():"";
        String dstDatabase = dataRelation.getDstDatabase()!=null?dataRelation.getDstDatabase().trim():"";
        String dstSchema = dataRelation.getDstSchema()!=null?dataRelation.getDstSchema().trim():"";
        String dstTable = dataRelation.getDstTable()!=null?dataRelation.getDstTable().trim():"";
        String dstField = dataRelation.getDstField()!=null?dataRelation.getDstField().trim():"";
        String id = Md5Utils.generateMD5(srcDatasource+srcDatabase+srcSchema+srcTable+srcField+dstDatasource+dstDatabase
        +dstSchema+dstTable+dstField);
        dataRelation.setId(id);
        dataRelationDao.insertDataRelation(dataRelation);
    }



    @Override
    public void updateDataRelation(DataRelation dataRelation) {
        dataRelationDao.updateDataRelation(dataRelation);
    }

    @Override
    public void delDataRelation(String id) {
        dataRelationDao.deleteDataRelationById(id);
    }

    @Override
    public PageQueryResult<DataRelation> getPageDataRelation(PageRequestVo<DataRelationVoRequest> pageRequestVo) {
        DataRelationVoRequest dataRelationVoRequest = (DataRelationVoRequest) pageRequestVo.getCondition();
        String srcField = dataRelationVoRequest.getSrcField();
        String srcTable = dataRelationVoRequest.getSrcTable();
        String dataBase = dataRelationVoRequest.getDataBase();
        String checkFlag = dataRelationVoRequest.getCheckFlag();
        int pageNum = pageRequestVo.getPageNum();
        int pageSize = pageRequestVo.getPageSize();

        PageHelper.startPage(pageNum,pageSize);
        List<DataRelation> orderList = dataRelationMapper.selectDataRelation(srcTable,checkFlag,dataBase);

        return PageUtil.getPageQueryResult(pageRequestVo, new PageInfo<DataRelation>(orderList));
    }

    @Override
    public void exportExcelOms(PageRequestVo<DataRelationVoRequest> pageRequestVo, HttpServletResponse response) {
        DataRelationVoRequest omsVoRequest = (DataRelationVoRequest) pageRequestVo.getCondition();
        String srcField = omsVoRequest.getSrcField();
        String checkFlag = omsVoRequest.getCheckFlag();
        String dataBase = omsVoRequest.getDataBase();
        String srcTable = omsVoRequest.getSrcTable();

        List<DataRelation> omsList = dataRelationMapper.selectDataRelation(srcTable,checkFlag,dataBase);
        ExcelUtil.exportExcel(response, omsList, DataRelation.class, "数据血缘", null, "数据血缘列表");
    }

}
