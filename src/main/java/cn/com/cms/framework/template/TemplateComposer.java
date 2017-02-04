package cn.com.cms.framework.template;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import cn.com.cms.common.SystemConstant;
import cn.com.cms.view.constant.EItemType;
import cn.com.cms.view.model.ViewItem;
import cn.com.cms.view.model.ViewModel;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 模板设计服务类
 * 
 * @author shishb
 * @version 1.0
 */
public class TemplateComposer {

	static Logger LOG = Logger.getLogger(TemplateComposer.class.getName());

	/**
	 * 扫描模板的可编辑区域
	 * 
	 * @param viewModel
	 * @param filePath
	 * @return
	 */
	public static List<ViewItem> scanEditableArea(ViewModel viewModel, String filePath) {
		String content = readTemplate(filePath);
		return findEditableArea(viewModel, content);
	}

	public static Template composer(String templateContent, List<ViewItem> viewItems) {
		Template template = null;
		for (ViewItem viewItem : viewItems) {
			String itemContent = findTemplateContent(templateContent, viewItem.getCode());
			if (!itemContent.isEmpty()) {
				templateContent = templateContent.replace(itemContent, viewItem.getContent());
			}
		}
		Configuration cfg = new Configuration();
		cfg.setTemplateLoader(new StringTemplateLoader(templateContent));
		cfg.setDefaultEncoding("UTF-8");
		try {
			LOG.debug("模板内容大小：" + templateContent.length());
			template = cfg.getTemplate(templateContent);
		} catch (IOException e) {
			LOG.warn(e);
		}
		return template;
	}

	public static String readTemplate(String filePath) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				stringBuilder.append(temp);
				stringBuilder.append("\r\n");
			}
			reader.close();
		} catch (IOException e) {
			LOG.warn(e);
		}
		return stringBuilder.toString();
	}

	/**
	 * 寻找freemarker模板中可配置的区域
	 * 
	 * @param viewModel
	 * @param templateContent
	 * @return
	 */
	public static List<ViewItem> findEditableArea(ViewModel viewModel, String templateContent) {
		List<ViewItem> result = new ArrayList<ViewItem>();
		try {
			Parser parser = new Parser(templateContent);
			parser.setEncoding("utf-8");
			HasAttributeFilter[] filterAttribute = new HasAttributeFilter[] { new HasAttributeFilter("ecode") };
			NodeFilter nodeFilter = new AndFilter(filterAttribute);
			NodeList nodelist = parser.extractAllNodesThatMatch(nodeFilter);
			for (int i = 0; i < nodelist.size(); i++) {
				Node node = nodelist.elementAt(i);
				TagNode tagNode = new TagNode();
				tagNode.setText(node.toHtml());
				ViewItem viewItem = new ViewItem();
				viewItem.setModelId(viewModel.getId());
				viewItem.setCode(tagNode.getAttribute("ecode"));
				String content = node.getChildren().toHtml().trim();
				viewItem.setContent(content);
				Map<String, String> propertiesMap = new HashMap<String, String>();
				if (content.contains("@")) {
					if (content.contains("@主图列表")) {
						viewItem.setItemType(EItemType.OneImgList);
					} else if (content.contains("@焦点轮播图") || content.contains("@友情链接") || content.contains("@图片新闻列表")
							|| content.contains("@首页轮播图")) {
						viewItem.setItemType(EItemType.ImgList);
					} else if (content.contains("@标签列表")) {
						viewItem.setItemType(EItemType.Sort);
					} else {
						viewItem.setItemType(EItemType.Default);
					}
					content = content.replace("<@", "");
					content = content.replace("/>", "");
					String[] list = content.split(SystemConstant.SPACE_SEPARATOR);
					viewItem.setContentTypes("1");
					int size = list.length;
					for (int j = 1; j < size; j++) {
						String[] properties = list[j].split("=");
						if (properties.length > 1)
							propertiesMap.put(properties[0], properties[1]);
					}
				} else {
					viewItem.setItemType(EItemType.Default);
					viewItem.setContentTypes("1");
				}
				if (null != propertiesMap.get("行数")) {
					viewItem.setMaxRows(Integer.valueOf(propertiesMap.get("行数")));
				}
				if (null != propertiesMap.get("长度")) {
					viewItem.setMaxWords(Integer.valueOf(propertiesMap.get("长度")));
				}
				result.add(viewItem);
			}
		} catch (ParserException e) {
			LOG.warn(e);
		}
		return result;
	}

	/**
	 * 寻找freemarker模板编辑区域
	 * 
	 * @param templateContent
	 * @param value
	 * @return
	 */
	private static String findTemplateContent(String templateContent, String value) {
		String result = "";
		try {
			Parser parser = new Parser(templateContent);
			HasAttributeFilter filterAttribute = new HasAttributeFilter("ecode", value);
			NodeList nodelist = parser.extractAllNodesThatMatch(filterAttribute);
			if (null != nodelist && nodelist.size() > 0) {
				result = nodelist.elementAt(0).getChildren().toHtml().trim();
			}
		} catch (ParserException e) {
			LOG.warn(e);
		}
		return result;

	}

	/**
	 * 替换路径
	 * 
	 * @param templateContent
	 * @param path
	 * @return
	 */
	public static String replacePath(String templateContent, String path) {
		String result = templateContent;
		try {
			Parser parser = new Parser(templateContent);
			NodeFilter filter = new TagNameFilter("link");
			NodeFilter script_filter = new TagNameFilter("script");
			NodeFilter img_filter = new TagNameFilter("img");
			NodeFilter nodeFilter = new OrFilter(filter, script_filter);
			nodeFilter = new OrFilter(nodeFilter, img_filter);
			NodeList nodelist = parser.parse(nodeFilter);
			Node[] nodes = nodelist.toNodeArray();
			for (Node node : nodes) {
				Tag tag = (Tag) node;
				if (null != tag.getAttribute("href")) {
					String str = tag.getAttribute("href");
					if (null != str && !str.contains("http")) {
						// result = result.replace(str, path + "/" + str);
						// 注意路径问题
						result = result.replace(str, str);
					}
				}
				if (null != tag.getAttribute("src")) {
					String str = tag.getAttribute("src");
					if (null != str && !str.contains("http")) {
						if (!str.contains("$")) {
							// result = result.replace("src=\"" + str, "src=\""
							// + path + "/" + str);
							result = result.replace("src=\"" + str, "src=\"" + str);
						}
					}
				}
			}
		} catch (ParserException e) {
			LOG.warn(e);
		}
		return result;
	}
}
