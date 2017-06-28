package cn.com.cms.framework.esb.cache.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.expiry.EternalExpiryPolicy;
import javax.cache.expiry.ModifiedExpiryPolicy;
import javax.cache.expiry.TouchedExpiryPolicy;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.spring.SpringCacheManager;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.stereotype.Service;

import cn.com.cms.framework.esb.cache.base.EExpiryPolicy;
import cn.com.cms.framework.esb.cache.dao.Cache;

@Service
public class IgniteCacheImpl<T> implements Cache<T> {
	@Resource
	private SpringCacheManager springCacheManager;
	private Ignite ignite;
	private String gridName;
	private CacheConfiguration<Object, Object> cacheConfiguration;
	IgniteCache<Object, Object> cache;

	@PostConstruct
	public void init() throws Exception {
		cacheConfiguration = springCacheManager.getDynamicCacheConfiguration();
		gridName = springCacheManager.getGridName();
		ignite = Ignition.ignite();
		cache = getIgnite().getOrCreateCache(cacheConfiguration);
	}

	@SuppressWarnings("unchecked")
	public T get(Object key) {
		assert ignite != null;
		if (null != getCache()) {
			return (T) getCache().get(key);
		}
		return null;
	}

	public void put(Object key, Object value) {
		assert ignite != null;
		if (null != getCache()) {
			getCache().put(key, value);
		}
	}

	public void put(Object key, Object value, long time) {
		assert ignite != null;
		if (null != getCache()) {
			IgniteCache<Object, Object> cache = getCache(EExpiryPolicy.Touched, time);
			cache.put(key, value);
		}
	}

	public void put(Object key, Object value, EExpiryPolicy expiryPolicy, long time) {
		assert ignite != null;
		if (null != getCache()) {
			IgniteCache<Object, Object> cache = getCache(expiryPolicy, time);
			cache.put(key, value);
		}
	}

	public boolean remove(Object key) {
		assert ignite != null;
		if (null != getCache()) {
			return getCache().remove(key);
		} else {
			return false;
		}
	}

	public Ignite getIgnite() {
		return ignite;
	}

	public String getGridName() {
		return gridName;
	}

	public CacheConfiguration<Object, Object> getCacheConfiguration() {
		return cacheConfiguration;
	}

	public IgniteCache<Object, Object> getCache(EExpiryPolicy policy, long time) {
		switch (policy) {
		case Accessed:
			return getCache().withExpiryPolicy(new AccessedExpiryPolicy(new Duration(TimeUnit.SECONDS, time)));
		case Created:
			return getCache().withExpiryPolicy(new CreatedExpiryPolicy(new Duration(TimeUnit.SECONDS, time)));
		case Eternal:
			return getCache().withExpiryPolicy(new EternalExpiryPolicy());
		case Modified:
			return getCache().withExpiryPolicy(new ModifiedExpiryPolicy(new Duration(TimeUnit.SECONDS, time)));
		case Touched:
		default:
			return getCache().withExpiryPolicy(new TouchedExpiryPolicy(new Duration(TimeUnit.SECONDS, time)));
		}
	}

	public IgniteCache<Object, Object> getCache() {
		return cache;
	}

}
