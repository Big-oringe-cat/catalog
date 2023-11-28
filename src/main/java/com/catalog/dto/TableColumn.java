package com.catalog.dto;
import lombok.Data;


/**
 * DataTable
 * @Description:  表属性
 * @author wangxilu
 * @date 1/2/2023
 */
@Data
public class TableColumn {
    //列名称
    private String columnName;

    //数据类型
    private String dataType;

    //长度
    private String length;

    //是否为空
    private String isNull;

    //默认值
    private String columDefault;

}
