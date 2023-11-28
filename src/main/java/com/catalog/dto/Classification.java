package com.catalog.dto;

import com.definesys.mpaas.query.annotation.Column;
import com.definesys.mpaas.query.annotation.Table;
import lombok.Data;


/**
 * DataTable
 * @author wangxilu
 * @date 1/2/2023
 */
@Table(value = "classification")
@Data
public class Classification {
    private String id;

    @Column(value = "class_name")
    private String className;

}
