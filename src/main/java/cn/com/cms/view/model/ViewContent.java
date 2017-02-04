package cn.com.cms.view.model;

import cn.com.cms.framework.base.BaseEntity;
import cn.com.cms.view.constant.EContentType;
import cn.com.cms.view.constant.ENameLinkType;

/**
 * 页面內容表
 * 
 * @author shishb
 * @version 1.0
 */
public class ViewContent extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Integer pageId;
	private Integer itemId;
	private String itemCode;
	private String name;
	private EContentType contentType;
	private String content;
	private String filterCondition;
	private String listFormat;
	private ENameLinkType nameLinkType;
	private String nameLink;

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public EContentType getContentType() {
		return contentType;
	}

	public void setContentType(EContentType contentType) {
		this.contentType = contentType;
	}

	public String getFilterCondition() {
		return filterCondition;
	}

	public void setFilterCondition(String filterCondition) {
		this.filterCondition = filterCondition;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getListFormat() {
		return listFormat;
	}

	public void setListFormat(String listFormat) {
		this.listFormat = listFormat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameLink() {
		return nameLink;
	}

	public void setNameLink(String nameLink) {
		this.nameLink = nameLink;
	}

	public ENameLinkType getNameLinkType() {
		return nameLinkType;
	}

	public void setNameLinkType(ENameLinkType nameLinkType) {
		this.nameLinkType = nameLinkType;
	}

}
