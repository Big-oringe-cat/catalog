package com.catalog.dto;

import com.definesys.mpaas.query.annotation.Column;
import com.definesys.mpaas.query.annotation.Table;
import lombok.Data;


/**
 * TableOrigin
 * @Description:  table check表
 * @Description:
 * @Author: miaowei
 * @Since: 2023/03/28
 */
@Table(value = "table_check")
@Data
public class TableCheck {
    @Column(value = "oid")
    private String oid;
    @Column(value = "import_id")
    private String importId;
    @Column(value = "datasource")
    private String datasource;
    @Column(value = "database_name")
    private String databaseName;
    @Column(value = "schema_name")
    private String schemaName;
    @Column(value = "tablename")
    private String tablename;
    @Column(value = "business_term")
    private String businessTerm;
    @Column(value = "frequency")
    private String frequency;
    @Column(value = "rows")
    private String rows;
    @Column(value = "table_comment")
    private String tableComment;
    @Column(value = "table_comment_eng")
    private String tableCommentEng;
    @Column(value = "origin_flag")
    private String originFlag;
    @Column(value = "type")
    private String type;
    @Column(value = "update_time")
    private String updateTime;
    @Column(value = "modify_time")
    private String modifyTime;
    @Column(value = "size")
    private String size;
    @Column(value = "category")
    private String category;
    @Column(value = "update_frequency")
    private String updateFrequency;
    @Column(value = "operator")
    private String operator;
}
