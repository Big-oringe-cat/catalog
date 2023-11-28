package com.catalog.util;

import com.catalog.vo.PageRequestVo;
import com.definesys.mpaas.query.db.PageQueryResult;
import com.github.pagehelper.PageInfo;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: liangjun.wu
 * @since: 2020/3/28 16:00
 * @history: 1.2020/3/28 created by liangjun.wu
 */
public class PageUtil {

    public static PageQueryResult getPageQueryResult(PageRequestVo pageRequestVo , PageInfo<?> pageInfo){
        PageQueryResult pageQueryResult = new PageQueryResult();
        pageQueryResult.setResult(pageInfo.getList());
        pageQueryResult.setCount(pageInfo.getTotal());
        return pageQueryResult;
    }
}
