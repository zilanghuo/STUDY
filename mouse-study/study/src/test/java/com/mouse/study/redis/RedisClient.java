package com.mouse.study.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lwf
 * @date 2017/11/28
 * use:
 */
public class RedisClient {
    static Jedis jedis;

    @org.junit.BeforeClass
    public static void beforeClass() {
        jedis = new Jedis("172.17.34.2",6379);
    }

    // Redis和Java字符串实例
    @org.junit.Test
    public void testString() {
        jedis.set("tutorial-name", "Redis tutorial");
        System.out.println("Stored string in redis:: "+ jedis.get("tutorial-name"));

        // 输出：Stored string in redis:: Redis tutorial
    }



    //Redis和Java列表示例
    @org.junit.Test
    public void testList() {
        jedis.lpush("tutorial-list", "Redis");
        jedis.lpush("tutorial-list", "Mongodb");
        jedis.lpush("tutorial-list", "Mysql");
        // Get the stored data and print it
        List<String> list = jedis.lrange("tutorial-list", 0, 5);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("Stored string in redis:: " + list.get(i));
        }
		/*输出
		Stored string in redis:: Mysql
		Stored string in redis:: Mongodb
		Stored string in redis:: Redis
		*/
    }

    //Redis和Java的键实例
    @org.junit.Test
    public void testKeys(){
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println("List of stored keys:: " + key);
        }

		/*输出
		  List of stored keys:: tutorial-name
		  List of stored keys:: tutorial-list
		  */
    }

    //Redis和Java的hash实例
    @org.junit.Test
    public void testHash(){
        String key="user";
        String field_name="name";
        String field_name_value="tianshouzhi";
        String field_city="city";
        String field_city_value="shanghai";
        jedis.hset(key, field_name, field_name_value);
        jedis.hset(key, field_city, field_city_value);

        Map<String, String> map = jedis.hgetAll(key);
        Set<Map.Entry<String,String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
		/*
		 * 输出
		 * name:tianshouzhi
		 * city:shanghai
		 */
    }

    //Redis和Java的集合实例
    @org.junit.Test
    public void testSet(){
        String key="set_key";
        String[] members=new String[]{"a","b","a","c"};
        jedis.sadd(key, members);

        Set<String> smembers = jedis.smembers(key);
        for (String string : smembers) {
            System.out.println(string);
        }
			/*
			 * 输出
			 * name:tianshouzhi
			 * city:shanghai
			 */

    }

    // Redis和Java的集合实例
    @org.junit.Test
    public void testZSet() {
        String key = "zset_key";
        Map<String,Double> scoreMembers=new HashMap<String, Double>();
        jedis.zadd(key, scoreMembers);

        Set<String> zrange = jedis.zrange(key, 0, 2);

        for (String member : zrange) {
            System.out.println(member);
            System.out.println(jedis.zscore(key, member));
        }

    }

    //订阅：需要先执行订阅操作
    //覆盖JedisPubSub中的onMessage，用于回调
    @org.junit.Test
    public void testSubsribe() {
        JedisPubSub jedisPubSub = new JedisPubSub() {

            @Override
            public void onMessage(String channel, String message) {
                System.out.println(channel);
                System.out.println(message);
            }

        };
        jedis.subscribe(jedisPubSub, "channel1");

		/*输出
		channel1
		test publish substribe*/
    }

    //发布
    @org.junit.Test
    public void testPublish(){
        String channel="channel1";
        String message="test publish substribe";
        jedis.publish(channel, message);
    }

}
