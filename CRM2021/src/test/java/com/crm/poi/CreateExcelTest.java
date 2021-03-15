package com.crm.poi;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class CreateExcelTest {
    public static void main(String[] args) throws  Exception{
        //1.创建一个工作薄，对应一个excel文件
        HSSFWorkbook wb=new HSSFWorkbook();

        //2.创建一页
        HSSFSheet sheet=wb.createSheet("学生列表");
        //3.行
        HSSFRow row=sheet.createRow(0); //从0
        //4.第一列
        HSSFCell cell=row.createCell(0);
        cell.setCellValue("学号");

        cell=row.createCell(1);
        cell.setCellValue("姓名");

        cell=row.createCell(2);
        cell.setCellValue("年龄");

        //4.创建样式对象
        HSSFCellStyle style=wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        //5.填写数据
        for(int i=1;i<10;i++){
            row=sheet.createRow(i); //从第2行走数据
            cell=row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue(i);

            cell=row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue("tom"+i);

            cell=row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue(20+i);
        }

        //6.使用输出流对象
        OutputStream os=new FileOutputStream("d:\\stduent.xls");
        wb.write(os); //输出excel
        os.close();
        wb.close();
        System.out.println("edn");
    }


}
