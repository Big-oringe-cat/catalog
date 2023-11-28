package com.catalog.service;

import com.catalog.dto.DataTable;
import com.catalog.dto.FieldOrigin;
import com.catalog.vo.*;
import com.definesys.mpaas.query.db.PageQueryResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author:   wangxilu
 * Date:     2020/8/19 9:46
 */
public interface DataTableService {

    void addDataTable(DataTable dataTable);

    void updateDataTable(DataTable dataTable);

    void delDataTable(String id);

    PageQueryResult<DataTable> getPageDataTable(PageRequestVo<DataTableVoRequest> pageRequestVo);

    List<DataTableExcelVo> getExcelDataTable();

    DataStatisticsVo statisticsData();

    List<DataTable> findById(String id);

    List<String> getDataBase();

    CategoryNumVo findCountByCategory(String category);

    CategoryNumVo findCategoryEmpty();

    CategoryNumVo findDsEmpty();

}