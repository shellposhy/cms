package cn.com.cms.view.model;

import java.util.Date;
import java.util.List;

import cn.com.cms.framework.base.TreeNodeEntity;
import cn.com.cms.library.constant.EDataStatus;
import cn.com.cms.view.constant.EPageType;

/**
 * 页面对象
 * 
 * @author shishb
 * @version 1.0
 */
public class ViewPage extends TreeNodeEntity<ViewPage> {
	private static final long serialVersionUID = 1L;
	private Integer modelId;
	private String file;
	private String code;
	private EPageType pageType;
	private EDataStatus status;
	private Date publishTime;
	private List<ViewContent> viewContentList;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public EPageType getPageType() {
		return pageType;
	}

	public void setPageType(EPageType pageType) {
		this.pageType = pageType;
	}

	public EDataStatus getStatus() {
		return status;
	}

	public void setStatus(EDataStatus status) {
		this.status = status;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public List<ViewContent> getViewContentList() {
		return viewContentList;
	}

	public void setViewContentList(List<ViewContent> viewContentList) {
		this.viewContentList = viewContentList;
	}

}
