package com.catalog.service;

import com.catalog.dto.DataRelation;
import com.catalog.vo.DataRelationVoRequest;
import com.catalog.vo.PageRequestVo;
import com.definesys.mpaas.query.db.PageQueryResult;

import javax.servlet.http.HttpServletResponse;

/**
 * Author:   wangxilu
 * Date:     2020/8/19 9:46
 */
public interface DataRelationService {

    void addDataRelation(DataRelation dataRelation);

    void updateDataRelation(DataRelation dataRelation);

    void delDataRelation(String id);

    PageQueryResult<DataRelation> getPageDataRelation(PageRequestVo<DataRelationVoRequest> pageRequestVo);

    void exportExcelOms(PageRequestVo<DataRelationVoRequest> pageRequestVo, HttpServletResponse response);

}