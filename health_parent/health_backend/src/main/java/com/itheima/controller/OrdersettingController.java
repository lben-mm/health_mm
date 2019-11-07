package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.domain.OrderSetting;
import com.itheima.entity.Result;
import com.itheima.service.OrdersettingService;
import com.itheima.untils.POIUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 预约管理
 */
@RestController
@RequestMapping("/ordersetting")
public class OrdersettingController {

    @Reference
    private OrdersettingService ordersettingService;
    //上传文件
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile")MultipartFile excelFile){

    /*    //解析文件
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(excelFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取表
        XSSFSheet sheetAt = workbook.getSheetAt(0);
        //获取最后一行的索引
        int lastRowNum = sheetAt.getLastRowNum();
        for (int i = 1; i <= lastRowNum; i++) {
            //获取行对象
            XSSFRow row = sheetAt.getRow(i);
            XSSFCell cell_1 = row.getCell(1);
            Date dateCellValue = cell_1.getDateCellValue();
            XSSFCell cell_2 = row.getCell(2);
            int numericCellValue = (int) cell_2.getNumericCellValue();
            //创建对象
            OrderSetting orderSetting = new OrderSetting();
            orderSetting.setOrderDate(dateCellValue);
            orderSetting.setNumber(numericCellValue);*/


        List<String[]> strings = null;
        //创建新的集合
        List<OrderSetting> orderSettingList = new ArrayList<>();
        try {
            strings = POIUtils.readExcel(excelFile);
            for (String[] string : strings) {
                String s = string[0];
                String s1 = string[1];
                OrderSetting orderSetting = new OrderSetting();
                orderSetting.setOrderDate(new Date(s));
                orderSetting.setNumber(Integer.parseInt(s1));
                orderSettingList.add(orderSetting);
            }
            ordersettingService.upload(orderSettingList);
            return  new Result(true , MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }

    }

    //获取预约数据
    @RequestMapping("/getNumber")
    public List<Map> getNumber(String date){
        return ordersettingService.getNumber(date);
    }

    //手动设置预约人数
    @RequestMapping("/setNumber")
    public Result setNumber(@RequestBody OrderSetting orderSetting){
        return ordersettingService.setNumber(orderSetting);
    }

}
