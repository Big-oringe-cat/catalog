package com.catalog.dto;

import com.definesys.mpaas.query.annotation.Column;
import com.definesys.mpaas.query.annotation.Table;
import lombok.Data;

import java.util.Date;


/**
 * DataTable
 * @Description:  操作日志
 * @author wangxilu
 * @date 1/2/2023
 */
@Table(value = "sys_log")
@Data
public class SysLog {
    private String id;

    //操作类型
    @Column(value = "operation_type")
    private String operationType;

    //操作人
    @Column(value = "operator")
    private String operatorUser;

    //操作时间
    @Column(value = "operation_date")
    private Date operationDate;

    //开始时间
    @Column(value = "start_time")
    private Date startTime;

    //结束时间
    @Column(value = "end_time")
    private Date endTime;

    @Column(value = "request_param")
    private String requestParam;

    //模块
    @Column(value = "model")
    private String  model;

    //消耗时间
    @Column(value = "consum_time")
    private Integer  consumTime;
}
