package cn.com.cms.view.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.lucene.document.Document;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.microduo.index.lucene3.MdSortField;
import com.microduo.index.lucene3.SearchResult;

import cn.com.cms.base.config.AppConfig;
import cn.com.cms.base.data.FieldCodes;
import cn.com.cms.data.util.DataUtil;
import cn.com.cms.data.util.DataVo;
import cn.com.cms.library.constant.EDataStatus;
import cn.com.cms.library.constant.EDataType;
import cn.com.cms.library.constant.ELibraryNodeType;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.model.DataBase;
import cn.com.cms.library.service.LibraryDataService;
import cn.com.cms.library.service.LibraryService;
import cn.com.cms.system.service.PathService;
import cn.com.cms.util.MessageResources;
import cn.com.cms.view.constant.EPageType;
import cn.com.cms.view.dao.ViewPageMapper;
import cn.com.cms.view.model.ViewPage;
import cn.com.cms.view.vo.ViewPreviewVo;
import cn.com.people.data.util.DateTimeUtil;
import cn.com.people.data.util.FileUtil;
import cn.com.people.data.util.FreeMarkerUtil;

/**
 * 列表/频道/专题页面发布类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class ViewListService {
	private final static String SUBPAGE_FILE_NAME = "index.html";
	@Resource
	private AppConfig appConfig;
	@Resource
	private PathService pathService;
	@Resource
	private LibraryDataService libraryDataService;
	@Resource
	private LibraryService<DataBase> libraryService;
	@Resource
	private ViewPageMapper viewPageMapper;

	/**
	 * 列表/频道/专题页面发布
	 * 
	 * @return
	 */
	public void publish() {
		List<DataBase> dataBaseList = libraryService.findAll(ELibraryType.SYSTEM_DATA_BASE, ELibraryNodeType.Lib);
		List<ViewPage> pageList = viewPageMapper.findByTypeAndStatus(EPageType.SysPage, EDataStatus.Yes);
		if (null != pageList && pageList.size() > 0) {
			for (ViewPage page : pageList) {
				String channelPath = MessageResources.getValue("app.channel.path");
				if (!Strings.isNullOrEmpty(channelPath)) {
					if (page.getCode().equals(channelPath)) {
						if (null != dataBaseList && dataBaseList.size() > 0) {
							for (DataBase node : dataBaseList) {
								Map<String, Object> data = new HashMap<String, Object>();
								data.put("appPath", appConfig.getAppPath());
								data.put("pageTitle", node.getName());
								productData(node, data);
								String folderPath = pathService.getStaticPhysicalParth() + "/" + page.getCode()
										+ "/list" + node.getPathCode();
								String htmlFileFullName = folderPath + SUBPAGE_FILE_NAME;
								FileUtil.createFolder(folderPath);
								FreeMarkerUtil.publish(pathService.getTemplatePhysicalParth(),
										page.getCode() + "/channel.html", data, htmlFileFullName);
							}
						}
						break;
					}
				}
			}
		}
	}

	/**
	 * 组织数据
	 * 
	 * @param node
	 * @param result
	 * @return
	 */
	private Map<String, Object> productData(DataBase node, Map<String, Object> result) {
		ViewPreviewVo vo = new ViewPreviewVo();
		vo = productData(node.getId(), 0, appConfig.getAdminDataTablePageSize(), appConfig.getAppPath());
		result.put("dataList", vo);
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
	private ViewPreviewVo productData(Integer baseId, Integer firstResult, Integer pageSize, String appPath) {
		ViewPreviewVo result = new ViewPreviewVo();
		Integer[] Ids = { baseId };
		MdSortField[] dsSortFieldsArray = {
				new MdSortField(FieldCodes.DOC_TIME, DataUtil.dataType2SortType(EDataType.DateTime), true) };
		String queryStr = "*:*";
		int numHits = appConfig.getDefaultIndexSearchNumHits();
		try {
			SearchResult hotDatasResult = libraryDataService.searchIndex(queryStr, numHits, dsSortFieldsArray, null,
					firstResult, pageSize, Ids);
			result = document2ViewVo(hotDatasResult, appPath);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 将查询的Result类型转换为VO
	 * 
	 * @param result
	 * @param pathCode
	 * @return
	 */
	private ViewPreviewVo document2ViewVo(SearchResult result, String pathCode) {
		ViewPreviewVo view = new ViewPreviewVo();
		if (null != result && null != result.documents && result.documents.length > 0) {
			for (Document document : result.documents) {
				DataVo dataVO = new DataVo();
				dataVO.setId(Integer.parseInt(document.get(FieldCodes.ID)));
				if (null != document.get(FieldCodes.TITLE)) {
					dataVO.setTitle(document.get(FieldCodes.TITLE));
				}
				dataVO.setAuthors(document.get(FieldCodes.AUTHORS));
				String summary = "";
				if (null == document.get(FieldCodes.SUMMARY) || ("").equals(document.get(FieldCodes.SUMMARY))) {
					summary = document.get(FieldCodes.CONTENT);
				} else {
					summary = document.get(FieldCodes.SUMMARY);
				}
				if (summary.length() > appConfig.getSummaryLength()) {
					summary = summary.substring(0, appConfig.getSummaryLength());
				}
				dataVO.setSummary(summary);
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
