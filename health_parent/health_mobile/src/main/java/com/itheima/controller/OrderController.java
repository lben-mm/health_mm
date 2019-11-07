package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.domain.OrderInfo;
import com.itheima.entity.Result;
import com.itheima.service.OrderService;
import com.itheima.untils.SMSUtils;
import com.itheima.untils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * 体检预约
 */
@RestController
@RequestMapping("/orderInfo")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    //发送短信验证码
    @RequestMapping("/getCode")
    public Result getCode(String telephone){
        //随机生成验证码
        Integer templateCode = ValidateCodeUtils.generateValidateCode(4);
        //调用工具类 发送验证码
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone, String.valueOf(templateCode));
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false ,"手机验证码码发送失败,请重试");
        }

        //将验证码存入redis中
        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,300, String.valueOf(templateCode));


        return  new Result(true,"手机验证码发送成功,请注意查收");
    }


    //体检预约
    @RequestMapping("/orderSet")
    public Result orderSet(@RequestBody Map map) throws Exception{
        //获取用户手机号
        String telephone = (String) map.get("telephone");

        //获取用户发送的验证码
        String validateCode = (String) map.get("validateCode");
        //从redis中取出验证码
        String redisCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        //进行验证码校验
        if (!redisCode.equals(validateCode)){
            //验证码不正确
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //调用业务层
        return  orderService.orderSet(map);


    }

    //查询OrderInfo
    @RequestMapping("/findOrderInfoById")
    public OrderInfo findOrderInfoById(Integer id){
        return orderService.findOrderInfoById(id);
    }


}
