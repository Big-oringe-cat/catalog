package com.catalog.service;

import com.catalog.dto.TableOrigin;
import com.catalog.vo.PageRequestVo;
import com.catalog.vo.TableOriginTopVo;
import com.catalog.vo.TableOriginVoRequest;
import com.catalog.vo.TableSizeTopVo;
import com.definesys.mpaas.query.db.PageQueryResult;

import java.util.List;

/**
 * Author:   wangxilu
 * Date:     2020/8/19 9:46
 */
public interface TableOriginService {

    void addTableOrigin(TableOrigin tableOrigin);

    void updateTableOrigin(TableOrigin tableOrigin);

    void delTableOrigin(String id);

    List<TableOriginTopVo> selectTableTop10();

    List<TableSizeTopVo> selectTableSizeTop10();

    PageQueryResult<TableOrigin> getPageTableOrigin(PageRequestVo<TableOriginVoRequest> pageRequestVo);

}