package cn.com.cms.page.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.microduo.index.lucene3.MdSortField;
import com.microduo.index.lucene3.SearchResult;

import cn.com.cms.base.AppConfig;
import cn.com.cms.base.BaseController;
import cn.com.cms.common.BaseMappingJsonView;
import cn.com.cms.common.CmsData;
import cn.com.cms.common.DataTablesVo;
import cn.com.cms.common.FieldCodes;
import cn.com.cms.common.JsonPara;
import cn.com.cms.library.constant.EDataType;
import cn.com.cms.library.model.BaseLibrary;
import cn.com.cms.library.service.LibraryDataService;
import cn.com.cms.library.service.LibraryService;
import cn.com.people.data.util.DateTimeUtil;
import cn.com.cms.data.util.DataUtil;
import cn.com.cms.data.util.DataVO;

/**
 * 前端页面控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/page")
public class PageController extends BaseController {
	private static Logger log = Logger.getLogger(PageController.class);
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
	@RequestMapping("/list/{id}")
	public String list(@PathVariable int id, Model model) {
		BaseLibrary<?> dataBase = libraryService.find(id);
		model.addAttribute("dataBaseId", id);
		model.addAttribute("dataBase", dataBase);
		return "default/list";
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
		MdSortField[] dsSortFieldsArray = {
				new MdSortField(FieldCodes.DOC_TIME, DataUtil.dataType2SortType(EDataType.DateTime), true) };
		String[] hightLightFields = { FieldCodes.TITLE };
		SearchResult searchResult = libraryDataService.searchIndex(queryString,
				appConfig.getDefaultIndexSearchNumHits(), dsSortFieldsArray, hightLightFields, pageStart,
				appConfig.getAdminDataTablePageSize(), (Integer[]) dbIds.toArray(new Integer[dbIds.size()]));
		List<DataVO> result = Lists.newArrayList();
		if (null != searchResult && null != searchResult.documents && searchResult.documents.length > 0) {
			for (Document document : searchResult.documents) {
				result.add(new DataVO(document));
			}
		}
		DataTablesVo<DataVO> dataTablesVo = null;
		if (null == result) {
			dataTablesVo = new DataTablesVo<DataVO>(sEcho, 0, 0, result);
		} else {
			dataTablesVo = new DataTablesVo<DataVO>(sEcho, searchResult.totalHits, searchResult.totalHits, result);
		}
		mv.addStaticAttribute("dataTablesVo", dataTablesVo);
		return mv;
	}
}
