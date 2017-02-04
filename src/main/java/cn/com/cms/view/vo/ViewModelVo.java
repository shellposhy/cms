package cn.com.cms.view.vo;

import cn.com.cms.view.model.ViewModel;

/**
 * 页面模板VO
 * 
 * @author shishb
 * @version 1.0
 */
public class ViewModelVo {
	private Integer id;
	private String name;
	private String code;
	private String modelType;
	private int orderId;
	private String imgUrl;
	private String fileName;

	public static ViewModelVo convertFromViewModel(ViewModel viewModel) {
		ViewModelVo modelVo = new ViewModelVo();
		modelVo.setId(viewModel.getId());
		modelVo.setName(viewModel.getName());
		modelVo.setCode(viewModel.getCode());
		modelVo.setOrderId(viewModel.getOrderId());
		modelVo.setFileName(viewModel.getFileName());
		modelVo.setModelType(viewModel.getModelType().getTitle());
		return modelVo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
