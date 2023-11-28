package com.catalog.service;

import com.catalog.dto.DiscoverTable;
import com.catalog.vo.*;
import com.definesys.mpaas.query.db.PageQueryResult;

import java.util.List;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @Author: miaowei
 * @Since: 2023/03/27
 */
public interface DiscoverTableService {


    PageQueryResult<DiscoverTable> getPageDiscoverTable(PageRequestVo<DataTableVoRequest> pageRequestVo);

    int addDiscoverTable(List<DiscoverTableVoRequest> RequestVos,String username);
//    int addDiscoverField(List<DiscoverTableVoRequest> RequestVos,String username);

    int updateSkip(String id,boolean skip);
    int batchUpdateSkip(String[] ids,boolean skip);

    PageQueryResult<DiscoverFieldVo> getPageDiscoverField(PageRequestVo<DiscoverFieldVoRequest> pageRequestVo);
}