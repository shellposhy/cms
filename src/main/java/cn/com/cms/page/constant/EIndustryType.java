package cn.com.cms.page.constant;

/**
 * 行业分类枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum EIndustryType {
	Com("企业（国有企业/私营企业/外资企业/合资企业）"), Estate("房地产/建筑/建材/工程"), Public("国家机关/党群组织/事业单位"), Edu("教育/培训/科研/院校"), Media(
			"媒体/出版/文化传播"), Finance("金融业（银行/投资/基金/证券/保险）"), Other("其他");
	private String value;

	public static EIndustryType valuesOf(int index) {
		return EIndustryType.values()[index];
	}

	private EIndustryType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
