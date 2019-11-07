package com.itheima.dao;

import com.itheima.domain.Emp;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface EmpDao {

    //查询
    @Select("select * from emp")
    public List<Emp> findAll();

    //增加
    @Insert("insert into emp values(#{id},#{name},#{salary},#{birthday})")
    public void saveOne(Emp emp);

    //根据id获取信息
    @Select("select * from emp where id=#{id}")
    public Emp finaById(int id);

    //修改
    @Update("update emp set name=#{name},salary=#{salary},birthday=#{birthday} where id=#{id}")
    public void updateEmp(Emp emp);

    //删除
    @Delete("delete from emp where id=#{id}")
    public void deleteById(int id);




}
