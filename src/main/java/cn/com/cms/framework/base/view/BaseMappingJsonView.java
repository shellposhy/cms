package cn.com.cms.framework.base.view;

import java.util.Map;

import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * Json处理对象
 * 
 * @author shishb
 * @version 1.0
 */
public class BaseMappingJsonView extends MappingJacksonJsonView {

	protected Object filterModel(Map<String, Object> model) {
		Map<?, ?> result = (Map<?, ?>) super.filterModel(model);
		if (result.size() == 1) {
			return result.values().iterator().next();
		} else {
			return result;
		}
	}
}
