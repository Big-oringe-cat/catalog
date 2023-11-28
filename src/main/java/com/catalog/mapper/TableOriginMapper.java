package com.catalog.mapper;

import com.catalog.dto.TableOrigin;
import com.catalog.vo.TableOriginTopVo;
import com.catalog.vo.TableSizeTopVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Author:   wangxilu
 * Date:     2022/11/17 16:55
 */
@Mapper
public interface TableOriginMapper {

    List<TableOrigin> selectTableOrigin(@Param("tableName") String tableName);

    List<TableOriginTopVo> selectTableTop10();

    List<TableSizeTopVo> selectTableSizeTop10();

}