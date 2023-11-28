package com.catalog.vo;

import com.definesys.mpaas.query.annotation.Column;
import lombok.Data;

/**
 * @author: wangxilu
 * @since: 2022/11/23 11:31
 */
@Data
public class DataFieldVoRequest {

    private String dataBase;

    private String checkFlag;

    private String field;

    private String fieldComment;

    private String fieldCommentEng;


}
