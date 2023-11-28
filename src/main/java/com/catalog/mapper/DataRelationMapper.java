package com.catalog.mapper;

import com.catalog.dto.DataRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Author:   wangxilu
 * Date:     2022/11/17 16:55
 */
@Mapper
public interface DataRelationMapper {


    List<DataRelation> selectDataRelation(@Param("srcTable") String srcTable,
                                          @Param("checkFlag") String checkFlag,
                                          @Param("dataBase") String dataBase);

}