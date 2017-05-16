package cn.com.cms.base.config;

/**
 * 基础业务操作
 * <p>
 * 提供业务<code>Controller</code>层数据操作逻辑
 * 
 * @version 1.0
 * @author shishb
 */
public interface ControllerOperator {

	/**
	 * 业务操作
	 * 
	 * @throws Exception
	 */
	void operate();

	/**
	 * 成功时的页面
	 * 
	 * @return
	 */
	String getSuccessView();

	/**
	 * 失败时的页面
	 * 
	 * @return
	 */
	String getFailureView();

	/**
	 * 失败时执行
	 */
	void onFailure();

}
