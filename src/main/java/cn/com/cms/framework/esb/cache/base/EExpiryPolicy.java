package cn.com.cms.framework.esb.cache.base;

public enum EExpiryPolicy {
	Accessed("accessed"), Created("created"), Eternal("eternal"), Modified("updated"), Touched("touched");
	private String title;

	private EExpiryPolicy(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

}
