package com.catalog.mapper;

import com.catalog.dto.FieldOrigin;
import com.catalog.dto.TableOrigin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Author:   wangxilu
 * Date:     2022/11/17 16:55
 */
@Mapper
public interface FieldOriginMapper {

    List<FieldOrigin> selectFieldOrigin(@Param("field") String field);

}