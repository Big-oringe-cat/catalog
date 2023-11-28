package com.catalog.controller;

import com.catalog.dto.SysLog;
import com.catalog.dto.TableOrigin;
import com.catalog.service.SysLogService;
import com.catalog.service.TableOriginService;
import com.catalog.vo.PageRequestVo;
import com.catalog.vo.SysLogVoRequest;
import com.catalog.vo.TableOriginTopVo;
import com.catalog.vo.TableOriginVoRequest;
import com.definesys.mpaas.common.http.Response;
import com.definesys.mpaas.query.db.PageQueryResult;
import com.definesys.mpaas.swagger.DataResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @RequestMapping(value = "/list")
    @ApiOperation("分页列表")
    public Response list(@RequestBody PageRequestVo<SysLogVoRequest> pageRequestVo) {
        PageQueryResult<SysLog> list =sysLogService.getSysLog(pageRequestVo);
        return Response.ok().data(list);
    }
}