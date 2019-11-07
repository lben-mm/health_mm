package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.domain.CheckItem;
import com.itheima.entity.Result;

import java.util.List;

/**
 * 检查项
 */
public interface CheckitemDao {

    //新增检查项
    public void saveCheckItem(CheckItem checkItem);

    //分页查询
    Page<CheckItem> selectByPage(String queryString);
   // Page<CheckItem> selectByPage(String queryString);
    //根据id删除检查项
    void deleteById(Integer id);
    //根据id查询数据
    CheckItem findById(Integer id);
    //修改检查项
    void updateCheckItem(CheckItem checkItem);
    //根据checkItem id 查询中间表 t_checkgroup_checkitem
    Long selectByCheckItemId(Integer id);
    //查询检查组中检查项
    List<CheckItem> findAll();
}
