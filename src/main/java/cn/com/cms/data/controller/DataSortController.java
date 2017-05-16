package cn.com.cms.data.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.com.cms.base.config.BaseController;
import cn.com.cms.base.config.ControllerOperator;
import cn.com.cms.base.data.JsonPara;
import cn.com.cms.base.view.BaseMappingJsonView;
import cn.com.cms.data.service.DataSortService;
import cn.com.cms.framework.config.SystemConstant;
import cn.com.cms.framework.security.UserSecurityService;
import cn.com.cms.framework.tree.DefaultTreeNode;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.model.BaseLibrary;
import cn.com.cms.library.model.DataSort;
import cn.com.cms.library.service.LibraryService;
import cn.com.cms.user.model.User;
import cn.com.cms.user.service.UserActionService;
import cn.com.cms.user.service.UserGroupService;

/**
 * 数据分类控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/data/sort")
public class DataSortController extends BaseController {
	private static Logger LOG = Logger.getLogger(DataSortController.class);
	@Resource
	private DataSortService dataSortService;
	@Resource
	public UserSecurityService userSecurityService;
	@Resource
	public LibraryService<?> libraryService;
	@Resource
	public UserGroupService userGroupService;
	@Resource
	public UserActionService userActionService;

	/**
	 * 进入列表页面
	 * 
	 * @param type
	 *            {g:全局标签, u:用户库标签}
	 * @return
	 * @author Cheng
	 * @created 2012-12-18 上午10:04:16
	 */
	@RequestMapping(value = "/{type}", method = RequestMethod.GET)
	public String list(@PathVariable("type") String type) {
		LOG.debug("=====data.sort.list=========");
		return "/admin/library/sort/" + type + "list";
	}

	/**
	 * 根据类型获取对应库的树
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/library/{type}/tree")
	public MappingJacksonJsonView libraryTree(HttpServletResponse response, @PathVariable int type) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		DefaultTreeNode root = libraryService.findTree(ELibraryType.valueOf(type));
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("root", root);
		return mv;
	}

	/**
	 * 通用列表查询
	 * 
	 * @param baseId
	 * @param jsonParas
	 * @return
	 */
	@RequestMapping(value = "/{baseId}/s", method = RequestMethod.POST)
	public MappingJacksonJsonView search(@PathVariable(value = "baseId") Integer baseId,
			@RequestBody JsonPara[] jsonParas) {
		LOG.debug("=====data.sort.search=========");
		Map<String, String> paraMap = JsonPara.getParaMap(jsonParas);
		String queryStr = paraMap.get(JsonPara.DataTablesParaNames.sSearch);
		DefaultTreeNode datasortTree = null;
		datasortTree = dataSortService.findTreeByNameAndDBId(queryStr, baseId, null);
		datasortTree.name = "根分类";
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("datasortTree", datasortTree);
		return mv;
	}

	/**
	 * 数据编辑数据标签树
	 * 
	 * @param baseId
	 * @return
	 */
	@RequestMapping(value = "/{baseId}/tree", method = RequestMethod.POST)
	public MappingJacksonJsonView commonAndDbSortTree(@PathVariable(value = "baseId") Integer baseId) {
		LOG.debug("=====data.sort.edit.tree=========");
		DefaultTreeNode datasortTree = new DefaultTreeNode();
		datasortTree = dataSortService.findCommonAndDBSortTree(baseId, null);
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("datasortTree", datasortTree);
		return mv;
	}

	/**
	 * 单独初始化数据编辑页面中数据分类树
	 * 
	 * @param baseId
	 * @return
	 */
	@RequestMapping(value = "/tree/{baseId}", method = RequestMethod.POST)
	public MappingJacksonJsonView tree(@PathVariable(value = "baseId") Integer baseId) {
		LOG.debug("=====data.sort.tree=========");
		DefaultTreeNode datasortTree = dataSortService.findTreeByNameAndDBId(null, baseId, null);
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("datasortTree", datasortTree);
		return mv;
	}

	/**
	 * 获得多个数据库的标签
	 * 
	 * @param jsonParas
	 * @return
	 */
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	public MappingJacksonJsonView DbSort(@RequestBody JsonPara[] jsonParas) {
		LOG.debug("=====data.sort.multi.database.tree=========");
		Map<String, String> jsons = JsonPara.getParaMap(jsonParas);
		String searchId = jsons.get(JsonPara.DataTablesParaNames.searchIdStr);
		String[] searchIds = searchId.split(SystemConstant.COMMA_SEPARATOR);
		List<DataSort> list = new ArrayList<DataSort>();
		for (String id : searchIds) {
			List<DataSort> sorts = dataSortService.findByDBId(Integer.valueOf(id));
			if (sorts != null && sorts.size() > 0) {
				list.addAll(sorts);
			}
		}
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("datasortTree", DefaultTreeNode.parseTree(list));
		return mv;
	}

	/**
	 * 进入新增页面
	 * 
	 * @param baseId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{baseId}/new", method = RequestMethod.GET)
	public String preNew(@PathVariable(value = "baseId") Integer baseId, Model model) {
		LOG.debug("========data.sort.new========");
		DataSort dataSort = new DataSort();
		dataSort.setBaseId(baseId);
		model.addAttribute(dataSort);
		return "/admin/library/sort/edit";
	}

	/**
	 * 进入修改页面
	 * 
	 * @param Id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") Integer Id, Model model) {
		LOG.debug("========data.srot.edit========");
		DataSort datasort = dataSortService.find(Id);
		model.addAttribute("dataSort", datasort);
		return "/admin/library/sort/edit";
	}

	/**
	 * 保存
	 * 
	 * @param datasort
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String save(@Valid final DataSort datasort, BindingResult result, final Model model,
			HttpServletRequest request) {
		LOG.debug("========data.srot.save========");
		User currentUser = userSecurityService.currentUser(request);
		if (datasort.getId() == null) {
			datasort.setCreatorId(currentUser.getId());
			datasort.setCreateTime(new Date());
		}
		datasort.setUpdaterId(currentUser.getId());
		datasort.setUpdateTime(new Date());
		return super.save(datasort, result, model, new ControllerOperator() {
			public void operate() {
				dataSortService.saveDataSort(datasort);
			}

			public String getSuccessView() {
				if (datasort.getBaseId().equals(0)) {
					return "redirect:/admin/data/sort/g";
				} else {
					BaseLibrary<?> base = libraryService.find(datasort.getBaseId());
					if (base != null) {
						if (base.getType().ordinal() == 0) {
							return "redirect:/admin/data/sort/u";
						} else if (base.getType().ordinal() == 1) {
							return "redirect:/admin/data/sort/u";
						} else {
							return "redirect:/admin/data/sort/g";
						}
					} else {
						return "redirect:/admin/data/sort/g";
					}
				}
			}

			public String getFailureView() {
				model.addAttribute(datasort);
				return "/admin/library/sort/edit";
			}

			public void onFailure() {
			}
		});
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public MappingJacksonJsonView delete(@PathVariable(value = "id") final Integer id) {
		LOG.debug("========data.srot.delete========");
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		if (dataSortService.hasChildren(id)) {
			mv.addStaticAttribute("error", true);
			mv.addStaticAttribute("msg", "该标签下有子标签，无法删除！");
		} else {
			dataSortService.deleteDataSort(id);
			mv.addStaticAttribute("error", false);
		}
		return mv;
	}
}