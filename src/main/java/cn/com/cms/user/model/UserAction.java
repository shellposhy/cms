package cn.com.cms.user.model;

import cn.com.cms.framework.base.TreeNodeEntity;

/**
 * 系统权限对象
 * 
 * @author shishb
 * @version 1.0
 */
public class UserAction extends TreeNodeEntity<UserAction> {
	private static final long serialVersionUID = 1L;
	private String code;
	private String uri;
	private String iconSkin;
	private Integer type;
	private Integer orderID;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOrderID() {
		return orderID;
	}

	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}
}
