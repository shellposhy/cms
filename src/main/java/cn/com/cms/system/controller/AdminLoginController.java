package cn.com.cms.system.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Strings;

import cn.com.cms.base.BaseController;
import cn.com.cms.framework.tree.MenuTreeNode;
import cn.com.cms.user.model.User;
import cn.com.cms.user.model.UserAction;
import cn.com.cms.user.service.UserActionService;
import cn.com.cms.user.service.UserActionService.ActionPropertySetter;
import cn.com.cms.user.service.UserService;
import cn.com.people.data.util.StringUtil;

/**
 * 登录通用处理业务逻辑
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminLoginController extends BaseController {
	private static Logger log = Logger.getLogger(AdminLoginController.class.getName());
	@Resource
	private UserActionService userActionService;
	@Resource
	private UserService userService;

	/**
	 * 进入首页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String admin(HttpServletRequest request) {
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		if (null == currentUser) {
			return "/admin/login";
		}
		return "/admin/index";
	}

	/**
	 * 进入到后台登录页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model) {
		log.debug("=======admin.login=========");
		if (!Strings.isNullOrEmpty(request.getParameter("from"))) {
			model.addAttribute("from", request.getParameter("from"));
		}
		return "/admin/login";
	}

	/**
	 * 退出系统
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		log.debug("=======admin.logout=========");
		request.getSession().setAttribute("currentUser", null);
		return "/admin/login";
	}

	/**
	 * 系统登录逻辑处理
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/security/check", method = RequestMethod.POST)
	public String excute(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		log.debug("=======admin.security.check=========");
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		User currentUser = userService.findByNamePwd(name, StringUtil.encodeToMD5(password));
		if (null == currentUser) {
			return "/admin/login";
		}
		// 加载权限树
		MenuTreeNode menuTreeNode = userActionService.findTreeByUser(MenuTreeNode.class, currentUser,
				new ActionPropertySetter<MenuTreeNode>() {
					public void set(MenuTreeNode node, UserAction entity) {
						if (entity != null) {
							if ("#".equals(entity.getUri())) {
								node.uri = entity.getUri();
							} else {
								node.setUri(entity.getUri());
							}
							node.iconSkin = entity.getIconSkin();
							if (null == node.iconSkin) {
								node.iconSkin = "";
							}
						}
					}
				});
		ObjectMapper om = new ObjectMapper();
		String jsonActionTree = om.writeValueAsString(menuTreeNode);
		request.getSession().setAttribute("jsonActionTree", jsonActionTree);
		request.getSession().setAttribute("currentUser", currentUser);
		if (!Strings.isNullOrEmpty(request.getParameter("from"))) {
			return "redirect:" + request.getParameter("from");
		}
		return "redirect:/admin/index";
	}

	/**
	 * 进入后台首页
	 * 
	 * @param request
	 * @return {@link String}
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		log.debug("=======admin.index=========");
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		if (null == currentUser) {
			return "/admin/login";
		} else {
			return "/admin/index";
		}
	}
}
