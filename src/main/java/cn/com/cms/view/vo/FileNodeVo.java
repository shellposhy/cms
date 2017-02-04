package cn.com.cms.view.vo;

import java.util.List;

/**
 * 文件结构树VO
 * 
 * @author shishb
 * @version 1.0
 */
public class FileNodeVo {
	private String name;
	private boolean directory;
	private List<FileNodeVo> children;
	private String absolutePath;
	private String fileUrl;

	public boolean isDirectory() {
		return directory;
	}

	public void setDirectory(boolean directory) {
		this.directory = directory;
	}

	public List<FileNodeVo> getChildren() {
		return children;
	}

	public void setChildren(List<FileNodeVo> children) {
		this.children = children;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the url
	 */
	public String getFileUrl() {
		return fileUrl;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setFileUrl(String url) {
		this.fileUrl = url;
	}

}
