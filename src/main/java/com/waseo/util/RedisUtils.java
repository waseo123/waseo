package com.waseo.util;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis缓存工具类
 */
public class RedisUtils {

    public static void main(String[] args) {
        System.out.println("中国人");
    }

    private static final String IP = "127.0.0.1"; // ip
    private static final int PORT = 6379;         // 端口
    private static final String AUTH = "";          // 密码(原始默认是没有密码)
    private static int MAX_ACTIVE = 1024;       // 最大连接数
    private static int MAX_IDLE = 200;          // 设置最大空闲数
    private static int MAX_WAIT = 10000;        // 最大连接时间
    private static int TIMEOUT = 10000;         // 超时时间
    private static boolean BORROW = true;         // 在borrow一个事例时是否提前进行validate操作
    private static JedisPool pool = null;
    private static Logger logger = Logger.getLogger(RedisUtils.class);

    private static RedisUtils redisCacheUtils = null;

    private RedisUtils() {
    }

    public static RedisUtils getInstance() {
        if (redisCacheUtils == null) {
            synchronized (RedisUtils.class) {
                if (redisCacheUtils == null)
                    redisCacheUtils = new RedisUtils();
            }
        }
        return redisCacheUtils;
    }

    /**
     * 初始化线程池
     */
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(MAX_ACTIVE);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWaitMillis(MAX_WAIT);
        config.setTestOnBorrow(BORROW);
        pool = new JedisPool(config, IP, PORT, TIMEOUT);
    }

    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("保存对象失败:" + e.getMessage());
        } finally {
            closeJedis(jedis);
        }
    }

    public String get(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = getJedis();
            value = jedis.get(key);
        } catch (Exception e) {
            logger.error("保存对象失败:" + e.getMessage());
        } finally {
            closeJedis(jedis);
        }
        return value;
    }

    /**
     * 保存对象
     *
     * @param key 主键
     * @param obj 对象
     */
    public void setObject(String key, Object obj) {
        Jedis jedis = null;
        try {
            jedis = getJedis();

            jedis.set(key.getBytes(), SerializeUtils.serialize(obj));
        } catch (Exception e) {
            logger.error("保存对象失败:" + e.getMessage());
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 获取对象
     *
     * @param key 主键
     * @return
     */
    public Object getObject(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] bytes = jedis.get(key.getBytes());
            return SerializeUtils.unSerialize(bytes);
        } catch (Exception e) {
            logger.error("查询对象失败:" + e.getMessage());
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    /**
     * @param @param key
     * @param @param seconds
     * @Description: 设置对象失效时间
     */
    public void disableTime(String key, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.expire(key, seconds);

        } catch (Exception e) {
            logger.debug("设置失效失败.");
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 获取redis连接
     */
    public synchronized Jedis getJedis() {
        try {
            if (pool != null)
                return pool.getResource();
        } catch (Exception e) {
            logger.info("连接池连接异常");
        }
        return null;
    }

    /**
     * 关闭连接
     */
    public synchronized void closeJedis(Jedis jedis) {
        if (jedis != null)
            jedis.close();
    }
}
