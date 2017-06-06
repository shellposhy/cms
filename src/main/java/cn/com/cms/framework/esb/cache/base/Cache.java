package cn.com.cms.framework.esb.cache.base;

/**
 * 缓存接口
 * 
 * @history
 * @see
 */
public interface Cache<T> {

	/**
	 * 从指定的缓存块中获取缓存对象
	 * 
	 * @param key
	 *            缓存对象的键值
	 * @return 返回缓存对象
	 */
	T get(Object key);

	/**
	 * 将对象放入指定缓存块中
	 * 
	 * @param key
	 *            缓存对象的键值
	 * @param value
	 *            要缓存的对象
	 */
	void put(Object key, Object value);

	/**
	 * 从缓存块中清除掉对象
	 * 
	 * @param key
	 *            缓存对象的键值
	 * @return
	 */
	boolean remove(Object key);

}
