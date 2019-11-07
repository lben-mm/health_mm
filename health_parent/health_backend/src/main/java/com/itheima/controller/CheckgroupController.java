package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.domain.CheckGroup;
import com.itheima.domain.CheckItem;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.CheckgroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 检查组
 */
@RestController
@RequestMapping("/checkGroup")
public class CheckgroupController {
    @Reference
    private CheckgroupService checkgroupService;
    //分页查询
    @RequestMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean){
        return checkgroupService.findByPage(queryPageBean);
    }

    //添加检查组
    @RequestMapping("/saveCheckGroup")
    public Result saveCheckGroup(@RequestBody CheckGroup checkGroup , Integer[] checkItemIds){
        return checkgroupService.saveCheckGroup(checkGroup,checkItemIds);
    }

    //获取复选框选中情况
    @RequestMapping("/findCheckBoxById")
    public List<Integer> findCheckBoxById(Integer id){
        return checkgroupService.findCheckBoxById(id);
    }

    //回显检查组的数据
    @RequestMapping("/findCheckGroupById")
    public CheckGroup findCheckGroupById(Integer id){
        return checkgroupService.findCheckGroupById(id);
    }

    //修改检查组
    @RequestMapping("/updateCheckGroup")
    public Result updateCheckGroup(@RequestBody CheckGroup checkGroup ,Integer[] checkItemIds){
        return checkgroupService.updateCheckGroup(checkGroup,checkItemIds);
    }

    //根据id删除数据
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        return checkgroupService.deleteById(id);
    }

    //获取所有的检查组
    @RequestMapping("/findAllCheckGroup")
    public List<CheckGroup> findAllCheckGroup(){
        return checkgroupService.findAllCheckGroup();
    }

    //根据id查询检查项
    @RequestMapping("/selectCheckItem")
    public List<CheckItem> selectCheckItem(Integer id){
        return  checkgroupService.selectCheckItem(id);
    }
}
