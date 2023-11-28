package com.catalog.dto;

import com.definesys.mpaas.query.annotation.Column;
import com.definesys.mpaas.query.annotation.Table;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import java.util.Date;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description: 自动发现table视图
 * @Author: miaowei
 * @Since: 2023/03/27
 */
@Data
public class DiscoverTable {
    private String id;

    //表名
    private String tableName;

    //表名注释_CN
    private String tableComment;

    //表名注释_ENG
    private String tableCommentEng;

    //数据库
    private String databaseName;

    //模式
    private String  schemaName;

    //数据源
    private String  datasource;

    //更新时间
    private Date updateTime;

    //操作人
    private String  operatorUser;

    private String  category;

    //更新频率
    private String  updateFrequency;

    //检查状态
    private String  checkFlag;

    //检查状态
    private Boolean skip;
    //检查状态
    private Boolean expire;

}
