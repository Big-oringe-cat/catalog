package com.catalog.dto;
import lombok.Data;


/**
 * DataTable
 * @Description:  数据表树
 * @author wangxilu
 * @date 1/2/2023
 */
@Data
public class TableTree {
    //数据表名
    private String tableName;

    //数据库
    private String tableCatalog;

}
