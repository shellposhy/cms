package cn.com.cms.framework.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import cn.com.people.data.util.AESUtil;

/**
 * 数据库属性文件解密
 */
public class DecryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private final static String KEY = "ZHIHUI_DATA";

	/**
	 * 重写父类方法，解密指定属性名对应的属性值
	 */
	protected String convertProperty(String propertyName, String propertyValue) {
		String value = propertyValue;
		if (isEncryptPropertyVal(propertyName)) {
			value = AESUtil.decrypt(propertyValue, KEY);
		}
		return value;
	}

	/**
	 * 判断属性值是否需要解密，这里我约定需要解密的属性名用password结尾
	 * 
	 * @param propertyName
	 * @return
	 */
	private boolean isEncryptPropertyVal(String propertyName) {
		if (propertyName.endsWith("password")) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println(AESUtil.encrypt("1234", KEY));
	}
}
