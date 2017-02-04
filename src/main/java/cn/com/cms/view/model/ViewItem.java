package cn.com.cms.view.model;

import cn.com.cms.framework.base.BaseEntity;
import cn.com.cms.view.constant.EItemType;

/**
 * 页面项目
 * 
 * @author shishb
 * @version 1.0
 */
public class ViewItem extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Integer modelId;
	private String code;
	private EItemType itemType;
	private String contentTypes;
	private String content;
	private String contentHtml;
	private int maxRows;
	private int maxWords;
	private String dbTreeSelId;

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public EItemType getItemType() {
		return itemType;
	}

	public void setItemType(EItemType itemType) {
		this.itemType = itemType;
	}

	public String getContentTypes() {
		return contentTypes;
	}

	public void setContentTypes(String contentTypes) {
		this.contentTypes = contentTypes;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentHtml() {
		return contentHtml;
	}

	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public int getMaxWords() {
		return maxWords;
	}

	public void setMaxWords(int maxWords) {
		this.maxWords = maxWords;
	}

	public String getDbTreeSelId() {
		return dbTreeSelId;
	}

	public void setDbTreeSelId(String dbTreeSelId) {
		this.dbTreeSelId = dbTreeSelId;
	}

}
