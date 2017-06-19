package com.mouse.data.redis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * Created by lwf on 2017/6/19.
 * use to do:
 */
public class RedisTest {

    private static Jedis jedis;

    public static void main(String[] args) {
        jedis = new Jedis("47.93.227.137");
        jedis.auth("passport");
        System.out.println("连接redis服务器成功");

        // 查看服务是否运行
        System.out.println("Server is running: " + jedis.ping());
        System.out.println("-------------------------------------- ");

        // 设置 redis 字符串数据
        jedis.set("runoobkey", "Redis tutorial");
        // 获取存储的数据并输出
        System.out.println("String中存储:runoobkey=" + jedis.get("runoobkey"));
        System.out.println("-------------------------------------- ");

        // 存储数据到列表中
        jedis.lpush("tutorial-list", "Redis");
        jedis.lpush("tutorial-list", "Mongodb");
        jedis.lpush("tutorial-list", "Mysql");
        // 获取存储的数据并输出
        List<String> list = jedis.lrange("tutorial-list", 0, 5);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("List中存储:tutorial-list[" + i + "]=" + list.get(i));
        }
        System.out.println("List长度：" + jedis.llen("tutorial-list"));
        System.out.println("-------------------------------------- ");

        // 操作hashset
        jedis.hset("ukey", "nickname", "lgmyg");
        jedis.hset("ukey", "username", "zilanghuo");
        Map<String, String> hashsets = jedis.hgetAll("ukey");
        String[] keys = {"name", "nickname", "username", "password", "points"};
        for (int i = 0; i < keys.length; i++) {
            System.out.println("Hash中存储：" + keys[i] + "=" + hashsets.get(keys[i]));
        }
        System.out.println("-------------------------------------- ");
        System.out.println("------------"+jedis.exists("tutorial-list")+"------------------");

    }
}
