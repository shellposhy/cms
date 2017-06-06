package cn.com.cms.framework.esb.cache.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.spring.SpringCacheManager;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.stereotype.Service;

import cn.com.cms.framework.esb.cache.base.Cache;

@Service
public class IgniteCacheImpl<T> implements Cache<T> {
	@Resource
	private SpringCacheManager springCacheManager;
	// Define the Properties
	private Ignite ignite;
	private String gridName;
	private CacheConfiguration<Object, Object> cacheConfiguration;
	IgniteCache<Object, Object> cache;

	/**
	 * Get the value by Key
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T get(Object key) {
		assert ignite != null;
		if (null != getCache()) {
			return (T) getCache().get(key);
		}
		return null;
	}

	/**
	 * Set the cache value
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public void put(Object key, Object value) {
		assert ignite != null;
		if (null != getCache()) {
			getCache().put(key, value);
		}
	}

	/**
	 * Remove the cache value
	 * 
	 * @param key
	 * @return
	 */
	public boolean remove(Object key) {
		assert ignite != null;
		if (null != getCache()) {
			return getCache().remove(key);
		} else {
			return false;
		}
	}

	/**
	 * init method
	 * 
	 * @return
	 */
	@PostConstruct
	public void init() throws Exception {
		cacheConfiguration = springCacheManager.getDynamicCacheConfiguration();
		gridName = springCacheManager.getGridName();
		ignite = Ignition.ignite();
		cache = getIgnite().getOrCreateCache(cacheConfiguration);
	}

	// getter and seter
	public Ignite getIgnite() {
		return ignite;
	}

	public String getGridName() {
		return gridName;
	}

	public CacheConfiguration<Object, Object> getCacheConfiguration() {
		return cacheConfiguration;
	}

	public IgniteCache<Object, Object> getCache() {
		return cache;
	}

}
