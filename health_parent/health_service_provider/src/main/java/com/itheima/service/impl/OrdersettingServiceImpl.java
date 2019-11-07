package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.OrdersettingDao;
import com.itheima.domain.OrderSetting;
import com.itheima.entity.Result;
import com.itheima.service.OrdersettingService;
import com.itheima.untils.POIUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service(interfaceClass = OrdersettingService.class)
@Transactional
public class OrdersettingServiceImpl implements OrdersettingService {
    @Autowired
    private OrdersettingDao ordersettingDao;
    //上传文件
    @Override
    public void upload(List<OrderSetting> list) {
        //调用持久层 ,添加
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                t_ordersetting(orderSetting);
            }
        }

    }
    //获取预约人数数据
    @Override
    public List<Map> getNumber(String date) {
        //设置日期
        String start = date+"-1";
        String end = date +"-31";
        Map<String,Object> m = new HashMap<>();
        m.put("start",start);
        m.put("end",end);
        //调用dao层
       List<OrderSetting> list =  ordersettingDao.getNumber(m);
       //封装数据
        List<Map> maps = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
           Map<String,Object> map = new HashMap<>();
           map.put("date",orderSetting.getOrderDate().getDate());
           map.put("number",orderSetting.getNumber());
           map.put("reservations",orderSetting.getReservations());
           maps.add(map);
        }
        return maps;
    }
    //手动设置预约人数
    @Override
    public Result setNumber(OrderSetting orderSetting) {
        try {
            t_ordersetting(orderSetting);
            return new Result(true,"设置预约人数成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false ,"设置预约人数失败!");
        }
    }
    //对表t_ordersetting进行操作
    public void t_ordersetting(OrderSetting orderSetting){
        long count = ordersettingDao.findByDate(orderSetting.getOrderDate());

        if (count > 0) {
            //如果存在 执行更新操作
            ordersettingDao.updateOrdersettig(orderSetting);
            System.out.println("hhhhhhhhhhhhhhhh");

        } else {
            //如果不存在 执行添加操作
            ordersettingDao.saveOrdersetting(orderSetting);
            System.out.println("哈哈哈哈哈哈哈哈哈哈或或或或或或或或或");
        }
    }
}
