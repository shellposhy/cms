package cn.com.cms.library.controller.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.com.cms.base.AppConfig;
import cn.com.cms.base.BaseController;
import cn.com.cms.base.ControllerOperator;
import cn.com.cms.common.BaseMappingJsonView;
import cn.com.cms.common.DataTablesVo;
import cn.com.cms.common.JsonPara;
import cn.com.cms.common.SystemConstant;
import cn.com.cms.data.model.DataField;
import cn.com.cms.data.service.DataFieldService;
import cn.com.cms.data.util.CharacterPinyinUtil;
import cn.com.cms.framework.base.Result;
import cn.com.cms.framework.security.UserSecurityService;
import cn.com.cms.library.constant.EDataFieldType;
import cn.com.cms.library.model.ColumnModel;
import cn.com.cms.library.model.ColumnModelFieldMap;
import cn.com.cms.library.service.LibraryModelFieldMapService;
import cn.com.cms.library.service.LibraryModelService;
import cn.com.cms.library.vo.ColumnModelVo;
import cn.com.cms.system.contant.ETaskStatus;
import cn.com.cms.system.contant.ETaskType;
import cn.com.cms.system.model.Task;
import cn.com.cms.system.service.TaskService;
import cn.com.cms.user.model.User;

/**
 * 数据库模板控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/library/model")
public class LibraryModelController extends BaseController {
	public static Logger log = Logger.getLogger(LibraryModelController.class);

	@Resource
	private AppConfig appConfig;
	@Resource
	private LibraryModelService columnModelService;
	@Resource
	private LibraryModelFieldMapService columnModelFieldMapService;
	@Resource
	private DataFieldService dataFieldService;
	@Resource
	private UserSecurityService userSecurityService;
	@Resource
	private TaskService taskService;

	/**
	 * 进入模板列表页
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		log.debug("=====library.model.list=========");
		return "/admin/library/model/list";

	}

	/**
	 * 查询模板列表（查询所有，带条件查询）
	 * 
	 * @param jsonParas
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/s", method = RequestMethod.POST)
	public MappingJacksonJsonView search(@RequestBody JsonPara[] jsonParas, HttpServletRequest request) {
		log.debug("===library.model.search====");
		Map<String, String> paraMap = JsonPara.getParaMap(jsonParas);
		int sEcho = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.sEcho));
		Integer iDisplayStart = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.iDisplayStart));
		int firstResult = 0;
		if (null != iDisplayStart) {
			firstResult = iDisplayStart;
		}
		int pageSize = appConfig.getAdminDataTablePageSize();
		String queryStr = paraMap.get(JsonPara.DataTablesParaNames.sSearch);
		if ("".equals(queryStr)) {
			queryStr = null;
		}
		Result<ColumnModel> sr = columnModelService.findByName(queryStr, null, firstResult, pageSize);
		List<ColumnModel> columnModeList = sr.getList();
		List<ColumnModelVo> showList = new ArrayList<ColumnModelVo>();
		int showSize = columnModeList.size();
		if (showSize != 0 && showSize > 0) {
			for (ColumnModel columnModel : columnModeList) {
				ColumnModelVo modelVo = ColumnModelVo.convertFromColumnModel(columnModel);
				showList.add(modelVo);
			}
		}
		int totalCount = sr.getTotalCount();
		DataTablesVo<ColumnModelVo> dataTablesVo = new DataTablesVo<ColumnModelVo>(sEcho, totalCount, totalCount,
				showList);
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("dataTablesVo", dataTablesVo);
		return mv;
	}

	/**
	 * 进入新增页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String preNew(Model model) {
		log.debug("===library.model.preNew=====");
		List<DataField> allFieldMapList = dataFieldService.findByType(EDataFieldType.Normal);
		model.addAttribute("columnModel", new ColumnModel());
		model.addAttribute("allFields", allFieldMapList);
		return "/admin/library/model/edit";
	}

	/**
	 * 进入修改页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Integer id, Model model) {
		log.debug("=====library.model.edit====");
		ColumnModel columnModel = columnModelService.find(id);
		List<ColumnModelFieldMap> modelFieldMaps = columnModelFieldMapService.findByColumnModelId(id);
		List<DataField> modelFields = new ArrayList<DataField>();
		StringBuilder builder = new StringBuilder();
		for (ColumnModelFieldMap columnModelFieldMap : modelFieldMaps) {
			DataField dataField = dataFieldService.find(columnModelFieldMap.getFieldId());
			modelFields.add(dataField);
			builder.append(columnModelFieldMap.getFieldId()).append(SystemConstant.COMMA_SEPARATOR);
		}
		builder.deleteCharAt(builder.length() - 1);
		columnModel.setDataFields(modelFields);
		columnModel.setMoreDataFieldsStr(builder.toString());
		List<DataField> allFieldMapList = dataFieldService.findByType(EDataFieldType.Normal);
		model.addAttribute("columnModel", columnModel);
		model.addAttribute("allFields", allFieldMapList);
		return "/admin/library/model/edit";
	}

	/**
	 * 保存新增和修改
	 * 
	 * @param columnModel
	 * @param result
	 * @param model
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated final ColumnModel columnModel, BindingResult result, final Model model,
			final HttpServletRequest request) {
		log.debug("=====library.model.save========");
		User currentUser = userSecurityService.currentUser(request);
		final Integer currentId = currentUser.getId();
		return super.save(columnModel, result, model, new ControllerOperator() {
			public void operate() {
				Integer columnModelId = columnModel.getId();
				String[] modelFieldStr = columnModel.getMoreDataFieldsStr().split(SystemConstant.COMMA_SEPARATOR);
				StringBuilder describe = new StringBuilder();
				List<ColumnModelFieldMap> columnModelFieldMaps = new ArrayList<ColumnModelFieldMap>();
				for (int i = 0; i < modelFieldStr.length; i++) {
					Integer fieldId = Integer.parseInt(modelFieldStr[i]);
					DataField dataField = dataFieldService.find(fieldId);
					ColumnModelFieldMap columnFieldMap = new ColumnModelFieldMap();
					columnFieldMap.setColumnModelId(columnModelId);
					columnFieldMap.setFieldId(fieldId);
					columnModelFieldMaps.add(columnFieldMap);
					if (i == modelFieldStr.length - 1) {
						describe.append(dataField.getName());
					} else {
						describe.append(dataField.getName());
						describe.append(SystemConstant.COMMA_SEPARATOR);
					}
				}
				// 更新对象数据
				columnModel.setDescribe(describe.toString());
				columnModel.setUpdaterId(currentId);
				columnModel.setUpdateTime(new Date());
				if (null != columnModelId) {
					columnModelFieldMapService.deleteByColumnId(columnModelId);
					columnModelFieldMapService.batchInsert(columnModelFieldMaps);
					columnModelService.update(columnModel);
					// 更新数据库模板时，写入任务调度，负责维护数据库字段和数据
					Task task = new Task();
					task.setName(columnModel.getName() + ":模板编辑");
					task.setCode(CharacterPinyinUtil.converterToFirstSpell(columnModel.getName()));
					task.setTaskType(ETaskType.MODEL_EDIT);
					task.setOwnerId(currentId);
					task.setProgress(0);
					task.setTaskStatus(ETaskStatus.Preparing);
					task.setModelId(columnModel.getId());
					task.setCreateTime(new Date());
					task.setUpdateTime(new Date());
					taskService.save(task);
				} else {
					columnModel.setCreatorId(currentId);
					columnModel.setCreateTime(new Date());
					columnModelService.insert(columnModel);
					Integer newColumnId = columnModel.getId();
					List<ColumnModelFieldMap> fieldMaps = new ArrayList<ColumnModelFieldMap>();
					for (ColumnModelFieldMap fieldMap : columnModelFieldMaps) {
						fieldMap.setColumnModelId(newColumnId);
						fieldMaps.add(fieldMap);
					}
					columnModelFieldMapService.batchInsert(fieldMaps);
				}
			}

			public void onFailure() {
				if (null != columnModel.getId()) {
					List<ColumnModelFieldMap> modelFieldMaps = columnModelFieldMapService
							.findByColumnModelId(columnModel.getId());
					List<DataField> modelFields = new ArrayList<DataField>();
					for (ColumnModelFieldMap columnModelFieldMap : modelFieldMaps) {
						DataField dataField = dataFieldService.find(columnModelFieldMap.getFieldId());
						modelFields.add(dataField);
					}
					columnModel.setDataFields(modelFields);
				}
				List<DataField> allFieldMaps = dataFieldService.findAllDataField();
				model.addAttribute("allFields", allFieldMaps);
			}

			public String getSuccessView() {
				return "redirect:/admin/library/model";
			}

			public String getFailureView() {
				return "/admin/library/model/edit";

			}
		});
	}

	/**
	 * 删除
	 * 
	 * @param jsonPara
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestBody JsonPara jsonPara) {
		log.debug("===library.model.delete=====");
		String[] idStr = jsonPara.value.split(SystemConstant.COMMA_SEPARATOR);
		int len = idStr.length;
		if (len >= 1) {
			for (int i = 0; i < len; i++) {
				ColumnModel model = columnModelService.find(Integer.parseInt(idStr[i]));
				if (!model.isForSys()) {
					columnModelFieldMapService.deleteByColumnId(Integer.parseInt(idStr[i]));
					columnModelService.delete(Integer.parseInt(idStr[i]));
				}
			}
		}
		return "redirect:/admin/library/model";
	}

}
