package com.catalog.service;

import com.catalog.dto.Category;
import com.catalog.dto.Classification;
import com.catalog.vo.CategoryVoRequest;
import com.catalog.vo.ClassificationVoRequest;
import com.catalog.vo.PageRequestVo;
import com.definesys.mpaas.query.db.PageQueryResult;

import java.util.List;

/**
 * Author:   wangxilu
 * Date:     2020/8/19 9:46
 */
public interface DictService {

    List<Category> getCategory();

    List<Classification> getClassification();

    void addCategory(Category category);

    void updateCategory(Category category);

    void delCategory(String id);

    void addClassification(Classification classification);

    void updateClassification(Classification classification);

    void delClassification(String id);

    PageQueryResult<Category> getPageCategory(PageRequestVo<CategoryVoRequest> pageRequestVo);

    PageQueryResult<Classification> getPageClassification(PageRequestVo<ClassificationVoRequest> pageRequestVo);

}