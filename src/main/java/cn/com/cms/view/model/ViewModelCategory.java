package cn.com.cms.view.model;

import cn.com.cms.framework.base.TreeNodeEntity;

/**
 * 页面模板目录表
 * 
 * @author shishb
 * @version 1.0
 */
public class ViewModelCategory extends TreeNodeEntity<ViewModelCategory> {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String code;
	private Integer orderId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
}