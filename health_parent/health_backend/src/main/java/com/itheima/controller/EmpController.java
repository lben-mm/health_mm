package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.Empservice;
import com.itheima.domain.Emp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/emp")
public class EmpController {

    @Reference
    private Empservice empservice;

    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/find")
    @ResponseBody
    public List<Emp> findAll() {
        return  empservice.findAll();
    }

    /**
     * 添加用户
     * @param emp
     */
    @RequestMapping("/save")
    @ResponseBody
    public String saveOne(@RequestBody Emp emp,ModelAndView modelAndView) {
        //empservice.saveOne(emp);
        System.out.println(emp);
        //modelAndView.setViewName("redirect:lists.html");
        return "ok";
    }

    /**
     * 根据id查询
     */
    @RequestMapping("/findById")
    @ResponseBody
    public Emp findById( Integer id){
        return  empservice.finaById(id);
    }

    /**
     * 修改用户
     */
    @RequestMapping("/updateEmp")
    public String updateEmp(@RequestBody Emp emp) {
        empservice.updateEmp(emp);
        return "redirect:lists.html";

    }

    /**
     * 删除用户
     */
    @RequestMapping("/delete")
    public String deleteById( Integer id) {
        empservice.deleteById(id);
       // return "redirect:/emp/find";
        return "redirect:lists.html";
    }





}
