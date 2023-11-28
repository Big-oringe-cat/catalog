package com.catalog.dto;

import com.definesys.mpaas.query.annotation.Column;
import com.definesys.mpaas.query.annotation.Table;
import lombok.Data;


/**
 * TableOrigin
 * @Description:  采集table表
 * @author wangxilu
 * @date 1/2/2023
 */
@Table(value = "table_origin")
@Data
public class TableOrigin {
    private String id;

    //数据来源
    @Column(value = "datasource")
    private String datasource;

    //哪个项目
    @Column(value = "database")
    private String database;

    //表名
    @Column(value = "tablename")
    private String tableName;

    //表的业务含义
    @Column(value = "business_term")
    private String businessTerm;

    //更新频率
    @Column(value = "frequency")
    private String frequency;
}
