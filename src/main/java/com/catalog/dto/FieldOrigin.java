package com.catalog.dto;

import com.definesys.mpaas.query.annotation.Column;
import com.definesys.mpaas.query.annotation.Table;
import lombok.Data;


/**
 * FieldOrigin
 * @Description:  采集field表
 * @author wangxilu
 * @date 1/2/2023
 */
@Table(value = "field_origin")
@Data
public class FieldOrigin {
    private String id;

    //表ID
    @Column(value = "table_id")
    private String tableId;

    //字段名称
    @Column(value = "field")
    private String field;

    //字段类型
    @Column(value = "fieldtype")
    private String fieldtype;

    //字段长度
    @Column(value = "field_length")
    private String fieldLength;

    //字段样本数据
    @Column(value = "field_demo")
    private String fieldDemo;

    //字段业务含义
    @Column(value = "business_term")
    private String businessTerm;
}
