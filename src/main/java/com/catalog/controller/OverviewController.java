package com.catalog.controller;

import com.catalog.dto.Category;
import com.catalog.service.DataFieldService;
import com.catalog.service.DataTableService;
import com.catalog.service.DictService;
import com.catalog.service.SysLogService;
import com.catalog.vo.BusinessSlatisticsVo;
import com.catalog.vo.CategoryNumVo;
import com.catalog.vo.EmptyVo;
import com.definesys.mpaas.common.http.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/overView")
public class OverviewController {

    @Autowired
    private DataTableService dataTableService;

    @Autowired
    private DictService dictService;

    @Autowired
    private DataFieldService dataFieldService;


    @RequestMapping(value = "/getCategoryPercentage")
    @ApiOperation("查询分类占比")
    public Response getCategoryPercentage() {
        List<BusinessSlatisticsVo> overList =  new ArrayList();
        List<Category> list =dictService.getCategory();
        for(Category category : list){
            CategoryNumVo categoryNumVo = dataTableService.findCountByCategory(category.getCategoryName());
            if(categoryNumVo!=null){
                BusinessSlatisticsVo businessSlatisticsVo = new BusinessSlatisticsVo();
                Double categoryNum = categoryNumVo.getCategoryNum().doubleValue();
                Double totalNum = categoryNumVo.getTotalNum().doubleValue();
                Double result = categoryNum/totalNum*100;
                DecimalFormat df = new DecimalFormat("#.00");
                businessSlatisticsVo.setCateGory(category.getCategoryName());
                businessSlatisticsVo.setPercentage(Double.valueOf(df.format(result)));
                businessSlatisticsVo.setValue(categoryNumVo.getCategoryNum());
                businessSlatisticsVo.setTotalValue(categoryNumVo.getTotalNum());
                overList.add(businessSlatisticsVo);
            }
        }
        Double unKnown = 0.00;
        for(BusinessSlatisticsVo businessSlatisticsVo : overList){
            unKnown += businessSlatisticsVo.getPercentage();
        }
        BusinessSlatisticsVo business = new BusinessSlatisticsVo();
        business.setCateGory("unKnown");
        business.setPercentage(100-unKnown);
        overList.add(business);
        return Response.ok().data(overList);
    }

    @RequestMapping(value = "/getEmptyPercentage")
    @ApiOperation("查询非空占比")
    public Response getEmptyPercentage() {
        CategoryNumVo categoryNumVo = dataTableService.findCategoryEmpty();
        Double categoryNum = categoryNumVo.getCategoryNum().doubleValue();
        Double totalNum = categoryNumVo.getTotalNum().doubleValue();
        Double categoryPs = categoryNum/totalNum*100;

        CategoryNumVo dsVo = dataTableService.findDsEmpty();
        Double dsNum = dsVo.getCategoryNum().doubleValue();
        Double totalDsNum = dsVo.getTotalNum().doubleValue();
        Double ds = dsNum/totalDsNum*100;

        CategoryNumVo filedDsVo = dataFieldService.findDsEmpty();
        Double fieldDsNum = filedDsVo.getCategoryNum().doubleValue();
        Double totalFiledDsNum = filedDsVo.getTotalNum().doubleValue();
        Double filedDs = fieldDsNum/totalFiledDsNum*100;

        DecimalFormat df = new DecimalFormat("#.00");

        EmptyVo emptyVo = new EmptyVo();
        emptyVo.setCategoryEmptyPercentage(Double.valueOf(df.format(categoryPs)));
        emptyVo.setCategoryNoEmptyPercentage(100-Double.valueOf(df.format(categoryPs)));
        emptyVo.setTableEmptyPercentage(Double.valueOf(df.format(ds)));
        emptyVo.setTableNoEmptyPercentage(100-Double.valueOf(df.format(ds)));
        emptyVo.setColumnEmptyPercentage(Double.valueOf(df.format(filedDs)));
        emptyVo.setColumnNoEmptyPercentage(100-Double.valueOf(df.format(filedDs)));
        return Response.ok().data(emptyVo);
    }

}