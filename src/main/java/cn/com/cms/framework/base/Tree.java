package cn.com.cms.framework.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.Predicate;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 树形结构基础服务类
 * 
 * @author shishb
 * @version 1.0
 */
public class Tree<T extends TreeNodeEntity<T>> implements Serializable {
	private static final long serialVersionUID = -4470274101422980661L;
	public static final int ROOT_LEVEL = 0;
	private List<TreeNodeEntity<T>> rootList = Lists.newLinkedList();
	private Map<Integer, T> idNodeMap = Maps.newLinkedHashMap();

	public T get(Integer id) {
		return idNodeMap.get(id);
	}

	/**
	 * 先序遍历列表
	 */
	private List<T> preOrderList;

	@SuppressWarnings("unchecked")
	public List<T> getRootNodes() {
		List<T> rootNodeEntities = getPreOrderList();
		List<T> ret = new ArrayList<T>();
		ret.addAll(rootNodeEntities);
		CollectionUtils.filter(rootNodeEntities, new Predicate() {
			public boolean evaluate(Object object) {
				if (object instanceof TreeNodeEntity) {
					TreeNodeEntity<T> node = (TreeNodeEntity<T>) object;
					if (node.getParentID() == null) {
						return true;
					}
				}
				return false;
			}
		});
		return rootNodeEntities;
	}

	public List<T> getAllParentNodesById(Integer id) {
		List<T> parentNodeEntities = new ArrayList<T>();
		loopParentNodes(id, parentNodeEntities);
		return parentNodeEntities;
	}

	private void loopParentNodes(Integer id, List<T> parentNodeEntities) {
		T node = get(id);
		if (node == null) {
			return;
		}
		if (node.getParentID() != null && node.getParentID().longValue() > 0) {
			loopParentNodes(node.getParentID(), parentNodeEntities);
			parentNodeEntities.add(get(node.getParentID()));
		}
	}

	/**
	 * 获取以某个节点下的所有孩子节点
	 * 
	 * @param id
	 * @return
	 */
	public Set<Integer> getSubTreeNodes(Integer id) {
		Set<Integer> result = new HashSet<Integer>();
		if (MapUtils.isEmpty(idNodeMap)) {
			return result;
		}
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(id);
		while (!queue.isEmpty()) {// 获取目标组的下一层直接孩子节点的集合
			id = queue.remove();
			T node = idNodeMap.get(id);
			if (null == node)
				continue;
			result.add(id);
			List<TreeNodeEntity<T>> children = node.getChildren();
			if (children.size() > 0)
				// 将队首的所有孩子节点入队
				for (TreeNodeEntity<T> child : children)
				queue.add(child.getId());
		}
		return result;
	}

	public Set<Integer> getSubTreeLeafNodes(Integer id) {
		// 实现按层次遍历以orgid为根的子树
		Set<Integer> leafs = new HashSet<Integer>();
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(id);
		while (!queue.isEmpty()) {
			// 获取目标组的下一层直接孩子节点的集合
			id = queue.remove();
			T node = idNodeMap.get(id);
			List<TreeNodeEntity<T>> children = node.getChildren();
			if (children.size() > 0)
				// 将队首的所有孩子节点入队
				for (TreeNodeEntity<T> child : children)
				queue.add(child.getId());
			else
				// 此节点为叶子节点
				leafs.add(id);
		}
		return leafs;
	}

	public Collection<T> getAllNodeEntity() {
		return idNodeMap.values();
	}

	public Map<Integer, T> getIdNodeMap() {
		return idNodeMap;
	}

	public void setIdNodeMap(Map<Integer, T> idNodeMap) {
		this.idNodeMap = idNodeMap;
	}

	/**
	 * 添加根节点
	 * 
	 */
	public void addRoot(TreeNodeEntity<T> rootNode) {
		rootList.add(rootNode);
		preOrderList = null;
	}

	public List<T> getPreOrderList() {
		if (preOrderList == null) {
			preOrderList = doPreOrderTraversal();
		}
		return preOrderList;
	}

	/**
	 * 先序遍历
	 */
	public List<T> doPreOrderTraversal() {
		if (rootList == null) {
			return null;
		}
		List<T> result = new ArrayList<T>();
		for (TreeNodeEntity<T> node : rootList) {
			doPreOrderTraversal(node, result);
		}
		return result;
	}

	private void doPreOrderTraversal(TreeNodeEntity<T> node, List<T> result) {
		if (node == null) {
			return;
		}
		result.add(node.getOneself());
		for (TreeNodeEntity<T> childNode : node.getChildren()) {
			doPreOrderTraversal(childNode, result);
		}
	}

	public void setPreOrderList(List<T> preOrderList) {
		this.preOrderList = preOrderList;
	}

	/**
	 * 遍历搜索节点
	 * 
	 * @param nodeList
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unused")
	private TreeNodeEntity<T> seekNode(List<TreeNodeEntity<T>> nodeList, T t) {
		TreeNodeEntity<T> retNode = null;
		if (CollectionUtils.isNotEmpty(nodeList)) {
			for (TreeNodeEntity<T> node : nodeList) {
				if (node.getOneself().equals(t)) {
					retNode = node;
					break;
				} else if ((retNode = seekNode(node.getChildren(), t)) != null) {
					break;
				}
			}
		}
		return retNode;
	}

}
