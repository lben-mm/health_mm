package com.itheima.dao;

import com.itheima.domain.OrderSetting;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrdersettingDao {
    //根据日期查询
    @Select("select count(id) from t_ordersetting where orderDate = #{orderDate}")
    long findByDate(Date orderDate);
    //添加操作
    @Insert("insert into t_ordersetting values(#{id},#{orderDate},#{number},#{reservations})")
    void saveOrdersetting(OrderSetting orderSetting);

    //更新操作
    @Update("update t_ordersetting set number = #{number} , reservations = #{reservations} where orderDate = #{orderDate} ")
    void updateOrdersettig(OrderSetting orderSetting);
    //查询预约人数数据
    @Select(" SELECT * FROM t_ordersetting WHERE orderDate between #{start} and #{end}")
    List<OrderSetting> getNumber(Map date);
}
