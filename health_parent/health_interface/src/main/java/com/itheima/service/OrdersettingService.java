package com.itheima.service;

import com.itheima.domain.OrderSetting;
import com.itheima.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface OrdersettingService {
    //上传文件
    void upload(List<OrderSetting> list);
    //获取预约数据
    List<Map> getNumber(String date);
    //手动设置预约人数
    Result setNumber(OrderSetting orderSetting);
}
