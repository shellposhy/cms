package cn.com.cms.library.constant;

/**
 * 数据库字段索引类别
 * 
 * @author shishb
 * @version 1.0
 */
public enum EIndexType {
	Analyzed("分析"), AnalyzedNoNorms("分析不打分"), No("不索引"), NotAnalyzed("不分析"), NotAnalyzedNoNorms("不分析不打分");
	private String title;

	public String getTitle() {
		return title;
	}

	private EIndexType(String title) {
		this.title = title;
	}
}
