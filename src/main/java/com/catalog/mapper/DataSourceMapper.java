package com.catalog.mapper;

import com.catalog.dto.DataTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.sql.DataSource;
import java.util.List;


/**
 * Author:   wangxilu
 * Date:     2022/11/17 16:55
 */
@Mapper
public interface DataSourceMapper {

    List<DataSource> selectDataSource(@Param("dataBase") String dataBase);

}