package cn.com.cms.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.com.cms.base.AppConfig;
import cn.com.cms.base.BaseController;
import cn.com.cms.common.BaseMappingJsonView;
import cn.com.cms.common.DataTablesVo;
import cn.com.cms.common.JsonPara;
import cn.com.cms.framework.base.BaseLog;
import cn.com.cms.framework.base.Result;
import cn.com.cms.system.service.LogFileService;
import cn.com.cms.system.vo.LogVo;
import cn.com.people.data.util.DateTimeUtil;

/**
 * 日志文件处理类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/log")
public class LogController extends BaseController {
	private static Logger log = Logger.getLogger(LogController.class.getName());
	@Resource
	private LogFileService logFileService;
	@Resource
	private AppConfig appConfig;

	@RequestMapping(value = "/{type}/list", method = RequestMethod.GET)
	public String list(@PathVariable("type") int type) {
		log.debug("=====log.file.list=========");
		if (type == 1) {
			return "/admin/log/admin/list";
		} else {
			return "/admin/log/web/list";
		}
	}

	@RequestMapping(value = "/{type}/s", method = RequestMethod.POST)
	public MappingJacksonJsonView search(@PathVariable(value = "type") int type, @RequestBody JsonPara[] jsonParas) {
		log.debug("=====log.file.search=========");
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		Map<String, String> paraMap = JsonPara.getParaMap(jsonParas);
		int firstResult = 0;
		int sEcho = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.sEcho));
		int pageSize = appConfig.getAdminDataTablePageSize();
		Date date = DateTimeUtil.parseShort(paraMap.get(JsonPara.DataTablesParaNames.day));
		Integer iDisplayStart = Integer.parseInt(paraMap.get(JsonPara.DataTablesParaNames.iDisplayStart));
		if (iDisplayStart != null) {
			firstResult = iDisplayStart;
		}
		String queryStr = paraMap.get(JsonPara.DataTablesParaNames.sSearch);
		if (("").equals(queryStr)) {
			queryStr = null;
		}
		Result<BaseLog> result = (Result<BaseLog>) logFileService.searchLog(type, date, date, queryStr, firstResult,
				pageSize, true);
		if (result != null) {
			int totalCount = result.getTotalCount();
			List<LogVo> logVoList = LogVo.changeVoList(result.getList(), type);
			DataTablesVo<LogVo> dataTablesVo = new DataTablesVo<LogVo>(sEcho, totalCount, totalCount, logVoList);
			mv.addStaticAttribute("dataTablesVo", dataTablesVo);
		} else {
			List<LogVo> logVoList = new ArrayList<LogVo>();
			DataTablesVo<LogVo> dataTablesVo = new DataTablesVo<LogVo>(sEcho, 0, 0, logVoList);
			mv.addStaticAttribute("dataTablesVo", dataTablesVo);
		}
		return mv;
	}
}
