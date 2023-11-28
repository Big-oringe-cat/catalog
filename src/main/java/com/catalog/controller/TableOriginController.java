package com.catalog.controller;

import com.catalog.dto.TableOrigin;
import com.catalog.service.TableOriginService;
import com.catalog.vo.PageRequestVo;
import com.catalog.vo.TableOriginTopVo;
import com.catalog.vo.TableOriginVoRequest;
import com.catalog.vo.TableSizeTopVo;
import com.definesys.mpaas.common.http.Response;
import com.definesys.mpaas.query.db.PageQueryResult;
import com.definesys.mpaas.swagger.DataResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tableOrigin")
public class TableOriginController {

    @Autowired
    private TableOriginService tableOriginService;

    @RequestMapping(value = "/getTableOriginPageList")
    @ApiOperation("分页列表")
    public Response getTableOriginPageList(@RequestBody PageRequestVo<TableOriginVoRequest> pageRequestVo) {
        PageQueryResult<TableOrigin> list =tableOriginService.getPageTableOrigin(pageRequestVo);
        return Response.ok().data(list);
    }

    @RequestMapping(value = "/addTableOrigin")
    @ApiOperation("新增")
    public Response add(@RequestBody TableOrigin tableOrigin) {
        tableOriginService.addTableOrigin(tableOrigin);
        return Response.ok();
    }

    @RequestMapping(value = "/update")
    @ApiOperation("修改")
    public Response update(@RequestBody TableOrigin tableOrigin) {
        tableOriginService.updateTableOrigin(tableOrigin);
        return Response.ok();
    }

    @RequestMapping(value = "/delTableOrigin", method = RequestMethod.GET)
    @ApiOperation("删除")
    public DataResponse delTableOrigin(@RequestParam String id) {
        tableOriginService.delTableOrigin(id);
        return DataResponse.ok();
    }

    @GetMapping(value = "/getTableOriginTop10")
    @ApiOperation("获取前十的数据")
    public Response getTableOriginTop10() {
        List<TableOriginTopVo> list =tableOriginService.selectTableTop10();
        return Response.ok().data(list);
    }

    @GetMapping(value = "/getTableSizeTop10")
    @ApiOperation("获取表Size前十的数据")
    public Response getTableSizeTop10() {
        List<TableSizeTopVo> list =tableOriginService.selectTableSizeTop10();
        return Response.ok().data(list);
    }

}