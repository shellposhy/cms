package cn.com.cms.export.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.com.cms.library.dao.DataSortMapper;
import cn.com.cms.library.model.DataSort;

/**
 * 数据标签树服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class SortTreeService {
	@Resource
	private DataSortMapper dataSortMapper;

	/**
	 * 根据父节点获得节点的所有子树
	 * 
	 * @param code
	 * @param baseId
	 * @return
	 */
	public List<DataSort> findSortTreeByParent(String code, Integer baseId) {
		DataSort root = dataSortMapper.findByCode(code, baseId);
		List<DataSort> result = Lists.newArrayList();
		if (null != root) {
			List<DataSort> child = dataSortMapper.findByParentId(root.getId());
			if (null != child && child.size() > 0) {
				generateTree(child, result);
			}
		}
		return result;
	}

	/**
	 * 递归循环
	 * 
	 * @param child
	 * @param result
	 * @return
	 */
	public void generateTree(List<DataSort> child, List<DataSort> result) {
		if (null != child && child.size() > 0) {
			for (DataSort dataSort : child) {
				List<DataSort> nodes = dataSortMapper.findByParentId(dataSort.getId());
				if (null != nodes && nodes.size() > 0) {
					dataSort.setForDataNode(false);
					result.add(dataSort);
					generateTree(nodes, result);
				} else {
					dataSort.setForDataNode(true);
					result.add(dataSort);
				}
			}
		}
	}
}
