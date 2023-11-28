package com.catalog.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.catalog.config.Neo4jConfig;
import com.catalog.dao.*;
import com.catalog.dto.*;
import com.catalog.mapper.DiscoverTableMapper;
import com.catalog.service.DiscoverTableService;
import com.catalog.service.Neo4jService;
import com.catalog.util.Md5Utils;
import com.catalog.util.PageUtil;
import com.catalog.vo.*;
import com.definesys.mpaas.query.db.PageQueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.neo4j.driver.*;
import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @Author: miaowei
 * @Since: 2023/04/16
 */
@Service
public class Neo4jServiceImpl implements Neo4jService {

    @Resource
    private Neo4jConfig neo4jConfig;

    public List<Map<String, Object>> getNeoResultOrigin(Neo4jVoRequest neo4jVoRequest){
        String cql = neo4jVoRequest.getCql();
        Result result = neo4jConfig.search(cql);
        List<Map<String, Object>> neoResult = neo4jConfig.neoOrigin(result);
        return neoResult;
    };

    public Map<String, Object> getNeoResult(Neo4jVoRequest neo4jVoRequest){
        String cql = neo4jVoRequest.getCql();
        Result result = neo4jConfig.search(cql);
        Map<String, Object> neoResult = neo4jConfig.neoNew(result);
        return neoResult;
    };

}
