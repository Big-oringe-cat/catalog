package com.catalog.dao;

import com.catalog.dto.DataRelation;
import com.definesys.mpaas.query.MpaasQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DataRelationDao
 *
 * @author wangxilu
 * @date 1/2/2023
 */
@Component
public class DataRelationDao {
    @Autowired
    private MpaasQueryFactory sw;

    public String  insertDataRelation(DataRelation dataRelation){
        String  id = (String) sw.buildQuery()
                .doInsert(dataRelation);
        return id;
    }
    public void  updateDataRelation(DataRelation dataRelation){
        sw.buildQuery()
                .update(new String[]{"dst_table","dst_field","src_table","src_field","realtion","dst_datasource","dst_database","dst_schema","src_datasource","src_database","src_schema","update_time","operator"})
                .eq("id",dataRelation.getId())
                .doUpdate(dataRelation);
    }
    public int deleteDataRelationById(String id){
        Integer res = sw.buildQuery()
                .eq("id", id)
                .doDelete(DataRelation.class);
        return res;
    }

}
