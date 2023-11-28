package com.catalog.controller;



import com.alibaba.fastjson.JSONObject;
import com.catalog.service.Neo4jService;
import com.catalog.service.SysLogService;
import com.catalog.vo.*;
import com.definesys.mpaas.common.http.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description: neo4j转发代理
 * @Author: miaowei
 * @Since: 2023/04/16
 */
@RestController
@RequestMapping("/neo")
public class Neo4jController {

    @Autowired
    private Neo4jService neo4jService;
    @Autowired
    private SysLogService sysLogService;


    @RequestMapping(value = "/getNeoResultOrigin")
    @ApiOperation("neo4j原生api格式")
    public Response getNeoResultOrigin(@RequestBody Neo4jVoRequest neo4jVoRequest) {
        List<Map<String, Object>> list =neo4jService.getNeoResultOrigin(neo4jVoRequest);
        return Response.ok().data(list);
    }

    @RequestMapping(value = "/getNeoResult")
    @ApiOperation("新格式")
    public Response getNeoResult(@RequestBody Neo4jVoRequest neo4jVoRequest) {

        Map<String, Object> data =neo4jService.getNeoResult(neo4jVoRequest);
        return Response.ok().data(data);
    }
}