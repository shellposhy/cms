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

import cn.com.cms.framework.base.BaseController;
import cn.com.cms.framework.base.ControllerOperator;
import cn.com.cms.framework.base.Result;
import cn.com.cms.framework.base.table.DataTablesVo;
import cn.com.cms.framework.base.view.BaseMappingJsonView;
import cn.com.cms.framework.config.AppConfig;
import cn.com.cms.framework.config.JsonPara;
import cn.com.cms.framework.config.SystemConstant;
import cn.com.cms.user.model.User;
import cn.com.cms.user.model.UserGroup;
import cn.com.cms.user.service.UserGroupService;
import cn.com.cms.user.service.UserService;
import cn.com.cms.user.vo.UserVo;
import cn.com.people.data.util.JsonUtil;
import cn.com.people.data.util.StringUtil;

/**
 * 用户控制类
 * 
 * @author shishb
 * @version 1.0
 */

@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {

	private static Logger log = Logger.getLogger(UserController.class.getName());

	@Resource
	private UserService userService;
	@Resource
	private UserGroupService userGroupService;
	@Resource
	private AppConfig appConfig;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		log.debug("=====user.list=========");
		return "/admin/user/list";
	}

	/**
	 * 查询所有用户列表（ip.pw,ip+pw）
	 * 
	 * @param jsonParas
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/s", method = RequestMethod.POST)
	public MappingJacksonJsonView searchAll(@RequestBody JsonPara[] jsonParas, HttpServletRequest request) {
		log.debug("=====user.search=========");
		Map<String, String> paraMap = JsonPara.getParaMap(jsonParas);
		int sEcho = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.sEcho));
		Integer iDisplayStart = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.iDisplayStart));
		// 页面展现结果集
		int firstResult = 0;
		int pageSize = appConfig.getAdminDataTablePageSize();
		String queryStr = paraMap.get(JsonPara.DataTablesParaNames.sSearch);
		if (iDisplayStart != null) {
			firstResult = iDisplayStart;
		}
		if (("").equals(queryStr)) {
			queryStr = null;
		}
		Result<User> userResult = userService.searchAllUsers(queryStr, firstResult, pageSize);
		List<UserVo> showUserList = this.getShowUserList(userResult.getList());
		int totalCount = userResult.getTotalCount();
		DataTablesVo<UserVo> dataTablesVo = new DataTablesVo<UserVo>(sEcho, totalCount, totalCount, showUserList);
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("dataTablesVo", dataTablesVo);
		return mv;
	}

	/**
	 * multi user upload
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(HttpServletRequest request) {
		return "redirect:/admin/user";
	}

	/**
	 * 通过机构Id查询用户列表
	 * 
	 * @param orgId
	 * @param jsonParas
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{orgId}/s", method = RequestMethod.POST)
	public MappingJacksonJsonView search(@PathVariable("orgId") Integer orgId, @RequestBody JsonPara[] jsonParas,
			HttpServletRequest request) {
		log.debug("=====user.org.search=========");
		Map<String, String> paraMap = JsonPara.getParaMap(jsonParas);
		int sEcho = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.sEcho));
		Integer iDisplayStart = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.iDisplayStart));
		int firstResult = 0;
		int pageSize = appConfig.getAdminDataTablePageSize();
		String queryStr = paraMap.get(JsonPara.DataTablesParaNames.sSearch);
		if (iDisplayStart != null) {
			firstResult = iDisplayStart;
		}
		if ("".equals(queryStr)) {
			queryStr = null;
		}
		Result<User> result = userService.findByOrgAndUserName(orgId, queryStr, firstResult, pageSize);
		List<UserVo> showUserList = this.getShowUserList(result.getList());
		int totalCount = result.getTotalCount();
		DataTablesVo<UserVo> dataTablesVo = new DataTablesVo<UserVo>(sEcho, totalCount, totalCount, showUserList);
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("dataTablesVo", dataTablesVo);
		return mv;
	}

	/**
	 * 进入添加新用户页面
	 * 
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/{orgId}/new", method = RequestMethod.GET)
	public String preNew(@PathVariable("orgId") Integer orgId, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		log.debug("=====user.org.new=========");
		User user = new User();
		user.setOrgID(orgId);
		model.addAttribute("user", user);
		model.addAttribute("groupListJson", JsonUtil.getJsonFromList(userGroupService.findList(null)));
		return "/admin/user/edit";
	}

	/**
	 * 进入用户编辑页面
	 * 
	 * @param userId
	 * @param model
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Integer userId, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		log.debug("====user.edit=========");
		User user = userService.find(userId);
		StringBuilder treeSelId = new StringBuilder();
		for (int i = 0; i < user.getUserGroupList().size(); i++) {
			UserGroup userGroup = user.getUserGroupList().get(i);
			if (i > 0) {
				treeSelId.append(SystemConstant.COMMA_SEPARATOR);
			}
			treeSelId.append(userGroup.getId());
		}
		user.setTreeSelId(treeSelId.toString());
		user.setOldPassword(user.getPassword());
		model.addAttribute("user", user);
		model.addAttribute("groupListJson", JsonUtil.getJsonFromList(userGroupService.findList(null)));
		return "/admin/user/edit";
	}

	/**
	 * 保存新增,更新用户
	 * 
	 * @param user
	 * @param response
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String save(@Valid final User user, BindingResult result, final Model model,
			final HttpServletRequest request) {
		log.debug("=====user.save=========");
		return super.save(user, result, model, new ControllerOperator() {
			public void operate() {
				User currentUser = (User) request.getSession().getAttribute("currentUser");
				Integer currentId = currentUser.getId();
				user.setUpdateTime(new Date());
				user.setUpdaterId(currentId);
				List<UserGroup> userGroupList = new ArrayList<UserGroup>();
				if (user.getTreeSelId() != null && !user.getTreeSelId().equals("")) {
					String[] idStr = user.getTreeSelId().split(SystemConstant.COMMA_SEPARATOR);
					Integer[] groupIds = new Integer[idStr.length];
					for (int i = 0; i < idStr.length; i++) {
						groupIds[i] = Integer.parseInt(idStr[i]);
					}
					for (int j = 0; j < groupIds.length; j++) {
						UserGroup userGroup = new UserGroup();
						userGroup = userGroupService.find(groupIds[j]);
						if (userGroup != null) {
							userGroupList.add(userGroup);
						}
					}
				}
				user.setUserGroupList(userGroupList);
				if (user.getId() == null) {
					user.setCreateTime(new Date());
					user.setCreatorId(currentId);
				}
				if (null != user.getPassword() && !user.getPassword().isEmpty()) {
					user.setPassword(StringUtil.encodeToMD5(user.getPassword()));
				} else {
					if (null != user.getId()) {
						user.setPassword(user.getOldPassword());
					}
				}
				user.setOrderId(1);
				user.setPic("1");
				user.setForSys(SystemConstant.IS_USER_DATA);
				user.setUpdateTime(new Date());
				user.setUpdaterId(currentId);
				userService.save(user);
			}

			public void onFailure() {
				String groupListJson;
				try {
					groupListJson = JsonUtil.getJsonFromList(userGroupService.findList(null));
					model.addAttribute("groupListJson", groupListJson);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			public String getSuccessView() {
				return "redirect:/admin/user";
			}

			public String getFailureView() {
				return "/admin/user/edit";
			}
		});

	}

	/**
	 * 删除用户
	 * 
	 * @param userIdStr
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestBody JsonPara jsonPara) {
		log.debug("=========user.delete===========");
		String[] idStr = jsonPara.value.split(SystemConstant.COMMA_SEPARATOR);
		Integer[] userIds = new Integer[idStr.length];
		try {
			for (int i = 0; i < idStr.length; i++) {
				userIds[i] = Integer.parseInt(idStr[i]);
			}
			userService.petrify(userIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/user";
	}

	/**
	 * 拼装前台用户显示列表List
	 * 
	 * 所属用户组名称
	 * 
	 * @param userList
	 * @return
	 */
	public List<UserVo> getShowUserList(List<User> userList) {
		List<UserVo> showUserList = new ArrayList<UserVo>();
		if (userList.size() != 0 && userList.size() > 0) {
			for (int i = 0; i < userList.size(); i++) {
				User user = userList.get(i);
				List<UserGroup> userGroupList = userGroupService.findByUserId(user.getId());
				// 用户无关联
				if (userGroupList == null || userGroupList.size() == 0) {
					user.setGroupNames("未与组关联");
				}
				// 用户有关联组
				else {
					StringBuffer groupNames = new StringBuffer();
					// 仅关联一个用户组
					if (userGroupList.size() == 1) {
						groupNames.append(userGroupList.get(0).getName());
					}
					// 关联多个用户组,仅显示前两个组名
					else {
						groupNames.append(userGroupList.get(0).getName());
						groupNames.append(SystemConstant.COMMA_SEPARATOR);
						groupNames.append(userGroupList.get(1).getName());
					}
					user.setGroupNames(groupNames.toString());

				}
				UserVo userVo = UserVo.convertUserVo(user);
				showUserList.add(userVo);
			}
		}
		return showUserList;
	}
}
