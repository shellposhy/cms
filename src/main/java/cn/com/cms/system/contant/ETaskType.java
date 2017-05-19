package cn.com.cms.system.contant;

/**
 * 任务类型枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum ETaskType {
	MODEL_EDIT("数据库模板编辑"), DB_REPAIR("数据库修复"), INDEX_ALL("生成全部索引"), DATA_COPY("数据复制/移库");
	private String title;

	private ETaskType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

}
