package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetmealDao;
import com.itheima.domain.Setmeal;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;

    //新增套餐
    @Override
    public Result saveSetmeal(Setmeal setmeal, Integer[] checkGroupIds) {
        try {
            //新增表 t_setmeal
            setmealDao.addSetmeal(setmeal);
            //将图片存入Redis数据库中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());

            //新增中间表 t_setmeal_checkgroup
            for (Integer checkGroupId : checkGroupIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("checkGroupId",checkGroupId);
                map.put("setmealId",setmeal.getId());
                setmealDao.save_sc(map);
            }
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }

    }
    //分页查询
    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        //分页查询
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //获取查询条件
        String queryString = queryPageBean.getQueryString();
        if (queryString != null){
            queryString = queryString.trim();
        }
        //添加查询
        Page<Setmeal> page = setmealDao.findByPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Integer[] getCheckGroupFind(Integer id) {
        return setmealDao.getCheckGroupFind(id);
    }

    @Override
    public Setmeal getSetmeal(Integer id) {
        return setmealDao.getSetmeal(id);
    }

    @Override
    public Result deleteById(Integer id) {
        try {
            //删除中间表
            setmealDao.delete_sc(id);
            //删除套餐
            setmealDao.delete_setmeal(id);
            return new Result(true,"删除预约套餐成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,"删除预约套餐失败!");
        }

    }


}
