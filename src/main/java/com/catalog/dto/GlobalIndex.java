package com.catalog.dto;

import com.definesys.mpaas.query.annotation.Column;
import com.definesys.mpaas.query.annotation.Table;
import lombok.Data;


/**
 * DataTable
 * @Description:  全局索引表
 * @author wangxilu
 * @date 1/2/2023
 */
@Table(value = "global_index")
@Data
public class GlobalIndex {
    private String id;

    //名称
    @Column(value = "name")
    private String name;

    //类型
    @Column(value = "type")
    private String type;

    //描述
    @Column(value = "description")
    private String description;

    //字段类型对应表id
    @Column(value = "tid")
    private String tid;

}
