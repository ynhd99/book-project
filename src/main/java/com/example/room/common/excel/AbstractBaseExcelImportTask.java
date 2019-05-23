package com.example.room.common.excel;
import com.example.room.common.excel.util.POIUtil;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.utils.common.AirUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : scl
 * @Project: scm-sale
 * @Package com.choice.scm.sale.excel
 * @Description: 基本excel导入抽象类
 * @date Date : 2019年03月14日 13:27
 */
public abstract class AbstractBaseExcelImportTask {
    /**
     * 封装对象
     *
     * @return
     */
    public abstract Map<Integer, ExcelMetaData> initMetaData();

    /**
     * 执行
     *
     * @param file
     * @param clas
     * @return
     */
    public ExcelImportMessage execute(MultipartFile file, Class clas) {
        //基础处理
        ExcelImportMessage excelImportMessage = baseHandleExcel(file, clas);
        //业务处理
        businessHandleExcel(excelImportMessage);
        if (AirUtils.hv(excelImportMessage.getErrorList())) {
            excelImportMessage.setUploadStatus(false);
            excelImportMessage.setCorrectList(null);
            //结果排序
            POIUtil.ExcelResultSort(excelImportMessage);
            return excelImportMessage;
        }
        excelImportMessage.setImportMsg(excelImportMessage.getCorrectList().size());
        excelImportMessage.setCorrectList(null);
        return excelImportMessage;
    }

    /**
     * 基础校验，包括重复，格式，表头信息，解析出实体类，添加颜色
     *
     * @param file
     * @return
     */
    public ExcelImportMessage baseHandleExcel(MultipartFile file, Class<?> clas) {
        ExcelImportMessage excelImportMessage = new ExcelImportMessage();
        excelImportMessage.setUploadStatus(true);
        List<String> errorList = new ArrayList<>();
        excelImportMessage.setErrorList(errorList);
        Map<Integer, ExcelMetaData> map = initMetaData();
        Workbook workbook = POIUtil.checkFileAndWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        try {
            //解析sheet页，把错误信息以及对象信息存储下来
            parseSheet(sheet, map, excelImportMessage, clas);
        } catch (Exception e) {
            throw new SaleBusinessException("解析文件出错！");
        } finally {
            //清空workbook
            workbook = null;
        }
        //如果为空  去除空行后没有数据
        if(!AirUtils.hv(excelImportMessage.getCorrectList())){
            throw new SaleBusinessException("导入信息不能为空");
        }
        return excelImportMessage;
    }

    /**
     * 业务处理抽象方法，需要业务去实现的
     *
     * @param excelImportMessage
     */
    public abstract void businessHandleExcel(ExcelImportMessage excelImportMessage);

    /**
     * 初始化去重对象
     *
     * @param map
     * @return
     */
    public Map<Integer, Map<String, Integer>> initRepeatMap(Map<Integer, ExcelMetaData> map) {
        Map<Integer, Map<String, Integer>> repeatMap = new HashMap<>();
        for (Map.Entry<Integer, ExcelMetaData> entry : map.entrySet()) {
            if (!entry.getValue().isRepeated()) {
                repeatMap.put(entry.getKey(), new HashMap<>());
            }
        }
        return repeatMap;
    }

    /**
     * 解析sheet页面获取数据
     *
     * @param sheet
     * @param map
     * @param excelImportMessage
     * @param clas
     * @return
     * @throws Exception
     */
    public void parseSheet(Sheet sheet, Map<Integer, ExcelMetaData> map, ExcelImportMessage excelImportMessage, Class<?> clas) throws Exception {
        int rowNum = sheet.getLastRowNum();
        List<Object> objectList = new ArrayList<>();
        //初始化判重对象
        Map<Integer, Map<String, Integer>> repeatMap = initRepeatMap(map);
        for (int i = 1; i <= rowNum; i++) {
            Row row = sheet.getRow(i);
            //判断空行
            if (POIUtil.isRowEmpty(row)) {
                continue;
            }
            //解析数据
            Object o = parseData(row, map, repeatMap, excelImportMessage, clas);
            //添加数据
            objectList.add(o);
        }
        excelImportMessage.setCorrectList(objectList);
    }

    /**
     * 解析数据，包括验证数据的重复性以及格式。
     *
     * @param row
     * @param map
     * @param repeatMap
     * @param excelImportMessage
     * @param clas
     * @return
     * @throws Exception
     */
    public Object parseData(Row row, Map<Integer, ExcelMetaData> map, Map<Integer, Map<String, Integer>> repeatMap,
                            ExcelImportMessage excelImportMessage, Class<?> clas) throws Exception {
        Object o = clas.newInstance();
        int rowNumb = row.getRowNum() + 1;
        for (Map.Entry<Integer, ExcelMetaData> entry : map.entrySet()) {
            Cell cell = row.getCell(entry.getKey());
            ExcelMetaData excelMetaData = entry.getValue();
            //检查数据格式
            String message = POIUtil.checkCell(cell, rowNumb, entry.getKey(), excelMetaData);
            if (AirUtils.hv(message)) {
                excelImportMessage.getErrorList().add(message);
            }
            //如果为空执行跳过
            if (null == cell) {
                continue;
            }
            String repeatMessage;
            String value = cell.getStringCellValue().trim();
            //检查是否重复
            if (!excelMetaData.isRepeated()) {
                repeatMessage = repeatCheck(cell, repeatMap, entry.getKey(), excelMetaData.getTitle());
                if (AirUtils.hv(repeatMessage)) {
                    excelImportMessage.getErrorList().add(repeatMessage);
                }
            }
            //解析数据，如果有错误信息不会解析
            if (!AirUtils.hv(message) && AirUtils.hv(excelMetaData.getName()) && AirUtils.hv(value)) {
                //赋值
                BeanUtils.setProperty(o, excelMetaData.getName(), value);
            }
        }
        //添加行号
        BeanUtils.setProperty(o, ExcelConstants.ROW_NAME, row.getRowNum() + 1);
        //是否考虑去掉这一行数据,如果是需要判断
        return o;
    }


    /**
     * 判断是否重复
     *
     * @param cell
     * @param repeatMap
     * @param key
     * @return
     */
    private String repeatCheck(Cell cell, Map<Integer, Map<String, Integer>> repeatMap, Integer key, String title) {
        if (!AirUtils.hv(cell.getStringCellValue().trim())) {
            return null;
        }
        StringBuilder errorMessage = new StringBuilder();
        Integer row = repeatMap.get(key).get(cell.getStringCellValue());
        if (AirUtils.hv(row)) {
            errorMessage.append("第");
            errorMessage.append(cell.getAddress().getRow() + 1);
            errorMessage.append("行第");
            errorMessage.append(cell.getAddress().getColumn() + 1);
            errorMessage.append("列“");
            errorMessage.append(title);
            errorMessage.append("”与第");
            errorMessage.append(row);
            errorMessage.append("行第");
            errorMessage.append(cell.getAddress().getColumn() + 1);
            errorMessage.append("列“");
            errorMessage.append(title);
            errorMessage.append("”重复");
        } else {
            repeatMap.get(key).put(cell.getStringCellValue(), cell.getAddress().getRow() + 1);
        }
        return errorMessage.toString();
    }

}
