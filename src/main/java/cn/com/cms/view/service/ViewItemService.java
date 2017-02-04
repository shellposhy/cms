package cn.com.cms.view.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.view.dao.ViewItemMapper;
import cn.com.cms.view.model.ViewItem;

/**
 * 页面项目管理服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class ViewItemService {
	@Resource
	private ViewItemMapper viewItemMapper;

	/**
	 * 根据模板编号和编码查询
	 * 
	 * @param modelId
	 * @param code
	 * @return
	 */
	public cn.com.cms.view.model.ViewItem findByModelIdAndCode(Integer modelId, String code) {
		return viewItemMapper.findByModelIdAndCode(modelId, code);
	}

	/**
	 * 根据模型Id查询数据
	 * 
	 * @param modelId
	 *            模型id
	 * @return {@link List}
	 */
	public List<ViewItem> findByModelId(Integer modelId) {
		return viewItemMapper.findByModelId(modelId);
	}
}
