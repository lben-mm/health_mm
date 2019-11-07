package com.itheima.dao;

import com.itheima.domain.Member;
import com.itheima.domain.Order;
import com.itheima.domain.OrderInfo;
import com.itheima.domain.OrderSetting;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.Map;

public interface OrderDao {
    //查询 t_ordersetting orderDate
    @Select("select * from t_ordersetting where orderDate = #{orderDate}")
    OrderSetting findByDate(Date orderDate);
    //根据手机号查询是否为会员 t_member
    @Select("select * from t_member where phoneNumber = #{telephone}")
    Member findByPhoneNumber(String telephone);
    //注册会员
    void saveMember(Member mem);
    //查询是否在同一天 约了 同一个 套餐
    @Select("select count(*) from t_order od , t_member tm where od.member_id = #{member_id} and od.setmeal_id = #{setmeal_id} and od.orderDate = #{orderDate} and tm.phoneNumber =#{phoneNumber} ")
    Long findMember(Map<String, Object> ms);
    //添加预约信息
    void insertOrder(Order order);
    //增加预约人数
    @Update("update t_ordersetting set reservations = #{reservations} where orderDate = #{orderDate}")
    void updateOrderSetting(Map<String, Object> mp);
    //套餐详情
    OrderInfo findOrderInfoById(Integer id);
}
