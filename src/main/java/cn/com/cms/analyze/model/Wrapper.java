package cn.com.cms.analyze.model;

/**
 * 安全执行包装类
 * 
 * @author shishb
 * @version 1.0
 */
public class Wrapper<T> {
	public final T object;

	public Wrapper(T object) {
		this.object = object;
	}
}
