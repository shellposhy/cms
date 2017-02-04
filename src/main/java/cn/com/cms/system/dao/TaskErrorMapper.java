
package cn.com.cms.system.dao;

import java.util.List;

import cn.com.cms.system.model.TaskError;

/**
 * 任务错误服务类
 * 
 * @author shishb
 * @version 1.0
 */
public interface TaskErrorMapper {
	List<TaskError> findByTaskId(Integer taskId);

	TaskError find(Integer id);

	void insert(TaskError taskError);

	void update(TaskError taskError);

	void delete(Integer id);

	void batchDelete(Integer taskId);
}
