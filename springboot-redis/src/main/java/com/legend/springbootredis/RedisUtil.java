package com.legend.springbootredis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Redis 工具类
 */
public class RedisUtil {

    protected static ReentrantLock lockPool = new ReentrantLock();
    protected static ReentrantLock lockJedis = new ReentrantLock();

    private static final Long RELEASE_SUCCESS = 1L;

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

    // Redis服务器IP
    private static String IP = "127.0.0.1";

    // Redis的端口号
    private static int PORT = 6379;


    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    /**
     * redis过期时间,以秒为单位
     */
    // 一小时
    public final static int EXRP_HOUR = 60 * 60;
    // 一天
    public final static int EXRP_DAY = 60 * 60 * 24;
    // 一个月
    public final static int EXRP_MONTH = 60 * 60 * 24 * 30;

    public final static String SHARE_KEY_PREFIX = "share_";

    /**
     * 初始化Redis连接池
     * master.redis.ip=${REDIS_HOST:127.0.0.1}
     * master.redis.port=6379
     * master.redis.password=
     * master.redis.max_active=500
     * master.redis.max_idle=5
     * master.redis.max_wait=10000
     * master.redis.timeout=10000
     */
    private static void initialPool() {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(100);
            config.setMaxIdle(5);
            config.setMinIdle(8);//设置最小空闲数
            config.setMaxWaitMillis(10000);
            config.setTestOnBorrow(false);
            config.setTestOnReturn(false);
            //Idle时进行连接扫描
            config.setTestWhileIdle(true);
            //表示idle object evitor两次扫描之间要sleep的毫秒数
            config.setTimeBetweenEvictionRunsMillis(30000);
            //表示idle object evitor每次扫描的最多的对象数
            config.setNumTestsPerEvictionRun(10);
            //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
            config.setMinEvictableIdleTimeMillis(60000);
            jedisPool = new JedisPool(config, IP, PORT, 10000);
        } catch (Exception e) {
            LOGGER.error("First create JedisPool error : " + e);
        }
    }

    /**
     * 在多线程环境同步初始化
     */
    private static synchronized void poolInit() {
        if (null == jedisPool) {
            initialPool();
        }
    }


    /**
     * 同步获取Jedis实例
     *
     * @return Jedis
     */
    public synchronized static Jedis getJedis() {
        poolInit();
        Jedis jedis = null;
        try {
            if (null != jedisPool) {
                jedis = jedisPool.getResource();
                // 选择下标为8的数据库，即DB8
                jedis.select(8);
                try {
                    jedis.auth("");
                } catch (Exception e) {
                    //LOGGER.error("jedis auth fail error : " + e);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Get jedis error : " + e);
        }
        return jedis;
    }

    /**
     * 设置 String
     *
     * @param key
     * @param value
     */
    public synchronized static void set(String key, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            jedis = getJedis();
            jedis.set(key, value);

        } catch (Exception e) {
            LOGGER.error("Set key error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 设置 byte[]
     *
     * @param key
     * @param value
     */
    public synchronized static void set(byte[] key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
        } catch (Exception e) {
            LOGGER.error("Set key error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 设置 String 过期时间
     *
     * @param key
     * @param value
     * @param seconds 以秒为单位
     */
    public synchronized static void set(String key, String value, int seconds) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            jedis = getJedis();
            jedis.setex(key, seconds, value);

        } catch (Exception e) {
            LOGGER.error("Set keyex error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 设置 byte[] 过期时间
     *
     * @param key
     * @param value
     * @param seconds 以秒为单位
     */
    public synchronized static void set(byte[] key, byte[] value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
            jedis.expire(key, seconds);

        } catch (Exception e) {
            LOGGER.error("Set key error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 获取String值
     *
     * @param key
     * @return value
     */
    public synchronized static String get(String key) {

        Jedis jedis = null;
        String value = null;
        try {
            jedis = getJedis();
            if (null == jedis) {
                return null;
            }
            value = jedis.get(key);
        } catch (Exception e) {
            LOGGER.error("get key error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return value;
    }

    /**
     * 获取byte[]值
     *
     * @param key
     * @return value
     */
    public synchronized static byte[] get(byte[] key) {
        Jedis jedis = null;
        byte[] value = null;
        try {
            jedis = getJedis();
            if (null == jedis) {
                return null;
            }
            value = jedis.get(key);

        } catch (Exception e) {
            LOGGER.error("get key[] error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }

        return value;
    }

    /**
     * 删除值
     *
     * @param key
     */
    public synchronized static void remove(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.del(key);
        } catch (Exception e) {
            LOGGER.error("Remove keyex error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 删除值
     *
     * @param key
     */
    public synchronized static void remove(byte[] key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.del(key);

        } catch (Exception e) {
            LOGGER.error("Remove keyex error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * lpush
     *
     * @param key
     * @param key
     */
    public synchronized static void lpush(String key, String... strings) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            jedis.lpush(key, strings);

        } catch (Exception e) {
            LOGGER.error("lpush error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * lrem
     *
     * @param key
     * @param count
     * @param value
     */
    public synchronized static void lrem(String key, long count, String value) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            jedis.lrem(key, count, value);

        } catch (Exception e) {
            LOGGER.error("lrem error : " + e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * sadd
     *
     * @param key
     * @param value
     * @param seconds
     */
    public synchronized static void sadd(String key, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            jedis.sadd(key, value);
            jedis.expire(key, seconds);
        } catch (Exception e) {
            LOGGER.error("sadd error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * smembers
     *
     * @param key
     */
    public synchronized static Set smembers(String key) {
        Jedis jedis = null;
        Set value = null;
        try {
            jedis = RedisUtil.getJedis();
            value = jedis.smembers(key);
        } catch (Exception e) {
            LOGGER.error("sadd error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return value;
    }

    /**
     * smembers
     *
     * @param key
     */
    public synchronized static void srem(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            jedis.srem(key, members);
        } catch (Exception e) {
            LOGGER.error("sadd error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }


    /**
     * incr
     *
     * @param key
     * @return value
     */
    public synchronized static Long incr(String key) {
        Jedis jedis = null;
        Long value = null;
        try {
            jedis = getJedis();
            if (null == jedis) {
                return null;
            }
            value = jedis.incr(key);

        } catch (Exception e) {
            LOGGER.error("incr error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }

        return value;
    }

    /**
     * decr
     *
     * @param key
     * @return value
     */
    public synchronized static Long decr(String key) {
        Jedis jedis = null;
        Long value = null;
        try {
            jedis = getJedis();
            if (null == jedis) {
                return null;
            }
            value = jedis.decr(key);

        } catch (Exception e) {
            LOGGER.error("decr error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }


        return value;
    }

    public synchronized static void expire(String key, int timeout) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.expire(key, timeout);
        } catch (Exception e) {
            LOGGER.error("expire error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }


    /**
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("tryGetDistributedLock error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }

        return false;

    }

    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(String lockKey, String requestId) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("releaseDistributedLock error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }


        return false;
    }


    public static long scard(String key) {
        long count = 0;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            count = jedis.scard(key);
        } catch (Exception e) {
            LOGGER.error(" scard error : " + e);
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return count;
    }
}