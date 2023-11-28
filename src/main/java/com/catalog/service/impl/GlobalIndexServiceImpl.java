package com.catalog.service.impl;


import com.catalog.dao.DataFieldDao;
import com.catalog.dto.DataField;
import com.catalog.dto.GlobalIndex;
import com.catalog.mapper.DataFieldMapper;
import com.catalog.mapper.GlobalIndexMapper;
import com.catalog.service.DataFieldService;
import com.catalog.service.GlobalIndexService;
import com.catalog.util.PageUtil;
import com.catalog.vo.DataFieldVoRequest;
import com.catalog.vo.GlobalIndexVoRequest;
import com.catalog.vo.PageRequestVo;
import com.definesys.mpaas.query.db.PageQueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @Author: wangxilu
 * @Since: 2019/12/12
 */
@Service
public class GlobalIndexServiceImpl implements GlobalIndexService {

    @Resource
    private GlobalIndexMapper globalIndexMapper;

    @Override
    public List<GlobalIndex> getGlobalIndex(String description) {

        List<GlobalIndex> orderList = globalIndexMapper.selectGlobalIndex(description);

        return orderList;
    }

}
