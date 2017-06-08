package cn.com.cms.framework.base.table;

import java.util.HashMap;
import java.util.Map;

/***
 * 接收前台dataTables列表请求参数
 * 
 * @author shishb
 * @version 1.0
 */
public class JsonPara {
	public static final class DataTablesParaNames {
		public static final String sEcho = "sEcho";
		public static final String iDisplayStart = "iDisplayStart";
		public static final String sSearch = "sSearch";
		public static final String iType = "iType";
		public static final String day = "day";
		public static final String searchIdStr = "searchIdStr";
		public static final String mSearch = "mSearch";
		public static final String sortField = "sortField";
		public static final String para = "para";
		public static final String timePeroid = "timePeroid";
		public static final String type = "type";
		public static final String queryType = "queryType";
	}

	public String name;
	public String value;

	public static Map<String, String> getParaMap(JsonPara[] paras) {
		Map<String, String> map = null;
		if (paras != null && paras.length > 0) {
			map = new HashMap<String, String>(paras.length);
			for (JsonPara para : paras) {
				map.put(para.name, para.value);
			}
		}
		return map;
	}
}
