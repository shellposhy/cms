package cn.com.cms.analyze.wrap;

/**
 * 安全执行包装类
 * 
 * @author shishb
 * @version 1.0
 */
public class Wrapper<T> {

	// 对象
	public final T object;

	public Wrapper(T object) {
		this.object = object;
	}
}
