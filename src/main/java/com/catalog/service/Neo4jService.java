package com.catalog.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.catalog.dto.DiscoverTable;
import com.catalog.vo.*;
import com.definesys.mpaas.query.db.PageQueryResult;

import java.util.List;
import java.util.Map;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @Author: miaowei
 * @Since: 2023/03/27
 */
public interface Neo4jService {

    List<Map<String, Object>> getNeoResultOrigin(Neo4jVoRequest neo4jVoRequest);
    Map<String, Object> getNeoResult(Neo4jVoRequest neo4jVoRequest);

}