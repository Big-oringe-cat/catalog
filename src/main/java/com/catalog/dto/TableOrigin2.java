package com.catalog.dto;

import com.definesys.mpaas.query.annotation.Column;
import com.definesys.mpaas.query.annotation.Table;
import lombok.Data;


/**
 * TableOrigin
 * @Description:  采集table表
 * @Description:
 * @Author: miaowei
 * @Since: 2023/03/27
 */
@Table(value = "table_origin")
@Data
public class TableOrigin2 {
    private String id;
    @Column(value = "datasource")
    private String datasource;
    @Column(value = "database")
    private String databaseName;
    @Column(value = "schema")
    private String schemaName;
    @Column(value = "tablename")
    private String tableName;
    @Column(value = "business_term")
    private String business_term;
    @Column(value = "frequency")
    private String frequency;
    @Column(value = "rows")
    private String rows;
    @Column(value = "update_time")
    private String update_time;
    @Column(value = "size")
    private String size;
    @Column(value = "type")
    private String type;
    @Column(value = "skip")
    private Boolean skip;
    @Column(value = "expire")
    private Boolean expire;
}
