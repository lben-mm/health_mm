package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.domain.Setmeal;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface SetmealDao {
    
    void addSetmeal(Setmeal setmeal);

    void save_sc(Map<String, Integer> map);
    //分页查询
    Page<Setmeal> findByPage(String value);
    @Select("select checkgroup_id from t_setmeal_checkgroup where setmeal_id = #{id}")
    Integer[] getCheckGroupFind(Integer id);
    @Select("select * from t_setmeal where id = #{id}")
    Setmeal getSetmeal(Integer id);
    @Delete("delete from t_setmeal_checkgroup  where setmeal_id = #{id} ")
    void delete_sc(Integer id);
    @Delete("delete from t_setmeal  where id = #{id} ")
    void delete_setmeal(Integer id);
}
