package com.catalog.mapper;

import com.catalog.dto.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Author:   wangxilu
 * Date:     2022/11/17 16:55
 */
@Mapper
public interface CategoryMapper {

    List<Category> selectAll();

    List<Category> selectPage(@Param("categoryName") String categoryName);

}