package com.catalog.service;

import com.catalog.dto.FieldOrigin;
import com.catalog.vo.FieldOriginVoRequest;
import com.catalog.vo.PageRequestVo;
import com.definesys.mpaas.query.db.PageQueryResult;

/**
 * Author:   wangxilu
 * Date:     2020/8/19 9:46
 */
public interface FieldOriginService {

    void addFieldOrigin(FieldOrigin fieldOrigin);

    void updateFieldOrigin(FieldOrigin fieldOrigin);

    void delFieldOrigin(String id);

    PageQueryResult<FieldOrigin> getPageFieldOrigin(PageRequestVo<FieldOriginVoRequest> pageRequestVo);

}