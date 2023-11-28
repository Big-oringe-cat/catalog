package com.catalog.vo;

import lombok.Data;

import java.util.List;

/**
 * @author: wangxilu
 * @since: 2022/11/23 11:31
 */
@Data
public class DataBaseTreeVo {

    private String dataBase;

    private String isContain;

    private List<DataTableTreeVo> dataTableList;

}
