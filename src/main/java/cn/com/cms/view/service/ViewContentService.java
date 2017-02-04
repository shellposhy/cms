package cn.com.cms.view.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.view.dao.ViewContentMapper;
import cn.com.cms.view.model.ViewContent;

/**
 * 首页内容配置Service类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class ViewContentService {

	@Resource
	private ViewContentMapper viewContentMapper;

	/**
	 * 新增数据
	 * 
	 * @param content
	 * @return {@link Integer} 自增序列值
	 */
	public Integer insert(ViewContent content) {
		viewContentMapper.deleteByPageIdAndItemId(content.getPageId(), content.getItemId());
		viewContentMapper.insert(content);
		return content.getId();
	}

	/**
	 * 更新数据
	 * 
	 * @param content
	 * @return {@link void}
	 */
	public void update(ViewContent content) {
		viewContentMapper.update(content);
	}

	/**
	 * 根据主键查询内容实体
	 * 
	 * @param id
	 * @return {@link ViewContent}
	 */
	public ViewContent findById(Integer id) {
		return viewContentMapper.find(id);
	}

	/**
	 * 根据页面编号查询内容列表
	 * 
	 * @param pageId
	 * @return {@link List}
	 */
	public List<ViewContent> findByPageId(Integer pageId) {
		return viewContentMapper.findByPageId(pageId);
	}

	/**
	 * 根据页面编号和区域编号查询
	 * 
	 * @param pageId
	 * @param itemId
	 * @return {@link ViewContent}
	 */
	public ViewContent findByPageIdAndItemId(Integer pageId, Integer itemId) {
		return viewContentMapper.findByPageIdAndItemId(pageId, itemId);
	}

	/**
	 * 根据pageId和itemId进行删除
	 * 
	 * @param pageId
	 * @param itemId
	 * @return
	 * 
	 */
	public void deleteByPageIdAndItemId(Integer pageId, Integer itemId) {
		viewContentMapper.deleteByPageIdAndItemId(pageId, itemId);
	}

}
