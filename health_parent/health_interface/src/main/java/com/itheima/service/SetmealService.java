package com.itheima.service;

import com.itheima.domain.Setmeal;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;

public interface SetmealService {
    //新增套餐管理
    Result saveSetmeal(Setmeal setmeal, Integer[] checkGroupIds);
    //分页查询
    PageResult findByPage(QueryPageBean queryPageBean);
    //回显编辑 复选框的数据
    Integer[] getCheckGroupFind(Integer id);
    //回显套餐数据
    Setmeal getSetmeal(Integer id);
    //删除套餐
    Result deleteById(Integer id);
}
