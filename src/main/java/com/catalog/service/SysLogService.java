package com.catalog.service;

import com.catalog.dto.SysLog;import com.catalog.vo.PageRequestVo;
import com.catalog.vo.SysLogVoRequest;
import com.definesys.mpaas.query.db.PageQueryResult;

/**
 * Author:   wangxilu
 * Date:     2020/8/19 9:46
 */
public interface SysLogService {

    void addSysLog(SysLog sysLog);

    PageQueryResult<SysLog> getSysLog(PageRequestVo<SysLogVoRequest> pageRequestVo);


}