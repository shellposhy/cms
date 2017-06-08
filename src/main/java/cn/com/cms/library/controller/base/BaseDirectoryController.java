package cn.com.cms.library.controller.base;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.com.cms.base.config.BaseController;
import cn.com.cms.base.config.ControllerOperator;
import cn.com.cms.base.view.BaseMappingJsonView;
import cn.com.cms.framework.base.tree.DefaultTreeNode;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.model.BaseLibrary;
import cn.com.cms.library.service.LibraryDirectoryService;
import cn.com.cms.user.model.User;
import cn.com.cms.user.service.UserSecurityService;

/**
 * 库（Library）目录管理的基类
 * 
 * @author shishb
 * @version 1.0
 */
public class BaseDirectoryController<T extends BaseLibrary<T>> extends BaseController {

	protected ELibraryType getLibType() {
		return ELibraryType.SYSTEM_DATA_BASE;
	}

	protected final String URL_PREFIX = "/admin/library/";

	protected Logger LOG = Logger.getLogger(BaseDirectoryController.class.getName());

	@Resource
	private LibraryDirectoryService<T> libraryDirectoryService;
	@Resource
	private UserSecurityService userSecurityService;

	/**
	 * 获取目录树
	 * 
	 * @return DefaultTreeNode
	 */
	protected MappingJacksonJsonView getDirectoryTree() {
		DefaultTreeNode directoryTree = libraryDirectoryService.findTree(getLibType());
		directoryTree.name = "根分类";
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("directoryTree", directoryTree);
		return mv;
	}

	/**
	 * 获取空目录树，用于添加目录的时候选择父级目录
	 * 
	 * @return
	 */
	protected MappingJacksonJsonView getEmptyDirectoryTree() {
		DefaultTreeNode directoryTree = libraryDirectoryService.findEmptyDirectoryTree(getLibType());
		directoryTree.id = 0;
		directoryTree.name = "根目录";
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("directoryTree", directoryTree);
		return mv;
	}

	/**
	 * 添加目录
	 * 
	 * @param parentId
	 *            父目录ID
	 * @param model
	 * @return
	 */
	protected String preNewDirectory(int parentId, Model model, Class<T> clazz) {
		try {
			T directory = clazz.newInstance();
			directory.setParentID(parentId);
			directory.setType(getLibType());
			model.addAttribute(directory);
		} catch (InstantiationException e) {
			LOG.error(e);
		} catch (IllegalAccessException e) {
			LOG.error(e);
		}
		return URL_PREFIX + getLibType().getCode() + "/directory/edit";
	}

	/**
	 * 保存目录
	 * 
	 * @param directory
	 * @param result
	 * @param model
	 * @return
	 */
	protected String saveDirectory(final T directory, BindingResult result, final Model model,
			final HttpServletRequest request) {
		final User currentUser = userSecurityService.currentUser(request);
		if (directory.getId() == null) {
			directory.setCreatorId(currentUser.getId());
			directory.setCreateTime(new Date());
			directory.setModelId(null == directory.getModelId() ? 0 : directory.getModelId());
			directory.setDataUpdateTime(new Date());
		}
		directory.setUpdaterId(currentUser.getId());
		directory.setUpdateTime(new Date());
		return super.save(directory, result, model, new ControllerOperator() {
			public void operate() {
				libraryDirectoryService.save(directory);
			}

			public String getSuccessView() {
				return "redirect:/admin/system/library";
			}

			public String getFailureView() {
				model.addAttribute(directory);
				return URL_PREFIX + getLibType().getCode() + "/directory/edit";
			}

			public void onFailure() {
			}

		});
	}

	/**
	 * 编辑目录
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	protected String editDirectory(Integer id, Model model) {
		T directory = libraryDirectoryService.find(id);
		directory.setType(getLibType());
		model.addAttribute(directory);
		return URL_PREFIX + getLibType().getCode() + "/directory/edit";
	}

	/**
	 * 删除目录
	 * 
	 * @param id
	 * @return
	 */
	protected MappingJacksonJsonView deleteDirectory(Integer id) {
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		if (libraryDirectoryService.hasChildren(id)) {
			mv.addStaticAttribute("error", true);
			mv.addStaticAttribute("msg", "该分类下有子分类或者数据库，无法删除！");
		} else {
			libraryDirectoryService.delete(id);
			mv.addStaticAttribute("error", false);
		}
		return mv;
	}
}
