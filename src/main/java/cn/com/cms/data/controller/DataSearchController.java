package cn.com.cms.data.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.microduo.index.lucene3.MdSortField;
import com.microduo.index.lucene3.SearchResult;

import cn.com.cms.base.config.AppConfig;
import cn.com.cms.base.config.BaseController;
import cn.com.cms.base.data.DataTablesVo;
import cn.com.cms.base.data.FieldCodes;
import cn.com.cms.base.data.JsonPara;
import cn.com.cms.base.view.BaseMappingJsonView;
import cn.com.cms.data.constant.ELogicType;
import cn.com.cms.data.model.DataField;
import cn.com.cms.data.service.DataFieldService;
import cn.com.cms.framework.config.SystemConstant;
import cn.com.cms.framework.security.UserSecurityService;
import cn.com.cms.library.constant.EDataType;
import cn.com.cms.library.constant.ELibraryNodeType;
import cn.com.cms.library.model.BaseLibrary;
import cn.com.cms.data.util.DataFieldVo;
import cn.com.cms.data.util.DataUtil;
import cn.com.cms.data.util.DataVo;
import cn.com.cms.library.service.LibraryDataService;
import cn.com.cms.library.service.LibraryService;
import cn.com.cms.library.service.LibraryTableService;
import cn.com.cms.user.service.UserService;
import cn.com.people.data.util.DateTimeUtil;

