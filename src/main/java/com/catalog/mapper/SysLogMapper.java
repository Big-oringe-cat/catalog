package com.catalog.mapper;

import com.catalog.dto.SysLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * Author:   wangxilu
 * Date:     2022/11/17 16:55
 */
@Mapper
public interface SysLogMapper {


    List<SysLog> selectSysLog();

}