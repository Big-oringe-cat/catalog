package com.catalog.dto;

import com.definesys.mpaas.query.annotation.Column;
import com.definesys.mpaas.query.annotation.Table;
import lombok.Data;

import java.util.Date;


/**
 * DataTable
 * @Description:  人工整理field表
 * @author wangxilu
 * @date 1/2/2023
 */
@Table(value = "data_field")
@Data
public class DataField {
    private String id;

    //表Id
    @Column(value = "table_id")
    private String tableId;

    //字段
    @Column(value = "field")
    private String field;

    //类型
    @Column(value = "field_type")
    private String fieldType;

    //字段注释_CN
    @Column(value = "field_comment")
    private String fieldComment;

    //字段注释_ENG
    @Column(value = "field_comment_eng")
    private String fieldCommentEng;

    //更新时间
    @Column(value = "update_time")
    private Date updateTime;

    //字段分类_ENG
    @Column(value = "classfication")
    private String  classfication;

    //安全级别
    @Column(value = "security_level")
    private String  securityLevel;

    //业务术语
    @Column(value = "business_terms")
    private String  businessTerms;

    //操作人
    @Column(value = "operator")
    private String  operatorUser;

    //检查状态
    private String  checkFlag;

    //sample
    private String  sample;

    //表名
    private String  tableName;


}
