package cn.com.cms.page.controller.flatlab;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.cms.framework.base.BaseController;
import cn.com.cms.framework.config.AppConfig;
import cn.com.cms.library.service.LibraryDataService;
import cn.com.cms.library.service.LibraryService;

/**
 * Flatlab Template Request Action
 */
@Controller
@RequestMapping("/page/flatlab")
public class PageFlatlabController extends BaseController {
	private static Logger LOG = Logger.getLogger(PageFlatlabController.class.getName());
	@Resource
	private AppConfig appConfig;
	@Resource
	private LibraryDataService libraryDataService;
	@Resource
	private LibraryService<?> libraryService;

	@RequestMapping("**/{tableId}_{dataId}")
	public String view(HttpServletRequest request, @PathVariable Integer tableId, @PathVariable Integer dataId) {
		LOG.info("======flatlat page info=====");
		return "flatlab/detail";
	}
	
	
}
