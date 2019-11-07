package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.domain.CheckItem;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.CheckitemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 检查项
 */
@Controller
@RequestMapping("/checkitem")
@ResponseBody
public class CheckitemContorller {

    @Reference
    private CheckitemService checkitemService;

    //添加检查项
    @RequestMapping("/saveItem")
    public Result saveCheckItem(@RequestBody CheckItem checkItem) {
        return checkitemService.saveCheckItem(checkItem);
    }

    //分页查询
    @RequestMapping("/selectByPage")
    public PageResult selectById(@RequestBody QueryPageBean queryPageBean){
        return checkitemService.selectByPage(queryPageBean);
    }

    //根据id删除

    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        return checkitemService.deleteById(id);
    }

    //根据id查询
    @RequestMapping("/findById")
    public CheckItem findById(Integer id){
        return checkitemService.findById(id);
    }

    //修改检查项
    @RequestMapping("/updateCheckItem")
    public Result updateCheckItem(@RequestBody CheckItem checkItem){
        return checkitemService.updateCheckItem(checkItem);
    }

    //查询检查组中的检查项
    @RequestMapping("/findAll")
    public List<CheckItem> findAll() {
        return checkitemService.findAll();
    }

    //批量删除
    @RequestMapping("/deleteChecks")
    public Result deleteChecks(Integer[] params){
        return checkitemService.deleteChecks(params);
    }
}
