package cn.com.cms.library.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.com.cms.common.JsonPara;
import cn.com.cms.data.model.DataField;
import cn.com.cms.data.util.DataVO;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.controller.base.BaseDataController;
import cn.com.cms.library.model.DataBase;

/**
 * 系统数据处理控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/system/library/data")
public class SystemDataController extends BaseDataController<DataBase> {

	protected ELibraryType getLibType() {
		return ELibraryType.SYSTEM_DATA_BASE;
	}

	@RequestMapping("/{libraryId}")
	public String list(@PathVariable int libraryId, Model model) {
		return super.list(libraryId, model);
	}

	@RequestMapping(value = "/search/{libraryId}", method = RequestMethod.POST)
	public MappingJacksonJsonView search(@PathVariable int libraryId, @RequestBody JsonPara[] jsonParas) {
		return super.search(libraryId, jsonParas);
	}

	@RequestMapping("/new/{libraryId}")
	public String preNew(@PathVariable Integer libraryId, Model model) {
		return super.preNew(libraryId, model);
	}

	@RequestMapping("/tablehead/{libraryId}")
	@ResponseBody
	public List<DataField> getTableHeadFields(@PathVariable int libraryId) {
		return super.getTableHeadFields(libraryId);
	}

	@RequestMapping(value = "/save/{libraryId}", method = RequestMethod.POST)
	@ResponseBody
	public String save(HttpServletRequest request, @PathVariable("libraryId") int baseId, @Valid DataVO data,
			BindingResult result, Model model) {
		return super.save(request, baseId, data, result, model);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public void delete(@RequestBody JsonPara jsonpara) {
		super.delete(jsonpara);
	}

	@RequestMapping(value = "/edit/{tableId}/{dataId}", method = RequestMethod.GET)
	public String edit(@PathVariable int tableId, @PathVariable int dataId, HttpServletRequest request, Model model) {
		return super.edit(tableId, dataId, request, model);
	}

	@RequestMapping("/info/{tableId}/{dataId}")
	public String info(@PathVariable int tableId, @PathVariable int dataId, HttpServletRequest request, Model model) {
		return super.info(tableId, dataId, request, model);
	}
}
