package cn.com.cms.framework.esb.cache.impl;

import javax.annotation.Resource;

import org.apache.ignite.cache.spring.SpringCacheManager;
import org.springframework.stereotype.Service;

import cn.com.cms.framework.esb.cache.base.Cache;

@Service
public class IgniteCacheImpl<T> implements Cache<T> {

	@Resource
	private SpringCacheManager springCacheManager;

	public T get(String cacheName, Object key) {
		return null;
	}

	public void put(String cacheName, Object key, Object value) {

	}

	public boolean remove(String cacheName, Object key) {
		return false;
	}

	public void removeAll(String cacheName) {

	}

}
