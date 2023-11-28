package com.catalog.dao;

import com.catalog.dto.FieldCheck;
import com.catalog.dto.TableCheck;
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
public class FieldCheckDao {
    @Autowired
    private MpaasQueryFactory sw;

    public void insertFieldCheck(FieldCheck fieldCheck){
        sw.buildQuery()
                .doInsert(fieldCheck);
    }
    public int deleteFieldCheckBytid(String id){
        Integer res = sw.buildQuery()
                .eq("tid", id)
                .doDelete(FieldCheck.class);
        return res;
    }
    public int deleteFieldCheckByImportTid(String id){
        Integer res = sw.buildQuery()
                .eq("import_tid", id)
                .doDelete(FieldCheck.class);
        return res;
    }
    public int deleteFieldCheckByfid(String id){
        Integer res = sw.buildQuery()
                .eq("fid", id)
                .doDelete(FieldCheck.class);
        return res;
    }
    public int deleteFieldCheckByImportFid(String id){
        Integer res = sw.buildQuery()
                .eq("import_fid", id)
                .doDelete(FieldCheck.class);
        return res;
    }

}
