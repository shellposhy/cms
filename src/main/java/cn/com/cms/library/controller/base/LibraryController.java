package cn.com.cms.library.controller.base;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.com.cms.base.config.BaseController;
import cn.com.cms.base.data.JsonPara;
import cn.com.cms.base.view.BaseMappingJsonView;
import cn.com.cms.framework.config.SystemConstant;
import cn.com.cms.framework.security.UserSecurityService;
import cn.com.cms.framework.tree.DefaultTreeNode;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.model.BaseLibrary;
import cn.com.cms.library.service.LibraryService;
import cn.com.cms.user.model.User;

/**
 * 数据库树控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/library")
public class LibraryController extends BaseController {

	@Resource
	private LibraryService<?> libraryService;
	@Resource
	private UserSecurityService userSecurityService;

	@RequestMapping("/find/{id}")
	public MappingJacksonJsonView find(@PathVariable("id") Integer id) {
		BaseLibrary<?> library = libraryService.find(id);
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("dataBase", library);
		return mv;
	}

	/**
	 * 获取所有库的树
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/tree")
	public MappingJacksonJsonView getAllTree(HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		User user = userSecurityService.currentUser(request);
		DefaultTreeNode root = libraryService.findTree(user, ELibraryType.values());
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("root", root);
		return mv;
	}

	/**
	 * 根据用户的数据权限获得用户可以看到的树
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/userTree")
	public MappingJacksonJsonView getTypeTree(HttpServletResponse response, HttpServletRequest request) {
		User user = userSecurityService.currentUser(request);
		DefaultTreeNode root = libraryService.findTree(user, ELibraryType.dataBases());
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("root", root);
		return mv;
	}

	/**
	 * 根据类型获取对应库的树
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/{type}/tree")
	public MappingJacksonJsonView getTree(HttpServletResponse response, @PathVariable int type) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		DefaultTreeNode root = libraryService.findTree(ELibraryType.valueOf(type));
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("root", root);
		return mv;
	}

	@RequestMapping("/{type}/alltree")
	public MappingJacksonJsonView getAllTree(HttpServletResponse response, @PathVariable int type) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		DefaultTreeNode root = libraryService.findTree(ELibraryType.valueOf(type));
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("root", root);
		return mv;
	}

	@RequestMapping("/partTree")
	public MappingJacksonJsonView findPartTreeByIds(HttpServletResponse response, @RequestBody JsonPara jsonPara) {
		String[] idStrs = jsonPara.value.split(SystemConstant.COMMA_SEPARATOR);
		Integer[] ids = new Integer[idStrs.length];
		for (int i = 0; i < idStrs.length; i++) {
			ids[i] = Integer.valueOf(idStrs[i]);
		}
		DefaultTreeNode root = libraryService.findPartTree(ids);
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("root", root);
		return mv;
	}
}
