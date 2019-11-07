package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.untils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;
    public void clearImg(){
        //获取两个集合的差值
        //Jedis resource = jedisPool.getResource();

        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //遍历集合
        if (sdiff != null){
            for (String imgName : sdiff) {
                //调用工具类 删除服务器的文件
                QiniuUtils.deleteFileFromQiniu(imgName);
                //删除Redis中的数据
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,imgName);
                System.out.println("删除了:"+imgName);
            }
        }
        System.out.println("执行完毕");
        //释放资源
        jedisPool.close();
    }
}
