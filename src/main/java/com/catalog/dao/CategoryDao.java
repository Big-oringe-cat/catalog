package com.catalog.dao;

import com.catalog.dto.Category;
import com.catalog.dto.DataTable;
import com.catalog.dto.SysLog;
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
public class CategoryDao {
    @Autowired
    private MpaasQueryFactory sw;

    public String  insertCategory(Category category){
        String  id = (String) sw.buildQuery()
                .doInsert(category);
        return id;
    }
    public int deleteCategoryById(String id){
        Integer res = sw.buildQuery()
                .eq("id", id)
                .doDelete(Category.class);
        return res;
    }
    public void  updateCategory(Category category){
        sw.buildQuery()
                .update(new String[]{"category_name"})
                .eq("id",category.getId())
                .doUpdate(category);
    }
}
