package com.catalog.controller;

import com.catalog.dto.GlobalIndex;
import com.catalog.service.GlobalIndexService;
import com.definesys.mpaas.common.http.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/globalIndex")
public class GlobalIndexController {

    @Autowired
    private GlobalIndexService globalIndexService;

    @RequestMapping(value = "/getGlobalIndexPageList")
    @ApiOperation("全局搜索")
    public Response getDataTablePageList(@RequestParam String description) {
        List<GlobalIndex> list =globalIndexService.getGlobalIndex(description);
        return Response.ok().data(list);
    }
}