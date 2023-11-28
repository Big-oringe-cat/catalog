package com.catalog.dao;

import com.catalog.dto.Category;
import com.catalog.dto.DataSource;
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
public class DataSourceDao {
    @Autowired
    private MpaasQueryFactory sw;

    public String  insertDataSource(DataSource dataSource){
        String  id = (String) sw.buildQuery()
                .doInsert(dataSource);
        return id;
    }
    public int deleteDtaSourceById(String id){
        Integer res = sw.buildQuery()
                .eq("id", id)
                .doDelete(DataSource.class);
        return res;
    }
    public void  updateDataSource(DataSource dataSource){
        sw.buildQuery()
                .update(new String[]{"data_base_name","data_source_name","source_address","source_owner","source_type","maintainer"})
                .eq("id",dataSource.getId())
                .doUpdate(dataSource);
    }
}
