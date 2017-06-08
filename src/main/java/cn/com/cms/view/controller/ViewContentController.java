package cn.com.cms.view.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.com.cms.base.config.BaseController;
import cn.com.cms.base.view.BaseMappingJsonView;
import cn.com.cms.framework.base.tree.DefaultTreeNode;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.service.LibraryService;
import cn.com.cms.view.model.ViewContent;
import cn.com.cms.view.model.ViewItem;
import cn.com.cms.view.model.ViewModel;
import cn.com.cms.view.model.ViewPage;
import cn.com.cms.view.service.ViewContentService;
import cn.com.cms.view.service.ViewItemService;
import cn.com.cms.view.service.ViewModelService;
import cn.com.cms.view.service.ViewPageService;

/**
 * 页面内容配置类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/view/content")
public class ViewContentController extends BaseController {

	@Resource
	private ViewPageService viewPageService;
	@Resource
	private ViewModelService viewModelService;
	@Resource
	private ViewItemService viewItemService;
	@Resource
	private ViewContentService viewContentService;
	@Resource
	private LibraryService<?> libraryService;

	@RequestMapping("/{pageId}/{itemCode}")
	public MappingJacksonJsonView getAllTree(HttpServletResponse response, HttpServletRequest request,
			@PathVariable int pageId, @PathVariable String itemCode) {
		ViewPage viewPage = viewPageService.findById(pageId);
		ViewModel viewModel = viewModelService.find(viewPage.getModelId());
		ViewItem viewItem = viewItemService.findByModelIdAndCode(viewModel.getId(), itemCode);
		ViewContent viewContent = viewContentService.findByPageIdAndItemId(pageId, viewItem.getId());
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("content", viewContent);
		DefaultTreeNode databaseTree = libraryService.findTree(ELibraryType.dataBases());
		mv.addStaticAttribute("root", databaseTree);
		return mv;
	}

}
