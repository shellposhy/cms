package cn.com.cms.system.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

import com.google.common.collect.Lists;

import cn.com.cms.base.AppConfig;
import cn.com.cms.base.BaseController;
import cn.com.cms.base.ControllerOperator;
import cn.com.cms.common.BaseMappingJsonView;
import cn.com.cms.common.DataTablesVo;
import cn.com.cms.common.JsonPara;
import cn.com.cms.framework.base.Result;
import cn.com.cms.system.model.SysParameter;
import cn.com.cms.system.service.SysParameterService;
import cn.com.cms.system.vo.ParamVo;

/**
 * 系统参数设置控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/param")
public class ParamController extends BaseController {
	private static Logger log = Logger.getLogger(ParamController.class.getName());
	@Resource
	private SysParameterService sysParameterService;
	@Resource
	private AppConfig appConfig;

	/**
	 * 系统参数列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		log.debug("======system.parameter.list======");
		return "/admin/param/list";
	}

	/**
	 * 新建参数
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/new")
	public String preNew(Model model) {
		log.debug("======system.parameter.new======");
		model.addAttribute("param", new SysParameter());
		return "/admin/param/edit";
	}

	/**
	 * 编辑
	 */
	@RequestMapping("/{id}/edit")
	public String edit(@PathVariable Integer id, Model model) {
		log.debug("======system.parameter.edit======");
		model.addAttribute("param", sysParameterService.find(id));
		return "/admin/param/edit";
	}

	/**
	 * 保存
	 * 
	 * @param sysParam
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String save(@Valid final SysParameter sysParam, BindingResult result, final Model model,
			HttpServletRequest request) {
		return super.save(sysParam, result, model, new ControllerOperator() {
			public void operate() {
				if (null != sysParam.getId()) {
					sysParameterService.update(sysParam);
				} else {
					sysParameterService.insert(sysParam);
				}
			}

			public void onFailure() {
			}

			public String getSuccessView() {
				return "redirect:/admin/param";
			}

			public String getFailureView() {
				return "/admin/param/"+sysParam.getId()+"/edit";
			}
		});
	}

	/**
	 * ajax调用的查询方法
	 * 
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/s", method = RequestMethod.POST)
	public MappingJacksonJsonView search(@RequestBody JsonPara[] jsonParas) {
		log.debug("======system.parameter.search======");
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		Map<String, String> paraMap = JsonPara.getParaMap(jsonParas);
		int sEcho = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.sEcho));
		Integer iDisplayStart = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.iDisplayStart));
		int firstResult = 0;
		if (null != iDisplayStart) {
			firstResult = iDisplayStart;
		}
		int pageSize = appConfig.getAdminDataTablePageSize();
		String word = paraMap.get(JsonPara.DataTablesParaNames.sSearch);
		if (word.isEmpty()) {
			word = null;
		}
		Result<SysParameter> result = sysParameterService.result(word, firstResult, pageSize);
		List<ParamVo> list = Lists.newArrayList();
		if (null != result && null != result.getList() && result.getList().size() > 0) {
			for (SysParameter parameter : result.getList()) {
				list.add(ParamVo.convert(parameter));
			}
		}
		DataTablesVo<ParamVo> dataTablesVo = new DataTablesVo<ParamVo>(sEcho, result.getTotalCount(), result.getTotalCount(),
				list);
		mv.addStaticAttribute("dataTablesVo", dataTablesVo);
		return mv;
	}
}
