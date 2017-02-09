package cn.com.cms.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

/**
 * 获取属性文件工具
 * 
 * @author shishb
 * @version 1.0
 */
public class MessageResources {
	private static Logger log = Logger.getLogger(MessageResources.class.getName());
	public static ResourceBundle resourceBundle;

	public MessageResources() {
	}

	public static void close() {
		resourceBundle = null;
	}

	public static String getValue(String key) {
		if (resourceBundle == null)
			return "";
		if (!resourceBundle.containsKey(key))
			return "";
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			log.error(e);
			e.printStackTrace();
			return "";
		}
	}

	static {
		try {
			resourceBundle = ResourceBundle.getBundle("messages_zh_CN");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
