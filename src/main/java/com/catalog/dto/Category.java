package com.catalog.dto;

import com.definesys.mpaas.query.annotation.Column;
import com.definesys.mpaas.query.annotation.Table;
import lombok.Data;

import java.util.Date;


/**
 * DataTable
 * @author wangxilu
 * @date 1/2/2023
 */
@Table(value = "category")
@Data
public class Category {
    private String id;

    @Column(value = "category_id")
    private String categoryId;

    @Column(value = "category_name")
    private String categoryName;

}
