package com.catalog.vo;

import lombok.Data;

/**
 * @author: wangxilu
 * @since: 2022/11/23 11:31
 */
@Data
public class EmptyVo {

    //表描述空值比例
    private Double tableEmptyPercentage;

    //表描述非空值比例
    private Double tableNoEmptyPercentage;

    //字段描述空值比例
    private Double columnEmptyPercentage;

    //字段描述非空值比例
    private Double columnNoEmptyPercentage;

    //类别空值比例
    private Double categoryEmptyPercentage;

    //类别非空值比例
    private Double categoryNoEmptyPercentage;


}
