package com.catalog.service.impl;


import com.catalog.dao.DataFieldDao;
import com.catalog.dao.SysLogDao;
import com.catalog.dto.DataField;
import com.catalog.dto.SysLog;
import com.catalog.mapper.DataFieldMapper;
import com.catalog.mapper.DataTableMapper;
import com.catalog.mapper.SysLogMapper;
import com.catalog.service.DataFieldService;
import com.catalog.service.SysLogService;
import com.catalog.util.Md5Utils;
import com.catalog.util.PageUtil;
import com.catalog.vo.DataFieldVoRequest;
import com.catalog.vo.DataTableVoRequest;
import com.catalog.vo.PageRequestVo;
import com.catalog.vo.SysLogVoRequest;
import com.definesys.mpaas.query.db.PageQueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @Author: wangxilu
 * @Since: 2019/12/12
 */
@Service
public class SysLogServiceImpl implements SysLogService {


    @Autowired
    private SysLogDao sysLogDao;

    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    public void addSysLog(SysLog sysLog) {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        sysLog.setId(uuid);
        sysLogDao.insertSysLog(sysLog);
    }

    @Override
    public PageQueryResult<SysLog> getSysLog(PageRequestVo<SysLogVoRequest> pageRequestVo) {
        SysLogVoRequest fieldOriginVoRequest = (SysLogVoRequest) pageRequestVo.getCondition();
        int pageNum = pageRequestVo.getPageNum();
        int pageSize = pageRequestVo.getPageSize();

        PageHelper.startPage(pageNum,pageSize);
        List<SysLog> orderList = sysLogMapper.selectSysLog();
        return PageUtil.getPageQueryResult(pageRequestVo, new PageInfo<SysLog>(orderList));
    }

}
