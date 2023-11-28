package com.catalog.vo;

import lombok.Data;

/**
 * @author: wangxilu
 * @since: 2022/11/23 11:31
 */
@Data
public class DataRelationVoRequest {

    private String srcTable;

    private String dataBase;

    private String srcField;

    private String checkFlag;


}
