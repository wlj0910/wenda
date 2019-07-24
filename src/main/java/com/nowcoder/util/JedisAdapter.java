package com.nowcoder.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nowcoder.model.User;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

public class JedisAdapter {
    public static void print(int index,Object object){
        System.out.println(String.format("%d,%s",index,object.toString()));
    }
    public static void main(String[] args){
        Jedis jedis=new Jedis("127.0.0.1",6379);
        jedis.auth("123456");
        jedis.select(9);
        jedis.flushAll();//删除
        jedis.set("hello","world");
        print(1,jedis.get("hello"));
        jedis.rename("hello","newHello");
        print(1,jedis.get("newHello"));
        jedis.setex("hello2",10,"world");
        print(2,jedis.get("hello2"));
        jedis.set("pv","100");
        jedis.incr("pv");
        jedis.incrBy("pv",5);
        print(3,jedis.get("pv"));
        jedis.decrBy("pv",3);
        print(4,jedis.get("pv"));
        print(5,jedis.keys("*"));

        //list 可重复
        String listName="list";
        jedis.del(listName);
        for(int i=0;i<10;i++){
            jedis.lpush(listName,"a"+String.valueOf(i));
        }
        print(6,jedis.lrange(listName,0,12));
        print(7,jedis.llen(listName));
        print(8,jedis.lpop(listName));
        print(7,jedis.llen(listName));
        print(9,jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER,"a4","xx"));
        print(10,jedis.linsert(listName,BinaryClient.LIST_POSITION.BEFORE,"a4","bb"));
        print(11,jedis.lrange(listName,0,12));

        //hash
        String userKey="userxx";
        jedis.hset(userKey,"name","jim");
        jedis.hset(userKey,"age","12");
        jedis.hset(userKey,"phone","18674539021");
        print(12,jedis.hget(userKey,"phone"));
        print(13,jedis.hgetAll(userKey));
        jedis.hdel(userKey,"phone");
        print(14,jedis.hgetAll(userKey));
        print(15,jedis.hexists(userKey,"email"));
        print(16,jedis.hexists(userKey,"name"));
        print(17,jedis.hkeys(userKey));
        print(18,jedis.hvals(userKey));
        jedis.hsetnx(userKey,"school","xj");
        jedis.hsetnx(userKey,"name","yxy");
        print(19,jedis.hgetAll(userKey));

        //set 去重
        String likeKey1="newslike1";
        String likeKey2="newslike2";
        for(int i=0;i<10;i++){
            jedis.sadd(likeKey1,String.valueOf(i));
            jedis.sadd(likeKey2,String.valueOf(i*i));
        }
        print(20,jedis.smembers(likeKey1));
        print(21,jedis.smembers(likeKey2));
        print(22,jedis.sunion(likeKey1,likeKey2));
        print(23,jedis.sdiff(likeKey1,likeKey2));
        print(24,jedis.sinter(likeKey1,likeKey2));
        print(25,jedis.sismember(likeKey1,"12"));
        print(26,jedis.sismember(likeKey2,"16"));
        jedis.srem(likeKey1,"5");
        print(27,jedis.smembers(likeKey1));
        jedis.smove(likeKey2,likeKey1,"25");
        print(28,jedis.smembers(likeKey1));
        print(29,jedis.scard(likeKey1));

        //Sorted Sets
        String rankKey="rankKey";
        jedis.zadd(rankKey,15,"jim");
        jedis.zadd(rankKey,60,"Ben");
        jedis.zadd(rankKey,90,"lee");
        jedis.zadd(rankKey,75,"Lucy");
        jedis.zadd(rankKey,65,"mei");
        jedis.zadd(rankKey,80,"li");
        print(30,jedis.zcard(rankKey));
        print(31,jedis.zcount(rankKey,61,100));
        print(32,jedis.zscore(rankKey,"Lucy"));
        jedis.zincrby(rankKey,2,"Lucy");
        print(33,jedis.zscore(rankKey,"Lucy"));
        jedis.zincrby(rankKey,2,"luc");
        print(34,jedis.zrange(rankKey,0,100));
        print(35,jedis.zrange(rankKey,0,10));
        print(36,jedis.zrange(rankKey,4,7));
        print(36,jedis.zrevrange(rankKey,4,7));
        for(Tuple tuple:jedis.zrangeByScoreWithScores(rankKey,"0","100")){
            print(37,tuple.getElement()+":"+String.valueOf(tuple.getScore()));
        }
        print(38,jedis.zrank(rankKey,"Ben"));
        print(39,jedis.zrevrank(rankKey,"Ben"));

        String setKey="zset";
        jedis.zadd(setKey,1,"a");
        jedis.zadd(setKey,1,"b");
        jedis.zadd(setKey,1,"c");
        jedis.zadd(setKey,1,"d");
        print(40,jedis.zlexcount(setKey,"-","+"));
        print(41,jedis.zlexcount(setKey,"(b","[d"));//边界测试
        print(42,jedis.zlexcount(setKey,"[b","[d"));
        jedis.zrem(setKey,"b");
        print(43,jedis.zrange(setKey,0,10));
        jedis.zremrangeByLex(setKey,"(c","+");
        print(44,jedis.zrange(setKey,0,2));

        //连接池
        JedisPool pool=new JedisPool("127.0.0.1",6379);
        for(int i=0;i<10;i++){
            Jedis j=pool.getResource();
            j.auth("123456");
            j.select(9);
            print(45,j.get("pv"));
            j.close();
        }

        User user=new User();
        user.setName("wlj");
        user.setPassword("123");
        user.setHeadUrl("a.png");
        user.setSalt("salt");
        user.setId(1);
        print(46, JSONObject.toJSONString(user));
        jedis.set("user1",JSONObject.toJSONString(user));

        String value=jedis.get("user1");
        User user2= JSON.parseObject(value,User.class);
        print(47,user2);



    }
}
