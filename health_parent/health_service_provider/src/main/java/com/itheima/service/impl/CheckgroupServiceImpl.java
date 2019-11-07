package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.CheckgroupDao;
import com.itheima.domain.CheckGroup;
import com.itheima.domain.CheckItem;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.CheckgroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckgroupService.class)
@Transactional
public class CheckgroupServiceImpl implements CheckgroupService {
    @Autowired
    private CheckgroupDao checkgroupDao;

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        //进行分页查询
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //除去空白
        String queryString = queryPageBean.getQueryString();
        if(queryString != null){
            queryString = queryString.trim();
        }

        Page<CheckGroup> page = checkgroupDao.findByPage(queryString);
        //Page<CheckGroup> page = checkgroupDao.findByPage_cc(queryString);
        //Long count = checkgroupDao.selectCount();
        return new PageResult(page.getTotal(),page.getResult());
    }
    //添加检查组
    @Override
    public Result saveCheckGroup(CheckGroup checkGroup, Integer[] checkItemIds) {
        try {
            //添加checkGroup
            checkgroupDao.addCheckGroup(checkGroup);
            //获取新增id
            Integer checkGroupId = checkGroup.getId();
            //添加表t_checkgroup_checkitem
            for (Integer checkItemId : checkItemIds) {
                Map<String,Integer> addMap = new HashMap<>();
                addMap.put("checkItem",checkItemId);
                addMap.put("checkGroup",checkGroupId);
                checkgroupDao.add_ItemId_GroupId(addMap);

            }
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
    }
    //回显检查项复选框
    @Override
    public List<Integer> findCheckBoxById(Integer id) {

        return checkgroupDao.findCheckBoxById(id);
    }
    //回显检查组数据
    @Override
    public CheckGroup findCheckGroupById(Integer id) {
        return checkgroupDao.findCheckGroupById(id);
    }
    //修改检查组数据
    @Override
    public Result updateCheckGroup(CheckGroup checkGroup, Integer[] checkItemIds) {
        try {
            //修改检查组
            checkgroupDao.updateCheckGroup(checkGroup);
            //删除关联表
            checkgroupDao.delete_cc_ById(checkGroup.getId());
            //添加关联表
            //添加表t_checkgroup_checkitem
            for (Integer checkItemId : checkItemIds) {
                Map<String,Integer> addMap = new HashMap<>();
                addMap.put("checkItem",checkItemId);
                addMap.put("checkGroup",checkGroup.getId());
                checkgroupDao.add_ItemId_GroupId(addMap);
            }
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
    }

    //根据id删除检查组信息
    @Override
    public Result deleteById(Integer id) {
        try {
            //查询关联表 t_setmeal_checkgroup
            Long count = checkgroupDao.select_sc(id);
            if (count > 0){
                return new Result(false,"删除失败,该检查组存在于检查套餐中!");
            }
            //删除中间表的关联关系
            checkgroupDao.delete_cc_ById(id);
            //删除检查组
            checkgroupDao.deleteById(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    @Override
    public List<CheckGroup> findAllCheckGroup() {
        return checkgroupDao.findAllCheckGroup();
    }
    //根据id查询检查项
    @Override
    public List<CheckItem> selectCheckItem(Integer id) {
        return checkgroupDao.selectCheckItem(id);
    }
}
