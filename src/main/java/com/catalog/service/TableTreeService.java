package com.catalog.service;

import com.catalog.dto.TableColumn;
import com.catalog.dto.TableTree;
import com.catalog.vo.*;
import com.definesys.mpaas.query.db.PageQueryResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author:   wangxilu
 * Date:     2020/8/19 9:46
 */
public interface TableTreeService {

    List<TableTree> selectTableTree();

    List<DataSourceTreeVo> selectAllDataSource();

    List<DataBaseTreeVo> selectDataBase(@Param("datasource") String datasource);

    List<DataTableTreeVo> selectDataTable(@Param("databaseName") String databaseName);

    PageQueryResult<TableColumn> selectByTableName(PageRequestVo<TableColumnVoRequest> pageRequestVo);

}