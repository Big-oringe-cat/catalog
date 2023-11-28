package com.catalog.dao;

import com.catalog.dto.TableOrigin;
import com.catalog.dto.TableOrigin2;
import com.definesys.mpaas.query.MpaasQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TableOrigin2Dao
 *
 * @Author: miaowei
 * @Since: 2023/03/28
 */
@Component
public class TableOrigin2Dao {
    @Autowired
    private MpaasQueryFactory sw;

    public String  insertTableOrigin(TableOrigin2 tableOrigin){
        String id = (String) sw.buildQuery()
                .doInsert(tableOrigin);
        return id;
    }
    public int  updateTableOriginSkip(String id,boolean skip){
        Integer integer = sw.buildQuery()
                .update("skip",skip)
                .eq("id", id)
                .doUpdate(TableOrigin2.class);
        return integer;
    }
    public int  batchUpdateTableOriginSkip(String[] ids, boolean skip){
        Integer integer = sw.buildQuery()
                .update("skip",skip)
                .in("id", ids)
                .doUpdate(TableOrigin2.class);
        return integer;
    }
    public int deleteTableOriginById(String id){
        Integer res = sw.buildQuery()
                .eq("id", id)
                .doDelete(TableOrigin2.class);
        return res;
    }
}
