package com.catalog.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.definesys.mpaas.query.annotation.Column;
import com.definesys.mpaas.query.annotation.Table;
import lombok.Data;

import java.util.Date;


/**
 * DataTable
 * @Description:  数据血缘表
 * @author wangxilu
 * @date 1/2/2023
 */
@Table(value = "data_relation")
@Data
public class DataRelation {
    private String id;

    //目标表
    @Excel(name = "目标表")
    @Column(value = "dst_table")
    private String dstTable;

    //目标表字段
    @Excel(name = "目标表字段")
    @Column(value = "dst_field")
    private String dstField;

    //来源表
    @Excel(name = "来源表")
    @Column(value = "src_table")
    private String srcTable;

    //来源表字段
    @Excel(name = "来源表字段")
    @Column(value = "src_field")
    private String srcField;

    //关联关系
    @Excel(name = "关联关系")
    @Column(value = "realtion")
    private String realtion;

    //目标表数据源
    @Excel(name = "目标表数据源")
    @Column(value = "dst_datasource")
    private String dstDatasource;

    //目标表数据库
    @Excel(name = "目标表数据库")
    @Column(value = "dst_database")
    private String dstDatabase;

    //目标表模式
    @Excel(name = "目标表模式")
    @Column(value = "dst_schema")
    private String dstSchema;

    //源表数据源
    @Excel(name = "源表数据源")
    @Column(value = "src_datasource")
    private String srcDatasource;

    //源表数据库
    @Excel(name = "源表数据库")
    @Column(value = "src_database")
    private String srcDatabase;

    //源表模式
    @Excel(name = "源表模式")
    @Column(value = "src_schema")
    private String srcSchema;

    //更新时间
    @Column(value = "update_time")
    private Date updateTime;

    //操作人
    @Column(value = "operator")
    private String  operatorUser;

    //检查状态
    private String  checkFlag;

}
