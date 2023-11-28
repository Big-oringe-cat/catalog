package com.catalog.util;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.params.ExcelForEachParams;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import org.apache.poi.ss.usermodel.*;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: liuliqian
 * @since: 2019/8/23 上午11:40
 * @history: 1.2019/8/23 created by liuliqian
 */
public class ExcelStyleUtil implements IExcelExportStyler {

    private static final short STRING_FORMAT = (short) BuiltinFormats.getBuiltinFormat("TEXT");
    private static final short FONT_SIZE_TEN = 10;
    private static final short FONT_SIZE_ELEVEN = 11;
    private static final short FONT_SIZE_TWELVE = 12;

    private CellStyle headerStyle;

    private CellStyle titleStyle;

    private CellStyle styles;

    public ExcelStyleUtil(Workbook workbook) {
        this.init(workbook);
    }


    private void init(Workbook workbook) {
        this.headerStyle = initHeaderStyle(workbook);
        this.titleStyle = initTitleStyle(workbook);
        this.styles = initStyles(workbook);
    }


    @Override
    public CellStyle getHeaderStyle(short color) {
        return headerStyle;
    }


    @Override
    public CellStyle getTitleStyle(short color) {
        return titleStyle;
    }


    @Override
    public CellStyle getStyles(boolean parity, ExcelExportEntity entity) {
        return styles;
    }


    @Override
    public CellStyle getStyles(Cell cell, int dataRow, ExcelExportEntity entity, Object obj, Object data) {
        return getStyles(true, entity);
    }


    @Override
    public CellStyle getTemplateStyles(boolean isSingle, ExcelForEachParams excelForEachParams) {
        return null;
    }


    private CellStyle initHeaderStyle(Workbook workbook) {
        CellStyle style = getBaseCellStyle(workbook);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(getFont(workbook, FONT_SIZE_TWELVE, true));
        return style;
    }


    private CellStyle initTitleStyle(Workbook workbook) {
        CellStyle style = getBaseCellStyle(workbook);
        style.setFont(getFont(workbook, FONT_SIZE_ELEVEN, false));
        style.setAlignment(HorizontalAlignment.CENTER);
        //背景色
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }


    private CellStyle initStyles(Workbook workbook) {
        CellStyle style = getBaseCellStyle(workbook);
        style.setFont(getFont(workbook, FONT_SIZE_TEN, false));
        style.setDataFormat(STRING_FORMAT);
        return style;
    }


    private CellStyle getBaseCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        return style;
    }

    private Font getFont(Workbook workbook, short size, boolean isBold) {
        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setBold(isBold);
        font.setFontHeightInPoints(size);
        return font;
    }
}
