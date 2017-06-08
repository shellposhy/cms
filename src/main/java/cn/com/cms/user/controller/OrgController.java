package cn.com.cms.user.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.com.cms.framework.base.BaseController;
import cn.com.cms.framework.base.ControllerOperator;
import cn.com.cms.framework.base.Node;
import cn.com.cms.framework.base.tree.DefaultTreeNode;
import cn.com.cms.framework.base.view.BaseMappingJsonView;
import cn.com.cms.framework.config.SystemConstant;
import cn.com.cms.user.model.Org;
import cn.com.cms.user.model.User;
import cn.com.cms.user.service.OrgService;
import cn.com.cms.user.service.UserGroupService;
import cn.com.cms.user.service.UserService;
import cn.com.people.data.util.JsonUtil;

/**
 * 机构控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/org")
public class OrgController extends BaseController {

	public static Logger log = Logger.getLogger(OrgController.class);

	@Resource
	private OrgService orgService;
	@Resource
	private UserService userService;
	@Resource
	private UserGroupService userGroupService;

	/**
	 * 进入机构树
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		log.debug("=======org.list=======");
		return "/admin/org/list";
	}

	/**
	 * 获取完整的 机构树
	 * 
	 * @return
	 */
	@RequestMapping(value = "/s", method = RequestMethod.POST)
	public MappingJacksonJsonView tree() {
		log.debug("==org.tree====");
		DefaultTreeNode orgTree = orgService.findTree();
		orgTree.name = "根机构";
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("orgTree", orgTree);
		return mv;
	}

	/**
	 * 获取完整的 机构树
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getNoUserOrgTree")
	public MappingJacksonJsonView findAllNoUserOrgs() {
		log.debug("==org.no.user.orgs====");
		DefaultTreeNode orgTree = orgService.findAllNoUserOrgs();
		orgTree.name = "根机构";
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("orgTree", orgTree);
		return mv;
	}

	/**
	 * 进入机构新增页面
	 * 
	 * @param pid
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String preNew(Model model) throws JsonGenerationException, JsonMappingException, IOException {
		log.debug("=======OrgController.preNew=======");
		model.addAttribute("org", new Org());
		List<Node<Integer, String>> groups = userGroupService.findList(null);
		model.addAttribute("groupListJson", JsonUtil.getJsonFromList(groups));
		return "/admin/org/edit";
	}

	/**
	 * 进入机构修改页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Integer id, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		log.debug("=======org.edit==========");
		Org org = orgService.find(id);
		StringBuilder treeSelId = new StringBuilder();
		List<Integer> groupIds = orgService.findGroupIdsByOrgId(id);
		for (int i = 0; i < groupIds.size(); i++) {
			if (i > 0) {
				treeSelId.append(SystemConstant.COMMA_SEPARATOR);
			}
			treeSelId.append(groupIds.get(i));
		}
		org.setTreeSelId(treeSelId.toString());
		model.addAttribute("org", org);
		List<Node<Integer, String>> groups = userGroupService.findList(null);
		model.addAttribute("groupListJson", JsonUtil.getJsonFromList(groups));
		return "/admin/org/edit";
	}

	/**
	 * 保存新增和修改
	 * 
	 * @param org
	 * @param result
	 * @param model
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated final Org org, BindingResult result, final Model model,
			final HttpServletRequest httpServletRequest) {
		log.debug("=======OrgController.save==========");
		return super.save(org, result, model, new ControllerOperator() {
			public void operate() {
				User currentUser = (User) httpServletRequest.getSession().getAttribute("currentUser");
				Integer currentId = currentUser.getId();
				org.setUpdaterId(currentId);
				orgService.save(org);
			}

			public void onFailure() {
			}

			public String getSuccessView() {
				return "redirect:/admin/user";
			}

			public String getFailureView() {
				return "/admin/org/edit";
			}
		});
	}

	/**
	 * 删除机构
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public MappingJacksonJsonView delete(@PathVariable("id") Integer id) {
		log.debug("==org.delete====");
		int realUsrCount = userService.findByOrgId(id).size(); // 查看机构下是否有用户关联
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		if (orgService.hasChild(id)) {
			mv.addStaticAttribute("error", true);
			mv.addStaticAttribute("msg", "该机构下有子机构，无法删除！");
		} else if (realUsrCount > 0) {
			mv.addStaticAttribute("error", true);
			mv.addStaticAttribute("msg", "该机构下存在用户，无法删除！");
		} else {
			orgService.delete(id);
			mv.addStaticAttribute("error", true);
		}
		return mv;
	}

}
