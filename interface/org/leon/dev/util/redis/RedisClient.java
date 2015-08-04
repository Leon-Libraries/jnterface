package org.leon.dev.util.redis;

import org.apache.log4j.Logger;
import redis.clients.jedis.*;


/**
 * Created by LeonWong on 2015/6/19.
 */
public class RedisClient {

    public static Logger log = Logger.getLogger(RedisClient.class);
    //Redis服务器IP
    private static final String ADDR = "127.0.0.1";
    //Redis的端口号
    private static final int PORT = 6379;
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10;
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;// 非切片连接池

    /**
     * 实例化连接池，连接池是单例模式
     */
    private static void initPool(){
        if(jedisPool == null || jedisPool.isClosed()){
            // 池基本配置
            try{
                JedisPoolConfig config = new JedisPoolConfig();
                config.setTestOnBorrow(TEST_ON_BORROW);
                config.setMaxWaitMillis(MAX_WAIT);
                jedisPool = new JedisPool(config, ADDR, PORT);
            }catch(Exception e){
                log.error("初始化redis连接池失败，具体异常详见日志");
                e.printStackTrace();
            }
        }
    }

    /**
     * 直接获取jedis链接
     * @return
     */
    public static Jedis getConnection(){
        initPool();//初始化连接池
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
        }catch (Exception e){
            log.error("获取Jedis链接出错，具体异常详见日志");
            e.printStackTrace();
        }
        return jedis;
    }

    /**
     * 关闭连接
     */
    public static void close(Jedis jedis){
        jedis.close();
    }
}
