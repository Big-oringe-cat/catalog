package com.catalog.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.definesys.mpaas.query.annotation.Column;
import com.definesys.mpaas.query.annotation.Table;
import lombok.Data;

import java.util.Date;


/**
 * DataTable
 * @Description:  人工整理table表
 * @author wangxilu
 * @date 1/2/2023
 */
@Table(value = "data_table")
@Data
public class DataTable {
    private String id;

    //表名
    @Column(value = "tablename")
    private String tableName;

    //表名注释_CN
    @Column(value = "table_comment")
    private String tableComment;

    //表名注释_ENG
    @Column(value = "table_comment_eng")
    private String tableCommentEng;

    //数据库
    @Column(value = "database_name")
    private String databaseName;

    //模式
    @Column(value = "schema_name")
    private String  schemaName;

    //数据源
    @Column(value = "datasource")
    private String  datasource;

    //更新时间
    @Column(value = "update_time")
    private Date updateTime;

    //操作人
    @Column(value = "operator")
    private String  operatorUser;

    @Column(value = "category")
    private String  category;

    //更新频率
    @Column(value = "update_frequency")
    private String  updateFrequency;

    //检查状态
    private String  checkFlag;

}
