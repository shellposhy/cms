package cn.com.cms.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * 容器工具包
 * <p>
 * 常用的容器一些通用的处理方式
 * 
 * @author shishb
 * @version 1.0
 */
public class CollectionUtil {

	/**
	 * 把逗号分隔的字符串转为列表
	 * 
	 * @param commaString
	 * @return
	 */
	public static List<Integer> changeStringIntSplitComma2List(String commaString) {
		List<Integer> result = null;
		if (!Strings.isNullOrEmpty(commaString)) {
			result = Lists.newArrayList();
			String[] ids = commaString.split(",");
			if (null != ids && ids.length > 0) {
				for (String id : ids) {
					result.add(Integer.valueOf(id));
				}
			}
		}
		return result;
	}

	/**
	 * map对象按值排序
	 * 
	 * @param map
	 *            需要按值排序的<code>Map</code>对象
	 * @param type
	 *            排序类型<code>true</code>升序<code>false</code>降序
	 * @return
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sort(Map<K, V> map, final boolean type) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				if (type) {
					return (o1.getValue()).compareTo(o2.getValue());
				} else {
					return (o2.getValue()).compareTo(o1.getValue());
				}
			}
		});
		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}
