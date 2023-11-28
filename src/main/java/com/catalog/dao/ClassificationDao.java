package com.catalog.dao;

import com.catalog.dto.Category;
import com.catalog.dto.Classification;
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
public class ClassificationDao {
    @Autowired
    private MpaasQueryFactory sw;

    public String  insertClassification(Classification classification){
        String  id = (String) sw.buildQuery()
                .doInsert(classification);
        return id;
    }
    public int deleteClassificationById(String id){
        Integer res = sw.buildQuery()
                .eq("id", id)
                .doDelete(Classification.class);
        return res;
    }
    public void  updateClassification(Classification classification){
        sw.buildQuery()
                .update(new String[]{"class_name"})
                .eq("id",classification.getId())
                .doUpdate(classification);
    }
}
