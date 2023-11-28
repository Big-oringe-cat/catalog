package com.catalog.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.Data;

import java.util.List;

/**
 * @author: wangxilu
 * @since: 2022/11/23 11:31
 */
@Data
public class DataTableExcelVo {

    @Excel(name = "表名",needMerge = true)
    private String tableName;

    @Excel(name = "表名注释_CN",needMerge = true)
    private String tableComment;

    @Excel(name = "表名注释_ENG",needMerge = true)
    private String tableNameEng;

    @Excel(name = "数据库",needMerge = true)
    private String databaseName;

    @Excel(name = "模式",needMerge = true)
    private String  schemaName;

    @Excel(name = "数据源",needMerge = true)
    private String  datasource;

    @Excel(name = "表分类",needMerge = true)
    private String  category;

    @Excel(name = "更新频率",needMerge = true)
    private String  updateFrequency;

    @ExcelCollection(name = "字段信息")
    private List<DataFieldExcelVo> fields;
}
