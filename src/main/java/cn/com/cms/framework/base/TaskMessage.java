package cn.com.cms.framework.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.com.cms.system.model.Task;

/**
 * 系统任务消息队列
 * 
 * @author shishb
 * @version 1.0
 */
public class TaskMessage implements Serializable {
	private static final long serialVersionUID = -8894563646629418585L;
	private String target;
	private Task task;
	private Map<String, Object> paraMap;

	public TaskMessage() {
		paraMap = new HashMap<String, Object>();
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void put(String name, Object value) {
		paraMap.put(name, value);
	}

	public Object get(String name) {
		return paraMap.get(name);
	}

	public Set<String> getNames() {
		return paraMap.keySet();
	}
}
