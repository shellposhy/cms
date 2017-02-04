package cn.com.cms.framework.template;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import freemarker.cache.TemplateLoader;

/**
 * freemarker模板加载服务类
 * 
 * @author shishb
 * @version 1.0
 */
public class StringTemplateLoader implements TemplateLoader {

	private String template;

	/**
	 * 构造函数
	 * 
	 * @return
	 */
	public StringTemplateLoader(String template) {
		this.template = template;
		if (template == null) {
			this.template = "";
		}
	}

	/**
	 * 关闭模板资源
	 * 
	 * @param templateSource
	 * @return
	 */
	public void closeTemplateSource(Object templateSource) throws IOException {
		((StringReader) templateSource).close();
	}

	/**
	 * 根据名称查找模板
	 * 
	 * @param name
	 * @return
	 */
	public Object findTemplateSource(String name) throws IOException {
		return new StringReader(template);
	}

	/**
	 * 获得最新修改模板信息
	 * 
	 * @param templateSource
	 * @return
	 */
	public long getLastModified(Object templateSource) {
		return 0;
	}

	/**
	 * 获得模板<code>Reader</code>对象
	 * 
	 * @param templateSource
	 * @param encoding
	 * @return
	 */
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		return (Reader) templateSource;
	}

}