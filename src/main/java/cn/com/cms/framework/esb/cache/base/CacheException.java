package cn.com.cms.framework.esb.cache.base;

/**
 * Catch the cache Exception
 * 
 * @author shishb
 * @version 1.0
 */
public class CacheException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CacheException() {
		super();
	}

	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}

	public CacheException(String message) {
		super(message);
	}

	public CacheException(Throwable cause) {
		super(cause);
	}
}
