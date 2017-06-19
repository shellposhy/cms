package cn.com.cms.library.controller.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.com.cms.data.model.DataField;
import cn.com.cms.data.model.DataTable;
import cn.com.cms.data.service.DataFieldService;
import cn.com.cms.framework.base.BaseController;
import cn.com.cms.framework.base.CmsData;
import cn.com.cms.framework.base.ControllerOperator;
import cn.com.cms.framework.base.Node;
import cn.com.cms.framework.base.table.DataTablesVo;
import cn.com.cms.framework.base.table.FieldCodes;
import cn.com.cms.framework.base.view.BaseMappingJsonView;
import cn.com.cms.framework.config.AppConfig;
import cn.com.cms.framework.config.JsonPara;
import cn.com.cms.framework.config.SystemConstant;
import cn.com.cms.library.constant.EAccessType;
import cn.com.cms.library.constant.EDataStatus;
import cn.com.cms.library.constant.EDataType;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.constant.ESecretLevel;
import cn.com.cms.library.model.BaseLibrary;
import cn.com.cms.library.service.LibraryDataService;
import cn.com.cms.library.service.LibraryService;
import cn.com.cms.library.service.LibraryTableService;
import cn.com.cms.library.service.LibraryPictureService;
import cn.com.cms.library.vo.AttachVo;
import cn.com.cms.system.service.ImagePathService;
import cn.com.cms.user.service.UserSecurityService;
import cn.com.cms.user.service.UserService;
import cn.com.cms.util.FileUtil;
import cn.com.people.data.util.DateTimeUtil;
import cn.com.people.data.util.PkUtil;
import cn.com.pepper.common.PepperResult;
import cn.com.pepper.comparator.base.PepperSortField;
import cn.com.cms.data.util.DataUtil;
import cn.com.cms.data.util.DataVo;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * 数据库数据基础控制类
 * 
 * @author shishb
 * @version 1.0
 */
public class BaseDataController<T extends BaseLibrary<T>> extends BaseController {

	protected ELibraryType getLibType() {
		return ELibraryType.SYSTEM_DATA_BASE;
	}

	protected final String URL_PREFIX = "/admin/library/";
	protected Logger LOG = Logger.getLogger(BaseDataController.class.getName());

