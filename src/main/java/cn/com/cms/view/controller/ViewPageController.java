package cn.com.cms.view.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.com.cms.framework.base.BaseController;
import cn.com.cms.framework.base.Result;
import cn.com.cms.framework.base.table.DataTablesVo;
import cn.com.cms.framework.base.view.BaseMappingJsonView;
import cn.com.cms.framework.config.AppConfig;
import cn.com.cms.framework.config.JsonPara;
import cn.com.cms.framework.config.SystemConstant;
import cn.com.cms.library.constant.EDataStatus;
import cn.com.cms.user.model.User;
import cn.com.cms.user.service.UserSecurityService;
import cn.com.cms.view.constant.EModelType;
import cn.com.cms.view.constant.EPageType;
import cn.com.cms.view.model.ViewModel;
import cn.com.cms.view.model.ViewPage;
import cn.com.cms.view.service.ViewModelService;
import cn.com.cms.view.service.ViewPageService;
import cn.com.cms.view.service.ViewPublishService;
import cn.com.cms.view.vo.ViewModelVo;
import cn.com.cms.view.vo.ViewPageVo;
import cn.com.people.data.util.DateTimeUtil;

/**
 * 页面管理控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/view/page")
public class ViewPageController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(ViewPageController.class);
	@Resource
	private AppConfig appConfig;
	@Resource
	private ViewPageService viewPageService;
	@Resource
	private ViewModelService viewModelService;
	@Resource
	private ViewPublishService viewPublishService;
	@Resource
	private UserSecurityService userSecurityService;

	/**
	 * 进入页面列表页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String list(Model model) {
		LOG.debug("====view.page.list====");
		return "/admin/view/page/list";
	}

	/**
	 * 首页发布
	 * 
	 * @param jsonPara
	 * @return
	 */
	@RequestMapping(value = "/publish", method = RequestMethod.POST)
	public MappingJacksonJsonView publish(@RequestBody JsonPara jsonPara) {
		LOG.debug("====view.page.home.publish====");
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		String[] pageIdStr = jsonPara.value.split(SystemConstant.COMMA_SEPARATOR);
		for (String pageId : pageIdStr) {
			viewPublishService.publish(Integer.parseInt(pageId));
		}
		return mv;
	}

	/**
	 * 基于页面发布为项目专题的项目列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/project/list", method = RequestMethod.POST)
	public MappingJacksonJsonView projectList() {
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		List<ViewPage> result = viewPageService.findByType(EPageType.SubjectPage);
		mv.addStaticAttribute("result", result);
		return mv;
	}

	/**
	 * 页面查询
	 * 
	 * @param jsonParas
	 * @return
	 */
	@RequestMapping(value = "/s", method = RequestMethod.POST)
	public MappingJacksonJsonView search(@RequestBody JsonPara[] jsonParas) {
		LOG.debug("====view.page.search====");
		Map<String, String> jsonMap = JsonPara.getParaMap(jsonParas);
		Integer sEcho = Integer.parseInt(jsonMap.get(JsonPara.DataTablesParaNames.sEcho));
		Integer iDisplayStart = Integer.parseInt(jsonMap.get(JsonPara.DataTablesParaNames.iDisplayStart));
		String sSearch = jsonMap.get(JsonPara.DataTablesParaNames.sSearch);
		int pageSize = appConfig.getAdminDataTablePageSize();
		int firstResult = 0;
		if (null != iDisplayStart) {
			firstResult = iDisplayStart;
		}
		Result<ViewPage> result = viewPageService.search(sSearch, firstResult, pageSize);
		List<ViewPageVo> list = new ArrayList<ViewPageVo>();
		int totalCount = result.getTotalCount();
		List<ViewPage> viewPages = result.getList();
		if (null != viewPages && viewPages.size() > 0) {
			for (ViewPage viewPage : viewPages) {
				ViewPageVo vo = ViewPageVo.conver2ViewPageVo(viewPage);
				vo.setCreateTime(DateTimeUtil.formatDateTime(viewPage.getCreateTime()));
				if (null != viewPage.getPublishTime()) {
					vo.setPublishTime(DateTimeUtil.formatDateTime(viewPage.getPublishTime()));
				}
				list.add(vo);
			}
		}
		DataTablesVo<ViewPageVo> dataTablesVo = new DataTablesVo<ViewPageVo>(sEcho, totalCount, totalCount, list);
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("dataTablesVo", dataTablesVo);
		return mv;
	}

	/**
	 * 进入页面新增页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/new")
	public String preNew(Model model) {
		LOG.debug("=====view.page.new====");
		return "/admin/view/page/edit";
	}

	/**
	 * 加载模板列表
	 * 
	 * @author shishb
	 * @return
	 */
	@RequestMapping("/model/{type}")
	public MappingJacksonJsonView getPageModel(@PathVariable EModelType type) {
		LOG.debug("========view.page.model.list========");
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		List<ViewModel> viewModels = viewModelService.findByType(type);
		List<ViewModelVo> modelList = new ArrayList<ViewModelVo>();
		for (ViewModel viewModel : viewModels) {
			ViewModelVo viewModelVo = ViewModelVo.convertFromViewModel(viewModel);
			String picPath = appConfig.getTemplatePath() + "/" + viewModel.getCode() + "/logo.jpg";
			File pic = new File(picPath);
			if (pic.exists()) {
				viewModelVo.setImgUrl(appConfig.getTemplateHome() + "/" + viewModel.getCode() + "/logo.jpg");
			}
			modelList.add(viewModelVo);
		}
		mv.addStaticAttribute("result", modelList);
		return mv;
	}

	/**
	 * 保存新增页面
	 * 
	 * @return pageId
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String save(HttpServletRequest request) {
		LOG.debug("========view.page.save========");
		User currentUser = userSecurityService.currentUser(request);
		String title = request.getParameter("title");
		String code = request.getParameter("code");
		String modelIdStr = request.getParameter("modelId");
		String pageType = request.getParameter("pageType");
		ViewPage viewPage = new ViewPage();
		viewPage.setName(title);
		viewPage.setCode(code);
		viewPage.setPageType(EPageType.values()[Integer.valueOf(pageType)]);
		// 原始状态未发布
		viewPage.setStatus(EDataStatus.Original);
		viewPage.setCreatorId(currentUser.getId());
		viewPage.setUpdaterId(currentUser.getId());
		viewPage.setCreateTime(new Date());
		viewPage.setUpdateTime(new Date());
		Integer modelId = Integer.parseInt(modelIdStr);
		viewPage.setModelId(modelId);
		ViewModel viewModel = viewModelService.find(modelId);
		EModelType modelType = viewModel.getModelType();
		switch (modelType) {
		case Index:
			viewPage.setPageType(EPageType.SysPage);
			break;
		case Subject:
			viewPage.setPageType(EPageType.SubjectPage);
			break;
		default:
			break;
		}
		try {
			viewPageService.save(viewPage);
			return "redirect:/admin/view/page";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/view/page/new";
		}
	}

	/**
	 * 删除功能
	 * 
	 * @param jsonPara
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public void delete(@RequestBody JsonPara jsonPara) {
		String[] pageIdStr = jsonPara.value.split(SystemConstant.COMMA_SEPARATOR);
		Integer[] pageIds = new Integer[pageIdStr.length];
		for (int i = 0; i < pageIds.length; i++) {
			pageIds[i] = Integer.parseInt(pageIdStr[i]);
		}
		try {
			viewPageService.delete(pageIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
