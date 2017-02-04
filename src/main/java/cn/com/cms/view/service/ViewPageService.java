package cn.com.cms.view.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.framework.base.Result;
import cn.com.cms.framework.security.UserSecurityService;
import cn.com.cms.view.constant.EPageType;
import cn.com.cms.view.dao.ViewContentMapper;
import cn.com.cms.view.dao.ViewPageMapper;
import cn.com.cms.view.model.ViewPage;
import cn.com.people.data.util.FileUtil;

/**
 * 页面管理服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class ViewPageService {
	@Resource
	private ViewPageMapper viewPageMapper;
	@Resource
	private ViewContentMapper viewContentMapper;
	@Resource
	private UserSecurityService userSecurityService;

	/**
	 * 分页查询结果集
	 * 
	 * @param qs
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	public Result<ViewPage> search(String qs, int firstResult, int pageSize) {
		Result<ViewPage> result = new Result<ViewPage>();
		List<ViewPage> viewPageList = new ArrayList<ViewPage>();
		int totalCount;
		if (null == qs || "".equals(qs)) {
			viewPageList = viewPageMapper.search(null, firstResult, pageSize);
			totalCount = viewPageMapper.count(null);
		}
		viewPageList = viewPageMapper.search(qs, firstResult, pageSize);
		totalCount = viewPageMapper.count(qs);
		result.setList(viewPageList);
		result.setTotalCount(totalCount);
		return result;
	}

	/**
	 * 根据类别查询
	 * 
	 * @param pageType
	 *            首页类型
	 * @return {@link List}
	 */
	public List<ViewPage> findByType(EPageType pageType) {
		return viewPageMapper.findByType(pageType.ordinal());
	}

	/**
	 * 删除（支持批量删除）
	 * 
	 * @param pageIds
	 * @return
	 */
	public void delete(Integer[] pageIds) {
		String classesPath = ViewPageService.class.getResource("/").getPath();
		String dirPath = classesPath.substring(0, classesPath.indexOf("WEB-INF"));
		int length = pageIds.length;
		if (length == 1) {
			ViewPage page = findById(pageIds[0]);
			FileUtil.deleteFile(dirPath + page.getFile());
			viewContentMapper.deleteByPageId(pageIds[0]);
			viewPageMapper.delete(pageIds[0]);
		} else if (length > 1) {
			for (Integer pageId : pageIds) {
				ViewPage page = findById(pageId);
				FileUtil.deleteFile(dirPath + page.getFile());
				viewContentMapper.deleteByPageId(pageId);
			}
			viewPageMapper.batchDelete(pageIds);
		}
	}

	/**
	 * 根据首页编号查询首页内容
	 * 
	 * @param pageId
	 * @return
	 * 
	 */
	public ViewPage findById(Integer pageId) {
		return viewPageMapper.find(pageId);
	}

	/**
	 * 保存(新增，修改)
	 * 
	 * @param viewPage
	 * @return
	 */
	public void save(ViewPage viewPage) {
		if (null == viewPage.getId()) {
			viewPageMapper.insert(viewPage);
		} else {
			update(viewPage);
		}
	}

	/**
	 * 更新
	 * 
	 * @param viewPage
	 * @return
	 */
	public void update(ViewPage viewPage) {
		viewPageMapper.update(viewPage);
	}

	/**
	 * 统计页面数(包括已发布和未发布)
	 * 
	 * @return
	 */
	public Integer count() {
		return viewPageMapper.count(null);
	}
}
