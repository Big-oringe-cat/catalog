package com.catalog.vo;

import lombok.Data;

import java.util.List;

/**
 * @author: wangxilu
 * @since: 2022/11/23 11:31
 */
@Data
public class DataSourceTreeVo {

    private String datasource;

    private String isContain;

    private List<DataBaseTreeVo> dataBaseList;

}
