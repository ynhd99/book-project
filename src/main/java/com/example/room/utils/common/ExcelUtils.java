package com.example.room.utils.common;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;

public class ExcelUtils {
    public static HSSFWorkbook exportExcel(String title, String[] headers){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);
        HSSFRow row = sheet.createRow(0);
        //设置表头数据
        for (int i = 0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellStyle(getHeaderStyle(workbook));
        }
        return workbook;
    }
    /**
     * 导出返回数据
     * @param workbook
     * @param response
     * @param fileName
     * @throws Exception
     */
  public static void returnExport(HSSFWorkbook workbook, HttpServletResponse response,String fileName){
      response.setContentType("application/octet-stream");
      try {
          response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes(),"iso-8859-1") + ".xls");
          response.flushBuffer();
          workbook.write(response.getOutputStream());
      }catch (Exception e){
          e.printStackTrace();
      }
  }

    /**
     * 设置表头样式
     * @param wb
     * @return
     */

    public static CellStyle getHeaderStyle(Workbook wb) {
        CellStyle headerStyle = wb.createCellStyle();
        headerStyle.setBorderRight(BorderStyle.THIN); // 设置边框线
        headerStyle.setRightBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headerStyle.setAlignment(HorizontalAlignment.CENTER);// 设置居中
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 设置居中
        headerStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 16);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        return headerStyle;
    }

    /**
     * 设置单元格样式
     * @param wb
     * @return
     */
    public static CellStyle getCellStyle(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderRight(BorderStyle.THIN); // 设置边框线
        cellStyle.setRightBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setAlignment(HorizontalAlignment.CENTER);// 设置居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 设置居中
        Font titleFont = wb.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 14);
        cellStyle.setFont(titleFont);
        return cellStyle;
    }
    public static Cell addCell(Row row, int column, Object val, CellStyle style) {
        Cell cell = row.createCell(column);
        try {
            if (val == null) {
                cell.setCellValue("");
            } else if (val instanceof String) {
                cell.setCellValue((String) val);
            } else if (val instanceof Integer) {
                cell.setCellValue((Integer) val);
            } else if (val instanceof Long) {
                cell.setCellValue((Long) val);
            } else if (val instanceof Double) {
                cell.setCellValue((Double) val);
            } else if (val instanceof Float) {
                cell.setCellValue((Float) val);
            } else if (val instanceof Date) {
                cell.setCellValue(DateUtils.formatDate((Date) val, DateUtils.FORMAT3));
            } else if (val instanceof BigDecimal) {
                cell.setCellValue(((BigDecimal) val).doubleValue());
            } else if (val instanceof Object) {
                cell.setCellValue(val.toString());
            }
        } catch (Exception ex) {
            cell.setCellValue(val.toString());
        }
        cell.setCellStyle(style);
        return cell;
    }

}
