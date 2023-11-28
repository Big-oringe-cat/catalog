package com.catalog.vo;

import lombok.Data;


/**
 * TableOrigin
 * @author wangxilu
 * @date 1/2/2023
 */
@Data
public class TableOriginTopVo {
    private String tablename;

    private Integer rowsSize;

    private String database;
}
