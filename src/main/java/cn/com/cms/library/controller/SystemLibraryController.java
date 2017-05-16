package cn.com.cms.library.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.com.cms.base.data.JsonPara;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.controller.base.BaseLibraryController;
import cn.com.cms.library.model.DataBase;

/**
 * 系统数据控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/system/library")
public class SystemLibraryController extends BaseLibraryController<DataBase> {

	protected ELibraryType getLibType() {
		return ELibraryType.SYSTEM_DATA_BASE;
	}

	@RequestMapping
	public String list(Model model) {
		return super.list(model);
	}

	@RequestMapping(value = "/tree")
	public MappingJacksonJsonView tree() {
		return super.tree();
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public MappingJacksonJsonView search(@RequestBody JsonPara[] jsonParas) {
		return super.search(jsonParas);
	}

	@RequestMapping("/find/{directoryId}")
	public MappingJacksonJsonView find(@PathVariable("directoryId") Integer directoryId, HttpServletRequest request) {
		return super.find(directoryId, request);
	}

	@RequestMapping("/new/{directoryId}")
	public String preNew(@PathVariable("directoryId") Integer directoryId, Model model) {
		return super.preNew(directoryId, model, DataBase.class);
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		return super.edit(id, model);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Valid final DataBase library, BindingResult result, Model model, HttpServletRequest request) {
		return super.save(library, result, model, request);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public String delete(@PathVariable("id") Integer id) {
		return super.delete(id);
	}

	@RequestMapping(value = "/displayFields/{modelId}")
	public MappingJacksonJsonView getDisplayFields(@PathVariable("modelId") Integer modelId) {
		return super.getDisplayFields(modelId);
	}
}
