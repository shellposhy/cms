package cn.com.cms.view.model;

import cn.com.cms.framework.base.BaseEntity;
import cn.com.cms.view.constant.EModelType;

/**
 * 页面模板
 * 
 * @author shishb
 * @version 1.0
 */
public class ViewModel extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Integer categoryId;
	private String name;
	private String code;
	private EModelType modelType;
	private String fileName;
	private String content;
	private int orderId;
	private String image;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public EModelType getModelType() {
		return modelType;
	}

	public void setModelType(EModelType modelType) {
		this.modelType = modelType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

}
