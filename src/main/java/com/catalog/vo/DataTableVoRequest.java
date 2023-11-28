package com.catalog.vo;

import lombok.Data;

/**
 * @author: wangxilu
 * @since: 2022/11/23 11:31
 */
@Data
public class DataTableVoRequest {

    private String tableName;

    private String dataBase;

    private String chenkFlag;

    private String tableComment;

    private String tableCommentEng;

    private String category;


}
