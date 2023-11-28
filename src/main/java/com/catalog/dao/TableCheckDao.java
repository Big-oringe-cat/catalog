package com.catalog.dao;

import com.catalog.dto.TableCheck;
import com.catalog.dto.TableOrigin;
import com.definesys.mpaas.query.MpaasQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TableCheckDao
 * @Description:
 * @Author: miaowei
 * @Since: 2023/03/28
 */
@Component
public class TableCheckDao {
    @Autowired
    private MpaasQueryFactory sw;

    public void insertTableCheck(TableCheck tableCheck){
        sw.buildQuery()
                .doInsert(tableCheck);
    }

    public int deleteTableCheckByOid(String oid){
        Integer res = sw.buildQuery()
                .eq("oid", oid)
                .doDelete(TableCheck.class);
        return res;
    }

}
