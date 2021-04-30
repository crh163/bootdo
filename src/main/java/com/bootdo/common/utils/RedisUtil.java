package com.bootdo.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * list缓存
     * @param key
     * @param v
     * @param time
     * @return
     */
    public <T> boolean cacheList(String key, T v, long time) {
        try {
            ListOperations<String, Object> listOps =  redisTemplate.opsForList();
            listOps.rightPush(key, v);
            if (time > 0) redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable t) {
            log.error("缓存[" + key + "]失败, value[" + v + "]"+t);
        }
        return false;
    }

    /**
     * 缓存list（永不过期）
     * @param key
     * @param v
     * @return
     */
    public <T> boolean cacheList(String key, T v) {
        return cacheList(key, v, -1);
    }

    /**
     * 缓存list
     * @param key
     * @param v
     * @param time
     * @return
     */
    public <T>  boolean cacheList(String key, List<T> v, long time) {
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        try {
            ListOperations<String, Object> listOps =  redisTemplate.opsForList();
            long l = listOps.rightPushAll(key, v);
            if (time > 0) redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable t) {
            log.error("缓存[" + key + "]失败, value[" + v + "]"+t);
        }
        return false;
    }

    /**
     * 缓存list
     * @param key
     * @param v
     * @return
     */
    public <T> boolean cacheList(String key, List<T> v) {
        return cacheList(key, v, -1);
    }

    /**
     * 获取list缓存
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> getList(String key, long start, long end) {
        try {
            ListOperations<String, Object> listOps =  redisTemplate.opsForList();
            return listOps.range(key, start, end);
        } catch (Throwable t) {
            log.error("获取list缓存失败key:"+ key + t);
        }
        return null;
    }

    /**
     * 获取总条数, 可用于分页
     * @param key
     * @return
     */
    public long getListSize(String key) {
        try {
            ListOperations<String, Object> listOps =  redisTemplate.opsForList();
            return listOps.size(key);
        } catch (Throwable t) {
            log.error("获取list长度失败key:" + key + t);
        }
        return 0;
    }

    /**
     * 获取总条数, 可用于分页
     * @param listOps
     * @param key
     * @return
     */
    public long getListSize(ListOperations<String, Object> listOps, String key) {
        try {
            return listOps.size(key);
        } catch (Throwable t) {
            log.error("获取list长度失败key:" +key+ t);
        }
        return 0;
    }

    /**
     * 移除list缓存
     * @param key
     * @return
     */
    public boolean removeOneOfList(String key) {
        try {
            ListOperations<String, Object> listOps =  redisTemplate.opsForList();
            listOps.rightPop(key);
            return true;
        } catch (Throwable t) {
            log.error("移除list缓存失败key:" + key + t );
        }

        return false;
    }

    /**
     * 保存Set
     * @param key
     * @param val
     * @param time
     * @return
     */
    public boolean add2ZSet(String key, Object val,double scope, long time) {
        try {
            ZSetOperations<String, Object> ops = redisTemplate.opsForZSet();
            ops.add(key, val,scope);
            if (time > 0) {
                expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            log.error("缓存[" + key + "]失败, value["+val.toString()+"]"+t);
        }
        return false;
    }

    /**
     * 保存Set
     * @param key
     * @param val
     * @param score
     * @return
     */
    public boolean add2ZSet(String key, Object val,double score) {
        try {
            ZSetOperations<String, Object> ops = redisTemplate.opsForZSet();
            return ops.add(key, val,score);
        } catch (Throwable t) {
            log.error("缓存[" + key + "]失败, value["+val.toString()+"]"+t);
        }
        return false;
    }

    /**
     * 保存Set
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> rangeByScoreWithScores(String key, double min, double max) {
        try {
            ZSetOperations<String, Object> ops = redisTemplate.opsForZSet();
            return ops.rangeByScoreWithScores(key, min, max);
        } catch (Throwable t) {
            log.error("rangeByScoreWithScores [" + key + "] 失败");
        }
        return Collections.emptySet();
    }


    /**
     * 删除
     * @return
     */
    public Long removeZset(String key, Object val) {
        try {
            ZSetOperations<String, Object> ops = redisTemplate.opsForZSet();
            return ops.remove(key, val);
        } catch (Throwable t) {
            log.error("remove [" + key + "] value ["+ val +"] 失败");
        }
        return -1L;
    }

    /**
     * 删除
     * @return
     */
    public Cursor<ZSetOperations.TypedTuple<Object>> scanZset(String key) {
        try {
            return  redisTemplate.opsForZSet().scan(key, ScanOptions.NONE);
        } catch (Exception e) {
            log.error("scan [" + key + "] 异常",e);
        }
        return null;
    }


    /******************************************** Set ****************************************************/

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> getSet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("getSet [" + key + "] 异常",e);
        }
        return null;
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean memberSetValue(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("memberSetValue [" + key + "] 异常",e);
        }
        return false;
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long addSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("addSet [" + key + "] 异常",e);
        }
        return 0;
    }

    /**
     * 将数据放入set缓存 (带过期时间)
     *
     * @param key    键
     * @param time   时间
     * @param timeUnit   时间单位
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long addSetAndTime(String key, long time, TimeUnit timeUnit, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0)
                expire(key, time, timeUnit);
            return count;
        } catch (Exception e) {
            log.error("addSetAndTime [" + key + "] 异常",e);
        }
        return 0;
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long getSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("getSetSize [" + key + "] 异常",e);
        }
        return 0;
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long removeSet(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            log.error("removeSet [" + key + "] 异常",e);
        }
        return 0;
    }

    /**
     * 更新有效期
     */
    public boolean expire(final String key, Long expireTime,TimeUnit timeUnit){
        boolean result = false;
        try {
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            log.error("更新缓存有效期失败：",e);
        }
        return result;
    }

}
