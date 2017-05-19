
package cn.com.cms.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.cms.system.contant.ETaskStatus;
import cn.com.cms.system.contant.ETaskType;

/**
 * 任务对象
 * 
 * @author shishb
 * @version 1.0
 * 
 */
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String code;
	private ETaskType taskType;
	private Integer ownerId;
	private String aim;
	private int progress;
	private Integer subId;
	private String subName;
	private int subProgress;
	private String context;
	private ETaskStatus taskStatus;
	private Integer modelId;
	private Integer baseId;
	private Integer tableId;
	private Integer dataId;
	private Date createTime;
	private Date updateTime;
	private List<TaskError> errorList;

	public Task() {
	}

	public Task(Integer id, String name, String code, ETaskType taskType, Integer ownerId, String aim, int progress,
			Integer subId, String subName, int subProgress, String context, ETaskStatus taskStatus, Integer modelId,
			Integer baseId, Integer tableId, Integer dataId, Date createTime, Date updateTime) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.taskType = taskType;
		this.ownerId = ownerId;
		this.aim = aim;
		this.progress = progress;
		this.subId = subId;
		this.subName = subName;
		this.subProgress = subProgress;
		this.context = context;
		this.taskStatus = taskStatus;
		this.modelId = modelId;
		this.baseId = baseId;
		this.tableId = tableId;
		this.dataId = dataId;
		this.createTime = createTime;
		this.updateTime = updateTime;
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

	public ETaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(ETaskType taskType) {
		this.taskType = taskType;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
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

	public Integer getSubId() {
		return subId;
	}

	public void setSubId(Integer subId) {
		this.subId = subId;
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

	public ETaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(ETaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public Integer getBaseId() {
		return baseId;
	}

	public void setBaseId(Integer baseId) {
		this.baseId = baseId;
	}

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<TaskError> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<TaskError> errorList) {
		this.errorList = errorList;
	}

	public void addErrorList(TaskError error) {
		if (null == errorList) {
			errorList = new ArrayList<TaskError>();
		}
		this.errorList.add(error);
	}

}
