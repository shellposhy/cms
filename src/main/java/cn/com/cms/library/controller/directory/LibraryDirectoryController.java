package cn.com.cms.library.controller.directory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.controller.base.BaseDirectoryController;
import cn.com.cms.library.model.DataBase;

/**
 * 系统数据目录控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/system/library/directory")
public class LibraryDirectoryController extends BaseDirectoryController<DataBase> {
	protected ELibraryType getLibType() {
		return ELibraryType.SYSTEM_DATA_BASE;
	}

	@RequestMapping("/tree")
	public MappingJacksonJsonView getDirectoryTree() {
		return super.getDirectoryTree();
	}

	@RequestMapping("/emptyTree")
	public MappingJacksonJsonView getEmptyDirectoryTree() {
		return super.getEmptyDirectoryTree();
	}

	@RequestMapping("/new/{parentId}")
	public String preNewDirectory(@PathVariable("parentId") int parentId, Model model) {
		return super.preNewDirectory(parentId, model, DataBase.class);
	}

	@RequestMapping("/{id}/edit")
	public String editDirectory(@PathVariable("id") int id, Model model) {
		return super.editDirectory(id, model);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveDirectory(@Valid DataBase directory, BindingResult result, final Model model,
			final HttpServletRequest request) {
		return super.saveDirectory(directory, result, model, request);
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public MappingJacksonJsonView deleteDirectory(@PathVariable("id") Integer id) {
		return super.deleteDirectory(id);
	}
}
