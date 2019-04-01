package com.example;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 解析 excel表格
 */

public class ParseExcelDemo {

    public static void main(String[] args) throws Exception {
        /*文件位置*/
        String fileName = "C:\\Users\\31474\\Desktop\\test.xlsx";
        Workbook workbook = null;
        if (fileName.endsWith(".xls")) {
            workbook = new HSSFWorkbook(new FileInputStream(new File(fileName)));
        } else if (fileName.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(new FileInputStream(new File(fileName)));
        } else {
            throw new Exception("Excel 文件格式错误");
        }
        parseExcel(workbook);
    }
    private static void parseExcel(Workbook workbook) {
        //获取第一个工作空间
        Sheet sheet = workbook.getSheetAt(0);
        //获取总行数
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            //获取每一行
            Row row = sheet.getRow(i);
            //求每一行的总单元格数量
            int cells = row.getPhysicalNumberOfCells();
            String rowValue = "";
            for (int i1 = 0; i1 < cells; i1++) {
                //获取每一列
                Cell cell = row.getCell(i1);
                //获取单元格的类型
                int cellType = cell.getCellType();
                switch (cellType) {
                    case Cell.CELL_TYPE_BLANK: //空白
                        rowValue += "\t";
                        break;
                    case Cell.CELL_TYPE_NUMERIC: //数字
                        rowValue += cell.getNumericCellValue() + "\t";
                        break;
                    case Cell.CELL_TYPE_STRING://字符串
                        rowValue += cell.getStringCellValue() + "\t";
                        break;
                    case Cell.CELL_TYPE_FORMULA://公式
                        rowValue += cell.getNumericCellValue() + "\t";
                        break;
                    case Cell.CELL_TYPE_BOOLEAN://boolean
                        rowValue += cell.getBooleanCellValue() + "\t";
                        break;
                    case Cell.CELL_TYPE_ERROR://错误
                        rowValue += "\t";
                        break;
                    default:
                        break;
                }
            }

            System.out.println(rowValue);


        }


    }


}
