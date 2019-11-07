package com.itheima.service;

import com.itheima.domain.CheckItem;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;

import java.util.List;

/**
 * 检查项
 */
public interface CheckitemService {
    //添加新增项
    public Result saveCheckItem(CheckItem checkItem);
    //分页查询
    PageResult selectByPage(QueryPageBean queryPageBean);

    //根据id删除
    Result deleteById(Integer id);

    //根据id查询
    CheckItem findById(Integer id);

    //修改检查项
    Result updateCheckItem(CheckItem checkItem);

    //查询检查组中的检查项
    List<CheckItem> findAll();
    //批量删除
    Result deleteChecks(Integer[] params);
}