/**
 * 数据搜索控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/data")
public class DataSearchController extends BaseController {
	private static Logger log = Logger.getLogger(DataSearchController.class);
	private static final String LOGIC_TYPE = "logic";
	private static final String TODAY = "today";
	private static final String MONTH = "month";
	private static final String DATE_TIME = "date_time";
	@Resource
	private LibraryDataService libraryDataService;
	@Resource
	private LibraryService<?> libraryService;
	@Resource
	private DataFieldService dataFieldService;
	@Resource
	private LibraryTableService libraryTableService;
	@Resource
	private UserSecurityService userSecurityService;
	@Resource
	private UserService userService;
	@Resource
	private AppConfig appConfig;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, null);
	}

	/**
	 * 快捷查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/qs", method = RequestMethod.GET)
	public String qs() {
		return "/admin/data/qs";
	}

	/**
	 * 高级查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/as", method = RequestMethod.GET)
	public String as() {
		return "/admin/data/as";
	}

	/**
	 * 拼装高级查询的查询语句
	 * 
	 * @param request
	 * @param response
	 * @author shishb
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/{type}/as", method = RequestMethod.POST)
	public String getAsQueryStr(@PathVariable int type, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		log.debug("===data.search.as.list===");
		String searchIdStr = request.getParameter("searchIdStr");
		// 存放带有逻辑NOT的查询字段和查询内容语句的map
		Map<DataField, String> notQueryMap = new HashMap<DataField, String>();
		List<DataField> dataFieldList = new ArrayList<DataField>();
		StringBuilder queryString = new StringBuilder();
		// 表达式查询
		if (type == 1) {
			String exprStr = request.getParameter("expression");
			if (exprStr.trim().isEmpty()) {
				queryString.append("*:*");
			} else {
				queryString.append(exprStr);
			}
		} else {
			List<DataField> dataFieldlist = dataFieldService.findFieldsByDBIds(searchIdStr);
			// 作为条件项的字段列表
			List<DataField> searchFields = new ArrayList<DataField>();
			for (DataField dataField : dataFieldlist) {
				if (null != request.getParameter(dataField.getCode())) {
					searchFields.add(dataField);
				}
			}
			// 循环迭代查询条件
			for (DataField dataField : searchFields) {
				StringBuilder sb = new StringBuilder(dataField.getCode()).append("_");
				String logicType = request.getParameter(sb.append(LOGIC_TYPE).toString());
				String fieldCode = dataField.getCode();
				String fieldValue = request.getParameter(fieldCode);
				if (!fieldValue.isEmpty() && fieldValue.contains(" ")) {
					StringBuilder tem = new StringBuilder();
					fieldValue = tem.append("(").append(fieldValue).append(")").toString();
				}
				// 处理字段类型为dateTime类型的字段
				if (dataField.getDataType().equals(EDataType.DateTime)) {
					StringBuilder newSb = new StringBuilder(dataField.getCode()).append("_");
					String startTime = "";
					String endTime = "";
					// 选择时间段
					if (fieldValue.equals(DATE_TIME)) {
						startTime = DateTimeUtil.format(
								DateTimeUtil.parse(request.getParameter(newSb.toString().concat("start"))),
								"yyyyMMddHHmmss");
						endTime = DateTimeUtil.format(
								DateTimeUtil.parse(request.getParameter(newSb.toString().concat("end"))),
								"yyyyMMddHHmmss");
					} else {
						// 选择本日
						if (fieldValue.equals(TODAY)) {
							String todayStr = DateTimeUtil.format(new Date(), "yyyyMMdd");
							startTime = todayStr + "000000";
							endTime = todayStr + "235959";
						}
						// 选择本月
						else if (fieldValue.equals(MONTH)) {
							startTime = DateTimeUtil.format(DateTimeUtil.getFirstDateOfThisMonth(), "yyyyMMdd")
									+ "000000";
							endTime = DateTimeUtil.format(DateTimeUtil.getLastDateOfThisMonth(), "yyyyMMdd") + "235959";
						}
					}
					if (!startTime.isEmpty() && !endTime.isEmpty()) {
						if (logicType.equals(ELogicType.Not.toString())) {
							StringBuilder temQueryStr = new StringBuilder();
							temQueryStr.append("#long#").append(dataField.getCode()).append(":[").append(startTime)
									.append(" TO ").append(endTime).append("] ");
							notQueryMap.put(dataField, temQueryStr.toString());
							dataFieldList.add(dataField);
						} else {
							if (queryString.toString().length() > 0) {
								if (logicType.equals(ELogicType.And.toString())) {
									queryString.append(" AND ");
								} else if (logicType.equals(ELogicType.Or.toString())) {
									queryString.append(" OR ");
								}
							}
							queryString.append("#long#").append(dataField.getCode()).append(":[").append(startTime)
									.append(" TO ").append(endTime).append("] ");
						}
					}
				}
				// 处理非时间字段
				else {
					if (logicType.equals(ELogicType.Not.toString())) {
						StringBuilder temQueryStr = new StringBuilder();
						temQueryStr.append(DataUtil.dataType2LuceneType(dataField.getDataType())).append(fieldCode)
								.append(":").append(fieldValue).append(" ");
						notQueryMap.put(dataField, temQueryStr.toString());
						dataFieldList.add(dataField);
					} else {
						if (queryString.length() > 0) {
							if (logicType.equals(ELogicType.And.toString())) {
								queryString.append(" AND ");
							} else if (logicType.equals(ELogicType.Or.toString())) {
								queryString.append(" OR ");
							}
						}
						queryString.append(DataUtil.dataType2LuceneType(dataField.getDataType())).append(fieldCode)
								.append(":").append(fieldValue).append(" ");
					}
				}
			}
		}
		// 处理查询字段
		if (queryString.length() <= 0 && notQueryMap.size() == 0) {
			queryString.append("*:*");
		} else if (notQueryMap.size() > 0) {
			StringBuilder temNotQueryStrSb = new StringBuilder();
			if (notQueryMap.size() == 1) {
				String temNotQueryStr = notQueryMap.get(dataFieldList.get(0));
				temNotQueryStrSb.append("NOT ").append(temNotQueryStr);
			} else {
				temNotQueryStrSb.append("NOT (");
				for (int i = 0; i < dataFieldList.size(); i++) {
					String subTemNotString = notQueryMap.get(dataFieldList.get(i));
					temNotQueryStrSb.append(subTemNotString);
					if (i > 0 && i < dataFieldList.size() - 1) {
						temNotQueryStrSb.append(" AND ");
					}
				}
				temNotQueryStrSb.append(")");
			}
			if (queryString.length() <= 0) {
				queryString.append("*:* ").append(temNotQueryStrSb.toString());
			} else {
				queryString.append(" ").append(temNotQueryStrSb.toString());
			}
		}
		String mSearch = java.net.URLEncoder.encode(java.net.URLEncoder.encode(queryString.toString(), "utf-8"),
				"utf-8");
		String iType = type == 0 ? "ms" : "es";
		return "redirect:/admin/data?iType=" + iType + "&mSearch=" + mSearch + "&searchIdStr=" + searchIdStr;
	}

	/**
	 * 获得所有的数据域对象公用的数据字段
	 * 
	 * @param jsonPara
	 * @return
	 */
	@RequestMapping(value = "/field", method = RequestMethod.POST)
	@ResponseBody
	public List<DataFieldVo> searchSelField(@RequestBody JsonPara jsonPara) {
		log.debug("=====data.search.field======");
		String dbIdStr = jsonPara.value;
		List<DataField> dataFields = dataFieldService.findFieldsInBasesByAccess(dbIdStr);
		List<DataFieldVo> dataFieldQueryVos = new ArrayList<DataFieldVo>();
		if (null != dataFields && dataFields.size() != 0) {
			for (DataField dataField : dataFields) {
				if (!dataField.getCode().equals("Sort_Ids")) {
					DataFieldVo dataFieldVO = new DataFieldVo(dataField.getId(), dataField.getName(),
							dataField.getCode(), dataField.getDataType().ordinal());
					dataFieldQueryVos.add(dataFieldVO);
				}
			}
		}
		return dataFieldQueryVos;
	}

	/**
	 * 进入list页面
	 * 
	 * @param baseId
	 * @param model
	 * @return
	 * @author Cheng
	 * @throws UnsupportedEncodingException
	 * @created 2012-12-17 下午5:46:59
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, @RequestParam String iType, @RequestParam String searchIdStr,
			@RequestParam(value = "mSearch", required = false) String mSearch,
			@RequestParam(value = "sortField", required = false) String sortField, HttpServletRequest request)
			throws UnsupportedEncodingException {
		log.debug("===data.search.list===");
		model.addAttribute("iType", iType);
		if (null != mSearch && !"".equals(mSearch)) {
			mSearch = java.net.URLDecoder.decode(mSearch, "UTF-8");
		}
		StringBuilder seachDbNameStr = new StringBuilder();
		String[] dataBaseIds = searchIdStr.split(SystemConstant.COMMA_SEPARATOR);
		for (String id : dataBaseIds) {
			BaseLibrary<?> dataBase = libraryService.find(Integer.valueOf(id));
			if (null != dataBase && ELibraryNodeType.Lib.equals(dataBase.getNodeType())) {
				seachDbNameStr.append(dataBase.getName() + SystemConstant.COMMA_SEPARATOR);
			}
		}
		model.addAttribute("searchDbName", seachDbNameStr.toString().length() > 1
				? seachDbNameStr.deleteCharAt(seachDbNameStr.toString().length() - 1) : "");
		model.addAttribute("mSearch", mSearch);
		model.addAttribute("searchIdStr", searchIdStr);
		return "/admin/library/default/data/list";
	}

	/**
	 * 查询指定数据库文章列表
	 * 
	 * @param baseId
	 * @param jsonParas
	 * @return
	 */
	@RequestMapping(value = "/s", method = RequestMethod.POST)
	public MappingJacksonJsonView search(@RequestParam String iType, @RequestParam String searchIdStr,
			@RequestParam(value = "mSearch", required = false) String mSearch, @RequestBody JsonPara[] jsonParas) {
		log.debug("=====data.search.search====");
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		Map<String, String> paraMap = JsonPara.getParaMap(jsonParas);
		// dataTable默认参数
		Integer iDisplayStart = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.iDisplayStart));
		int sEcho = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.sEcho));
		String queryStr = paraMap.get(JsonPara.DataTablesParaNames.sSearch);
		// 数据节点
		String[] searchIdstr = searchIdStr.split(SystemConstant.COMMA_SEPARATOR);
		Integer[] serachIds = new Integer[searchIdstr.length];
		for (int i = 0; i < serachIds.length; i++) {
			serachIds[i] = Integer.parseInt(searchIdstr[i]);
		}
		int firstResult = 0;
		int maxResult = appConfig.getAdminDataTablePageSize();
		int numHits = appConfig.getDefaultIndexSearchNumHits();
		if (iDisplayStart != null) {
			firstResult = iDisplayStart;
		}
		StringBuilder luneceStr = new StringBuilder();
		// 默认排序
		List<MdSortField> dsSortFields = new ArrayList<MdSortField>();
		DataField dsSortField = dataFieldService.getByCode(FieldCodes.DOC_TIME);
		dsSortFields
				.add(new MdSortField(FieldCodes.DOC_TIME, DataUtil.dataType2SortType(dsSortField.getDataType()), true));
		MdSortField[] dsSortFieldsArray = new MdSortField[dsSortFields.size()];
		// 结果集合
		SearchResult result = new SearchResult();
		String[] hightLightFields = { FieldCodes.AUTHORS, FieldCodes.TITLE, FieldCodes.CONTENT };
		// 快捷查询
		if ("qs".equals(iType)) {
			// 查询串的处理
			if (null == mSearch || "".equals(mSearch)) {
				if (null == queryStr || "".equals(queryStr)) {
					luneceStr.append("*:*");
				} else {
					luneceStr.append("Title:").append(queryStr).append(" OR Content:").append(queryStr)
							.append(" OR Authors:").append(queryStr);
				}
			} else {
				if (null == queryStr || "".equals(queryStr)) {
					luneceStr.append("Title:").append(mSearch).append(" OR Content:").append(mSearch)
							.append(" OR Authors:").append(mSearch);
				} else {
					luneceStr.append("(Title:").append(mSearch).append(" OR Content:").append(mSearch)
							.append(" OR Authors:").append(mSearch).append(") AND (").append("Title:").append(queryStr)
							.append(" OR Content:").append(queryStr).append(" OR Authors:").append(queryStr)
							.append(")");
				}
			}
			// 高亮处理
			if ("*:*".equals(luneceStr.toString())) {
				hightLightFields = null;
			}
			try {
				result = libraryDataService.searchIndex(luneceStr.toString(), numHits,
						dsSortFields.toArray(dsSortFieldsArray), hightLightFields, firstResult, maxResult, serachIds);
			} catch (Exception e) {
				result.documents = null;
				result.totalHits = 0;
			}
		}
		// 高级查询
		else if ("ms".equals(iType) || "es".equals(iType)) {
			luneceStr.append(mSearch);
			// 排序
			List<MdSortField> asSortFields = new ArrayList<MdSortField>();
			DataField asSortField = dataFieldService.getByCode(FieldCodes.DOC_TIME);
			dsSortFields.add(
					new MdSortField(FieldCodes.DOC_TIME, DataUtil.dataType2SortType(asSortField.getDataType()), true));
			MdSortField[] asSortFieldsArray = new MdSortField[dsSortFields.size()];
			List<String> hList = new ArrayList<String>(2);
			String[] hightLightField = null;
			if (mSearch.contains(FieldCodes.TITLE)) {
				hList.add(FieldCodes.TITLE);
			}
			if (mSearch.contains(FieldCodes.CONTENT)) {
				hList.add(FieldCodes.CONTENT);
			}
			if (mSearch.contains(FieldCodes.AUTHORS)) {
				hList.add(FieldCodes.AUTHORS);
			}
			if (hList.size() > 0) {
				hightLightField = new String[hList.size()];
				hightLightField = hList.toArray(hightLightField);
			}
			try {
				result = libraryDataService.searchIndex(luneceStr.toString(), numHits,
						asSortFields.toArray(asSortFieldsArray), hightLightField, firstResult, maxResult, serachIds);
			} catch (Exception e) {
				result.documents = null;
				result.totalHits = 0;
			}
		}
		// 组织文章
		List<DataVo> dataVoList = new ArrayList<DataVo>();
		if (null != result && null != result.documents && result.documents.length > 0) {
			for (Document document : result.documents) {
				dataVoList.add(new DataVo(document));
			}
		}
		DataTablesVo<DataVo> dataTablesVo = new DataTablesVo<DataVo>(sEcho,
				null != result && result.totalHits > 0 ? result.totalHits : 0,
				null != result && result.totalHits > 0 ? result.totalHits : 0, dataVoList);
		mv.addStaticAttribute("dataTablesVo", dataTablesVo);
		return mv;
	}
}
