package cn.com.cms.view.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.microduo.index.lucene3.MdSortField;
import com.microduo.index.lucene3.SearchResult;

import cn.com.cms.library.constant.EDataStatus;
import cn.com.cms.library.constant.EDataType;
import cn.com.cms.library.model.DataNavigate;
import cn.com.cms.library.service.LibraryDataService;
import cn.com.cms.library.service.LibraryService;
import cn.com.cms.system.service.CalendarEventService;
import cn.com.cms.system.service.PathService;
import cn.com.cms.view.constant.EPageType;
import cn.com.cms.view.dao.ViewContentMapper;
import cn.com.cms.view.dao.ViewItemMapper;
import cn.com.cms.view.dao.ViewModelMapper;
import cn.com.cms.view.dao.ViewPageMapper;
import cn.com.cms.view.model.ViewContent;
import cn.com.cms.view.model.ViewItem;
import cn.com.cms.view.model.ViewModel;
import cn.com.cms.view.model.ViewPage;
import cn.com.cms.view.vo.ViewPreviewVo;
import cn.com.people.data.util.DateTimeUtil;
import cn.com.people.data.util.FileUtil;
import cn.com.cms.data.util.DataUtil;
import cn.com.cms.data.util.DataVo;
import cn.com.cms.framework.base.table.FieldCodes;
import cn.com.cms.framework.base.tree.DefaultTreeNode;
import cn.com.cms.framework.config.AppConfig;
import cn.com.people.data.util.FreeMarkerUtil;

