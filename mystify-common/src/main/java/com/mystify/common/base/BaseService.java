package com.mystify.common.base;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import com.mystify.common.Constants;
import com.mystify.common.utils.CacheUtil;
import com.mystify.common.utils.DataUtil;
import com.mystify.common.utils.ExceptionUtil;
import com.mystify.common.utils.InstanceUtil;


import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
/**
 * 业务逻辑层基类<br/>
 * 继承基类后必须配置CacheConfig(cacheNames="")
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
public abstract class BaseService<T extends BaseModel> implements ApplicationContextAware {
	private static final Logger logger = LoggerFactory.getLogger(BaseService.class);
	
    @Autowired
    protected BaseMapper<T> mapper;
    protected ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /** 分页查询 */
    public static Page<Integer> getPage(Map<String, Object> params) {
        Integer current = 1;
        Integer size = 10;
        String orderBy = "id";
        if (DataUtil.isNotEmpty(params.get("pageNum"))) {
            current = Integer.valueOf(params.get("pageNum").toString());
        }
        if (DataUtil.isNotEmpty(params.get("pageSize"))) {
            size = Integer.valueOf(params.get("pageSize").toString());
        }
        if (DataUtil.isNotEmpty(params.get("orderBy"))) {
            orderBy = (String) params.get("orderBy");
            params.remove("orderBy");
        }
        if (size == -1) {
            return new Page<Integer>();
        }
        Page<Integer> page = new Page<Integer>(current, size, orderBy);
        page.setAsc(false);
        return page;
    }

    /** 根据Id查询(默认类型T) */
    public Page<T> getPage(Page<Integer> ids) {
        if (ids != null) {
            Page<T> page = new Page<T>(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            List<T> records = InstanceUtil.newArrayList();
            for (int i = 0; i < ids.getRecords().size(); i++) {
                records.add(null);
            }
            ExecutorService executorService = Executors.newFixedThreadPool(5);
            for (int i = 0; i < ids.getRecords().size(); i++) {
                final int index = i;
                executorService.execute(new Runnable() {
                    public void run() {
                        records.set(index, queryById(ids.getRecords().get(index)));
                    }
                });
            }
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                logger.error("awaitTermination", "", e);
            }
            page.setRecords(records);
            return page;
        }
        return new Page<T>();
    }

    /** 根据Id查询(默认类型T) */
    public Page<Map<String, Object>> getPageMap(Page<Integer> ids) {
        if (ids != null) {
            Page<Map<String, Object>> page = new Page<Map<String, Object>>(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            List<Map<String, Object>> records = InstanceUtil.newArrayList();
            for (int i = 0; i < ids.getRecords().size(); i++) {
                records.add(null);
            }
            ExecutorService executorService = Executors.newFixedThreadPool(5);
            for (int i = 0; i < ids.getRecords().size(); i++) {
                final int index = i;
                executorService.execute(new Runnable() {
                    public void run() {
                        records.set(index, InstanceUtil.transBean2Map(queryById(ids.getRecords().get(index))));
                    }
                });
            }
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                logger.error("awaitTermination", "", e);
            }
            page.setRecords(records);
            return page;
        }
        return new Page<Map<String, Object>>();
    }

    /** 根据Id查询(cls返回类型Class) */
    public <K> Page<K> getPage(Page<Integer> ids, Class<K> cls) {
        if (ids != null) {
            Page<K> page = new Page<K>(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            List<K> records = InstanceUtil.newArrayList();
            for (int i = 0; i < ids.getRecords().size(); i++) {
                records.add(null);
            }
            ExecutorService executorService = Executors.newFixedThreadPool(5);
            for (int i = 0; i < ids.getRecords().size(); i++) {
                final int index = i;
                executorService.execute(new Runnable() {
                    public void run() {
                        T t = queryById(ids.getRecords().get(index));
                        K k = InstanceUtil.to(t, cls);
                        records.set(index, k);
                    }
                });
            }
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                logger.error("awaitTermination", "", e);
            }
            page.setRecords(records);
            return page;
        }
        return new Page<K>();
    }

    /** 根据Id查询(默认类型T) */
    public List<T> getList(List<Integer> ids) {
        List<T> list = InstanceUtil.newArrayList();
        if (ids != null) {
            for (int i = 0; i < ids.size(); i++) {
                list.add(null);
            }
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            for (int i = 0; i < ids.size(); i++) {
                final int index = i;
                executorService.execute(new Runnable() {
                    public void run() {
                        list.set(index, queryById(ids.get(index)));
                    }
                });
            }
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                logger.error("awaitTermination", "", e);
            }
        }
        return list;
    }

    /** 根据Id查询(cls返回类型Class) */
    public <K> List<K> getList(List<Integer> ids, Class<K> cls) {
        List<K> list = InstanceUtil.newArrayList();
        if (ids != null) {
            for (int i = 0; i < ids.size(); i++) {
                list.add(null);
            }
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            for (int i = 0; i < ids.size(); i++) {
                final int index = i;
                executorService.execute(new Runnable() {
                    public void run() {
                        T t = queryById(ids.get(index));
                        K k = InstanceUtil.to(t, cls);
                        list.set(index, k);
                    }
                });
            }
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                logger.error("awaitTermination", "", e);
            }
        }
        return list;
    }

