package com.catalog.service.impl;


import com.catalog.dao.DataRelationDao;
import com.catalog.dao.DataSourceDao;
import com.catalog.dao.DataTableDao;
import com.catalog.dto.DataField;
import com.catalog.dto.DataSource;
import com.catalog.dto.DataTable;
import com.catalog.mapper.DataFieldMapper;
import com.catalog.mapper.DataSourceMapper;
import com.catalog.mapper.DataTableMapper;
import com.catalog.service.DataSourceService;
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
import java.util.UUID;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @Author: wangxilu
 * @Since: 2019/12/12
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {

    @Resource
    private DataSourceMapper dataSourceMapper;

    @Autowired
    private DataSourceDao dataSourceDao;

    @Override
    public PageQueryResult<DataSource> getPageDataSource(PageRequestVo<DataSourceVoRequest> pageRequestVo) {
        DataSourceVoRequest dataTableVoRequest = (DataSourceVoRequest) pageRequestVo.getCondition();
        String dataBase = dataTableVoRequest.getDataBase();
        int pageNum = pageRequestVo.getPageNum();
        int pageSize = pageRequestVo.getPageSize();

        PageHelper.startPage(pageNum,pageSize);
        List<javax.sql.DataSource> orderList = dataSourceMapper.selectDataSource(dataBase);

        return PageUtil.getPageQueryResult(pageRequestVo, new PageInfo<javax.sql.DataSource>(orderList));
    }

    @Override
    public void addDataSource(DataSource dataSource) {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        dataSource.setId(uuid);
        dataSourceDao.insertDataSource(dataSource);
    }

    @Override
    public void updateDataSource(DataSource dataSource) {
        dataSourceDao.updateDataSource(dataSource);
    }

    @Override
    public void delDataSource(String id) {
        dataSourceDao.deleteDtaSourceById(id);
    }

}