	@Resource
	protected AppConfig appConfig;
	@Resource
	protected UserService userService;
	@Resource
	protected LibraryDataService libraryDataService;
	@Resource
	protected LibraryService<T> libraryService;
	@Resource
	protected DataFieldService dataFieldService;
	@Resource
	protected LibraryTableService libraryTableService;
	@Resource
	protected UserSecurityService userSecurityService;
	@Resource
	private ImagePathService imagePathService;
	@Resource
	private LibraryPictureService pictureService;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, null);
	}

	/**
	 * 数据库数据复制
	 * 
	 * @param libraryId
	 * @param jsonParas
	 * @return
	 */
	public MappingJacksonJsonView copy(int libraryId, int type, JsonPara[] jsonParas) {
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		Map<String, String> paraMap = JsonPara.getParaMap(jsonParas);
		String dataIdsStr = paraMap.get(JsonPara.DataTablesParaNames.mSearch);
		String dbIdsStr = paraMap.get(JsonPara.DataTablesParaNames.searchIdStr);
		Integer taskId = libraryService.copyData(libraryId, Integer.valueOf(dbIdsStr), dataIdsStr, type);
		mv.addStaticAttribute("taskId", taskId);
		return mv;
	}

	/**
	 * 获得数据库所有节点
	 * 
	 * @param libId
	 * @return
	 */
	public MappingJacksonJsonView tree(Integer libId) {
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		T database = libraryService.find(libId);
		List<Node<Integer, String>> result = Lists.newArrayList();
		if (null != database) {
			List<T> databases = libraryService.findAll(database.getType(), database.getNodeType());
			if (null != databases && databases.size() > 0) {
				for (T base : databases) {
					Node<Integer, String> node = new Node<Integer, String>();
					node.id = base.getId();
					node.name = base.getName();
					result.add(node);
				}
			}
		}
		mv.addStaticAttribute("result", result);
		return mv;
	}

	/**
	 * 修复数据库索引
	 * 
	 * @param libId
	 * @return
	 */
	public MappingJacksonJsonView repair(Integer libId) {
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		Integer taskId = libraryService.repair(libId);
		mv.addStaticAttribute("taskId", taskId);
		return mv;
	}

	/**
	 * 数据列表
	 * 
	 * @param libraryId
	 * @param model
	 * @return
	 */
	public String list(int libraryId, Model model) {
		T library = libraryService.find(libraryId);
		List<DataField> fields = dataFieldService.findDisplayFieldsByDBId(libraryId);
		for (DataField dataField : fields) {
			if (FieldCodes.TITLE.equals(dataField.getCode())) {
				fields.remove(dataField);
				break;
			}
		}
		model.addAttribute("fields", fields);
		model.addAttribute("dataBase", library);
		return URL_PREFIX + getLibType().getCode() + "/data/list";
	}

	/**
	 * 获得头部字段列表
	 * 
	 * @param libraryId
	 * @return
	 */
	public List<DataField> getTableHeadFields(int libraryId) {
		List<DataField> fields = dataFieldService.findDisplayFieldsByDBId(libraryId);
		for (DataField dataField : fields) {
			if (FieldCodes.TITLE.equals(dataField.getCode())) {
				fields.remove(dataField);
				break;
			}
		}
		return fields;
	}

	/**
	 * 获取文章数据
	 * 
	 * @param libraryId
	 * @param jsonParas
	 * @return
	 */
	public MappingJacksonJsonView search(int libraryId, JsonPara[] jsonParas) {
		LOG.debug("========library.data.search=======");
		Map<String, String> paraMap = JsonPara.getParaMap(jsonParas);
		String queryStr = paraMap.get(JsonPara.DataTablesParaNames.sSearch);
		String[] hightLightFields = { FieldCodes.AUTHORS, FieldCodes.TITLE, FieldCodes.CONTENT };
		StringBuilder luneceStr = new StringBuilder();
		if (null == queryStr || queryStr.isEmpty()) {
			luneceStr.append("*:*");
			hightLightFields = null;
		} else {
			luneceStr.append("Title:").append(queryStr).append(" OR Content:").append(queryStr).append(" OR Authors:")
					.append(queryStr);
		}
		return search(luneceStr.toString(), hightLightFields, jsonParas, libraryId);
	}

	/**
	 * 组织datatable数据
	 * 
	 * @param queryStr
	 * @param hightLightFields
	 * @param jsonParas
	 * @param libraryIds
	 * @param
	 */
	public MappingJacksonJsonView search(String queryStr, String[] hightLightFields, JsonPara[] jsonParas,
			Integer... libraryIds) {
		Map<String, String> paraMap = JsonPara.getParaMap(jsonParas);
		int sEcho = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.sEcho));
		Integer iDisplayStart = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.iDisplayStart));
		int pageStart = (iDisplayStart == null ? 0 : iDisplayStart);
		int indexNumHits = appConfig.getDefaultIndexSearchNumHits();
		int pageCount = appConfig.getAdminDataTablePageSize();
		if (null == queryStr || queryStr.isEmpty()) {
			queryStr = "*:*";
		}
		PepperResult result = new PepperResult();
		try {
			PepperSortField[] sortFields = {
					new PepperSortField(FieldCodes.DOC_TIME, DataUtil.dataType2SortType(EDataType.DateTime), true) };
			result = libraryDataService.searchIndex(queryStr, indexNumHits, sortFields, hightLightFields, pageStart,
					pageCount, libraryIds);
		} catch (Exception e) {
			result.totalHits = 0;
			result.documents = null;
		}
		List<DataVo> dataVoList = Lists.newArrayList();
		if (null != result && null != result.documents && result.documents.length > 0) {
			for (Document document : result.documents) {
				DataVo dataVO = new DataVo(document);
				Integer dataId = Integer.parseInt(document.get(FieldCodes.ID));
				Integer tableId = Integer.valueOf(document.get(FieldCodes.TABLE_ID));
				List<String> imageList = libraryDataService.findDataImgs(tableId, dataId);
				if (null != imageList && imageList.size() > 0)
					dataVO.setImg(imageList.get(0));
				String attach = document.get(FieldCodes.ATTACH);
				if (!Strings.isNullOrEmpty(attach))
					dataVO.setAttach(attach);
				dataVoList.add(dataVO);
			}
		}
		DataTablesVo<DataVo> dataTablesVo = null;
		if (null == result) {
			dataTablesVo = new DataTablesVo<DataVo>(sEcho, 0, 0, dataVoList);
		} else {
			dataTablesVo = new DataTablesVo<DataVo>(sEcho, result.totalHits, result.totalHits, dataVoList);
		}
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("dataTablesVo", dataTablesVo);
		return mv;
	}

	/**
	 * 新增数据
	 * 
	 * @param libraryId
	 * @param model
	 * @return
	 */
	public String preNew(Integer libraryId, Model model) {
		List<DataField> fieldList = dataFieldService.findFieldsByDBId(libraryId);
		T library = libraryService.find(libraryId);
		DataVo dataVo = new DataVo();
		StringBuilder strBuilder = new StringBuilder();
		for (DataField field : fieldList) {
			if (field.getAccessType() != EAccessType.Sys) {
				strBuilder.append(field.getCode());
				strBuilder.append(",");
				dataVo.putFieldMap(field.getCode(), "");
			}
		}
		if (strBuilder.length() > 0) {
			strBuilder.deleteCharAt(strBuilder.length() - 1);
		}
		dataVo.setUuid(PkUtil.getShortUUID());
		model.addAttribute("dataVo", dataVo);
		model.addAttribute("dataBase", library);
		model.addAttribute("fieldsStr", strBuilder.toString());
		model.addAttribute("secretLevels", ESecretLevel.values());
		return URL_PREFIX + getLibType().getCode() + "/data/edit";
	}

	/**
	 * 编辑数据
	 * 
	 * @param tableId
	 * @param dataId
	 * @param request
	 * @param model
	 * @return
	 */
	public String edit(int tableId, int dataId, HttpServletRequest request, Model model) {
		Integer baseId = libraryTableService.find(tableId).getBaseId();
		CmsData peopleData = libraryDataService.find(tableId, dataId);
		DataVo dataVo = new DataVo();
		dataVo.setId(peopleData.getId());
		List<DataField> fieldList = dataFieldService.findFieldsByDBId(baseId);
		T library = libraryService.find(baseId);
		StringBuilder strBuilder = new StringBuilder();
		for (DataField field : fieldList) {
			if (field.getAccessType() != EAccessType.Sys) {
				strBuilder.append(field.getCode());
				strBuilder.append(",");
				dataVo.putFieldMap(field.getCode(),
						DataUtil.getDataTypeString(peopleData.get(field.getCode()), field.getDataType()));
			}
		}
		if (strBuilder.length() > 0) {
			strBuilder.deleteCharAt(strBuilder.length() - 1);
		}
		if (null != peopleData.get(FieldCodes.UUID)) {
			dataVo.setUuid(peopleData.get(FieldCodes.UUID).toString());
		}
		if (null != peopleData.get(FieldCodes.CREATE_TIME)) {
			dataVo.setCreateTime(DateTimeUtil.format((Date) peopleData.get(FieldCodes.CREATE_TIME), "yyyyMMdd"));
		}
		// 附件处理
		String docFileStr = (String) peopleData.get(FieldCodes.ATTACH);
		if (!Strings.isNullOrEmpty(docFileStr)) {
			String[] docFileNames = docFileStr.split(SystemConstant.SEPARATOR);
			List<AttachVo> attachList = Lists.newArrayList();
			for (String fileName : docFileNames) {
				AttachVo vo = new AttachVo(dataId, tableId, (String) peopleData.get(FieldCodes.UUID), fileName);
				attachList.add(vo);
			}
			model.addAttribute("attachList", attachList);
		}
		model.addAttribute("dataVo", dataVo);
		model.addAttribute("dataBase", library);
		model.addAttribute("dataId", dataId);
		model.addAttribute("tableId", tableId);
		model.addAttribute("fieldsStr", strBuilder.toString());
		model.addAttribute("secretLevels", ESecretLevel.values());
		return URL_PREFIX + getLibType().getCode() + "/data/edit";
	}

	/**
	 * 预览数据
	 * 
	 * @param tableId
	 * @param dataId
	 * @param request
	 * @param model
	 * @return
	 */
	public String info(int tableId, int dataId, HttpServletRequest request, Model model) {
		CmsData data = libraryDataService.find(tableId, dataId);
		DataVo vo = new DataVo(data);
		// 附件处理
		String docFileStr = (String) data.get(FieldCodes.ATTACH);
		if (!Strings.isNullOrEmpty(docFileStr)) {
			String[] docFileNames = docFileStr.split(SystemConstant.SEPARATOR);
			List<AttachVo> attachList = Lists.newArrayList();
			for (String fileName : docFileNames) {
				AttachVo attach = new AttachVo(dataId, tableId, (String) data.get(FieldCodes.UUID), fileName);
				attachList.add(attach);
			}
			model.addAttribute("attachList", attachList);
		}
		model.addAttribute("data", vo);
		return URL_PREFIX + getLibType().getCode() + "/data/info";
	}

	/**
	 * 下载附件
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String download(HttpServletRequest request, HttpServletResponse response) {
		CmsData data = libraryDataService.find(Integer.valueOf(request.getParameter("tableId")),
				Integer.valueOf(request.getParameter("id")));
		Date createTime = (Date) data.get(FieldCodes.CREATE_TIME);
		String createDate = DateTimeUtil.format(createTime, "yyyyMMdd");
		Integer baseId = libraryTableService.find(Integer.parseInt(request.getParameter("tableId"))).getBaseId();
		String filePath = FileUtil.getDocFilePath(appConfig.getAppPathHome(), baseId, createDate,
				request.getParameter("uuid"));
		request.setAttribute("filePath", filePath + request.getParameter("fileName"));
		request.setAttribute("fileName", request.getParameter("fileName"));
		return URL_PREFIX + getLibType().getCode() + "/data/download";
	}

	/**
	 * 保存数据
	 * 
	 * @param request
	 * @param baseId
	 * @param data
	 * @param result
	 * @param model
	 * @return
	 */
	public String save(final HttpServletRequest request, final int baseId, final DataVo data, BindingResult result,
			final Model model) {
		final DataTable dataTable = libraryService.getDataTable(baseId);
		final LinkedHashMap<String, DataField> dataFieldMap = dataFieldService.findAllDataFieldIndexCode();
		// 处理用户上传的临时图片
		String tempUploadPic = imagePathService.getTempPath() + "upic1/" + data.getUuid();
		File tmpFiles = new File(tempUploadPic);
		String changeContent = "";
		String createTime = request.getParameter("createTime");
		List<File> files = null;
		if (tmpFiles.isDirectory()) {
			files = FileUtil.getFiles(tempUploadPic, new ArrayList<File>());
			if (null != files && files.size() > 0) {
				if (null == createTime || createTime.isEmpty()) {
					createTime = DateTimeUtil.getCurrentDateTimeString("yyyyMMdd");
				}
				String realImagePath = imagePathService.getUPicRealRoot(baseId, createTime);
				for (File file : files) {
					try {
						FileUtils.copyFile(file, new File(realImagePath + file.getName()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				changeContent = pictureService.replaceContentPic(data.getFieldMap().get(FieldCodes.CONTENT), files,
						baseId, createTime);
				FileUtil.deleteAllFile(tempUploadPic);
				FileUtil.deleteFolder(tempUploadPic);
			}
		}
		final String finalContent = changeContent;
		final String finalCreateTime = Strings.isNullOrEmpty(createTime)
				? DateTimeUtil.getCurrentDateTimeString("yyyyMMdd") : createTime;
		return super.save(data, result, model, new ControllerOperator() {
			public void operate() {
				CmsData peopleData = new CmsData();
				peopleData.setId(data.getId());
				peopleData.setBaseId(baseId);
				peopleData.setDescription("");
				peopleData.setDataStatus(EDataStatus.Yes);
				peopleData.setTableId(dataTable.getId());
				peopleData.put(FieldCodes.UUID, data.getUuid());
				peopleData.put(FieldCodes.FINGER_PRINT, data.getUuid());
				// 内容处理
				for (String field : data.getFieldMap().keySet()) {
					peopleData.put(field, DataUtil.getDataTypeObject(data.getFieldMap().get(field),
							dataFieldMap.get(field).getDataType()));
					if (FieldCodes.CONTENT.equals(field)) {
						try {
							List<String> imgList = DataUtil.getImgs(data.getFieldMap().get(field));
							if (null != imgList && !imgList.isEmpty()) {
								peopleData.put(FieldCodes.IMGS, imgList.size());
							} else {
								peopleData.put(FieldCodes.IMGS, 0);
							}
						} catch (IOException e) {
							peopleData.put(FieldCodes.IMGS, 0);
						}
					}
				}
				if (!Strings.isNullOrEmpty(finalContent)) {
					peopleData.put(FieldCodes.CONTENT, finalContent);
				}
				// 上传附件处理
				File docFiles = new File(
						FileUtil.getDocFilePath(appConfig.getAppPathHome(), baseId, finalCreateTime, data.getUuid()));
				if (docFiles.exists()) {
					String[] files = docFiles.list();
					if (null != files && files.length > 0) {
						StringBuilder attach = new StringBuilder();
						for (String string : files) {
							attach.append(string);
							attach.append(SystemConstant.SEPARATOR);
						}
						if (attach.length() > 0) {
							attach.deleteCharAt(attach.length() - 1);
							peopleData.put(FieldCodes.ATTACH, attach.toString());
						}
					}
				}
				// 文档时间处理
				if (null == peopleData.get(FieldCodes.DOC_TIME)) {
					peopleData.put(FieldCodes.DOC_TIME,
							DataUtil.getDataTypeObject(DateTimeUtil.getCurrentDateTimeString(), EDataType.DateTime));
				}
				// 保存数据
				libraryDataService.save(peopleData, request);
			}

			public String getSuccessView() {
				return "redirect:/admin/" + getLibType().getCode() + "/data/" + baseId;
			}

			public String getFailureView() {
				model.addAttribute("data", data);
				return URL_PREFIX + getLibType().getCode() + "/data/edit";
			}

			public void onFailure() {
			}

		});
	}

	/**
	 * 删除文章
	 * 
	 * @param jsonpara
	 * @return
	 */
	public void delete(JsonPara jsonpara) {
		String[] para = jsonpara.value.split(SystemConstant.COMMA_SEPARATOR);
		for (int i = 0; i < para.length; i++) {
			String[] subPara = para[i].split("_");
			Integer dataId = Integer.parseInt(subPara[0]);
			Integer tableId = Integer.parseInt(subPara[1]);
			libraryDataService.delete(tableId, dataId);
		}
	}

}
