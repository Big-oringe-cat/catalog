package com.catalog.service;

import com.catalog.dto.DataField;
import com.catalog.vo.*;
import com.definesys.mpaas.query.db.PageQueryResult;

import java.util.List;

/**
 * Author:   wangxilu
 * Date:     2020/8/19 9:46
 */
public interface DataFieldService {

    void addDataField(DataField dataField);

    void updateDataField(DataField dataField);

    void updateDataFieldDictionary(DataField dataField);

    void delDataField(String id);

    List<DataField> getByTableId(String tableId);

    List<DataField> getById(String fieldId);

    PageQueryResult<DataField> getFieldDataByTableName(PageRequestVo<DataTableVoRequest> pageRequestVo);

    PageQueryResult<DataField> getPageDataField(PageRequestVo<DataFieldVoRequest> pageRequestVo);

    CategoryNumVo findDsEmpty();
}