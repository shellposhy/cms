package cn.com.cms.framework.esb.jms.listener;

import javax.annotation.Resource;
import javax.jms.MessageListener;

import cn.com.cms.system.model.Task;
import cn.com.people.data.util.DateTimeUtil;

/**
 * 任务消息侦听器
 * 
 * @author shishb
 * @version 1.0
 */
public abstract class DataCopyListener implements MessageListener {
	@Resource
	private TaskListener taskService;

	protected void updateTaskStatus(Task task) {
		task.setUpdateTime(DateTimeUtil.getCurrentDateTime());
		taskService.updateTaskStatus(task);
	}

	protected void updateTaskSubProgress(Integer taskId, String subName, Short subProgress) {
		taskService.updateTaskSubProgress(taskId, subName, subProgress);
	}

	protected Task findTask(Integer taskId) {
		return taskService.find(taskId);
	}

}