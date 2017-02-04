package cn.com.cms.system.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.framework.base.Result;
import cn.com.cms.system.dao.TaskErrorMapper;
import cn.com.cms.system.dao.TaskMapper;
import cn.com.cms.system.model.Task;

/**
 * 任务服务类
 * 
 * @author shsihb
 * @version 1.0
 */
@Service
public class TaskService {
	@Resource
	private TaskErrorMapper taskErrorMapper;
	@Resource
	private TaskMapper taskMapper;

	/**
	 * 分页查询
	 * 
	 * @param name
	 * @param first
	 * @param size
	 * @return
	 */
	public Result<Task> search(String name, int first, int size) {
		Result<Task> result = new Result<Task>(taskMapper.search(name, first, size), taskMapper.count(name));
		return result;
	}

	/**
	 * 新增数据
	 * 
	 * @param task
	 * @return
	 */
	public void save(Task task) {
		if (null != task) {
			if (null != task.getId()) {
				taskMapper.update(task);
			} else {
				taskMapper.insert(task);
			}
		}
	}

}
