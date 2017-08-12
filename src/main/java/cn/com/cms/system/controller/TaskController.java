package cn.com.cms.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.com.cms.framework.base.BaseController;
import cn.com.cms.framework.base.Result;
import cn.com.cms.framework.base.table.DataTablesVo;
import cn.com.cms.framework.base.view.BaseMappingJsonView;
import cn.com.cms.framework.config.AppConfig;
import cn.com.cms.framework.config.JsonPara;
import cn.com.cms.system.dao.TaskMapper;
import cn.com.cms.system.model.Task;
import cn.com.cms.system.vo.TaskVo;
import cn.com.cms.user.service.UserService;

/**
 * 任务控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/task")
public class TaskController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(TaskController.class);
	@Resource
	private AppConfig appConfig;
	@Resource
	private TaskMapper taskMapper;
	@Resource
	private UserService userService;

	@RequestMapping("/progress/{id}")
	public MappingJacksonJsonView findProgress(@PathVariable(value = "id") Integer id) {
		log.debug("====task.process==");
		Task task = taskMapper.find(id);
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		mv.addStaticAttribute("progress", task);
		return mv;
	}

	/**
	 * 任务列表
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		log.debug("====task.list==");
		return "/admin/task/list";
	}

	/**
	 * 任务列表查询
	 * 
	 * @param jsonParas
	 * @return
	 */
	@RequestMapping(value = "/s", method = RequestMethod.POST)
	public MappingJacksonJsonView search(@RequestBody JsonPara[] jsonParas) {
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		Map<String, String> paraMap = JsonPara.getParaMap(jsonParas);
		// 页面参数
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
		// result
		Result<Task> result = new Result<Task>();
		result.setList(taskMapper.search(word, firstResult, pageSize));
		result.setTotalCount(taskMapper.count(word));
		List<TaskVo> list = new ArrayList<TaskVo>();
		if (null != result && null != result.getList() && result.getList().size() > 0) {
			for (Task task : result.getList()) {
				TaskVo vo = TaskVo.convert(task);
				list.add(vo);
			}
		}
		DataTablesVo<TaskVo> dataTablesVo = new DataTablesVo<TaskVo>(sEcho, result.getTotalCount(),
				result.getTotalCount(), list);
		mv.addStaticAttribute("dataTablesVo", dataTablesVo);
		return mv;
	}
}
