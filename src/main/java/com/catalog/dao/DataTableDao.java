package com.catalog.dao;

import com.catalog.dto.DataTable;
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
public class DataTableDao {
    @Autowired
    private MpaasQueryFactory sw;

    public String  insertDataTable(DataTable dataTable){
        String  id = (String) sw.buildQuery()
                .doInsert(dataTable);
        return id;
    }
    public void  updateDataTable(DataTable dataTable){
        sw.buildQuery()
                .update(new String[]{"tablename","table_comment","table_comment_eng","database_name","schema_name","datasource","update_time","update_frequency","operator","category"})
                .eq("id",dataTable.getId())
                .doUpdate(dataTable);
    }
    public int deleteDataTableById(String id){
        Integer res = sw.buildQuery()
                .eq("id", id)
                .doDelete(DataTable.class);
        return res;
    }

    public DataTable findById(String id){
        DataTable res = sw.buildQuery()
                .eq("id", id)
                .doQueryFirst(DataTable.class);
        return res;
    }
}
