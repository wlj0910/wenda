package com.nowcoder.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nowcoder.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public class JedisAdapter implements InitializingBean{
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool pool=null;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool=new JedisPool("127.0.0.1",6379);
    }
    public long sadd(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            jedis.auth("123456");
            jedis.select(10);
            return jedis.sadd(key,value);
        }catch(Exception e){
            logger.error("增加失败"+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            jedis.auth("123456");
            jedis.select(10);
            return jedis.srem(key,value);
        }catch(Exception e){
            logger.error("增加失败"+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public long scard(String key){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            jedis.auth("123456");
            jedis.select(10);
            return jedis.scard(key);
        }catch(Exception e){
            logger.error("增加失败"+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            jedis.auth("123456");
            jedis.select(10);
            return jedis.sismember(key,value);
        }catch(Exception e){
            logger.error("增加失败"+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }
        return false;
    }

    public List<String> brpop(int timeout, String key){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            jedis.auth("123456");
            jedis.select(10);
            return jedis.brpop(timeout,key);
        }catch(Exception e){
            logger.error("增加失败"+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }


    public long lpush(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            jedis.auth("123456");
            jedis.select(10);
            return jedis.lpush(key,value);
        }catch(Exception e){
            logger.error("增加失败"+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public List<String> lrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.auth("123456");
            jedis.select(10);
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            logger.error("失败" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }


    public Jedis getJedis(){
        Jedis jedis=pool.getResource();
        jedis.auth("123456");
        jedis.select(10);
        return jedis;
    }

    public Transaction multi(Jedis jedis){
        try{
            return jedis.multi();
        }catch(Exception e){
            logger.error("发生异常"+e.getMessage());
        }
        return null;
    }

    public List<Object> exec(Transaction tx,Jedis jedis){
        try{
            return tx.exec();
        }catch(Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally{
            if(tx!=null){
                try {
                    tx.close();
                }catch(IOException ioe){
                    logger.error("发生异常"+ioe.getMessage());
                }
            }
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    public long zadd(String key,double score,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            jedis.auth("123456");
            jedis.select(10);
            return jedis.zadd(key,score,value);
        }catch(Exception e){
            logger.error("增加失败"+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public long zrem(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            jedis.auth("123456");
            jedis.select(10);
            return jedis.zrem(key,value);
        }catch(Exception e){
            logger.error("增加失败"+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public Set<String> zrange(String key, int start, int end){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            jedis.auth("123456");
            jedis.select(10);
            return jedis.zrange(key,start,end);
        }catch(Exception e){
            logger.error("增加失败"+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    public Set<String> zrevrange(String key, int start, int end){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            jedis.auth("123456");
            jedis.select(10);
            return jedis.zrevrange(key,start,end);
        }catch(Exception e){
            logger.error("增加失败"+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    public long zcard(String key){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            jedis.auth("123456");
            jedis.select(10);
            return jedis.zcard(key);
        }catch(Exception e){
            logger.error("增加失败"+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public Double zscore(String key,String member){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            jedis.auth("123456");
            jedis.select(10);
            return jedis.zscore(key,member);
        }catch(Exception e){
            logger.error("增加失败"+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    public static void print(int index, Object object){
        System.out.println(String.format("%d,%s",index,object.toString()));
    }
    public static void main(String[] args){
        JedisAdapter jj=new JedisAdapter();
        jj.sadd("wlj","123");


        Jedis jedis=new Jedis("127.0.0.1",6379);
        jedis.auth("123456");
        jedis.select(9);
        jedis.flushDB();//删除
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
            j.sadd("wlj","123456");
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
