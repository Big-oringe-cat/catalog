package com.catalog.dto;

import com.definesys.mpaas.query.annotation.Column;
import com.definesys.mpaas.query.annotation.Table;
import lombok.Data;


/**
 * TableOrigin
 * @Description:  field check表
 * @Description:
 * @Author: miaowei
 * @Since: 2023/03/30
 */
@Table(value = "field_check")
@Data
public class FieldCheck {
    @Column(value = "field")
    private String field;
    @Column(value = "fieldtype")
    private String fieldType;
    @Column(value = "field_length")
    private String fieldLength;
    @Column(value = "field_demo")
    private String fieldDemo;
    @Column(value = "field_demo2")
    private String fieldDemo2;
    @Column(value = "field_demo3")
    private String fieldDemo3;
    @Column(value = "business_term")
    private String businessTerm;
    @Column(value = "tid")
    private String tid;
    @Column(value = "import_tid")
    private String importTid;
    @Column(value = "fid")
    private String fid;
    @Column(value = "import_fid")
    private String importFid;
    @Column(value = "datasource")
    private String datasource;
    @Column(value = "database_name")
    private String databaseName;
    @Column(value = "schema_name")
    private String schemaName;
    @Column(value = "tablename")
    private String tablename;
    @Column(value = "import_type")
    private String importType;
    @Column(value = "field_comment")
    private String fieldComment;
    @Column(value = "field_comment_eng")
    private String fieldCommentEng;
    @Column(value = "origin_flag")
    private String originFlag;
    @Column(value = "update_time")
    private String updateTime;
    @Column(value = "modify_time")
    private String modifyTime;
}
