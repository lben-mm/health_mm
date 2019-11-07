package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.domain.CheckGroup;
import com.itheima.domain.CheckItem;

import java.util.List;
import java.util.Map;

public interface CheckgroupDao {

    //分页查询
    public Page<CheckGroup> findByPage(String queryString);
    //多表查询
    public Page<CheckGroup> findByPage_cc(String queryString);
    //public List<CheckGroup> findByPage_cc(String queryString);
    //添加checkGroup
    void addCheckGroup(CheckGroup checkGroup);
    //添加中间表 t_checkgroup_checkitem
    void add_ItemId_GroupId(Map<String, Integer> addMap);
    //复选框选中情况
    List<Integer> findCheckBoxById(Integer id);
    //回显检查组数据
    CheckGroup findCheckGroupById(Integer id);
    //修改检查组
    void updateCheckGroup(CheckGroup checkGroup);
    //删除中间表关联关系
    void delete_cc_ById(Integer id);
    //删除检查组
    void deleteById(Integer id);
    //查询中间表t_setmeal_checkgroup
    Long select_sc(Integer id);
    //根据id查询检查项
    List<CheckItem> selectCheckItem(Integer id);

    List<CheckGroup> findAllCheckGroup();
}
