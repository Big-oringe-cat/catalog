package com.catalog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.catalog.dto.TableColumn;
import com.catalog.service.TableTreeService;
import com.catalog.vo.*;
import com.definesys.mpaas.common.http.Response;
import com.definesys.mpaas.query.db.PageQueryResult;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tableTree")
public class DataTreeController {

    @Autowired
    private TableTreeService tableTreeService;

    @ApiOperation("左侧树形结构")
    @GetMapping (value = "/list")
    public Response list() {
        List<DataSourceTreeVo> treeList = tableTreeService.selectAllDataSource();
        JSONArray result = new JSONArray();
        for(DataSourceTreeVo dataSourceTreeVo : treeList){
            String datasource = dataSourceTreeVo.getDatasource();
            if(StringUtils.isEmpty(datasource)){
                continue;
            }
            JSONObject sourceObj = new JSONObject();
            sourceObj.put("level","1");
            sourceObj.put("label",dataSourceTreeVo.getDatasource());
            List<DataBaseTreeVo> dataBaseList = tableTreeService.selectDataBase(dataSourceTreeVo.getDatasource());
            if(dataBaseList.size()!=0){
                dataSourceTreeVo.setIsContain("1");
                sourceObj.put("isLeaf",false);
            }else{
                dataSourceTreeVo.setIsContain("0");
                sourceObj.put("isLeaf",true);
                result.add(sourceObj);
                continue;
            }
            dataSourceTreeVo.setDataBaseList(dataBaseList);
            for(DataBaseTreeVo dataBaseTreeVo : dataBaseList){
                String dataBase = dataBaseTreeVo.getDataBase();
                if(StringUtils.isEmpty(dataBase)){
                    continue;
                }
                List<DataTableTreeVo> list = tableTreeService.selectDataTable(dataBaseTreeVo.getDataBase());
                if(list.size()!=0){
                    dataBaseTreeVo.setIsContain("1");
                }else{
                    dataBaseTreeVo.setIsContain("0");
                }
                dataBaseTreeVo.setDataTableList(list);
            }
        }
       return Response.ok().data(treeList);
    }

    @ApiOperation("左侧树形结构")
    @GetMapping (value = "/listFormat")
    public Response listFormat() {
        List<DataSourceTreeVo> treeList = tableTreeService.selectAllDataSource();
        JSONArray result = new JSONArray();
        for(DataSourceTreeVo dataSourceTreeVo : treeList){
            String datasource = dataSourceTreeVo.getDatasource();
            if(StringUtils.isEmpty(datasource)){
                continue;
            }
            JSONObject sourceObj = new JSONObject();
            sourceObj.put("level","1");
            sourceObj.put("label",dataSourceTreeVo.getDatasource());
            sourceObj.put("id",dataSourceTreeVo.getDatasource());
            List<DataBaseTreeVo> dataBaseList = tableTreeService.selectDataBase(dataSourceTreeVo.getDatasource());
            if(dataBaseList.size()!=0){
                dataSourceTreeVo.setIsContain("1");
                sourceObj.put("isLeaf",false);
            }else{
                dataSourceTreeVo.setIsContain("0");
                sourceObj.put("isLeaf",true);
                result.add(sourceObj);
                continue;
            }
            JSONArray dataBaseResult = new JSONArray();
            for(DataBaseTreeVo dataBaseTreeVo : dataBaseList){
                String dataBase = dataBaseTreeVo.getDataBase();
                if(StringUtils.isEmpty(dataBase)){
                    continue;
                }
                JSONObject databaseObj = new JSONObject();
                databaseObj.put("level","2");
                databaseObj.put("label",dataBaseTreeVo.getDataBase());
                databaseObj.put("id",dataBaseTreeVo.getDataBase());
                List<DataTableTreeVo> list = tableTreeService.selectDataTable(dataBaseTreeVo.getDataBase());
                if(list.size()!=0){
                    dataBaseTreeVo.setIsContain("1");
                    databaseObj.put("isLeaf",false);
                }else{
                    dataBaseTreeVo.setIsContain("0");
                    databaseObj.put("isLeaf",true);
                    continue;
                }
                JSONArray tableArray = new JSONArray();
                for(DataTableTreeVo dataTableTreeVo:list){
                    String dataTableName = dataTableTreeVo.getTableName();
                    if(StringUtils.isEmpty(dataTableName)){
                        continue;
                    }
                    JSONObject tableObj = new JSONObject();
                    tableObj.put("level","3");
                    tableObj.put("isLeaf",true);
                    tableObj.put("label",dataTableTreeVo.getTableName());
                    tableObj.put("tableId",dataTableTreeVo.getTableId());
                    tableObj.put("id",dataTableTreeVo.getTableName());
                    tableArray.add(tableObj);
                }
                databaseObj.put("children",tableArray);
                dataBaseResult.add(databaseObj);
            }
            sourceObj.put("children",dataBaseResult);
            result.add(sourceObj);
        }
        return Response.ok().data(result);
    }

    @ApiOperation("根据左侧表名称查询列属性")
    @PostMapping (value = "/selectByTableName")
    public Response selectByTableName(@RequestBody PageRequestVo<TableColumnVoRequest> pageRequestVo) {
        PageQueryResult<TableColumn> treeList = tableTreeService.selectByTableName(pageRequestVo);
        return Response.ok().data(treeList);
    }


    @ApiOperation("获取左侧树的所有数据源")
    @GetMapping (value = "/dataSourceList")
    public Response dataSourceList() {
        List<DataSourceTreeVo> treeList = tableTreeService.selectAllDataSource();
        for(DataSourceTreeVo dataSourceTreeVo : treeList){
            List<DataBaseTreeVo> list = tableTreeService.selectDataBase(dataSourceTreeVo.getDatasource());
            if(list.size()!=0){
                dataSourceTreeVo.setIsContain("1");
            }else{
                dataSourceTreeVo.setIsContain("0");
            }
        }
        return Response.ok().data(treeList);
    }

    @ApiOperation("根据数据源获取左侧树的所有数据库")
    @GetMapping (value = "/dataBaseList")
    public Response dataBaseList(@RequestParam("datasource") String datasource) {
        List<DataBaseTreeVo> treeList = tableTreeService.selectDataBase(datasource);

        for(DataBaseTreeVo dataBaseTreeVo : treeList){
            List<DataTableTreeVo> list = tableTreeService.selectDataTable(dataBaseTreeVo.getDataBase());
            if(list.size()!=0){
                dataBaseTreeVo.setIsContain("1");
            }else{
                dataBaseTreeVo.setIsContain("0");
            }
        }
        return Response.ok().data(treeList);
    }

    @ApiOperation("根据数据库获取左侧树的所有数据库")
    @GetMapping (value = "/dataTableList")
    public Response dataTableList(@RequestParam("dataBase") String dataBase) {
        List<DataTableTreeVo> treeList = tableTreeService.selectDataTable(dataBase);
        return Response.ok().data(treeList);
    }

}