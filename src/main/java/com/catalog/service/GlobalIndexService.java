package com.catalog.service;

import com.catalog.dto.GlobalIndex;

import java.util.List;

/**
 * Author:   wangxilu
 * Date:     2020/8/19 9:46
 */
public interface GlobalIndexService {

    List<GlobalIndex> getGlobalIndex(String description);

}