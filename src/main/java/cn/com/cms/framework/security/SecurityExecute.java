package cn.com.cms.framework.security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import cn.com.cms.analyze.model.Analyze;
import cn.com.cms.analyze.model.Wrapper;

/**
 * 双检查锁机制安全执行服务类
 * 
 * @author shishb
 * @version 1.0
 */
public class SecurityExecute {

	private Wrapper<Analyze> wrapper;

	/**
	 * 实例化安全执行包装类
	 * 
	 * @return
	 */
	public Wrapper<Analyze> getWrapper() {
		Wrapper<Analyze> result = wrapper;
		if (null == result) {
			synchronized (this) {
				if (null == wrapper) {
					wrapper = new Wrapper<Analyze>(new Analyze());
				}
				result = wrapper;
			}
		}
		return result;
	}

	/**
	 * 执行方法
	 * 
	 * @return
	 */
	public void execute() {
		CacheBuilder.newBuilder().build(new CacheLoader<Wrapper<Analyze>, Analyze>() {
			public Analyze load(Wrapper<Analyze> key) throws Exception {
				key = getWrapper();
				Analyze result = key.object;
				result.execute();
				return result;
			}
		});
	}
}
