package com.catalog.service.impl;


import com.catalog.dao.TableOriginDao;
import com.catalog.dto.TableOrigin;
import com.catalog.mapper.TableOriginMapper;
import com.catalog.service.TableOriginService;
import com.catalog.util.PageUtil;
import com.catalog.vo.PageRequestVo;
import com.catalog.vo.TableOriginTopVo;
import com.catalog.vo.TableOriginVoRequest;
import com.catalog.vo.TableSizeTopVo;
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
public class TableOriginServiceImpl implements TableOriginService {


    @Autowired
    private TableOriginDao tableOriginDao;

    @Resource
    private TableOriginMapper tableOriginMapper;


    @Override
    public void addTableOrigin(TableOrigin tableOrigin) {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        tableOrigin.setId(uuid);
        tableOriginDao.insertTableOrigin(tableOrigin);
    }



    @Override
    public void updateTableOrigin(TableOrigin tableOrigin) {
        tableOriginDao.updateTableOrigin(tableOrigin);
    }

    @Override
    public void delTableOrigin(String id) {
        tableOriginDao.deleteTableOriginById(id);
    }

    @Override
    public List<TableOriginTopVo> selectTableTop10() {
        return tableOriginMapper.selectTableTop10();
    }

    @Override
    public List<TableSizeTopVo> selectTableSizeTop10() {
        return tableOriginMapper.selectTableSizeTop10();
    }

    @Override
    public PageQueryResult<TableOrigin> getPageTableOrigin(PageRequestVo<TableOriginVoRequest> pageRequestVo) {
        TableOriginVoRequest tableOriginVoRequest = (TableOriginVoRequest) pageRequestVo.getCondition();
        String tableName = tableOriginVoRequest.getTableName();
        int pageNum = pageRequestVo.getPageNum();
        int pageSize = pageRequestVo.getPageSize();

        PageHelper.startPage(pageNum,pageSize);
        List<TableOrigin> orderList = tableOriginMapper.selectTableOrigin(tableName);

        return PageUtil.getPageQueryResult(pageRequestVo, new PageInfo<TableOrigin>(orderList));
    }

}
