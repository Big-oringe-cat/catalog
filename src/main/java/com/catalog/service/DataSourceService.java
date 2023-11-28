package com.catalog.service;

import com.catalog.dto.Category;
import com.catalog.dto.DataSource;
import com.catalog.vo.*;
import com.definesys.mpaas.query.db.PageQueryResult;


/**
 * Author:   wangxilu
 * Date:     2020/8/19 9:46
 */
public interface DataSourceService {

    PageQueryResult<DataSource> getPageDataSource(PageRequestVo<DataSourceVoRequest> pageRequestVo);

    void addDataSource(DataSource dataSource);

    void updateDataSource(DataSource dataSource);

    void delDataSource(String id);


}