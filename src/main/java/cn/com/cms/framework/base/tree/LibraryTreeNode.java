package cn.com.cms.framework.base.tree;

import java.util.List;

/**
 * 数据库/标签节点数
 * 
 * @author shishb
 * @version 1.0
 */
public class LibraryTreeNode extends DefaultTreeNode {
	public int realId;
	public boolean isDir;
	public String uri;
	public String iconSkin;
	public boolean isSort;
	public String href;

	public int getRealId() {
		return realId;
	}

	public void setRealId(int realId) {
		this.realId = realId;
	}

	public boolean isDir() {
		return isDir;
	}

	public void setDir(boolean isDir) {
		this.isDir = isDir;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public DefaultTreeNode parent() {
		return super.parent();
	}

	public void addChildNode(DefaultTreeNode node) {
		super.addChildNode(node);
	}

	public List<DefaultTreeNode> getChildren() {
		return super.getChildren();
	}

	public boolean isSort() {
		return isSort;
	}

	public void setSort(boolean isSort) {
		this.isSort = isSort;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

}
