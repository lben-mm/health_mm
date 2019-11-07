package com.itheima.service;

import com.itheima.domain.CheckGroup;
import com.itheima.domain.CheckItem;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;

import java.util.List;

public interface CheckgroupService {

    //分页查询
    public PageResult findByPage(QueryPageBean queryPageBean);
    //添加检查组
    Result saveCheckGroup(CheckGroup checkGroup, Integer[] checkItemIds);
    //获取复选框选中情况
    List<Integer> findCheckBoxById(Integer id);
    //回显检查组数据
    CheckGroup findCheckGroupById(Integer id);
    //修改检查组
    Result updateCheckGroup(CheckGroup checkGroup, Integer[] checkItemIds);
    //根据id删除
    Result deleteById(Integer id);
    //查询所有检查组
    List<CheckGroup> findAllCheckGroup();
    //根据id查询检查项
    List<CheckItem> selectCheckItem(Integer id);
}