/*    @Transactional*/
    public void del(Integer id, Integer userId) {
        try {
            T record = this.queryById(id);
            mapper.updateById(record);
            CacheUtil.getCache().set(getCacheKey(id), record);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /*@Transactional*/
    public void delete(Long id) {
        try {
            mapper.deleteById(id);
            CacheUtil.getCache().del(getCacheKey(id));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

   /* @Transactional*/
    public Integer deleteByMap(Map<String, Object> columnMap) {
        return mapper.deleteByMap(columnMap);
    }

    /*@Transactional*/
    public T update(T record) {
        try {
            if (record.getId() == null) {
                mapper.insert(record);
            } else {
                T org = this.queryById(record.getId());
                String lockKey = getLockKey(record.getId());
                if (CacheUtil.getLock(lockKey)) {
                    try {
                        T update = InstanceUtil.getDiff(org, record);
                        update.setId(record.getId());
                        mapper.updateById(update);
                        record = mapper.selectById(record.getId());
                        CacheUtil.getCache().set(getCacheKey(record.getId()), record);
                    } finally {
                        CacheUtil.unlock(lockKey);
                    }
                } else {
                    sleep(20);
                    return update(record);
                }
            }
        } catch (DuplicateKeyException e) {
            String msg = ExceptionUtil.getStackTraceAsString(e);
            logger.error(Constants.Exception_Head + msg, e);
            throw new RuntimeException("已经存在相同的配置.");
        } catch (Exception e) {
            String msg = ExceptionUtil.getStackTraceAsString(e);
            logger.error(Constants.Exception_Head + msg, e);
            throw new RuntimeException(msg);
        }
        return record;
    }

    protected void sleep(int millis) {
        try {
            Thread.sleep(RandomUtils.nextLong(10, millis));
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }

   /* @Transactional*/
    @SuppressWarnings("unchecked")
    public T queryById(Integer id) {
        String key = getCacheKey(id);
        T record = (T) CacheUtil.getCache().get(key);
        if (record == null) {
            String lockKey = getLockKey(id);
            if (CacheUtil.getLock(lockKey)) {
                try {
                    record = mapper.selectById(id);
                    CacheUtil.getCache().set(key, record);
                } finally {
                    CacheUtil.unlock(lockKey);
                }
            } else {
                logger.debug(getClass().getSimpleName() + ":" + id + " retry queryById.");
                sleep(20);
                return queryById(id);
            }
        }
        return record;
    }

    public Page<T> query(Map<String, Object> params) {
        Page<Integer> page = getPage(params);
        page.setRecords(mapper.selectIdPage(page, params));
        return getPage(page);
    }

    public List<T> queryList(Map<String, Object> params) {
        List<Integer> ids = mapper.selectIdPage(params);
        List<T> list = getList(ids);
        return list;
    }

    protected <P> Page<P> query(Map<String, Object> params, Class<P> cls) {
        Page<Integer> page = getPage(params);
        page.setRecords(mapper.selectIdPage(page, params));
        return getPage(page, cls);
    }

    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    public List<T> selectList(Wrapper<T> entity) {
        return mapper.selectList(entity);
    }

    /** 获取缓存键值 */
    protected String getCacheKey(Object id) {
        String cacheName = getCacheKey();
        return new StringBuilder(Constants.CACHE_NAMESPACE).append(cacheName).append(":").append(id).toString();
    }

    /** 获取缓存键值 */
    protected String getLockKey(Object id) {
        String cacheName = getCacheKey();
        return new StringBuilder(Constants.CACHE_NAMESPACE).append(cacheName).append(":LOCK:").append(id).toString();
    }

    /**
     * @return
     */
    private String getCacheKey() {
        Class<?> cls = getClass();
        String cacheName = Constants.cacheKeyMap.get(cls);
        if (StringUtils.isBlank(cacheName)) {
            CacheConfig cacheConfig = cls.getAnnotation(CacheConfig.class);
            if (cacheConfig == null || cacheConfig.cacheNames() == null || cacheConfig.cacheNames().length < 1) {
                cacheName = getClass().getName();
            } else {
                cacheName = cacheConfig.cacheNames()[0];
            }
            Constants.cacheKeyMap.put(cls, cacheName);
        }
        return cacheName;
    }
}
