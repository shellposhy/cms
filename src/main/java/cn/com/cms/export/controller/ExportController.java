package cn.com.cms.export.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.com.cms.base.AppConfig;
import cn.com.cms.base.BaseController;
import cn.com.cms.common.BaseMappingJsonView;
import cn.com.cms.export.constant.ETemplateType;
import cn.com.cms.export.service.SortTreeService;
import cn.com.cms.framework.base.Return;
import cn.com.cms.library.model.DataSort;
import cn.com.cms.library.service.LibraryDataIndexService;
import cn.com.cms.system.service.PathService;
import cn.com.cms.util.MessageResources;
import cn.com.cms.view.model.ViewPage;
import cn.com.cms.view.service.ViewPageService;
import cn.com.people.data.util.FileUtil;
import cn.com.people.data.util.FreeMarkerUtil;

/**
 * 数据导出控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/export")
public class ExportController extends BaseController {
	private static Logger log = Logger.getLogger(ExportController.class.getName());
	@Resource
	private AppConfig appConfig;
	@Resource
	private PathService pathService;
	@Resource
	private SortTreeService sortTreeService;
	@Resource
	private LibraryDataIndexService libraryDataIndexService;
	@Resource
	private ViewPageService viewPageService;

	/**
	 * 导出word文档
	 * 
	 * @param pageId
	 * @param type
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/{pageId}/{type}")
	public MappingJacksonJsonView exprot(@PathVariable Integer pageId, @PathVariable Integer type) {
		log.debug("====admin.exprot.file===");
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		Return<String> result = new Return<String>();
		result.setSuccess(false);
		ETemplateType exprotType = ETemplateType.valuesof(type);
		ViewPage project = viewPageService.findById(pageId);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("appPath", appConfig.getAppPath());
		data.put("project", project);
		if (exprotType == ETemplateType.BIMSwb) {
			List<DataSort> dataTree = sortTreeService.findSortTreeByParent("swb", 0);
			data.put("dataTree", dataTree);
			String directory = MessageResources.getValue("app.path.word.export") + "/" + pageId;
			String wordFileFullName = directory + "/" + pageId + ".doc";
			File historyFile = new File(wordFileFullName);
			if (historyFile.exists() && historyFile.isFile()) {
				FileUtil.deleteFile(historyFile);
			}
			FileUtil.deleteFolder(directory);
			FileUtil.createFolder(directory);
			if (FreeMarkerUtil.publish(pathService.getTemplatePhysicalParth(), "export/word/swb/index.html", data,
					wordFileFullName)) {
				result.setSuccess(true);
			} else {
				result.setCode("err.OPERATE_ILLEGAL");
				result.setMessage(MessageResources.getValue("err.OPERATE_ILLEGAL"));
			}
		}
		mv.addStaticAttribute("result", result);
		return mv;
	}
}
