package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.domain.Order;
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
    public Result getCode(String telephone) {
        //随机生成验证码
        Integer templateCode = ValidateCodeUtils.generateValidateCode(4);
        //调用工具类 发送验证码
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, String.valueOf(templateCode));
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, "手机验证码码发送失败,请重试");
        }

        //将验证码存入redis中
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 300, String.valueOf(templateCode));


        return new Result(true, "手机验证码发送成功,请注意查收");
    }


    //体检预约
    @RequestMapping("/orderSet")
    public Result orderSet(@RequestBody Map map) throws Exception {
        //获取用户手机号
        String telephone = (String) map.get("telephone");

        //获取用户发送的验证码
        String validateCode = (String) map.get("validateCode");
        //从redis中取出验证码
        String redisCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        //进行验证码校验
        if (!redisCode.equals(validateCode) || validateCode == null || redisCode == null) {
            //验证码不正确
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Result result = null;
        try {
            //调用业务层
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result = orderService.orderSet(map);
            //进行短信通知
            if (result.isFlag()){
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone, String.valueOf(map.get("orderDate")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "预约失败!");
        }
        return result;

    }

    //查询OrderInfo
    @RequestMapping("/findOrderInfoById")
    public OrderInfo findOrderInfoById(Integer id) {
        return orderService.findOrderInfoById(id);
    }

    //登录发送验证码
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        //随机生成验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        try {
            //发送验证码
            SMSUtils.sendShortMessage(SMSUtils.Login_Code, telephone, String.valueOf(validateCode));
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //将验证码存入redis中 用于检验
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN, 300, String.valueOf(validateCode));
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    //登录验证
    @RequestMapping("/login")
    public Result login(@RequestBody Map map) {
        //进行验证码判断
        String validateCode = (String) map.get("validateCode");
        String telephone = (String) map.get("telephone");
        String redisCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (!redisCode.equals(validateCode) || redisCode == null || validateCode == null) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);

        }

        return new Result(true,"欢迎登录");

    }


}
