package com.palm.epalm.base.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 在内存中缓存操作结果，超时后调用自动更新
 * eg: Long time = MemActionCacheUtil.getWithCache(10L,key,new Callable<Long>() {//每次运行到此处时，会检测是否超时，没有超时的时候，就直接获取结果，而不运行方法体
 *
 * @author desire
 * @Override public Long call() throws Exception {
 * return System.currentTimeMillis();
 * }
 * });
 * //也可直接取已有的值
 * time = MemActionCacheUtil.getPhotoData(key)
 * //强制更新
 * time = MemActionCacheUtil.update(key)
 * @since 2013-07-31 21:11
 */
public class MemActionCacheUtil {
    private static Map<Long, DelayResult> callableTimeoutMap = new HashMap<Long, DelayResult>();

    /**
     * 操作缓存,在缓存时间内直接给出结果而不执行操作
     *
     * @param timeoutSeconds 超时时间(s)
     * @param key            缓存键值
     * @param action         需要执行的操作，有一个返回值
     * @param <T>            返回值类型
     * @return 操作返回值
     * @throws Exception
     * @should get a result
     */
    public static <T> T getWithCache(Long timeoutSeconds, Long key, Callable<T> action) throws Exception {
        DelayResult result = callableTimeoutMap.get(key);
        if (result == null) {
            result = new DelayResult(System.currentTimeMillis(), action);
            result.timeout = timeoutSeconds;
            callableTimeoutMap.put(key, result);
            return (T) result.value;
        } else {
            return (T) result.getValue();
        }

    }

    /**
     * 获取缓存中的数据
     *
     * @param key
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T getData(Long key) throws Exception {
        DelayResult result = callableTimeoutMap.get(key);
        if (result == null) {
            return null;
        } else {
            return (T) result.getValue();
        }
    }

    /**
     * 更新缓存中的数据
     *
     * @param key
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T update(Long key) throws Exception {
        DelayResult result = callableTimeoutMap.get(key);
        if (result == null) {
            return null;
        } else {
            result.update();
            return (T) result.value;
        }
    }

    /**
     * 移除缓存
     *
     * @param key
     */
    public void remove(Long key) {
        callableTimeoutMap.remove(key);
    }

    /**
     * 缓存结果及操作
     */
    private static class DelayResult {
        private Long time;
        private Object value;
        private Callable action;
        private Long timeout;

        private DelayResult(Long time, Callable action) throws Exception {
            this.time = time;
            this.action = action;
            this.value = action.call();
        }

        private void update() throws Exception {
            this.time = System.currentTimeMillis();
            this.value = action.call();
        }

        private Object getValue() throws Exception {
            if ((System.currentTimeMillis() - time) / 1000 > timeout) {
                update();
            }
            return value;
        }
    }
}
