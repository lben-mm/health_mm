package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.domain.Setmeal;
import com.itheima.service.MSetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal_me")
public class MSetmealController {
    @Reference
    private MSetmealService mSetmealService;

    //查询体检套餐
    @RequestMapping("/findAll")
    public List<Setmeal> findAll(){
        return mSetmealService.findAll();
    }

    //根据id查询套餐
    @RequestMapping("/findById")
    public Setmeal findById(Integer id){
        // mSetmealService.findById(id);

        return mSetmealService.findAllById_ccs(id);
    }
}
