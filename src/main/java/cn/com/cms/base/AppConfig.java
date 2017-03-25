package cn.com.cms.base;

import java.util.List;

/**
 * 项目初始化配置类
 * 
 * @author shishb
 */
public class AppConfig {
	private String appName;
	private String appPath;
	private String appPathHome;
	private int appLogSwitch;
	private String templatePath;
	private String templateHome;
	private int adminDataTablePageSize;
	private int adminDataTablePageMinSize;
	private int dataTableMaxRows;
	private int defaultIndexSearchNumHits;
	private int defaultWebIndexSearchNumHits;
	private int summaryLength;
	private int theCountPerTimeByRepairDataBase;
	private List<String> excludeUrls;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppPath() {
		return appPath;
	}

	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}

	public String getAppPathHome() {
		return appPathHome;
	}

	public void setAppPathHome(String appPathHome) {
		this.appPathHome = appPathHome;
	}

	public int getAppLogSwitch() {
		return appLogSwitch;
	}

	public void setAppLogSwitch(int appLogSwitch) {
		this.appLogSwitch = appLogSwitch;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getTemplateHome() {
		return templateHome;
	}

	public void setTemplateHome(String templateHome) {
		this.templateHome = templateHome;
	}

	public int getAdminDataTablePageSize() {
		return adminDataTablePageSize;
	}

	public void setAdminDataTablePageSize(int adminDataTablePageSize) {
		this.adminDataTablePageSize = adminDataTablePageSize;
	}

	public int getAdminDataTablePageMinSize() {
		return adminDataTablePageMinSize;
	}

	public void setAdminDataTablePageMinSize(int adminDataTablePageMinSize) {
		this.adminDataTablePageMinSize = adminDataTablePageMinSize;
	}

	public int getDataTableMaxRows() {
		return dataTableMaxRows;
	}

	public void setDataTableMaxRows(int dataTableMaxRows) {
		this.dataTableMaxRows = dataTableMaxRows;
	}

	public int getDefaultIndexSearchNumHits() {
		return defaultIndexSearchNumHits;
	}

	public void setDefaultIndexSearchNumHits(int defaultIndexSearchNumHits) {
		this.defaultIndexSearchNumHits = defaultIndexSearchNumHits;
	}

	public int getDefaultWebIndexSearchNumHits() {
		return defaultWebIndexSearchNumHits;
	}

	public void setDefaultWebIndexSearchNumHits(int defaultWebIndexSearchNumHits) {
		this.defaultWebIndexSearchNumHits = defaultWebIndexSearchNumHits;
	}

	public int getSummaryLength() {
		return summaryLength;
	}

	public void setSummaryLength(int summaryLength) {
		this.summaryLength = summaryLength;
	}

	public int getTheCountPerTimeByRepairDataBase() {
		return theCountPerTimeByRepairDataBase;
	}

	public void setTheCountPerTimeByRepairDataBase(int theCountPerTimeByRepairDataBase) {
		this.theCountPerTimeByRepairDataBase = theCountPerTimeByRepairDataBase;
	}

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

}
