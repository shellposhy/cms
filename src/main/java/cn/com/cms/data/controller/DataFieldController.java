package cn.com.cms.data.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.com.cms.base.AppConfig;
import cn.com.cms.base.BaseController;
import cn.com.cms.common.BaseMappingJsonView;
import cn.com.cms.common.DataTablesVo;
import cn.com.cms.common.JsonPara;
import cn.com.cms.data.model.DataField;
import cn.com.cms.framework.base.Result;
import cn.com.cms.library.service.DataFieldService;
import cn.com.cms.library.vo.DataFieldVO;

/**
 * 数据库字段管理控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/data/field")
public class DataFieldController extends BaseController {
	private static Logger log = Logger.getLogger(DataFieldController.class.getName());
	@Resource
	private DataFieldService dataFieldService;
	@Resource
	private AppConfig appConfig;

	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		log.debug("===data.field.list===");
		return "/admin/library/field/list";
	}

	@RequestMapping(value = "/s", method = RequestMethod.POST)
	public MappingJacksonJsonView search(@RequestBody JsonPara[] jsonParas) {
		log.debug("=====data.field.search======");
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		Map<String, String> paraMap = JsonPara.getParaMap(jsonParas);
		Integer iDisplayStart = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.iDisplayStart));
		int sEcho = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.sEcho));
		String word = paraMap.get(JsonPara.DataTablesParaNames.sSearch);
		word = null != word && word.trim().length() > 0 ? word : null;
		int firstResult = null != iDisplayStart ? iDisplayStart : 0;
		int pageSize = appConfig.getAdminDataTablePageSize();
		Result<DataField> result = dataFieldService.findByPage(word, firstResult, pageSize);
		// 组织数据对象
		List<DataFieldVO> dataFieldList = new ArrayList<DataFieldVO>();
		if (null != result && null != result.getList() && result.getList().size() > 0) {
			for (DataField dataField : result.getList()) {
				DataFieldVO vo = new DataFieldVO(dataField);
				dataFieldList.add(vo);
			}
		}
		DataTablesVo<DataFieldVO> dataTablesVo = new DataTablesVo<DataFieldVO>(sEcho, result.getTotalCount(),
				result.getTotalCount(), dataFieldList);
		mv.addStaticAttribute("dataTablesVo", dataTablesVo);
		return mv;
	}
}
