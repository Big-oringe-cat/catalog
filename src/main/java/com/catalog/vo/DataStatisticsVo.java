package com.catalog.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author: wangxilu
 * @since: 2022/11/23 11:31
 */
@Data
public class DataStatisticsVo {

    @Excel(name = "数据源")
    private Integer dataSourceNum;

    @Excel(name = "项目数/数据库")
    private Integer dataBaseNum;

    @Excel(name = "表数")
    private Integer tableNum;

    @Excel(name = "字段数")
    private Integer fieldNum;

}
