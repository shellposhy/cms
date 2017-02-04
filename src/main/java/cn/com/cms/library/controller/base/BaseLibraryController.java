package cn.com.cms.library.controller.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.com.cms.base.BaseController;
import cn.com.cms.base.ControllerOperator;
import cn.com.cms.common.BaseMappingJsonView;
import cn.com.cms.common.JsonPara;
import cn.com.cms.data.model.DataField;
import cn.com.cms.framework.base.LibraryTreeNode;
import cn.com.cms.framework.tree.DefaultTreeNode.PropertySetter;
import cn.com.cms.framework.security.UserSecurityService;
import cn.com.cms.library.constant.EDataFieldType;
import cn.com.cms.library.constant.EDataSourceType;
import cn.com.cms.library.constant.ELibraryNodeType;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.constant.ESecretLevel;
import cn.com.cms.library.model.BaseLibrary;
import cn.com.cms.library.model.ColumnModel;
import cn.com.cms.library.service.DataFieldService;
import cn.com.cms.library.service.LibraryModelService;
import cn.com.cms.library.service.LibraryService;
import cn.com.cms.user.model.User;
import cn.com.cms.user.model.UserGroup;
import cn.com.cms.user.service.UserGroupService;

/**
 * 数据库基础控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
public class BaseLibraryController<T extends BaseLibrary<T>> extends BaseController {
	@Resource
	private LibraryService<T> libraryService;
	@Resource
	private UserSecurityService userSecurityService;
	@Resource
	private UserGroupService userGroupService;
	@Resource
	private DataFieldService dataFieldService;
	@Resource
	private LibraryModelService columnModelService;
	protected final String URL_PREFIX = "/admin/library/";
	protected Logger LOG = Logger.getLogger(BaseLibraryController.class.getName());

	protected ELibraryType getLibType() {
		return ELibraryType.SYSTEM_DATA_BASE;
	}

	/**
	 * 列表页查询
	 * 
	 * @param model
	 * @return
	 */
	public String list(Model model) {
		return URL_PREFIX + getLibType().getCode() + "/list";
	}

	/**
	 * 通用查询
	 * 
	 * @param jsonParas
	 * @return
	 */
	public MappingJacksonJsonView search(@RequestBody JsonPara[] jsonParas) {
		Map<String, String> paraMap = JsonPara.getParaMap(jsonParas);
		String queryStr = paraMap.get(JsonPara.DataTablesParaNames.sSearch);
		List<T> objects = libraryService.findLikeName(queryStr, getLibType(), null);
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("objects", objects);
		return mv;
	}

	/**
	 * 查询数据库
	 * 
	 * @param directoryId
	 * @return
	 */
	public MappingJacksonJsonView find(Integer directoryId, HttpServletRequest request) {
		User currentUser = userSecurityService.currentUser(request);
		List<T> libraries = libraryService.findByParentId(directoryId, currentUser, getLibType());
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("libraries", libraries);
		return mv;
	}

	/**
	 * 获得整个树
	 * 
	 * @return
	 */
	public MappingJacksonJsonView tree() {
		LibraryTreeNode tree = libraryService.findTree(null, getLibType(), new PropertySetter<LibraryTreeNode, T>() {
			public void setProperty(LibraryTreeNode node, T entity) {
				if (null != entity) {
					if (ELibraryNodeType.Lib.equals(entity.getNodeType())) {
						node.setDir(false);
						node.checked = true;
					} else {
						node.setDir(true);
					}
				}
			}
		});
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("tree", tree);
		return mv;
	}

	/**
	 * 新建库
	 * 
	 * @param directoryId
	 * @param model
	 * @param clazz
	 * @return
	 */
	public String preNew(Integer directoryId, Model model, Class<T> clazz) {
		LOG.info("===database.new==");
		try {
			T library = clazz.newInstance();
			library.setParentID(directoryId);
			library.setType(getLibType());
			List<UserGroup> userGroups = userGroupService.findHasUser();
			List<ColumnModel> columnModels = columnModelService.findAll();
			model.addAttribute("columnModels", columnModels);
			model.addAttribute("userGroups", userGroups);
			model.addAttribute("dsTypeOptions", EDataSourceType.values());
			model.addAttribute("library", library);
		} catch (InstantiationException e) {
			LOG.error(e);
		} catch (IllegalAccessException e) {
			LOG.error(e);
		}
		return URL_PREFIX + getLibType().getCode() + "/edit";
	}

	/**
	 * 编辑数据库
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	public String edit(Integer id, Model model) {
		T library = libraryService.find(id);
		List<UserGroup> userGroups = userGroupService.findAll();
		List<ColumnModel> columnModels = columnModelService.findAll();
		model.addAttribute("columnModels", columnModels);
		model.addAttribute("userGroups", userGroups);
		model.addAttribute("dsTypeOptions", EDataSourceType.values());
		model.addAttribute("secretLevels", ESecretLevel.values());
		model.addAttribute("library", library);
		return URL_PREFIX + getLibType().getCode() + "/edit";
	}

	/**
	 * 保存数据库
	 * 
	 * @param library
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	public String save(final T library, BindingResult result, final Model model, final HttpServletRequest request) {
		final User currentUser = userSecurityService.currentUser(request);
		if (null == library.getId()) {
			library.setCreatorId(currentUser.getId());
			library.setCreateTime(new Date());
		}
		library.setUpdaterId(currentUser.getId());
		library.setUpdateTime(new Date());
		library.setType(getLibType());
		List<DataField> dataFields = dataFieldService.findByType(EDataFieldType.Required);
		List<DataField> modelFields = dataFieldService.findFieldsByModelId(library.getModelId());
		dataFields.addAll(modelFields);
		library.setDataFields(dataFields);
		return super.save(library, result, model, new ControllerOperator() {
			public void operate() {
				libraryService.saveDatabase(library, currentUser);
			}

			public String getSuccessView() {
				return "redirect:/admin/system/library";
			}

			public String getFailureView() {
				List<UserGroup> userGroups = userGroupService.findAll();
				List<ColumnModel> columnModels = columnModelService.findAll();
				model.addAttribute("columnModels", columnModels);
				model.addAttribute(library);
				model.addAttribute("userGroups", userGroups);
				model.addAttribute("dsTypeOptions", EDataSourceType.values());
				model.addAttribute("secretLevels", ESecretLevel.values());
				return URL_PREFIX + getLibType().getCode() + "/edit";
			}

			public void onFailure() {
			}
		});
	}

	/**
	 * 删除数据库
	 * 
	 * @param id
	 * @return
	 */
	public String delete(Integer id) {
		libraryService.deleteDatabase(id);
		return "redirect:/admin/system/library";
	}

	/**
	 * 获取用于列表显示的字段
	 * 
	 * @param modelId
	 * @return
	 */
	public MappingJacksonJsonView getDisplayFields(Integer modelId) {
		List<DataField> dataFileds = dataFieldService.findDisplayFieldsByModelId(modelId);
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("dataFileds", dataFileds);
		return mv;
	}
}
