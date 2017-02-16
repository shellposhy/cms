package cn.com.cms.data.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.framework.tree.DefaultTreeNode;
import cn.com.cms.framework.tree.DefaultTreeNode.PropertySetter;
import cn.com.cms.library.constant.EStatus;
import cn.com.cms.library.dao.DataSortMapper;
import cn.com.cms.library.dao.LibraryMapper;
import cn.com.cms.library.model.BaseLibrary;
import cn.com.cms.library.model.DataSort;

/**
 * 数据分类服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class DataSortService {
	@Resource
	private DataSortMapper dataSortMapper;
	@Resource
	private LibraryMapper<?> libraryMapper;

	/**
	 * 获取库的所有数据分类
	 * 
	 * @param dbId
	 * @return
	 */
	public List<DataSort> findByDBId(Integer dbId) {
		return dataSortMapper.findByDBId(dbId);
	}

	/**
	 * 获得名称相关的所有标签
	 * 
	 * @param name
	 * @return
	 */
	public List<DataSort> findByName(String name) {
		return dataSortMapper.findByName(name);
	}

	/**
	 * 根据编码和库编号查询
	 * 
	 * @param code
	 * @param dbId
	 * @return
	 */
	public DataSort findByCode(String code, Integer dbId) {
		return dataSortMapper.findByCode(code, dbId);
	}

	/**
	 * 根据名称和父节点查询
	 * 
	 * @param name
	 * @param parentId
	 * @return
	 */
	public DataSort findByNameAndParentId(String name, Integer parentId) {
		return dataSortMapper.findByNameAndParentId(name, parentId);
	}

	/**
	 * 根据编码和库查询
	 * 
	 * @param code
	 * @param dbId
	 * @return
	 */
	public DataSort findByCodeAndDB(String code, Integer dbId) {
		return dataSortMapper.findByCodeAndDB(code, dbId);
	}

	/**
	 * 根据数据库编号和类型查询
	 * 
	 * @param dbId
	 * @param type
	 * @return
	 */
	public List<DataSort> findByDbIdAndType(Integer dbId, Integer type) {
		return dataSortMapper.findByDbIdAndType(dbId, type);
	}

	/**
	 * 根据父节点和父节点获得树
	 * 
	 * @param dbId
	 * @param parentId
	 * @return
	 */
	public DefaultTreeNode findTreeByParentId(Integer dbId, Integer parentId) {
		List<DataSort> allList = findAllByDBId(dbId);
		DefaultTreeNode root = DefaultTreeNode.parseTree(allList);
		return DefaultTreeNode.findParentNode(root, parentId);
	}

	/**
	 * 查询库及标签和全局标签的树
	 * 
	 * @param dbId
	 * @return
	 */
	public DefaultTreeNode findCommonAndDBSortTree(Integer dbId, Integer type) {
		DefaultTreeNode result = new DefaultTreeNode();
		List<DataSort> dbList = dataSortMapper.findByDbIdAndType(dbId, type);
		if (!dbId.equals(0)) {
			List<DataSort> commonList = dataSortMapper.findByDbIdAndType(0, type);
			DefaultTreeNode dbNode = DefaultTreeNode.parseTree(dbList);
			DefaultTreeNode commonNode = DefaultTreeNode.parseTree(commonList);
			result.name = "root";
			result.id = 0;
			dbNode.name = "库标签";
			dbNode.id = -1;
			dbNode.nocheck = true;
			commonNode.name = "全局标签";
			commonNode.id = -2;
			commonNode.nocheck = true;
			result.name = "root";
			result.id = 0;
			result.addChildNode(commonNode);
			result.addChildNode(dbNode);
		} else {
			result = DefaultTreeNode.parseTree(dbList);
		}

		return result;
	}

	/**
	 * 根据名称查询数据库的数据分类
	 * 
	 * @param name
	 * @param dbId
	 * @param type
	 * @return
	 */
	public DefaultTreeNode findTreeByNameAndDBId(String name, Integer dbId, Integer type) {
		DefaultTreeNode result = new DefaultTreeNode();
		List<DataSort> allList = dataSortMapper.findByDbIdAndType(dbId, type);
		if (null == name || name.isEmpty()) {
			result = DefaultTreeNode.parseTree(allList);
		} else {
			List<DataSort> rList = dataSortMapper.findByNameDbIdAndType(name, dbId, type);
			Map<Integer, DataSort> allMap = new HashMap<Integer, DataSort>();
			for (DataSort ds : allList) {
				allMap.put(ds.getId(), ds);
			}
			result = DefaultTreeNode.parseTree(DefaultTreeNode.class, rList, allMap,
					new PropertySetter<DefaultTreeNode, DataSort>() {
						public void setProperty(DefaultTreeNode node, DataSort entity) {
						}
					});
		}
		return result;
	}

	/**
	 * 根据名称和数据库ID查询数据分类列表
	 * 
	 * @param name
	 * @param dbId
	 * @return
	 */
	public List<DataSort> findByNameAndDBId(String name, Integer dbId) {
		return dataSortMapper.findByNameAndDBId(name, dbId);
	}

	/**
	 * 根据ID查询数据分类
	 * 
	 * @param id
	 * @return
	 */
	public DataSort find(Integer id) {
		return dataSortMapper.find(id);
	}

	/**
	 * 根据pathCode查询
	 * 
	 * @param pathCode
	 * @return
	 */
	public DataSort findByPathCode(String pathCode) {
		return dataSortMapper.findByPathCode(pathCode);
	}

	/**
	 * 判断数据分类是否含有子节点
	 * 
	 * @param id
	 * @return
	 */
	public boolean hasChildren(Integer id) {
		List<DataSort> ds = dataSortMapper.findByParentId(id);
		return ds.size() > 0 ? true : false;
	}

	/**
	 * 根据数据库ID查询所有的数据分类
	 * 
	 * @param dbId
	 * @return
	 */
	private List<DataSort> findAllByDBId(Integer dbId) {
		return dataSortMapper.findByDBId(dbId);
	}

	/**
	 * 保存数据分类
	 * 
	 * @param dataSort
	 * @return
	 */
	public void saveDataSort(DataSort dataSort) {
		DataSort parent = dataSortMapper.find(dataSort.getParentID());
		if (dataSort.getBaseId() != null && dataSort.getBaseId().equals(0)) {
			if (null == parent) {
				dataSort.setPathCode("/" + dataSort.getCode() + "/");
			} else {
				dataSort.setPathCode(parent.getPathCode() + dataSort.getCode() + "/");
			}
		} else {
			BaseLibrary<?> library = libraryMapper.find(dataSort.getBaseId());
			String basePathCode = library.getPathCode();
			if (null == parent) {
				dataSort.setPathCode(basePathCode + dataSort.getCode() + "/");
			} else {
				dataSort.setPathCode(parent.getPathCode() + dataSort.getCode() + "/");
			}
		}
		if (null == dataSort.getId()) {
			dataSort.setOrderId(0);
			dataSort.setStatus(EStatus.Normal);
			dataSortMapper.insert(dataSort);
			dataSort.setOrderId(dataSort.getId());
			dataSortMapper.update(dataSort);
		} else {
			DataSort _dataSort = dataSortMapper.find(dataSort.getId());
			dataSort.setOrderId(_dataSort.getOrderId());
			dataSort.setStatus(_dataSort.getStatus());
			dataSort.setCreatorId(_dataSort.getCreatorId());
			dataSort.setCreateTime(_dataSort.getCreateTime());
			dataSortMapper.update(dataSort);
		}
	}

	/**
	 * 根据库Id和Code查询
	 * 
	 * @param code
	 * @param dbId
	 * @return
	 */
	public DataSort findByDbAndCode(String code, Integer dbId) {
		return dataSortMapper.findByDbAndCode(code, dbId);
	}

	/**
	 * 删除数据分类
	 * 
	 * @param id
	 * @return
	 */
	public void deleteDataSort(Integer id) {
		dataSortMapper.delete(id);
	}

	/**
	 * 获取分类跟节点
	 * 
	 * @param id
	 * @return
	 */
	public List<DataSort> findRootByDBId(Integer id) {
		return dataSortMapper.findRootByDBId(id);
	}

	/**
	 * 根据父节点查询
	 * 
	 * @param parentId
	 * @return
	 */
	public List<DataSort> findByParentId(Integer parentId) {
		return dataSortMapper.findByParentId(parentId);
	}
}
