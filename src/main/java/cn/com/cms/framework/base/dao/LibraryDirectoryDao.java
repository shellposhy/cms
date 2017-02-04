package cn.com.cms.framework.base.dao;

import cn.com.cms.framework.tree.DefaultTreeNode;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.model.BaseLibrary;

/**
 * 数据库目录服务类
 * 
 * @author shishb
 * @version 1.0
 */
public interface LibraryDirectoryDao<T extends BaseLibrary<T>> {

	public T find(Integer id);

	/**
	 * 根据库类型获得目录树
	 * 
	 * @param type
	 * @return
	 */
	public DefaultTreeNode findTree(ELibraryType type);

	/**
	 * 根据类型获取空目录的目录树
	 * 
	 * @param type
	 * @return
	 */
	public DefaultTreeNode findEmptyDirectoryTree(ELibraryType type);

	/**
	 * 保存目录
	 * 
	 * @param directory
	 */
	public void save(T directory);

	/**
	 * 删除目录
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 是否具有子节点
	 */
	public boolean hasChildren(Integer id);

}
