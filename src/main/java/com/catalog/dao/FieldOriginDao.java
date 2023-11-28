package com.catalog.dao;

import com.catalog.dto.FieldOrigin;
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
public class FieldOriginDao {
    @Autowired
    private MpaasQueryFactory sw;

    public String  insertFieldOrigin(FieldOrigin fieldOrigin){
        String id = (String) sw.buildQuery()
                .doInsert(fieldOrigin);
        return id;
    }
    public void  updateFieldOrigin(FieldOrigin fieldOrigin){
        sw.buildQuery()
                .update(new String[]{"table_id","field","fieldtype","field_length","field_demo","business_term"})
                .eq("id",fieldOrigin.getId())
                .doUpdate(fieldOrigin);
    }
    public int deleteFieldOriginById(String id){
        Integer res = sw.buildQuery()
                .eq("id", id)
                .doDelete(FieldOrigin.class);
        return res;
    }
}
