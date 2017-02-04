package cn.com.cms.system.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务错误信息
 * 
 * @author shishb
 * @version v1.0
 */
public class TaskError implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer taskId;
	private String content;
	private Date errTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getErrTime() {
		return errTime;
	}

	public void setErrTime(Date errTime) {
		this.errTime = errTime;
	}

}
