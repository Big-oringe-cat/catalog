package com.catalog.dao;

import com.catalog.dto.TableOrigin;
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
public class TableOriginDao {
    @Autowired
    private MpaasQueryFactory sw;

    public String  insertTableOrigin(TableOrigin tableOrigin){
        String id = (String) sw.buildQuery()
                .doInsert(tableOrigin);
        return id;
    }
    public void  updateTableOrigin(TableOrigin tableOrigin){
        sw.buildQuery()
                .update(new String[]{"datasource","database","table_name","business_term","frequency"})
                .eq("id",tableOrigin.getId())
                .doUpdate(tableOrigin);
    }
    public int deleteTableOriginById(String id){
        Integer res = sw.buildQuery()
                .eq("id", id)
                .doDelete(TableOrigin.class);
        return res;
    }
}
