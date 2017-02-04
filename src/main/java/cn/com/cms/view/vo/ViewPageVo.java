package cn.com.cms.view.vo;

import cn.com.cms.view.model.ViewPage;

/**
 * 页面实体VO
 * 
 * @author shishb
 * @version 1.0
 *
 */
public class ViewPageVo {
	private Integer id;
	private String title;
	private String code;
	private String type;
	private String status;
	private String createTime;
	private String publishTime;
	private String file;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	// 属性转换
	public static ViewPageVo conver2ViewPageVo(ViewPage viewPage) {
		ViewPageVo viewPageVo = new ViewPageVo();
		viewPageVo.setId(viewPage.getId());
		viewPageVo.setCode(viewPage.getCode());
		viewPageVo.setTitle(viewPage.getName());
		viewPageVo.setFile(viewPage.getFile());
		viewPageVo.setType(viewPage.getPageType().getTitle());
		switch (viewPage.getStatus()) {
		case Original:
			viewPageVo.setStatus("未发布");
			break;
		case Yes:
			viewPageVo.setStatus("已发布");
			break;
		default:
			break;
		}
		return viewPageVo;
	}

}
