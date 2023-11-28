package com.catalog.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author: wangxilu
 * @since: 2022/11/23 11:31
 */
@Data
public class DataFieldExcelVo {

    @Excel(name = "字段")
    private String fieldName;

    @Excel(name = "类型")
    private String fieldType;

    @Excel(name = "字段注释_CN")
    private String fieldComment;

    @Excel(name = "字段注释_ENG")
    private String fieldCommentEng;

    @Excel(name = "字段分类_ENG")
    private String classfication;

    @Excel(name = "安全级别")
    private String securityLevel;

    @Excel(name = "业务术语")
    private String businessTerms;

}
