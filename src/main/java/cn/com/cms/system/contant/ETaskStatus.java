package cn.com.cms.system.contant;

/**
 * 任务状态
 * 
 * @author shishb
 * @version 1.0
 */
public enum ETaskStatus {
	Preparing("准备执行"), Executing("正在执行"), Break("中断"), Finish("完成"), Rollbacked("已回滚");
	private String title;

	private ETaskStatus(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}