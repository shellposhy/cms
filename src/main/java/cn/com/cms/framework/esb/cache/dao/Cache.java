package cn.com.cms.framework.esb.cache.dao;

import cn.com.cms.framework.esb.cache.base.EExpiryPolicy;

public interface Cache<T> {

	public T get(Object key);

	public void put(Object key, Object value);

	public void put(Object key, Object value, long time);

	public void put(Object key, Object value, EExpiryPolicy expiryPolicy, long time);

	public boolean remove(Object key);

}
