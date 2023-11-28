package com.catalog.mapper;

import com.catalog.dto.DiscoverTable;
import com.catalog.dto.TableOrigin2;
import com.catalog.vo.DiscoverFieldVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @Author: miaowei
 * @Since: 2023/03/27
 */
@Mapper
public interface DiscoverTableMapper {

    List<DiscoverTable> selectDiscoverTable(@Param("tableName") String tableName,
                                            @Param("dataBase") String dataBase);

    TableOrigin2 selectOneTableOrigin(@Param("id") String id);

    List<DiscoverFieldVo>   selectDiscoverField(@Param("table_id") String tid,
                                                @Param("field") String field);
}