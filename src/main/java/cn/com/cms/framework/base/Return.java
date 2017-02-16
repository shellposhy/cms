package cn.com.cms.framework.base;

/**
 * 基础结果返回值
 * 
 * @author shishb
 * @version 1.0
 */
public class Return<T> {
	private boolean success;
	private String code;
	private String message;
	private T model;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}
}
