package com.catalog.dao;

import com.catalog.dto.DataField;
import com.catalog.dto.DataTable;
import com.definesys.mpaas.query.MpaasQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * MailHistoryDao
 *
 * @author wangxilu
 * @date 1/2/2023
 */
@Component
public class DataFieldDao {
    @Autowired
    private MpaasQueryFactory sw;

    public String  insertDataField(DataField dataField){
        String  id = (String) sw.buildQuery()
                .doInsert(dataField);
        return id;
    }
    public void  updateDataField(DataField dataField){
        sw.buildQuery()
                .update(new String[]{"table_id","field","field_type","field_comment","field_comment_eng","update_time","operator","classfication"})
                .eq("id",dataField.getId())
                .doUpdate(dataField);
    }

    public void  updateDataFieldDictionary(DataField dataField){
        sw.buildQuery()
                .update(new String[]{"field_comment_eng","update_time","operator","business_terms","classfication"})
                .eq("id",dataField.getId())
                .doUpdate(dataField);
    }

    public int deleteDataFieldById(String id){
        Integer res = sw.buildQuery()
                .eq("id", id)
                .doDelete(DataField.class);
        return res;
    }

    public List<DataField> getByTableId (String tableId){
        List<DataField> list = sw.buildQuery()
                .eq("table_id",tableId)
                .doQuery(DataField.class);
        return list;
    }

    public List<DataField> getById (String fieldId){
        List<DataField> res = sw.buildQuery()
                .eq("id",fieldId)
                .doQuery(DataField.class);
        return res;
    }
}
