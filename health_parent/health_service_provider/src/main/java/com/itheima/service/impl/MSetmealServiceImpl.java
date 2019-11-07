package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MSetmealDao;
import com.itheima.domain.CheckGroup;
import com.itheima.domain.CheckItem;
import com.itheima.domain.Setmeal;
import com.itheima.service.MSetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service(interfaceClass = MSetmealService.class)
@Transactional
public class MSetmealServiceImpl implements MSetmealService {
    @Autowired
    private MSetmealDao mSetmealDao;
    //查询所有套餐
    @Override
    public List<Setmeal> findAll() {
        return mSetmealDao.findAll();
    }
    //根据id查询套餐
    @Override
    public Setmeal findById(Integer id) {
        //根据id查询 t_setmeal 和 t_checkGroup
        Setmeal setmeal = mSetmealDao.findById_sg(id);
        //获取 setmeal 中的id
        List<CheckGroup> checkGroups = setmeal.getCheckGroups();
        for (CheckGroup checkGroup : checkGroups) {
            Integer checkGroupId = checkGroup.getId();
            List<CheckItem> checkItemList = mSetmealDao.findById_gt(checkGroupId);
            checkGroup.setCheckItems(checkItemList);
        }
        return setmeal;
    }

    @Override
    public Setmeal findAllById_ccs(Integer id) {
        return mSetmealDao.findAllById_ccs(id);
    }
}
