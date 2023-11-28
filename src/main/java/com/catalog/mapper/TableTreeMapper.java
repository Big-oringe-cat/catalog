package com.catalog.mapper;

import com.catalog.dto.TableColumn;
import com.catalog.dto.TableTree;
import com.catalog.vo.DataBaseTreeVo;
import com.catalog.vo.DataSourceTreeVo;
import com.catalog.vo.DataTableTreeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Author:   wangxilu
 * Date:     2022/11/17 16:55
 */
@Mapper
public interface TableTreeMapper {

    List<TableTree> selectTableTree();

    List<DataSourceTreeVo> selectAllDataSource();

    List<DataBaseTreeVo> selectDataBase(@Param("datasource") String datasource);

    List<DataTableTreeVo> selectDataTable(@Param("databaseName") String databaseName);

    List<TableColumn> selectByTableName(@Param("tableName") String tableName);

}