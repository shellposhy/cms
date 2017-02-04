package cn.com.cms.user.constant;

public enum EActionType {
	Read("读"), View("查看"), Write("写"), Add("添加"), Update("修改"), Delete("删除"), Download("下载"), Print("打印");
	private final String title;

	EActionType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}
}
