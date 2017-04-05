package cn.com.cms.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import cn.com.cms.framework.base.Result;
import cn.com.cms.user.dao.UserGroupMapMapper;
import cn.com.cms.user.model.User;
import cn.com.cms.user.model.UserGroup;
import cn.com.cms.user.service.UserActionService;
import cn.com.cms.user.service.UserGroupService;
import cn.com.cms.user.service.UserService;
import cn.com.cms.user.vo.UserGroupVo;
import cn.com.people.data.util.JsonUtil;

/**
 * 用户组管理控制类
 * 
 * @author gj
 */

@Controller
@RequestMapping("/admin/userGroup")
public class UserGroupController extends BaseController {

	private static Logger log = Logger.getLogger(UserGroupController.class.getName());
	@Resource
	private UserGroupService userGroupService;
	@Resource
	private UserActionService userActionService;
	@Resource
	private UserGroupMapMapper userGroupMapMapper;
	@Resource
	private UserService userService;
	@Resource
	private AppConfig appConfig;

	/**
	 * 用户组管理列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		log.debug("========user.group.list========");
		return "/admin/userGroup/list";
	}

	/**
	 * 分页查询ajax请求调用
	 * 
	 * @param fst
	 * @param qs
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/s", method = RequestMethod.POST)
	public MappingJacksonJsonView search(@RequestBody JsonPara[] jsonParas, HttpServletRequest request) {
		log.debug("========user.group.search========");
		Map<String, String> paraMap = JsonPara.getParaMap(jsonParas);
		int sEcho = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.sEcho));
		int firstResult = 0;
		int pageSize = appConfig.getAdminDataTablePageSize();
		Integer iDisplayStart = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.iDisplayStart));
		if (iDisplayStart != null) {
			firstResult = iDisplayStart;
		}
		String queryStr = paraMap.get(JsonPara.DataTablesParaNames.sSearch);
		if (("").equals(queryStr)) {
			queryStr = null;
		}
		Result<UserGroup> result = userGroupService.search(queryStr, firstResult, pageSize);
		List<UserGroup> userGroupList = result.getList();
		List<UserGroupVo> userGroupVoList = new ArrayList<UserGroupVo>();
		// 拼装页面展示的用户组列表
		for (UserGroup userGroup : userGroupList) {
			UserGroupVo userGroupVo = UserGroupVo.convertUserGroup(userGroup);
			List<Integer> userIdList = userGroupMapMapper.searchUserIdsByGroupId(userGroup.getId(), null, 0, 10);
			if (userIdList != null && userIdList.size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < userIdList.size(); i++) {
					if (i == userIdList.size() - 1) {
						sb.append(userService.find(userIdList.get(i)).getName());
					} else {
						sb.append(userService.find(userIdList.get(i)).getName()).append(SystemConstant.COMMA_SEPARATOR);
					}
				}
				if (userIdList.size() < 10) {
					userGroupVo.setUserStr(sb.toString());
				} else {
					userGroupVo.setUserStr(sb.toString() + "…");
				}
			} else {
				userGroupVo.setUserStr("无");
			}
			userGroupVoList.add(userGroupVo);
		}
		DataTablesVo<UserGroupVo> dataTablesVo = new DataTablesVo<UserGroupVo>(sEcho, result.getTotalCount(),
				result.getTotalCount(), userGroupVoList);
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("dataTablesVo", dataTablesVo);
		return mv;

	}

	/**
	 * 进入新增用户组页面
	 * 
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String preNew(Model model) throws JsonGenerationException, JsonMappingException, IOException {
		log.debug("========user.group.new========");
		model.addAttribute(new UserGroup());
		model.addAttribute("jsonActionTree", JsonUtil.getJsonFromObject(userActionService.findAdminTreeByGroup(null)));
		return "/admin/userGroup/edit";
	}

	/**
	 * 进入更新用户组页面
	 * 
	 * @param groupId
	 * @param model
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") Integer groupId, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		log.debug("========user.group.edit========");
		UserGroup userGroup = userGroupService.find(groupId);
		boolean allAdminAuthority = userGroup.isAllAdminAuthority();
		if (allAdminAuthority == SystemConstant.ALL_ADMIN_VOTE_NO) {
			List<Integer> selectIds = userActionService.findAdminActionByGroupId(groupId);
			StringBuilder treeSelId = new StringBuilder();
			for (int i = 0; i < selectIds.size(); i++) {
				if (i > 0) {
					treeSelId.append(SystemConstant.COMMA_SEPARATOR);
				}
				treeSelId.append(selectIds.get(i));
			}
			userGroup.setTreeSelId(treeSelId.toString());
		}
		model.addAttribute("jsonActionTree", JsonUtil.getJsonFromObject(userActionService.findAdminTreeByGroup(null)));
		model.addAttribute(userGroup);
		return "/admin/userGroup/edit";
	}

	/**
	 * 保存新增，更新用戶组
	 * 
	 * @param userGroup
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String save(@Valid final UserGroup userGroup, BindingResult result, final Model model,
			final HttpServletRequest request) {
		log.debug("========user.group.save========");
		return super.save(userGroup, result, model, new ControllerOperator() {
			public void operate() {
				User currentUser = (User) request.getSession().getAttribute("currentUser");
				Integer currentId = currentUser.getId();
				Integer groupId = userGroup.getId();
				List<Integer> actionIdList = new ArrayList<Integer>();
				if (userGroup.isAllAdminAuthority() == SystemConstant.ALL_ADMIN_VOTE_NO) {
					if (null != userGroup.getTreeSelId() && !("").equals(userGroup.getTreeSelId())) {
						String[] actIds = userGroup.getTreeSelId().split(SystemConstant.COMMA_SEPARATOR);
						for (int i = 0; i < actIds.length; i++) {
							actionIdList.add(Integer.parseInt(actIds[i]));
						}
					}
					userGroup.setActionList(actionIdList);
				}
				if (groupId == null) {
					userGroup.setCreatorId(currentId);
					userGroup.setCreateTime(new Date());
				}
				userGroup.setUpdaterId(currentId);
				userGroup.setUpdateTime(new Date());
				userGroupService.save(userGroup);
			}

			public String getSuccessView() {
				return "redirect:/admin/userGroup";
			}

			public String getFailureView() {
				return "/admin/userGroup/edit";
			}

			public void onFailure() {
				try {
					String jsonActionTree = JsonUtil.getJsonFromObject(userActionService.findAdminTreeByGroup(null));
					model.addAttribute("jsonActionTree", jsonActionTree);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 删除用户组，可批量删除
	 * 
	 * @param groupIdStr
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestBody JsonPara jsonpara, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {
		log.debug("========user.group.delete========");
		String[] idStr = jsonpara.value.split(SystemConstant.COMMA_SEPARATOR);
		Integer[] ids = new Integer[idStr.length];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = Integer.parseInt(idStr[i]);
		}
		try {
			userGroupService.delete(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/userGroup";
	}

}
