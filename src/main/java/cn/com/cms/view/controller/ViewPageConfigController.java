package cn.com.cms.view.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.cms.base.AppConfig;
import cn.com.cms.base.BaseController;
import cn.com.cms.framework.security.UserSecurityService;
import cn.com.cms.system.service.CalendarEventService;
import cn.com.cms.view.constant.EContentType;
import cn.com.cms.view.constant.EPageType;
import cn.com.cms.view.model.ViewContent;
import cn.com.cms.view.model.ViewItem;
import cn.com.cms.view.model.ViewModel;
import cn.com.cms.view.model.ViewPage;
import cn.com.cms.view.service.ViewChannelService;
import cn.com.cms.view.service.ViewContentService;
import cn.com.cms.view.service.ViewItemService;
import cn.com.cms.view.service.ViewModelService;
import cn.com.cms.view.service.ViewPageService;
import cn.com.cms.view.service.ViewPreviewService;
import cn.com.cms.view.service.ViewPublishService;
import cn.com.cms.view.vo.ViewPreviewVo;
import freemarker.template.TemplateException;

/**
 * 发布页面配置类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/view/page/config")
public class ViewPageConfigController extends BaseController {
	private Logger log = Logger.getLogger(ViewPageConfigController.class.getName());
	@Resource
	private AppConfig appConfig;
	@Resource
	private ViewPageService viewPageService;
	@Resource
	private ViewModelService viewModelService;
	@Resource
	private ViewItemService viewItemService;
	@Resource
	private CalendarEventService calendarEventService;
	@Resource
	private ViewPreviewService viewPreviewService;
	@Resource
	private ViewPublishService viewPublishService;
	@Resource
	private ViewContentService viewContentService;
	@Resource
	private UserSecurityService userSecurityService;
	@Resource
	private ViewChannelService viewChannelService;

	/**
	 * 进入页面配置
	 * 
	 * @param pageId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{pageId}")
	public String config(@PathVariable Integer pageId, Model model) {
		log.debug("=====view.page.config====");
		ViewPage viewPage = viewPageService.findById(pageId);
		model.addAttribute("pageId", pageId);
		model.addAttribute("pageTitle", viewPage.getName());
		model.addAttribute("modelId", viewPage.getModelId());
		model.addAttribute("viewContent", new ViewContent());
		return "/admin/view/page/config";
	}

	/**
	 * 页面预览
	 * 
	 * @param request
	 * @param pageId
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	@RequestMapping(value = "/preview/{pageId}")
	public String preview(HttpServletRequest request, @PathVariable("pageId") Integer pageId) {
		log.debug("=====view.page.preview====");
		String appPath = appConfig.getAppPath();
		ViewPage page = viewPageService.findById(pageId);
		ViewModel viewModel = viewModelService.find(page.getModelId());
		List<ViewItem> itemList = viewItemService.findByModelId(page.getModelId());
		Map<Integer, ViewContent> contentMap = viewPublishService.findViewContentMapByPageId(pageId);
		Map<String, Object> calendarEvent = calendarEventService.calendarEventData();
		request.setAttribute("appPath", appConfig.getAppPath());
		for (String key : calendarEvent.keySet()) {
			request.setAttribute(key, calendarEvent.get(key));
		}
		for (ViewItem viewItem : itemList) {
			ViewPreviewVo vo = viewPreviewService.preview(viewItem, contentMap, appPath, page);
			request.setAttribute(viewItem.getCode(), null == vo ? new ViewPreviewVo() : vo);
		}
		switch (viewModel.getModelType()) {
		case Subject:
			return "subject/" + viewModel.getCode() + "/index";
		default:
			return viewModel.getCode() + "/index";
		}
	}

	/**
	 * 保存区域配置
	 * 
	 * @param pageId
	 * @param viewContent
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{pageId}/saveItem", method = RequestMethod.POST)
	public String saveItem(@PathVariable Integer pageId, ViewContent viewContent, Model model,
			HttpServletRequest request) {
		ViewPage page = viewPageService.findById(pageId);
		ViewItem item = viewItemService.findByModelIdAndCode(page.getModelId(), viewContent.getItemCode());
		if (null != item && null != item.getId()) {
			viewContent.setItemId(item.getId());
			if (EPageType.SubjectPage == page.getPageType()) {
				viewContent.setContentType(EContentType.DbDataListContent);
			} else {
				viewContent.setContentType(EContentType.DbDataListContent);
			}
			viewContent.setPageId(pageId);
			viewContentService.insert(viewContent);
		}
		return "redirect:/admin/view/page/config/" + pageId;
	}
}
