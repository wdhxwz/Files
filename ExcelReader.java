package com.duowan.pointSystem.util.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/8/3 15:57
 * @Description: Excel文件读取
 */
public class ExcelReader {
    private Workbook workbook;

    /**
     * 功能描述: 表头行,默认为第一行，-1 表示没有表头行
     */
    private int columnHeaderRow = 0;

    private Map<String,String> columnNameMap = new HashMap<>();

    public ExcelReader(String filePath) throws IOException, InvalidFormatException {
        this(new FileInputStream(filePath));
    }

    public ExcelReader(InputStream inputStream) throws IOException, InvalidFormatException {
        Assert.notNull(inputStream,"文件不能为空");
        workbook = WorkbookFactory.create(inputStream);
    }

    /**
     * 功能描述: 设置表头行，-1 表示没有表头行，数据从下一行开始读取
     */
    public ExcelReader setColumnHeaderRow(int columnHeaderRow){
        this.columnHeaderRow = columnHeaderRow;

        return this;
    }

    /**
     * 功能描述: 设置表头映射
     */
    public ExcelReader setColumnNameMap(Map<String,String> columnNameMap){
        Assert.notNull(columnNameMap,"表头映射不能为空");
        this.columnNameMap = columnNameMap;
        return this;
    }

//    public Map<String,String> getData(int sheetIndex){
//        Sheet sheet = workbook.getSheetAt(sheetIndex);
//        Map<String,Integer> headerMap =  new HashMap<>();
//        int columnCount = 0;
//        if(columnHeaderRow > -1){
//            Row row = sheet.getRow(columnHeaderRow);
//            columnCount = row.getLastCellNum();
//            for( int columnIndex = 0;columnCount < columnCount;columnIndex ++){
//                if(columnNameMap.containsKey(row.getCell(columnIndex).getStringCellValue())){
//                    headerMap.put(columnNameMap.get(row.getCell(columnIndex).getStringCellValue()),columnIndex);
//                }
//            }
//        }
//
//        if(headerMap.isEmpty()){
//            return null;
//        }
//
//        int beginRowIndex = columnHeaderRow + 1 >= 0 ? columnHeaderRow + 1 : 0;
//        int lastRow = sheet.getLastRowNum();
//        Map<String,String> dataMap = new HashMap<>();
//        Row row = null;
//        for(int rowIndex = beginRowIndex;rowIndex < lastRow;rowIndex ++){
//            row = sheet.getRow(rowIndex);
//            for (Map.Entry<String,Integer> entry: headerMap.entrySet()) {
//
//            }
//        }
//
//        return dataMap;
//    }

    public List<List<String>> getData(int sheetIndex){
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        List<List<String>> dataList = new ArrayList<>();
        int lastRow = sheet.getLastRowNum();
        Row row = sheet.getRow(0);
        int lastColumn = row.getLastCellNum();
        for(int rowIndex = 0;rowIndex < lastRow;rowIndex ++){
            row = sheet.getRow(rowIndex);
            List<String> rowData = new ArrayList<>();
            for(int columnIndex = 0;columnIndex < lastColumn;columnIndex ++){
                rowData.add(getCellValue(row.getCell(columnIndex)));
            }
            dataList.add(rowData);
        }

        return dataList;
    }

    private String getCellValue(Cell cell){
       try{
           return  cell.getStringCellValue();
       }catch (Exception ex){
           return  cell.toString();
       }
    }







}