package com.itheima.dao;

import com.itheima.domain.CheckItem;
import com.itheima.domain.Setmeal;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MSetmealDao {
    //查询体检套餐所有
    @Select("select * from t_setmeal")
    List<Setmeal> findAll();
    //根据id查询 t_checkitem 和 t_checkgroup
    @Select("select  tk.name   from  t_checkitem  tk , t_checkgroup_checkitem cc , t_checkgroup tg where tk.id = cc.checkitem_id and cc.checkgroup_id = tg.id and tg.id = #{id}")
    List<CheckItem> findById_gt(Integer id);

    //根据id查询 t_setmeal 和 t_checkGroup
    Setmeal findById_sg(Integer id);

    //根据id查询所有 t_setmeal  t_checkGroup t_checkitem
    Setmeal findAllById_ccs(Integer id);
}
