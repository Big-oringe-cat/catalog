package com.catalog.mapper;

import com.catalog.dto.DataField;
import com.catalog.vo.CategoryNumVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Author:   wangxilu
 * Date:     2022/11/17 16:55
 */
@Mapper
public interface DataFieldMapper {


    List<DataField> selectDataField(@Param("field") String field,
                                    @Param("checkFlag") String checkFlag,
                                    @Param("fieldComment") String fieldComment,
                                    @Param("fieldCommentEng") String fieldCommentEng);

    List<DataField> selectDataFieldByTableIds(@Param("tableIds") String[] tableIds,
                                              @Param("checkFlag") String checkFlag,
                                              @Param("fieldComment") String fieldComment,
                                              @Param("fieldCommentEng") String fieldCommentEng);

    List<DataField> selectByTableId(@Param("tableId") String tableId);

    List<DataField> selectByFieldId(@Param("fieldId") String fieldId);

    CategoryNumVo findDsEmpty();

}