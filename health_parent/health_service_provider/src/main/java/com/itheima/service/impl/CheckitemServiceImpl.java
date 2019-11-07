package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.CheckitemDao;
import com.itheima.domain.CheckItem;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.CheckitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckitemService.class)
@Transactional
public class CheckitemServiceImpl implements CheckitemService {
    @Autowired
    private CheckitemDao checkitemDao;

    //添加检查项
    @Override
    public Result saveCheckItem(CheckItem checkItem) {
        try {
            checkitemDao.saveCheckItem(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }
    //分页查询
    @Override
    public PageResult selectByPage(QueryPageBean queryPageBean) {
        //设置分页参数
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

       /* //调用方法
        List<CheckItem> lists = checkitemDao.selectByPage(queryPageBean.getQueryString());
        //设置对象
        PageInfo<CheckItem> pageInfo = new PageInfo<CheckItem>(lists);*/
       //去空格
        String queryString = queryPageBean.getQueryString();
        if(queryString != null) {
           queryString = queryString.trim();
        }
        Page<CheckItem> pageInfo = checkitemDao.selectByPage(queryString);
        long total = pageInfo.getTotal();
        List<CheckItem> list = pageInfo.getResult();
        return new PageResult(total, list);
    }
    //根据id删除
    @Override
    public Result deleteById(Integer id) {
        try {
            //进行与检查组关联查询
            Long count = checkitemDao.selectByCheckItemId(id);
            if(count > 0){
                return new Result(false, "删除失败,该检查项存在于检查组中");
            }
            checkitemDao.deleteById(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }
    //根据id查询 用于修改回显数据
    @Override
    public CheckItem findById(Integer id) {
        return checkitemDao.findById(id);
    }
    //修改数据
    @Override
    public Result updateCheckItem(CheckItem checkItem) {
        try {
            checkitemDao.updateCheckItem(checkItem);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    //查询检查组中的检查项
    @Override
    public List<CheckItem> findAll() {
        return checkitemDao.findAll();

    }

    @Override
    public Result deleteChecks(Integer[] params) {
        try {

            for (Integer id : params) {
                //进行与检查组关联查询
                Long count = checkitemDao.selectByCheckItemId(id);
                if(count > 0){
                    return new Result(false, "删除失败,检查项存在于检查组中");
                }
                //没有关联 , 执行删除
                checkitemDao.deleteById(id);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, "删除成功");
    }
}