/**
 * 页面发布类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class ViewPublishService {
	Logger log = Logger.getLogger(ViewPublishService.class.getName());

	@Resource
	private ViewPageMapper viewPageMapper;
	@Resource
	private ViewModelMapper viewModelMapper;
	@Resource
	private ViewContentMapper viewContentMapper;
	@Resource
	private ViewItemMapper viewItemMapper;
	@Resource
	private ViewPreviewService viewPreviewService;
	@Resource
	private CalendarEventService calendarEventService;
	@Resource
	private AppConfig appConfig;
	@Resource
	private PathService pathService;
	@Resource
	private LibraryService<?> libraryService;
	@Resource
	private LibraryDataService libraryDataService;

	/**
	 * 基于时钟轮询的自动页面发布
	 * 
	 * @return
	 */
	public void autoPublish() {
		List<ViewPage> pageList = viewPageMapper.findByTypeAndStatus(EPageType.SysPage, EDataStatus.Yes);
		if (null != pageList) {
			for (ViewPage viewPage : pageList) {
				this.publish(viewPage.getId());
			}
		}
	}

	/**
	 * 发布
	 * 
	 * @param appPath
	 * @param pageId
	 * @return
	 * @author shishb
	 */
	public String publish(Integer pageId) {
		String appPath = appConfig.getAppPath();
		ViewPage page = viewPageMapper.find(pageId);
		ViewModel model = viewModelMapper.find(page.getModelId());
		Map<String, Object> data = new HashMap<String, Object>();
		List<ViewItem> itemList = viewItemMapper.findByModelId(page.getModelId());
		Map<Integer, ViewContent> contentMap = findViewContentMapByPageId(pageId);
		Map<String, Object> calendarData = calendarEventService.calendarEventData();
		for (ViewItem viewItem : itemList) {
			ViewPreviewVo vo = viewPreviewService.preview(viewItem, contentMap, appPath,page);
			data.put(viewItem.getCode(), null == vo ? new ViewPreviewVo() : vo);
		}
		data.putAll(calendarData);
		data.put("appPath", appPath);
		String htmlFileFullName = pathService.getStaticPhysicalParth();
		switch (model.getModelType()) {
		case Subject:
			htmlFileFullName += "/subject/" + model.getCode() + "/index.html";
			FileUtil.createFolder(pathService.getStaticPhysicalParth() + "/subject/" + model.getCode());
			FreeMarkerUtil.publish(pathService.getTemplatePhysicalParth(),
					"/subject/" + model.getCode() + "/index.html", data, htmlFileFullName);
			page.setFile("static/subject/" + model.getCode() + "/index.html");
			break;
		default:
			htmlFileFullName += "/" + model.getCode() + "/index.html";
			FreeMarkerUtil.publish(pathService.getTemplatePhysicalParth(), model.getCode() + "/index.html", data,
					htmlFileFullName);
			page.setFile("static/" + model.getCode() + "/index.html");
			break;
		}
		page.setStatus(EDataStatus.Yes);
		page.setPublishTime(DateTimeUtil.getCurrentDateTime());
		viewPageMapper.update(page);
		return page.getFile();
	}

	/**
	 * 获取区域和区域内容的对应关系
	 * 
	 * @param pageId
	 * @return
	 * @author Cheng
	 * @created 2013-1-25 下午7:15:37
	 */
	public Map<Integer, ViewContent> findViewContentMapByPageId(Integer pageId) {
		Map<Integer, ViewContent> result = new HashMap<Integer, ViewContent>();
		List<ViewContent> contentList = viewContentMapper.findByPageId(pageId);
		for (ViewContent viewContent : contentList) {
			result.put(viewContent.getItemId(), viewContent);
		}
		return result;
	}

	/**
	 * 发布页面时，组装数据标签数据
	 * 
	 * @param node
	 * @param appPath
	 * @param sortTree
	 * @param navigetaMap
	 * @return
	 */
	public Map<String, Object> createDataSortData(DataNavigate node, String appPath, Integer projectId,
			DefaultTreeNode sortTree, Map<Integer, DataNavigate> navigetaMap) {
		Map<String, Object> result = Maps.newHashMap();
		ViewPreviewVo dataList = new ViewPreviewVo();
		dataList = createViewData(node.getBaseId(), node.getId(), projectId, 0, 1000, appPath, navigetaMap);
		result.put("dataList", dataList);
		return result;
	}

	/**
	 * 通过索引组装区域数据
	 * 
	 * @param databaseId
	 * @param sortId
	 * @param firstResult
	 * @param pageSize
	 * @param appPath
	 * @return
	 */
	public ViewPreviewVo createViewData(Integer databaseId, Integer sortId, Integer projectId, Integer firstResult,
			Integer pageSize, String appPath, Map<Integer, DataNavigate> navigetaMap) {
		ViewPreviewVo result = new ViewPreviewVo();
		Integer[] Ids = { databaseId };
		int numHits = appConfig.getDefaultIndexSearchNumHits();
		MdSortField[] sortField = {
				new MdSortField(FieldCodes.DOC_TIME, DataUtil.dataType2SortType(EDataType.DateTime), true) };
		StringBuilder queryStr = new StringBuilder();
		queryStr.append("Sort_Ids:" + sortId).append(" AND ").append("Project_ID:" + projectId);
		try {
			SearchResult datasResult = libraryDataService.searchIndex(queryStr.toString(), numHits, sortField, null,
					firstResult, pageSize, Ids);
			result = result2ViewVO(datasResult, appPath, navigetaMap);
		} catch (RuntimeException e) {
			log.warn("查询数据出错！", e);
		}
		return result;
	}

	/**
	 * 将查询的Result类型转换为VO
	 * 
	 * @param result
	 * @param pathCode
	 * @param navigetaMap
	 * @return
	 */
	public ViewPreviewVo result2ViewVO(SearchResult result, String pathCode, Map<Integer, DataNavigate> navigetaMap) {
		ViewPreviewVo view = new ViewPreviewVo();
		if (null != result && null != result.documents && result.documents.length > 0) {
			for (Document document : result.documents) {
				DataVo dataVO = new DataVo();
				dataVO.setId(Integer.parseInt(document.get(FieldCodes.ID)));
				dataVO.setTitle(null != document.get(FieldCodes.TITLE) ? document.get(FieldCodes.TITLE) : "");
				dataVO.setAuthors(null != document.get(FieldCodes.AUTHORS) ? document.get(FieldCodes.AUTHORS) : "");
				dataVO.setSummary(null != document.get(FieldCodes.SUMMARY) ? document.get(FieldCodes.SUMMARY) : "");
				List<String> imgStr = libraryDataService.findDataImgs(
						Integer.parseInt(document.get(FieldCodes.TABLE_ID)),
						Integer.parseInt(document.get(FieldCodes.ID)));
				if (null != imgStr && imgStr.size() > 0) {
					dataVO.setImg(imgStr.get(0));
				}
				if (document.get(FieldCodes.DOC_TIME) != null) {
					dataVO.setDocTime(DateTimeUtil.formatDateTimeStr(document.get(FieldCodes.DOC_TIME)));
				}
				dataVO.setTableId(document.get(FieldCodes.TABLE_ID));
				view.addListItem(dataVO, pathCode);
			}
		}
		return view;
	}
}
