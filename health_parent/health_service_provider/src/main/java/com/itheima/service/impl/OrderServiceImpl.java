package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.OrderDao;
import com.itheima.domain.Member;
import com.itheima.domain.Order;
import com.itheima.domain.OrderInfo;
import com.itheima.domain.OrderSetting;
import com.itheima.entity.Result;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Override
    public Result orderSet(Map map) throws Exception {
        //判断预约是否开通,人数是否满足
        OrderSetting orderSetting = orderDao.findByDate(new SimpleDateFormat("yyyy-MM-dd").parse((String) map.get("orderDate")));
        //判断是否存在预约设置
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //判断人数是否满足
        if (orderSetting.getNumber() <= orderSetting.getReservations()) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        //根据手机号查询是否为会员 t_member
        Member member = orderDao.findByPhoneNumber((String) map.get("telephone"));

        //获取会员id
        Integer memId = null;

        if (member == null) {
            //不是会员 注册会员
            Member mem = new Member();
            mem.setName((String) map.get("name"));
            mem.setSex((String) map.get("sex"));
            mem.setIdCard((String) map.get("idCard"));
            mem.setPhoneNumber((String) map.get("telephone"));
            mem.setRegTime(new SimpleDateFormat("yyyy-MM-dd").parse((String) map.get("orderDate")));
            orderDao.saveMember(mem);
            memId = mem.getId();
        } else {
            //查询是否 是同一个人 在同一天 约了 同一个 套餐
            Map<String, Object> ms = new HashMap<>();
            memId = member.getId();
            ms.put("member_id", memId);
            ms.put("setmeal_id", Integer.parseInt((String) map.get("setmealId")));
            ms.put("orderDate", new SimpleDateFormat("yyyy-MM-dd").parse((String) map.get("orderDate")));
            ms.put("phoneNumber",(String) map.get("telephone"));
            Long count = orderDao.findMember(ms);
            if (count > 0) {
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }

        //进行预约设置
        Order order = new Order();
        order.setMemberId(memId);
        order.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").parse((String) map.get("orderDate")));
        order.setOrderType(String.valueOf(map.get("orderType")));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        orderDao.insertOrder(order);
        //增加预约人数  orderSetting.getReservations()
        Map<String,Object> mp = new HashMap<>();
        mp.put("orderDate",new SimpleDateFormat("yyyy-MM-dd").parse((String) map.get("orderDate")));
        mp.put("reservations",orderSetting.getReservations()+1);
        orderDao.updateOrderSetting(mp);
        return new Result(true, MessageConstant.ORDER_SUCCESS, order.getId());


    }

    @Override
    public OrderInfo findOrderInfoById(Integer id) {
        return orderDao.findOrderInfoById(id);
    }
}
