package cn.com.cms.library.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.framework.base.dao.LibraryDirectoryDao;
import cn.com.cms.framework.tree.DefaultTreeNode;
import cn.com.cms.library.constant.ELibraryNodeType;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.constant.EStatus;
import cn.com.cms.library.dao.LibraryMapper;
import cn.com.cms.library.model.BaseLibrary;

/**
 * 数据库目录服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class LibraryDirectoryService<T extends BaseLibrary<T>> implements LibraryDirectoryDao<T> {

	@Resource
	private LibraryMapper<T> libraryMapper;

	/**
	 * 根据主键查找
	 */
	public T find(Integer id) {
		return libraryMapper.find(id);
	}

	/**
	 * 根据库类型获得目录树
	 * 
	 * @param type
	 * @return
	 */
	public DefaultTreeNode findTree(ELibraryType type) {
		List<T> directoryList = libraryMapper.findAll(type, ELibraryNodeType.Directory);
		return DefaultTreeNode.parseTree(directoryList);
	}

	/**
	 * 根据类型获取空目录的目录树
	 * 
	 * @param type
	 * @return
	 */
	public DefaultTreeNode findEmptyDirectoryTree(ELibraryType type) {
		List<T> directoryList = libraryMapper.findEmptyDirectory(type);
		return DefaultTreeNode.parseTree(directoryList);
	}

	/**
	 * 保存
	 */
	public void save(T directory) {
		T parent = libraryMapper.find(directory.getParentID());
		directory.setPathCode(parent);
		directory.setNodeType(ELibraryNodeType.Directory);
		if (null == directory.getId()) {
			directory.setTables(0);
			directory.setStatus(EStatus.Normal);
			libraryMapper.insert(directory);
		} else {
			T _directory = libraryMapper.find(directory.getId());
			directory.setTables(_directory.getTables());
			directory.setStatus(_directory.getStatus());
			directory.setCreatorId(_directory.getCreatorId());
			directory.setCreateTime(_directory.getCreateTime());
			libraryMapper.update(directory);
		}
	}

	/**
	 * 删除目录
	 * 
	 * @param id
	 * @return
	 */
	public void delete(Integer id) {
		libraryMapper.delete(id);
	}

	/**
	 * 是否有子节点
	 * 
	 * @param id
	 * @return
	 */
	public boolean hasChildren(Integer id) {
		List<T> dbs = libraryMapper.findByParentId(id, null);
		return dbs.size() > 0 ? true : false;
	}

}
