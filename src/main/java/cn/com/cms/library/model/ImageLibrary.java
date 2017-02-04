package cn.com.cms.library.model;

import cn.com.cms.library.constant.EImgSizeType;

/**
 * 图片库对象
 * 
 * @author shishb
 * @version 1.0
 */
public class ImageLibrary extends BaseLibrary<ImageLibrary> {
	private static final long serialVersionUID = 1L;
	private EImgSizeType sizeType;
	private int sizeWidth;
	private int sizeHeight;
	private String waterMarkUrl;

	public EImgSizeType getSizeType() {
		return sizeType;
	}

	public void setSizeType(EImgSizeType sizeType) {
		this.sizeType = sizeType;
	}

	public int getSizeWidth() {
		return sizeWidth;
	}

	public void setSizeWidth(int sizeWidth) {
		this.sizeWidth = sizeWidth;
	}

	public int getSizeHeight() {
		return sizeHeight;
	}

	public void setSizeHeight(int sizeHeight) {
		this.sizeHeight = sizeHeight;
	}

	public String getWaterMarkUrl() {
		return waterMarkUrl;
	}

	public void setWaterMarkUrl(String waterMarkUrl) {
		this.waterMarkUrl = waterMarkUrl;
	}
}
