package com.catalog.service.impl;

import com.catalog.dto.TableColumn;
import com.catalog.dto.TableOrigin;
import com.catalog.dto.TableTree;
import com.catalog.mapper.TableTreeMapper;
import com.catalog.service.TableTreeService;
import com.catalog.util.PageUtil;
import com.catalog.vo.*;
import com.definesys.mpaas.query.db.PageQueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
public class TableTreeServiceImpl implements TableTreeService {


    @Resource
    private TableTreeMapper tableTreeMapper;


    @Override
    public List<TableTree> selectTableTree() {
        return tableTreeMapper.selectTableTree();
    }

    @Override
    public List<DataSourceTreeVo> selectAllDataSource() {
        return tableTreeMapper.selectAllDataSource();
    }

    @Override
    public List<DataBaseTreeVo> selectDataBase(String datasource) {
        return tableTreeMapper.selectDataBase(datasource);
    }

    @Override
    public List<DataTableTreeVo> selectDataTable(String dataBase) {
        return tableTreeMapper.selectDataTable(dataBase);
    }

    @Override
    public PageQueryResult<TableColumn> selectByTableName(PageRequestVo<TableColumnVoRequest> pageRequestVo) {
        TableColumnVoRequest tableColumnVoRequest = (TableColumnVoRequest) pageRequestVo.getCondition();
        String tableName = tableColumnVoRequest.getTableName();
        int pageNum = pageRequestVo.getPageNum();
        int pageSize = pageRequestVo.getPageSize();

        PageHelper.startPage(pageNum,pageSize);
        List<TableColumn> orderList = tableTreeMapper.selectByTableName(tableName);

        return PageUtil.getPageQueryResult(pageRequestVo, new PageInfo<TableColumn>(orderList));
    }
}
