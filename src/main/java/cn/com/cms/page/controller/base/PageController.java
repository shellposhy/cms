package cn.com.cms.page.controller.base;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.document.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.com.cms.library.constant.EDataType;
import cn.com.cms.library.model.BaseLibrary;
import cn.com.cms.library.service.LibraryDataService;
import cn.com.cms.library.service.LibraryService;
import cn.com.cms.library.vo.AttachVo;
import cn.com.cms.page.util.PagingUtil;
import cn.com.cms.util.FileUtil;
import cn.com.people.data.util.DateTimeUtil;
import cn.com.pepper.common.PepperResult;
import cn.com.pepper.comparator.base.PepperSortField;
import cn.com.cms.data.util.DataUtil;
import cn.com.cms.data.util.DataVo;
import cn.com.cms.framework.base.BaseController;
import cn.com.cms.framework.base.CmsData;
import cn.com.cms.framework.base.table.DataTablesVo;
import cn.com.cms.framework.base.table.FieldCodes;
import cn.com.cms.framework.base.view.BaseMappingJsonView;
import cn.com.cms.framework.config.AppConfig;
import cn.com.cms.framework.config.JsonPara;
import cn.com.cms.framework.config.SystemConstant;

/**
 * 前端页面控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/page/default")
public class PageController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(PageController.class.getName());
	@Resource
	private AppConfig appConfig;
	@Resource
	private LibraryDataService libraryDataService;
	@Resource
	private LibraryService<?> libraryService;

	/**
	 * 文章详情
	 * 
	 * @param request
	 * @param response
	 * @param tableId
	 * @param dataId
	 * @return
	 */
	@RequestMapping("**/{tableId}_{dataId}")
	public String view(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer tableId,
			@PathVariable Integer dataId) throws IOException {
		log.debug("====page.detail====");
		CmsData data = libraryDataService.find(tableId, dataId);
		if (null == data) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} else {
			BaseLibrary<?> dataBase = libraryService.findByTableId(tableId);
			for (String key : data.getLowerFieldSet()) {
				if ("doc_time".equals(key)) {
					Date docTime = (Date) data.get(FieldCodes.DOC_TIME);
					if (null != docTime) {
						request.setAttribute(key, DateTimeUtil.format(docTime, "yyyy.MM.dd"));
					}
				} else {
					request.setAttribute(key, data.get(key));
				}
			}
			// 附件处理
			String docFileStr = (String) data.get(FieldCodes.ATTACH);
			if (!Strings.isNullOrEmpty(docFileStr)) {
				String[] docFileNames = docFileStr.split(SystemConstant.SEPARATOR);
				List<AttachVo> attachList = Lists.newArrayList();
				for (String fileName : docFileNames) {
					AttachVo attach = new AttachVo(dataId, tableId, (String) data.get(FieldCodes.UUID), fileName);
					attachList.add(attach);
				}
				request.setAttribute("attachList", attachList);
			}
			request.setAttribute("base", dataBase);
			request.setAttribute("appPath",
					Strings.isNullOrEmpty(appConfig.getAppPath()) ? "" : appConfig.getAppPath());
			return "default/detail";
		}
	}

	/**
	 * 文章列表
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/{type}/list/{id}")
	public String list(@PathVariable String type, @PathVariable int id, HttpServletRequest request, Model model) {
		log.debug("===page.list===");
		BaseLibrary<?> dataBase = libraryService.find(id);
		Integer first = Strings.isNullOrEmpty(request.getParameter("pageNum")) ? 1
				: Integer.valueOf(request.getParameter("pageNum"));
		Integer start = (first - 1) * appConfig.getAdminDataTablePageMinSize();
		PepperSortField[] dsSortFieldsArray = {
				new PepperSortField(FieldCodes.DOC_TIME, DataUtil.dataType2SortType(EDataType.DateTime), true) };
		String[] hightLightFields = { FieldCodes.TITLE };
		String word = request.getParameter("word");
		String queryString = Strings.isNullOrEmpty(word) ? "*:*"
				: "Title:" + word.trim() + " OR Content:" + word.trim();
		PepperResult searchResult = libraryDataService.searchIndex(queryString,
				appConfig.getDefaultIndexSearchNumHits(), dsSortFieldsArray, hightLightFields, start,
				appConfig.getAdminDataTablePageMinSize(), id);
		List<DataVo> result = Lists.newArrayList();
		PagingUtil paging = null;
		if (null != searchResult && null != searchResult.documents && searchResult.documents.length > 0) {
			for (Document document : searchResult.documents) {
				DataVo vo = new DataVo(document);
				// 系统默认图片
				vo.setImg("/static/flatlab/img/default.jpg");
				if (!Strings.isNullOrEmpty(document.get(FieldCodes.DOC_TIME))) {
					String docTime = document.get(FieldCodes.DOC_TIME);
					vo.setMonth(DateTimeUtil.formatDateTimeStr(docTime, "yyyyMMddHHmmss", "MM"));
					vo.setYear(DateTimeUtil.formatDateTimeStr(docTime, "yyyyMMddHHmmss", "yyyy"));
					vo.setDay(DateTimeUtil.formatDateTimeStr(docTime, "yyyyMMddHHmmss", "dd"));
				}
				if (!Strings.isNullOrEmpty(document.get(FieldCodes.TABLE_ID))
						&& !Strings.isNullOrEmpty(document.get(FieldCodes.ID))) {
					List<String> imgs = libraryDataService.findDataImgs(
							Integer.valueOf(document.get(FieldCodes.TABLE_ID)),
							Integer.valueOf(document.get(FieldCodes.ID)));
					if (null != imgs && imgs.size() > 0) {
						vo.setImg(imgs.get(0));
					}
				}
				result.add(vo);
			}
			paging = new PagingUtil(appConfig.getAdminDataTablePageMinSize(), first, searchResult.totalHits);
		}
		model.addAttribute("dataBaseId", id);
		model.addAttribute("dataList", result);
		model.addAttribute("paging", paging);
		model.addAttribute("dataBase", dataBase);
		model.addAttribute("type", type);
		return type + "/channel";
	}

	/**
	 * 下载附件
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/download")
	public String download(HttpServletRequest request, HttpServletResponse response) {
		CmsData data = libraryDataService.find(Integer.valueOf(request.getParameter("tableId")),
				Integer.valueOf(request.getParameter("id")));
		Date createTime = (Date) data.get(FieldCodes.CREATE_TIME);
		String createDate = DateTimeUtil.format(createTime, "yyyyMMdd");
		Integer baseId = libraryService.findByTableId(Integer.parseInt(request.getParameter("tableId"))).getId();
		String filePath = FileUtil.getDocFilePath(appConfig.getAppPathHome(), baseId, createDate,
				request.getParameter("uuid"));
		request.setAttribute("filePath", filePath + request.getParameter("fileName"));
		request.setAttribute("fileName", request.getParameter("fileName"));
		return "/admin/library/default/data/download";
	}

	/**
	 * 检索索引，组装文章列表页
	 * 
	 * @param jsonParas
	 * @param iType
	 * @param searchIdStr
	 * @param iDisplayStart
	 * @return {@link DataResultVO}
	 */
	@RequestMapping(value = "/s", method = RequestMethod.POST)
	public MappingJacksonJsonView search(@RequestBody JsonPara[] jsonParas) {
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		Map<String, String> jsons = JsonPara.getParaMap(jsonParas);
		int baseId = Integer.parseInt(jsons.get(JsonPara.DataTablesParaNames.searchIdStr));
		int sEcho = Integer.parseInt(jsons.get(JsonPara.DataTablesParaNames.sEcho));
		Integer firstResult = Integer.parseInt(jsons.get(JsonPara.DataTablesParaNames.iDisplayStart));
		List<Integer> dbIds = Lists.newArrayList(baseId);
		String word = jsons.get(JsonPara.DataTablesParaNames.sSearch);
		String queryString = "";
		if (Strings.isNullOrEmpty(word)) {
			queryString = "*:*";
		} else {
			queryString = "Title:" + word.trim() + " OR Content:" + word.trim();
		}
		int pageStart = (firstResult == null ? 0 : firstResult);
		PepperSortField[] dsSortFieldsArray = {
				new PepperSortField(FieldCodes.DOC_TIME, DataUtil.dataType2SortType(EDataType.DateTime), true) };
		String[] hightLightFields = { FieldCodes.TITLE };
		PepperResult searchResult = libraryDataService.searchIndex(queryString,
				appConfig.getDefaultIndexSearchNumHits(), dsSortFieldsArray, hightLightFields, pageStart,
				appConfig.getAdminDataTablePageSize(), (Integer[]) dbIds.toArray(new Integer[dbIds.size()]));
		List<DataVo> result = Lists.newArrayList();
		if (null != searchResult && null != searchResult.documents && searchResult.documents.length > 0) {
			for (Document document : searchResult.documents) {
				result.add(new DataVo(document));
			}
		}
		DataTablesVo<DataVo> dataTablesVo = null;
		if (null == result) {
			dataTablesVo = new DataTablesVo<DataVo>(sEcho, 0, 0, result);
		} else {
			dataTablesVo = new DataTablesVo<DataVo>(sEcho, searchResult.totalHits, searchResult.totalHits, result);
		}
		mv.addStaticAttribute("dataTablesVo", dataTablesVo);
		return mv;
	}
}
