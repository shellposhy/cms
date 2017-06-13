package cn.com.cms.user.model;

import java.io.Serializable;
import java.util.List;

import cn.com.cms.framework.base.BaseEntity;
import cn.com.cms.user.constant.ELoginPageType;

/**
 * 用户组表
 * 
 * @author shishb
 * @version 1.0
 */
public class UserGroup extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 2170209631157650019L;
	// 基础属性
	private String name;
	private String code;
	private boolean allDataAuthority;
	private boolean allAdminAuthority;
	private boolean allFrontAuthority;
	private Integer secretLevel;
	private String memo;
	private ELoginPageType defaultPageType;
	private String defaultPageUrl;
	private Integer defaultPageID;

	// 后台管理权限
	private String treeSelId;
	private List<Integer> actionList;

	// 前台管理权限
	private String frontSelId;
	private List<Integer> frontActionList;

	// 读写预览下载打印权限
	private Integer[] readableIds;
	private Integer[] writableIds;
	private Integer[] viewableIds;
	private Integer[] downloadableIds;
	private Integer[] printableIds;

	public Integer[] getReadableIds() {
		return readableIds;
	}

	public void setReadableIds(Integer[] readableIds) {
		this.readableIds = readableIds;
	}

	public Integer[] getWritableIds() {
		return writableIds;
	}

	public void setWritableIds(Integer[] writableIds) {
		this.writableIds = writableIds;
	}

	public Integer[] getDownloadableIds() {
		return downloadableIds;
	}

	public void setDownloadableIds(Integer[] downloadableIds) {
		this.downloadableIds = downloadableIds;
	}

	public Integer[] getViewableIds() {
		return viewableIds;
	}

	public void setViewableIds(Integer[] viewableIds) {
		this.viewableIds = viewableIds;
	}

	public Integer[] getPrintableIds() {
		return printableIds;
	}

	public void setPrintableIds(Integer[] printableIds) {
		this.printableIds = printableIds;
	}

	public List<Integer> getActionList() {
		return actionList;
	}

	public void setActionList(List<Integer> actionList) {
		this.actionList = actionList;
	}

	public String getTreeSelId() {
		return treeSelId;
	}

	public void setTreeSelId(String treeSelId) {
		this.treeSelId = treeSelId;
	}

	public ELoginPageType getDefaultPageType() {
		return defaultPageType;
	}

	public void setDefaultPageType(ELoginPageType defaultPageType) {
		this.defaultPageType = defaultPageType;
	}

	public String getDefaultPageUrl() {
		return defaultPageUrl;
	}

	public void setDefaultPageUrl(String defaultPageUrl) {
		this.defaultPageUrl = defaultPageUrl;
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

	public boolean getAllDataAuthority() {
		return allDataAuthority;
	}

	public void setAllDataAuthority(boolean allDataAuthority) {
		this.allDataAuthority = allDataAuthority;
	}

	public boolean getAllAdminAuthority() {
		return allAdminAuthority;
	}

	public void setAllAdminAuthority(boolean allAdminAuthority) {
		this.allAdminAuthority = allAdminAuthority;
	}

	public Integer getDefaultPageID() {
		return defaultPageID;
	}

	public void setDefaultPageID(Integer defaultPageID) {
		this.defaultPageID = defaultPageID;
	}

	public Integer getSecretLevel() {
		return secretLevel;
	}

	public void setSecretLevel(Integer secretLevel) {
		this.secretLevel = secretLevel;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getFrontSelId() {
		return frontSelId;
	}

	public void setFrontSelId(String frontSelId) {
		this.frontSelId = frontSelId;
	}

	public List<Integer> getFrontActionList() {
		return frontActionList;
	}

	public void setFrontActionList(List<Integer> frontActionList) {
		this.frontActionList = frontActionList;
	}

	public boolean getAllFrontAuthority() {
		return allFrontAuthority;
	}

	public void setAllFrontAuthority(boolean allFrontAuthority) {
		this.allFrontAuthority = allFrontAuthority;
	}

}
