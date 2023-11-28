package com.catalog.mapper;

import com.catalog.dto.DataTable;
import com.catalog.dto.FieldOrigin;
import com.catalog.vo.CategoryNumVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Author:   wangxilu
 * Date:     2022/11/17 16:55
 */
@Mapper
public interface DataTableMapper {

    List<DataTable> selectDataTable(@Param("tableName") String tableName,
                                    @Param("checkFlag") String checkFlag,
                                    @Param("dataBase") String dataBase,
                                    @Param("tableComment") String tableComment,
                                    @Param("tableCommentEng") String tableCommentEng,
                                    @Param("category") String category);

    List<DataTable> selectById(@Param("tableId") String tableId);

    List<String> selectByDataBase(@Param("dataBase") String dataBase);

    Integer findDataSourceCount();

    Integer findProjectCount();

    Integer findTableCount();

    Integer findFieldCount();

    List<String> selectDataBase();

    CategoryNumVo findCountByCategory(@Param("category") String category);

    CategoryNumVo findCategoryEmpty();

    CategoryNumVo findDsEmpty();

}