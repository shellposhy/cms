package cn.com.cms.view.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.com.cms.base.AppConfig;
import cn.com.cms.data.service.DataSortService;
import cn.com.cms.framework.base.LibraryTreeNode;
import cn.com.cms.framework.tree.DefaultTreeNode.PropertySetter;
import cn.com.cms.library.constant.EDataNavigateType;
import cn.com.cms.library.model.BaseLibrary;
import cn.com.cms.library.model.DataNavigate;
import cn.com.cms.library.model.DataSort;
import cn.com.cms.library.service.LibraryDataService;
import cn.com.cms.library.service.LibraryService;
import cn.com.cms.system.service.PathService;
import cn.com.cms.view.dao.ViewContentMapper;
import cn.com.cms.view.dao.ViewModelMapper;
import cn.com.cms.view.dao.ViewPageMapper;
import cn.com.cms.view.model.ViewContent;
import cn.com.cms.view.model.ViewModel;
import cn.com.cms.view.model.ViewPage;
import cn.com.people.data.util.FileUtil;
import cn.com.people.data.util.FreeMarkerUtil;

/**
 * 频道或二级页发布
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class ViewChannelService {
	private static Logger log = Logger.getLogger(ViewChannelService.class.getName());
	private final static String SUBPAGE_FILE_NAME = "index.html";
	@Resource
	private AppConfig appConfig;
	@Resource
	private PathService pathService;
	@Resource
	private LibraryDataService libraryDataService;
	@Resource
	private LibraryService<?> libraryService;
	@Resource
	private DataSortService dataSortService;
	@Resource
	private ViewPublishService viewPublishService;
	@Resource
	private ViewPageMapper viewPageMapper;
	@Resource
	private ViewModelMapper viewModelMapper;
	@Resource
	private ViewContentMapper viewContentMapper;

	/**
	 * 基于标签库发布频道或二级页
	 * <p>
	 * 如果<code>dbId为0</code>基于全局变量的标签发布
	 * 
	 * @param dbId
	 *            数据库编号
	 * @param projectId
	 *            项目编号
	 * @return
	 */
	public void publistGlobalDataSort(Integer pageId) {
		log.info("====publish.data.sort.list======");
		ViewPage page = viewPageMapper.find(pageId);
		ViewModel model = viewModelMapper.find(page.getModelId());
		List<ViewContent> viewContentList = viewContentMapper.findByPageId(pageId);
		List<DataSort> dataSortList = dataSortService.findByDbIdAndType(0, null);
		List<DataNavigate> nodeList = Lists.newArrayList();
		Integer dbId = null != viewContentList && viewContentList.size() > 0
				? Integer.valueOf(viewContentList.get(0).getContent()) : null;
		BaseLibrary<?> dataBase = libraryService.find(dbId);
		Map<Integer, DataNavigate> navigetaMap = Maps.newHashMap();
		if (null != dataSortList && dataSortList.size() > 0) {
			for (DataSort dataSort : dataSortList) {
				DataNavigate dataNavigate = new DataNavigate(dataSort, dbId);
				nodeList.add(dataNavigate);
				navigetaMap.put(dataSort.getId(), new DataNavigate(dataSort, dbId));
			}
		}
		// 设置树形节点
		LibraryTreeNode tree = LibraryTreeNode.parseTree(LibraryTreeNode.class, nodeList,
				new PropertySetter<LibraryTreeNode, DataNavigate>() {
					public void setProperty(LibraryTreeNode node, DataNavigate entity) {
						if (null != entity && !Strings.isNullOrEmpty(entity.getPathCode())) {
							if (EDataNavigateType.DATA_SORT.equals(entity.getType())) {
								node.setHref(pathService.getStaticUrl() + "/page/list/"
										+ EDataNavigateType.DATA_SORT.ordinal() + "/" + entity.getRealId());
							} else {
								node.setHref(pathService.getStaticUrl() + entity.getPathCode() + SUBPAGE_FILE_NAME);
							}
						} else {
							node.setHref("#");
						}
					}
				});
		// 页面发布
		if (null != nodeList && nodeList.size() > 0) {
			for (DataNavigate node : nodeList) {
				Map<String, Object> data = Maps.newHashMap();
				if (null == node.getPathCode() || node.getPathCode().isEmpty())
					continue;
				if (EDataNavigateType.DATA_SORT.equals(node.getType()))
					continue;
				data.put("tree", tree);
				data.put("id", node.getId());
				data.put("appPath", appConfig.getAppPath());
				data.put("pageTitle", node.getName());
				data.put("pageType", node.getType().ordinal());
				data.put("pageId", dataBase.getId());
				data.put("parentPath", navigetaMap.get(node.getParentID()).getPathCode());
				data.putAll(
						viewPublishService.createDataSortData(node, appConfig.getAppPath(), pageId, tree, navigetaMap));
				String folderPath = pathService.getStaticPhysicalParth() + node.getPathCode();
				String htmlFileFullName = folderPath + SUBPAGE_FILE_NAME;
				FileUtil.createFolder(folderPath);
				FreeMarkerUtil.publish(pathService.getTemplatePhysicalParth(), model.getCode() + "/channel.html", data,
						htmlFileFullName);
			}
		}
	}
}
