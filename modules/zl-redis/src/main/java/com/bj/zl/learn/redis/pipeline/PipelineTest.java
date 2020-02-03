/**
 * create by Administrator
 * 2020-01-04
 */
package com.bj.zl.learn.redis.pipeline;

import com.bj.zl.learn.redis.jedis.pool.JedisPoolInstance;
import com.google.common.collect.Lists;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.io.Serializable;
import java.util.List;

/**
 * pipeline 传送的是一串命令的集合,而mset等传递的多个key
 */
public class PipelineTest {


    /*public static void main(String[] args) {
        //获取jedis连接
        Jedis jedis = JedisPoolInstance.getInstance().getResource();
        //获取pipeline管道
        Pipeline pipeline = jedis.pipelined();

        //向管道中增加命令--并未真正的执行命令
        List<String>  keys = Lists.newArrayList("p1","p2","p3","p4","p5");
        keys.forEach((key)->{
            pipeline.set(key,"0");
        });
        pipeline.incr("p1");
        //真正的执行命令,sync()没有返回值
        //pipeline.sync();
        //syncAndReturnAll会返回所有命令的执行结果.
        List<Object> result = pipeline.syncAndReturnAll();
        result.forEach((object)->{
            System.out.println(object);
        });
    }*/
    //spring整合
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        RedisTemplate<String,Serializable> redisTemplate = context.getBean(RedisTemplate.class);
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        List<Object> list = redisTemplate.executePipelined((RedisConnection connection)->{
            connection.openPipeline();
            //向管道中增加命令--并未真正的执行命令
            List<String>  keys = Lists.newArrayList("p6","p7","p8","p9");
            keys.forEach((key)->{
                connection.set(key.getBytes(),"0".getBytes());
            });
            connection.incr("p1".getBytes());
            //不能返回非空值
            return null;
        });


    }
}
