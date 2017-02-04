package cn.com.cms.system.vo;

import cn.com.cms.system.model.Task;
import cn.com.people.data.util.DateTimeUtil;

/**
 * 任务VO
 * 
 * @author shishb
 * @version 1.0
 */
public class TaskVo {
	private Integer id;
	private String name;
	private String code;
	private String taskType;
	private String ownerId;
	private String aim;
	private int progress;
	private String subName;
	private int subProgress;
	private String context;
	private String taskStatus;
	private String createTime;
	private String updateTime;

	public static TaskVo convert(Task task) {
		TaskVo vo = new TaskVo();
		vo.setId(task.getId());
		vo.setName(task.getName());
		vo.setCode(task.getCode());
		vo.setTaskType(null != task.getTaskType() ? task.getTaskType().getTitle() : "");
		vo.setProgress(task.getProgress());
		vo.setSubName(task.getSubName());
		vo.setSubProgress(task.getSubProgress());
		vo.setContext(task.getContext());
		vo.setTaskStatus(null != task.getTaskStatus() ? task.getTaskStatus().getTitle() : "");
		vo.setCreateTime(null != task.getCreateTime() ? DateTimeUtil.format(task.getCreateTime(), "yyyy年MM月dd日") : "");
		vo.setUpdateTime(null != task.getUpdateTime() ? DateTimeUtil.format(task.getUpdateTime(), "yyyy年MM月dd日") : "");
		return vo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getAim() {
		return aim;
	}

	public void setAim(String aim) {
		this.aim = aim;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public int getSubProgress() {
		return subProgress;
	}

	public void setSubProgress(int subProgress) {
		this.subProgress = subProgress;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
