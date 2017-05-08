package cn.com.cms.framework.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * 树形节点基础类
 * 
 * @author shishb
 * @version 1.0
 * @see BaseEntity
 * @see Serializable
 * @see Cloneable
 */
public abstract class TreeNodeEntity<K> extends BaseEntity implements Serializable, Cloneable {
	private static final long serialVersionUID = 239098856690572691L;
	protected String name;
	protected Integer parentID;
	private String path;
	private int level;
	private K oneself;
	private TreeNodeEntity<K> parent;
	private List<TreeNodeEntity<K>> children;

	public TreeNodeEntity() {
	}

	public TreeNodeEntity(K t, TreeNodeEntity<K> parent) {
		oneself = t;
		this.parent = parent;
	}

	public List<K> getAllChildrenList() {
		List<K> allChildren = new ArrayList<K>();
		seekAllChildrenList(allChildren, children);
		return allChildren;
	}

	private void seekAllChildrenList(List<K> allChildren, List<TreeNodeEntity<K>> nodeChildren) {
		if (CollectionUtils.isNotEmpty(nodeChildren)) {
			for (TreeNodeEntity<K> node : nodeChildren) {
				allChildren.add(node.oneself);
				seekAllChildrenList(allChildren, node.children);
			}
		}
	}

	/**
	 * 递归几层内的所有子节点
	 * 
	 * @param levelCount
	 * @return
	 */
	public List<K> getChildrenList(int levelCount) {
		List<K> allChildren = new ArrayList<K>();
		seekAllChildrenList(allChildren, children, levelCount);
		return allChildren;
	}

	private void seekAllChildrenList(List<K> allChildren, List<TreeNodeEntity<K>> nodeChildren, int level) {
		if (CollectionUtils.isNotEmpty(nodeChildren) && level > 0) {
			for (TreeNodeEntity<K> node : nodeChildren) {
				allChildren.add(node.oneself);
				seekAllChildrenList(allChildren, node.children, level - 1);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Object clone() {
		TreeNodeEntity<K> node = null;
		try {
			node = (TreeNodeEntity<K>) super.clone();
		} catch (CloneNotSupportedException e) {
			node = null;
		}
		return node;
	}

	public boolean isRoot() {
		return parent == null ? true : false;
	}

	public boolean isLeaf() {
		return children == null ? true : false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentID() {
		return parentID;
	}

	public void setParentID(Integer parentId) {
		this.parentID = parentId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int childrenSize() {
		return children != null ? children.size() : 0;
	}

	public K getOneself() {
		return oneself;
	}

	public void setOneself(K oneself) {
		this.oneself = oneself;
	}

	public TreeNodeEntity<K> getParent() {
		return parent;
	}

	public void setParent(TreeNodeEntity<K> parent) {
		this.parent = parent;
	}

	public List<TreeNodeEntity<K>> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNodeEntity<K>> children) {
		this.children = children;
	}

}
