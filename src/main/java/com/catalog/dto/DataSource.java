package com.catalog.dto;

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
@Table(value = "data_source")
@Data
public class DataSource {
    private String id;

    //数据库名称
    @Column(value = "data_base_name")
    private String dataBaseName;

    //数据源名称
    @Column(value = "data_source_name")
    private String dataSourceName;

    //数据源地址
    @Column(value = "source_address")
    private String sourceAddress;

    //数据源拥有者
    @Column(value = "source_owner")
    private String sourceOwner;

    //
    @Column(value = "source_type")
    private String sourceType;

    //maintainer
    @Column(value = "maintainer")
    private String  maintainer;

}
