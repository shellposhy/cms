package cn.com.cms.framework.base;

/**
 * 基础结果返回值
 * 
 * @author shishb
 * @version 1.0
 */
public class Return<T> {
	private boolean error;
	private String errorCode;
	private String errorMessage;
	private T result;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
