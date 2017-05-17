package cn.com.cms.system.service;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import cn.com.cms.framework.base.TaskMessage;
import cn.com.cms.system.dao.TaskErrorMapper;
import cn.com.cms.system.dao.TaskMapper;
import cn.com.cms.system.model.Task;
import cn.com.cms.system.model.TaskError;

/**
 * 任务服务类
 * 
 * @author shsihb
 * @version 1.0
 */
public class TaskService implements MessageListener {
	@Resource
	private JmsTemplate jmsTemplate;
	@Resource
	private ApplicationContext applicationContext;
	@Resource
	private TaskErrorMapper taskErrorMapper;
	@Resource
	private TaskMapper taskMapper;
	private Queue queue;
	private static final String MB_TARGET = "MB_TARGET";

	/**
	 * 增加JMS任务
	 * 
	 * @param taskMessage
	 * @return
	 */
	public void addTask(final TaskMessage taskMessage) {
		taskMapper.insert(taskMessage.getTask());
		jmsTemplate.send(queue, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage(taskMessage);
				message.setStringProperty(MB_TARGET, taskMessage.getTarget());
				return message;
			}
		});
	}

	/**
	 * 更新任务
	 * 
	 * @param task
	 * @return
	 */
	public void updateTaskStatus(Task task) {
		taskMapper.update(task);
		if (task.getErrorList() != null) {
			for (TaskError error : task.getErrorList()) {
				taskErrorMapper.insert(error);
			}
		}
	}

	/**
	 * 保存任务
	 * 
	 * @return
	 */
	public void save(Task task) {
		if (null != task.getId()) {
			taskMapper.update(task);
		} else {
			taskMapper.insert(task);
		}
	}

	/**
	 * 更新任务状态
	 * 
	 * @param id
	 * @param subName
	 * @param subProgress
	 * @return
	 */
	public void updateTaskSubProgress(Integer id, String subName, int subProgress) {
		taskMapper.updateSubProgress(id, subName, subProgress);
	}

	/**
	 * 删除任务
	 * 
	 * @param id
	 * @return
	 */
	public void deleteTask(Integer id) {
		taskErrorMapper.batchDelete(id);
		taskMapper.delete(id);
	}

	/**
	 * JMS消息发送
	 * 
	 * @param message
	 */
	public void onMessage(Message message) {
		try {
			String property = message.getStringProperty(MB_TARGET);
			if (null != property) {
				MessageListener mb = (MessageListener) applicationContext.getBean(property);
				if (mb != null) {
					mb.onMessage(message);
				}
			}
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	public Task find(Integer id) {
		return taskMapper.find(id);
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}
}
