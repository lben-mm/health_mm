package com.itheima.service;

import com.itheima.domain.OrderInfo;
import com.itheima.entity.Result;

import java.util.Map;

public interface OrderService  {
    //体检预约
    Result orderSet(Map map) throws Exception;
    //预约详情
    OrderInfo findOrderInfoById(Integer id);
}
