package cn.com.cms.framework.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.cms.framework.base.TreeNodeEntity;

/**
 * 系统默认树形节点
 * 
 * @author shishb
 * @version 1.0
 */
public class DefaultTreeNode extends Node<Integer, String> {

	protected Integer parentId;
	protected DefaultTreeNode parent;
	protected List<DefaultTreeNode> children;
	public boolean checked;
	public boolean nocheck;
	public int level;

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public DefaultTreeNode parent() {
		return parent;
	}

	public void addChildNode(DefaultTreeNode node) {
		if (children == null) {
			children = new ArrayList<DefaultTreeNode>();
		}
		children.add(node);
	}

	public List<DefaultTreeNode> getChildren() {
		return children;
	}

	public static interface PropertySetter<N extends DefaultTreeNode, E extends TreeNodeEntity<E>> {
		void setProperty(N node, E entity);
	}

	public static <E extends TreeNodeEntity<E>> DefaultTreeNode parseTree(List<E> list) {
		return parseTree(DefaultTreeNode.class, list, null);
	}

	public static <T extends DefaultTreeNode, E extends TreeNodeEntity<E>> T parseTree(Class<T> claszz, List<E> list,
			PropertySetter<T, E> propertySetter) {
		T root = null;
		try {
			root = claszz.newInstance();
			root.id = 0;
			root.name = "root";
			root.level = 0;
			if (null != propertySetter) {
				propertySetter.setProperty(root, null);
			}
			generateTree(claszz, root, list, propertySetter);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return root;
	}

	private static <T extends DefaultTreeNode, E extends TreeNodeEntity<E>> void generateTree(Class<T> claszz, T root,
			List<E> list, PropertySetter<T, E> propertySetter) throws InstantiationException, IllegalAccessException {
		List<T> nodeList = new ArrayList<T>();
		for (E element : list) {
			if (element.getParentID().equals(root.id)) {
				T node = claszz.newInstance();
				node.id = element.getId();
				node.name = element.getName();
				if (null != propertySetter) {
					propertySetter.setProperty(node, element);
				}
				node.parent = root;
				node.parentId = root.id;
				node.level = root.level + 1;
				root.addChildNode(node);
				nodeList.add(node);
			}
		}
		if (nodeList.size() > 0) {
			for (T node : nodeList) {
				generateTree(claszz, node, list, propertySetter);
			}
		}
	}

	public static DefaultTreeNode findParentNode(DefaultTreeNode root, Integer parentID) {
		if (root == null)
			return null;
		if (root.id.equals(parentID))
			return root;
		if (root.getChildren() != null) {
			for (DefaultTreeNode child : root.getChildren()) {
				DefaultTreeNode parent = findParentNode(child, parentID);
				if (parent != null)
					return parent;
			}
		}
		return null;
	}

	public static <T extends DefaultTreeNode> T partTree(T treeNode) {
		if (treeNode == null)
			return null;
		if (treeNode.children != null) {
			for (int i = 0; i < treeNode.children.size(); i++) {
				DefaultTreeNode childPartTree = partTree(treeNode.children.get(i));
				if (childPartTree == null) {
					treeNode.children.remove(i--);
				}
			}
			if (treeNode.children.size() == 0)
				treeNode.children = null;
		}
		if (treeNode.children != null || treeNode.checked)
			return treeNode;
		else
			return null;
	}

	/**
	 * 把命中的rList转换成树，树上包含所有命中的节点以及它们的所有父级及以上级节点。
	 * 
	 * @param claszz
	 *            T的class
	 * @param rList
	 *            搜索命中的节点
	 * @param allMap
	 *            所有节点的id-实体 Map
	 * @param propertySetter
	 *            属性设置器
	 * @return DefaultTreeNode的子类
	 */
	public static <T extends DefaultTreeNode, E extends TreeNodeEntity<E>> T parseTree(Class<T> claszz, List<E> rList,
			Map<Integer, E> allMap, PropertySetter<T, E> propertySetter) {
		List<E> resultList = new ArrayList<E>();
		for (E item : rList) {
			List<E> parents = findParentNodes(item, allMap, resultList);
			for (E e : parents) {
				boolean hasElement = false;
				for (E result : resultList) {
					if (e.getId().equals(result.getId()))
						hasElement = true;
				}
				if (!hasElement)
					resultList.add(e);
			}
			boolean hasElement = false;
			for (E result : resultList) {
				if (item.getId().equals(result.getId()))
					hasElement = true;
			}
			if (!hasElement)
				resultList.add(item);
		}
		return parseTree(claszz, resultList, propertySetter);
	}

	/**
	 * 递归查找节点的父路径
	 * 
	 * @param node
	 *            节点
	 * @param allMap
	 *            所有节点
	 * @param rList
	 *            已存在的路径节点
	 * @return
	 */
	private static <T extends TreeNodeEntity<T>> List<T> findParentNodes(T node, Map<Integer, T> allMap,
			List<T> rList) {
		List<T> result = new ArrayList<T>();
		if (null == rList) {
			rList = new ArrayList<T>();
		}
		if (node.getParentID() == null)
			return result;
		T parent = allMap.get(node.getParentID());
		if (null != parent && !rList.contains(parent)) {
			result.addAll(findParentNodes(parent, allMap, rList));
			result.add(parent);
		}
		return result;
	}
}
