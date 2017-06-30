package cn.com.cms.page.controller.flatlab;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.cms.data.util.DataVo;
import cn.com.cms.framework.base.BaseController;
import cn.com.cms.framework.base.CmsData;
import cn.com.cms.framework.base.Result;
import cn.com.cms.library.model.DataBase;
import cn.com.cms.page.service.WebPageService;
import cn.com.cms.page.util.PagingUtil;

/**
 * Flatlab Template Request Action
 */
@Controller
@RequestMapping("/page/flatlab")
public class PageFlatlabController extends BaseController {
	private static Logger LOG = Logger.getLogger(PageFlatlabController.class.getName());
	@Resource
	private WebPageService pageService;

	@RequestMapping("/list/{id}/{pageNum}")
	public String list(HttpServletRequest request, @PathVariable Integer id, @PathVariable int pageNum) {
		DataBase dataBase = pageService.findLibrary(id);
		Result<DataVo> result = pageService.listData(id, pageNum, null);
		PagingUtil paging = pageService.paging(pageNum, result.getTotalCount());
		request.setAttribute("dataBase", dataBase);
		request.setAttribute("parentBase", pageService.findLibrary(dataBase.getParentID()));
		request.setAttribute("dataList", result.getList());
		request.setAttribute("paging", paging);
		request.setAttribute("peerBaseList", pageService.listPeerLibrary(id));
		return "flatlab/list";
	}

	@RequestMapping("**/{tableId}_{dataId}")
	public String view(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer tableId,
			@PathVariable Integer dataId) {
		LOG.debug("======flatlat page info=====");
		CmsData data = pageService.data(tableId, dataId);
		if (null == data) {
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			DataBase dataBase = pageService.findByTableId(tableId);
			request.setAttribute("dataBase", dataBase);
			request.setAttribute("parentBase", pageService.findLibrary(dataBase.getParentID()));
			DataVo dataVo = new DataVo(data);
			request.setAttribute("data", dataVo);
		}
		return "flatlab/detail";
	}

}
