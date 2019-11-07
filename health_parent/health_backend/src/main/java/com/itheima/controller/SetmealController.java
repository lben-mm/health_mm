package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.domain.Setmeal;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.SetmealService;
import com.itheima.untils.QiniuUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;
    //图片上传
    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile){

        //获取文件后缀名
        String extension = FilenameUtils.getExtension(imgFile.getOriginalFilename());

        //设置文件名
        String name = UUID.randomUUID().toString()+"."+extension;
        System.out.println("上传文件名"+name);
        //使用工具类 上传文件
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),name);
            //上传成功,将图片存入Redis缓存
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,name);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,name);
    }

    //添加套餐
    @RequestMapping("/saveSetmeal")
    public Result saveSetmeal(@RequestBody Setmeal setmeal , Integer[] checkGroupIds){
        return setmealService.saveSetmeal(setmeal,checkGroupIds);
    }

    //分页查询
    @RequestMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.findByPage(queryPageBean);
    }

    //获取检查组选择
    @RequestMapping("/getCheckGroupFind")
    public Integer[] getCheckGroupFind(Integer id){
        return  setmealService.getCheckGroupFind(id);
    }

    //回显编辑框数据
    @RequestMapping("/getSetmeal")
    public Setmeal getSetmeal(Integer id){
        return setmealService.getSetmeal(id);
    }

    //删除套餐
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        return setmealService.deleteById(id);
    }

}
