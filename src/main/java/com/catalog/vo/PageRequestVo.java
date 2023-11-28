package com.catalog.vo;

import lombok.Data;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: liuliqian
 * @since: 2019/8/16 下午1:23
 * @history: 1.2019/8/16 created by liuliqian
 */
@Data
public class PageRequestVo<T> {

    private int pageSize;
    private int pageNum;
    private String[] ascs;
    private String[] descs;
    private T condition;

    public PageRequestVo() {
    }

    public PageRequestVo(int pageSize, int pageNum, String[] ascs, String[] descs, T condition) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.ascs = ascs;
        this.descs = descs;
        this.condition = condition;
    }

}
