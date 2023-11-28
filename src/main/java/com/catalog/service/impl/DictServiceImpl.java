package com.catalog.service.impl;


import com.catalog.dao.CategoryDao;
import com.catalog.dao.ClassificationDao;
import com.catalog.dto.Category;
import com.catalog.dto.Classification;
import com.catalog.dto.DataTable;
import com.catalog.mapper.CategoryMapper;
import com.catalog.mapper.ClassificationMapper;
import com.catalog.service.DictService;
import com.catalog.util.PageUtil;
import com.catalog.vo.CategoryVoRequest;
import com.catalog.vo.ClassificationVoRequest;
import com.catalog.vo.DataTableVoRequest;
import com.catalog.vo.PageRequestVo;
import com.definesys.mpaas.query.db.PageQueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @Author: wangxilu
 * @Since: 2019/12/12
 */
@Service
public class DictServiceImpl implements DictService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private CategoryDao categoryDao;

    @Resource
    private ClassificationDao classificationDao;

    @Resource
    private ClassificationMapper classificationMapper;


    @Override
    public List<Category> getCategory() {
        return categoryMapper.selectAll();
    }

    @Override
    public List<Classification> getClassification() {
        return classificationMapper.selectAll();
    }

    @Override
    public void addCategory(Category category) {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        category.setId(uuid);
        categoryDao.insertCategory(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryDao.updateCategory(category);
    }

    @Override
    public void delCategory(String id) {
        categoryDao.deleteCategoryById(id);
    }

    @Override
    public void addClassification(Classification classification) {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        classification.setId(uuid);
        classificationDao.insertClassification(classification);
    }

    @Override
    public void updateClassification(Classification classification) {
        classificationDao.updateClassification(classification);
    }

    @Override
    public void delClassification(String id) {
        classificationDao.deleteClassificationById(id);
    }

    @Override
    public PageQueryResult<Category> getPageCategory(PageRequestVo<CategoryVoRequest> pageRequestVo) {
        CategoryVoRequest dataTableVoRequest = (CategoryVoRequest) pageRequestVo.getCondition();
        String categoryName = dataTableVoRequest.getCategoryName();
        int pageNum = pageRequestVo.getPageNum();
        int pageSize = pageRequestVo.getPageSize();

        PageHelper.startPage(pageNum,pageSize);
        List<Category> orderList = categoryMapper.selectPage(categoryName);

        return PageUtil.getPageQueryResult(pageRequestVo, new PageInfo<Category>(orderList));
    }

    @Override
    public PageQueryResult<Classification> getPageClassification(PageRequestVo<ClassificationVoRequest> pageRequestVo) {
        ClassificationVoRequest dataTableVoRequest = (ClassificationVoRequest) pageRequestVo.getCondition();
        String className = dataTableVoRequest.getClassName();
        int pageNum = pageRequestVo.getPageNum();
        int pageSize = pageRequestVo.getPageSize();

        PageHelper.startPage(pageNum,pageSize);
        List<Classification> orderList = classificationMapper.selectPage(className);

        return PageUtil.getPageQueryResult(pageRequestVo, new PageInfo<Classification>(orderList));
    }
}
