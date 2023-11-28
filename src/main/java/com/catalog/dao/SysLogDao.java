package com.catalog.dao;

import com.catalog.dto.SysLog;
import com.definesys.mpaas.query.MpaasQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * MailHistoryDao
 *
 * @author wangxilu
 * @date 1/2/2023
 */
@Component
public class SysLogDao {
    @Autowired
    private MpaasQueryFactory sw;

    public String  insertSysLog(SysLog sysLog){
        String  id = (String) sw.buildQuery()
                .doInsert(sysLog);
        return id;
    }
    public int deleteSysLogById(String id){
        Integer res = sw.buildQuery()
                .eq("id", id)
                .doDelete(SysLog.class);
        return res;
    }
}
