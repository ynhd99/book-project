package com.example.room.utils.common;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
}
