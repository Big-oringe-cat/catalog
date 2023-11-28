package com.catalog.service.impl;


import com.catalog.dao.FieldOriginDao;
import com.catalog.dto.FieldOrigin;
import com.catalog.mapper.FieldOriginMapper;
import com.catalog.service.FieldOriginService;
import com.catalog.util.PageUtil;
import com.catalog.vo.FieldOriginVoRequest;
import com.catalog.vo.PageRequestVo;
import com.definesys.mpaas.query.db.PageQueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FieldOriginServiceImpl implements FieldOriginService {


    @Autowired
    private FieldOriginDao fieldOriginDao;

    @Resource
    private FieldOriginMapper fieldOriginMapper;


    @Override
    public void addFieldOrigin(FieldOrigin fieldOrigin) {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        fieldOrigin.setId(uuid);
        fieldOriginDao.insertFieldOrigin(fieldOrigin);
    }



    @Override
    public void updateFieldOrigin(FieldOrigin fieldOrigin) {
        fieldOriginDao.updateFieldOrigin(fieldOrigin);
    }

    @Override
    public void delFieldOrigin(String id) {
        fieldOriginDao.deleteFieldOriginById(id);
    }

    @Override
    public PageQueryResult<FieldOrigin> getPageFieldOrigin(PageRequestVo<FieldOriginVoRequest> pageRequestVo) {
        FieldOriginVoRequest fieldOriginVoRequest = (FieldOriginVoRequest) pageRequestVo.getCondition();
        String field = fieldOriginVoRequest.getField();
        int pageNum = pageRequestVo.getPageNum();
        int pageSize = pageRequestVo.getPageSize();

        PageHelper.startPage(pageNum,pageSize);
        List<FieldOrigin> orderList = fieldOriginMapper.selectFieldOrigin(field);

        return PageUtil.getPageQueryResult(pageRequestVo, new PageInfo<FieldOrigin>(orderList));
    }

}
