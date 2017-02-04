package cn.com.cms.system.contant;

/**
 * 系统初始化参数枚举类别
 * 
 * @author shishb
 * @version 1.0
 */
public enum ESysParamType {
	SysParam("系统参数"), UserField("用户扩展字段"), MobileImgSize("移动端图片尺寸");
	private ESysParamType(String value) {
		this.value = value;
	}

	public String getName() {
		return value;
	}

	private String value;
}
