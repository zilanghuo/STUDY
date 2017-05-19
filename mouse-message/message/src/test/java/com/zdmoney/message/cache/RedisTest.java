package com.zdmoney.message.cache;

import com.zdmoney.message.InitDBTestEnvironment;
import com.zdmoney.message.message.service.IMsgMessageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.*;

/**
 * Created by user on 2017/2/16.
 */
@Slf4j
public class RedisTest extends InitDBTestEnvironment {
    @Autowired
    private IMsgMessageService msgMessageService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedisCache() throws Exception {
        //添加一个 key
        ValueOperations<String, Object> value = redisTemplate.opsForValue();
//        value.set("lp", "hello word");
        //获取 这个 key 的值
        System.out.println(value.get("lp"));
        //添加 一个 hash集合
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        Map<String, Object> map = new HashMap<String, Object>();

        Collection<Object> values = map.values();

        for (Object obj:values){
            System.out.println(obj);
        }

//        map.put("name", "lp");
//        map.put("age", "26");
//        hash.putAll("lpMap", map);
        //获取 map
        System.out.println(hash.entries("lpMap"));
        //添加 一个 list 列表
        ListOperations<String, Object> list = redisTemplate.opsForList();
//        list.rightPush("lpList", "lp");
//        list.rightPush("lpList", "26");
        //输出 list
        System.out.println(list.range("lpList", 0, 1));
    }


    @Test
    public void testMsgMessageCache() throws Exception {
        ValueOperations<String, Object> value = redisTemplate.opsForValue();

//        System.out.println(value.size("MESSAGE_UNREAD_COUNT_104"));

        //message消息未读数
        log.info("message消息未读数="+value.get("MESSAGE_105"));

    }

}
