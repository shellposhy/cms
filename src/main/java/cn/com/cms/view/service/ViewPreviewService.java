/**
 * 数据预览服务类
 * @author shishb
 * @version 1.0
 * */
package cn.com.cms.view.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.microduo.index.lucene3.MdSortField;
import com.microduo.index.lucene3.SearchResult;

import cn.com.cms.data.model.DataField;
import cn.com.cms.data.model.DataTable;
import cn.com.cms.data.service.DataFieldService;
import cn.com.cms.library.constant.EDataType;
import cn.com.cms.library.model.BaseLibrary;
import cn.com.cms.library.model.DataBase;
import cn.com.cms.library.service.LibraryDataService;
import cn.com.cms.library.service.LibraryService;
import cn.com.cms.view.model.ViewContent;
import cn.com.cms.view.model.ViewItem;
import cn.com.cms.view.model.ViewPage;
import cn.com.cms.view.vo.ViewPreviewVo;
import cn.com.cms.data.util.DataUtil;
import cn.com.cms.framework.base.Result;
import cn.com.cms.framework.base.table.CmsData;
import cn.com.cms.framework.base.table.FieldCodes;
import cn.com.cms.framework.config.AppConfig;;

/**
 * 页面配置发布预览服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class ViewPreviewService {
	private final static Logger logger = Logger.getLogger(ViewPreviewService.class);
	@Resource
	private LibraryService<DataBase> libraryService;
	@Resource
	private LibraryDataService libraryDataService;
	@Resource
	private AppConfig appConfig;
	@Resource
	private DataFieldService dataFieldService;
	@Resource
	private ViewModelService viewModelService;

	/**
	 * 预览数据
	 * 
	 * @param viewItem
	 * @param contentMap
	 * @param appPath
	 * @return
	 */
	public ViewPreviewVo preview(ViewItem viewItem, Map<Integer, ViewContent> contentMap, String appPath,
			ViewPage page) {
		ViewPreviewVo result = new ViewPreviewVo();
		ViewContent viewContent = contentMap.get(viewItem.getId());
		if (null == viewContent) {
			return result;
		} else {
			switch (viewItem.getItemType()) {
			case Default:
			case Headline:
				result = dbContentHandle(viewItem, viewContent, appPath, page);
				break;
			case Sort:
				result = sortContentHandle(viewItem, viewContent, appPath, page);
				break;
			case OneImgList:
				result = firstImgDataHandler(viewItem, viewContent, appPath);
			case ImgList:
				result = imgDataListHandler(viewItem, viewContent, appPath);
			default:
				break;
			}
			return result;
		}
	}

	/**
	 * 标签数据列表
	 * 
	 * @param viewItem
	 * @param content
	 * @param appPath
	 * @return
	 */
	private ViewPreviewVo sortContentHandle(ViewItem viewItem, ViewContent content, String appPath, ViewPage page) {
		ViewPreviewVo result = new ViewPreviewVo();
		result.setTitle(null == content.getName() ? "" : content.getName());
		if (null != content.getNameLink()) {
			result.setHref(content.getNameLink());
		}
		if (!Strings.isNullOrEmpty(content.getContent())) {
			int rows = viewItem.getMaxRows();
			int baseId = Integer.valueOf(content.getContent());
			DataTable dataTable = libraryService.getDataTable(baseId);
			BaseLibrary<?> dataBase = libraryService.find(baseId);
			DataField dataField = null;
			result.setLength(viewItem.getMaxWords());
			try {
				int numHits = appConfig.getDefaultIndexSearchNumHits();
				MdSortField[] sortType = {
						new MdSortField(FieldCodes.PROJECT_ID, DataUtil.dataType2SortType(EDataType.Int), false) };
				List<CmsData> CmsDataList = new ArrayList<CmsData>();
				StringBuilder queryStr = new StringBuilder();
				queryStr.append("#int#").append(FieldCodes.DATA_STATUS).append(":3")
						.append(content.getFilterCondition());
				queryStr.append(" AND #int#").append(FieldCodes.PROJECT_ID).append(":" + content.getPageId());
				SearchResult searchResult = new SearchResult();
				searchResult = libraryDataService.searchIndex(queryStr.toString(), numHits, sortType, null, 0, rows,
						baseId);
				List<DataField> fieldList = dataFieldService.findFieldsByDBId(baseId);
				List<CmsData> searchResultList = DataUtil.getPeopleDataList(searchResult, fieldList);
				List<CmsData> dataList = Lists.newArrayList();
				if (null != searchResultList && searchResultList.size() > 0) {
					for (CmsData data : searchResultList) {
						if (null != data.getId() && null != data.getTableId()) {
							data.put(FieldCodes.CONTENT,
									libraryDataService.find(data.getTableId(), data.getId()).get(FieldCodes.CONTENT));
						}
						dataList.add(data);
					}
				}
				CmsDataList.addAll(dataList);
				if (null != dataTable) {
					switch (viewItem.getItemType()) {
					default:
						result.initList(content, CmsDataList, appPath, dataBase.getPathCode(), dataTable.getId(),
								dataField, page);
						break;
					}
				}
			} catch (Exception e) {
				logger.warn("数据组装错误！", e);
			}
		}
		return result;
	}

	/**
	 * 头条新闻
	 * 
	 * @param viewItem
	 * @param content
	 * @param appPath
	 * @return
	 */
	private ViewPreviewVo dbContentHandle(ViewItem viewItem, ViewContent content, String appPath, ViewPage page) {
		ViewPreviewVo result = new ViewPreviewVo();
		result.setTitle(null == content.getName() ? "" : content.getName());
		if (null != content.getNameLink()) {
			result.setHref(content.getNameLink());
		}
		if (null != content.getContent() && !content.getContent().isEmpty()) {
			int rows = viewItem.getMaxRows();
			int baseId = Integer.valueOf(content.getContent());
			DataTable dataTable = libraryService.getDataTable(baseId);
			BaseLibrary<?> dataBase = libraryService.find(baseId);
			DataField dataField = null;
			result.setLength(viewItem.getMaxWords());
			try {
				int numHits = appConfig.getDefaultIndexSearchNumHits();
				MdSortField[] sortType = {
						new MdSortField(FieldCodes.DOC_TIME, DataUtil.dataType2SortType(EDataType.DateTime), true) };
				List<CmsData> CmsDataList = new ArrayList<CmsData>();
				StringBuilder queryStr = new StringBuilder();
				queryStr.append("#int#").append(FieldCodes.DATA_STATUS).append(":3")
						.append(content.getFilterCondition());
				SearchResult searchResult = new SearchResult();
				searchResult = libraryDataService.searchIndex(queryStr.toString(), numHits, sortType, null, 0, rows,
						baseId);
				List<DataField> fieldList = dataFieldService.findFieldsByDBId(baseId);
				List<CmsData> searchResultList = DataUtil.getPeopleDataList(searchResult, fieldList);
				CmsDataList.addAll(searchResultList);
				if (null != dataTable) {
					switch (viewItem.getItemType()) {
					default:
						result.initList(content, CmsDataList, appPath, dataBase.getPathCode(), dataTable.getId(),
								dataField, page);
						break;
					}
				}
			} catch (Exception e) {
				logger.warn("数据组装错误！", e);
			}
		}
		return result;

	}

	/**
	 * 单图片模式
	 * 
	 * @param viewItem
	 * @param content
	 * @param appPath
	 * @return
	 */
	private ViewPreviewVo firstImgDataHandler(ViewItem viewItem, ViewContent content, String appPath) {
		ViewPreviewVo result = new ViewPreviewVo();
		result.setTitle(null == content.getName() ? "" : content.getName());
		if (null != content.getNameLink()) {
			result.setHref(content.getNameLink());
		}
		if (null != content.getContent() && !content.getContent().isEmpty()) {
			int rows = viewItem.getMaxRows();
			int baseId = Integer.valueOf(content.getContent());
			DataTable dataTable = libraryService.getDataTable(baseId);
			BaseLibrary<?> dataBase = libraryService.find(baseId);
			DataField dataField = null;
			Result<CmsData> imgResult = libraryDataService.searchImgData(dataTable, null, 0, viewItem.getMaxRows());
			if (null != imgResult && null != imgResult.getList() && !imgResult.getList().isEmpty()) {
				CmsData imgData = imgResult.getList().get(0);
				try {
					List<String> imgUrls = DataUtil.getImgs(imgData.get(FieldCodes.CONTENT).toString());
					if (null != imgUrls && !imgUrls.isEmpty()) {
						String url = imgUrls.get(0);
						url = url.replace("\\", "/");
						result.setImg(url);
					}
				} catch (IOException e) {
					logger.warn(e);
				}
				result.setHref(
						appPath + "/page" + dataBase.getPathCode() + imgData.getTableId() + "_" + imgData.getId());
				result.setTitle(imgData.get(FieldCodes.TITLE).toString());
				result.setSummary(imgResult.getList().get(0).get(FieldCodes.SUMMARY) == null ? ""
						: (imgResult.getList().get(0).get(FieldCodes.SUMMARY).toString()));
			}
			result.setLength(viewItem.getMaxWords());
			try {
				int numHits = appConfig.getDefaultIndexSearchNumHits();
				MdSortField[] dsSortFieldsArray = {
						new MdSortField(FieldCodes.DOC_TIME, DataUtil.dataType2SortType(EDataType.DateTime), true) };
				List<CmsData> CmsDataList = new ArrayList<CmsData>();
				SearchResult searchResult = libraryDataService.searchIndex("*:*", numHits, dsSortFieldsArray, null, 0,
						rows, baseId);
				List<DataField> fieldList = dataFieldService.findFieldsByDBId(baseId);
				List<CmsData> searchResultList = DataUtil.getPeopleDataList(searchResult, fieldList);
				CmsDataList.addAll(searchResultList);
				if (null != dataTable) {
					switch (viewItem.getItemType()) {
					default:
						result.initImgList(CmsDataList, appPath, dataBase.getPathCode(), dataTable.getId(), dataField);
						break;
					}
				}
			} catch (Exception e) {
				logger.warn("数据组装错误！", e);
			}
		}
		return result;
	}

	/**
	 * 图片列表
	 * 
	 * @param viewItem
	 * @param content
	 * @param appPath
	 * @return
	 */
	private ViewPreviewVo imgDataListHandler(ViewItem viewItem, ViewContent content, String appPath) {
		ViewPreviewVo result = new ViewPreviewVo();
		result.setTitle(null == content.getName() ? "" : content.getName());
		if (null != content.getNameLink()) {
			result.setHref(content.getNameLink());
		}
		if (null != content.getContent() && !content.getContent().isEmpty()) {
			int baseId = Integer.valueOf(content.getContent());
			DataTable dataTable = libraryService.getDataTable(baseId);
			BaseLibrary<?> dataBase = libraryService.find(baseId);
			DataField dataField = null;
			Result<CmsData> imgResult = new Result<CmsData>();
			List<CmsData> CmsDataList = new ArrayList<CmsData>();
			imgResult = libraryDataService.searchImgData(dataTable, null, 0, viewItem.getMaxRows());
			if (null != imgResult && null != imgResult.getList() && !imgResult.getList().isEmpty()) {
				for (CmsData imgData : imgResult.getList()) {
					try {
						List<String> imgUrls = DataUtil.getImgs(imgData.get(FieldCodes.CONTENT).toString());
						if (null != imgUrls && !imgUrls.isEmpty()) {
							String url = imgUrls.get(0);
							url = url.replace("\\", "/");
							imgData.put(FieldCodes.IMGS, url);
						}
						CmsDataList.add(imgData);
					} catch (IOException e) {
						logger.warn(e);
					}
				}
			}
			result.setLength(viewItem.getMaxWords());
			if (null != dataTable) {
				result.initImgList(CmsDataList, appPath, dataBase.getPathCode(), dataTable.getId(), dataField);
			}
		}
		return result;
	}
}
