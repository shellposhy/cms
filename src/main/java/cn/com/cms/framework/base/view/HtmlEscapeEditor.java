package cn.com.cms.framework.base.view;

import java.beans.PropertyEditorSupport;

import org.springframework.web.util.HtmlUtils;

/**
 * Html 特殊字符转义类
 */
public class HtmlEscapeEditor extends PropertyEditorSupport {

	public HtmlEscapeEditor() {
		super();
	}

	public void setAsText(String text) {
		if (text == null) {
			setValue(null);
		} else {
			setValue(HtmlUtils.htmlEscape(text));
		}
	}

	public String getAsText() {
		Object value = getValue();
		return value != null ? HtmlUtils.htmlUnescape(value.toString()) : "";
	}
}
