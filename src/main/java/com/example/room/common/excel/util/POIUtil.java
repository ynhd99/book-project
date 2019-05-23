package com.example.room.common.excel.util;

import com.example.room.common.excel.ExcelConstants;
import com.example.room.common.excel.ExcelImportMessage;
import com.example.room.common.excel.ExcelMetaData;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.utils.common.AirUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/**
 * @author : scl
 * @Project: scm-sale
 * @Package com.choice.scm.sale.common.util
 * @Description: poi excel 工具类
 * @date Date : 2019年03月14日 13:38
 */
public class POIUtil {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 检测文件是否合法,并返回相关信息
     *
     * @param file
     * @return
     */

    public static Workbook checkFileAndWorkbook(MultipartFile file) {
        Workbook workbook;
        if (!AirUtils.hv(file)) {
            throw new SaleBusinessException("文件不存在！");
        }
        if (!AirUtils.hv(file.getOriginalFilename())) {
            throw new SaleBusinessException("文件名称不能为空！");
        }
        String fileName = file.getOriginalFilename().toLowerCase();
        if (!fileName.endsWith(ExcelConstants.FILE_TYPE.XLS) && !fileName.endsWith(ExcelConstants.FILE_TYPE.XLSX)) {
            throw new SaleBusinessException("只允许导入后缀为xlsx、xls的Excel文件");
        }
        try {
            workbook = WorkbookFactory.create(file.getInputStream());
        } catch (Exception e) {
            throw new SaleBusinessException("读取文件 {} 出错", fileName);
        }
        int sheetNum = workbook.getNumberOfSheets();
        if (sheetNum <= 0) {
            throw new SaleBusinessException("导入信息不能为空");
        }
        Sheet sheet = workbook.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        if (rowNum <= 0) {
            throw new SaleBusinessException("导入信息不能为空");
        }
        return workbook;
    }

    /**
     * 检查excel表头信息
     *
     * @param sheet
     * @param map
     */
    public static void checkFileHeaderAndSheet(Sheet sheet, Map<Integer, ExcelMetaData> map, String moduleName) {

        Row row = sheet.getRow(0);
        int coloumNum = row.getPhysicalNumberOfCells();
        if (coloumNum != map.size()) {
            throw new SaleBusinessException("导入Excel表列数量与模板不一致，请重新下载【" + moduleName + "批量导入模板】进行操作");
        }
        for (int j = 0; j < coloumNum; j++) {
            if (!map.get(j).getTitle().equalsIgnoreCase(row.getCell(j).getStringCellValue())) {
                throw new SaleBusinessException("导入Excel表列名称与模板不一致，请重新下载【" + moduleName + "批量导入模板】进行操作");
            }
        }
    }

    /**
     * 解析单元格
     *
     * @param cell
     * @param key
     * @param metaData
     * @return
     */
    public static String checkCell(Cell cell, int rowNumb, int key, ExcelMetaData metaData) {

        String cellValue = null;
        StringBuilder errorList = new StringBuilder();
        //判断cell是否为空
        if (null != cell) {
            convertCellToStringType(cell);
            cellValue = cell.getStringCellValue().trim();
        } else if (metaData.isRequired()) {
            errorList.append("第");
            errorList.append(rowNumb);
            errorList.append("行第");
            errorList.append(key + 1);
            errorList.append("列“");
            errorList.append(metaData.getTitle());
            errorList.append("”没有填写");
            return errorList.toString();
        }
        //1，如果数据为空，且要求不能为空 2，如果数据为空且可以为空直接返回
        if (POIUtil.checkRequired(cellValue, metaData.isRequired())) {
            errorList.append("第" + rowNumb + "行第" + (key + 1) + "列“" + metaData.getTitle());
            errorList.append("”没有填写");
            return errorList.toString();
        } else if (!AirUtils.hv(cellValue) && !metaData.isRequired()) {
            return null;
        }
        //查看数据长度要求是不是符合要求，如果不穿参数默认为0,不校验直接返回true 以及正则
        if (!POIUtil.checkLength(cellValue, metaData.getMinlength(), metaData.getMaxlength()) ||
                !POIUtil.checkRgExpression(cellValue, metaData.getRgExpression())) {
            errorList.append("第" + rowNumb + "行第" + (key + 1) + "列“" + metaData.getTitle());
            errorList.append("”输入数据格式不符合要求");
            return errorList.toString();
        }
        return null;
    }

    /**
     * 判断是否可以为空
     *
     * @param cellValue
     * @param required
     * @return
     */
    public static boolean checkRequired(String cellValue, boolean required) {
        if (!AirUtils.hv(cellValue) && required) {
            return true;
        }
        return false;
    }

    /**
     * 校验字符长度
     *
     * @param value
     * @param minimum
     * @param maximum
     * @return
     */
    public static boolean checkLength(String value, int minimum, int maximum) {
        if (maximum <= 0 && minimum <= 0) {
            return true;
        }
        int length = value.length();
        if (length >= minimum && length <= maximum) {
            return true;
        }
        return false;
    }

    /**
     * 正则表达式检查
     *
     * @param value
     * @param rgExpression
     * @return
     */
    public static boolean checkRgExpression(String value, String rgExpression) {
        if (!AirUtils.hv(rgExpression)) {
            return true;
        }
        return value.matches(rgExpression);
    }

    /**
     * 判断是否事空行
     *
     * @param row
     * @return
     */
    public static boolean isRowEmpty(Row row) {
        if (!AirUtils.hv(row)) {
            return true;
        }
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if(cell == null){
                continue;
            }
            convertCellToStringType(cell);
            if (cell != null && AirUtils.hv(cell.getStringCellValue())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 数据强转
     *
     * @param obj
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    /**
     * 把cell统一转换成String 类型
     *
     * @param cell
     */
    public static void convertCellToStringType(Cell cell) {
        //时间格式是不是需要验证的
        if (cell.getCellTypeEnum() != CellType.STRING && ExcelConstants.DATE_FORMAT_YYYYMMDD.equalsIgnoreCase(cell.getCellStyle().getDataFormatString()) && cell.getCellTypeEnum() != CellType.BLANK) {
            String date = sdf.format(cell.getDateCellValue());
            cell.setCellType(CellType.STRING);
            cell.setCellValue(date);
        } else {
            cell.setCellType(CellType.STRING);
        }
    }
    /**
     * 根据行号排序
     *
     * @param excelImportMessage
     */
    public static void ExcelResultSort(ExcelImportMessage excelImportMessage) {
        //排序
        Collections.sort(excelImportMessage.getErrorList(), new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.parseInt(s1.split("第")[1].split("行")[0]) - Integer.parseInt(s2.split("第")[1].split("行")[0]);
            }
        });
    }
}
