package com.catalog.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.definesys.mpaas.common.exception.MpaasBusinessException;
import com.definesys.mpaas.common.http.Response;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description: 导出、导入Excel工具类
 * @author: liuliqian
 * @since: 2019/8/23 上午11:44
 * @history: 1.2019/8/23 created by liuliqian
 */
public class ExcelUtil {

    public static <T> void exportExcel(HttpServletResponse response, List<T> list,Class<T> clazz, String exportFileName,String title,String sheetName){
        response.setHeader("content-Type", "application/octet-stream");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(exportFileName, "UTF-8") + ".xlsx");
            response.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
        exportParams.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, clazz, list);

        try {
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> Response importExcel(MultipartFile file, Class<T> clazz){
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(0);
        importParams.setHeadRows(1);
        ExcelImportResult<T> importResult=null;
        try {
            importResult = ExcelImportUtil.importExcelMore(file.getInputStream(), clazz, importParams);
        }catch (Exception e){
            e.printStackTrace();
            return Response.error("no such excel file");
        }
        if(importResult.getFailList().size()>0){
            return Response.error("has some datas import failed ,please try again");
        }
        if(importResult.getList().size()>0){
            return Response.ok().data(importResult.getList());
        }else {
            return Response.error("import Excel no datas");
        }

    }
    public static <T> List<T> importExcel(MultipartFile file, Class<T> clazz,int titleStartRow,int headStartRow){
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(titleStartRow);
        importParams.setHeadRows(headStartRow);
        ExcelImportResult<T> importResult=null;
        try {
            importResult = ExcelImportUtil.importExcelMore(file.getInputStream(), clazz, importParams);
        }catch (Exception e){
            throw new MpaasBusinessException("导入失败");
        }
        if(importResult.getFailList().size()>0){
            throw new MpaasBusinessException("存在错误的数据");
        }
        if(importResult.getList().size()>0){
            return importResult.getList();
        }else {
            throw new MpaasBusinessException("导入文件不能为空");
        }
    }
}
