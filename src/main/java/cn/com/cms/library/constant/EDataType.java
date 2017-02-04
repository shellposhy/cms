package cn.com.cms.library.constant;

public enum EDataType {
	/**
	 * 0-Bool,true或false
	 */
	Bool("bool"),
	/**
	 * 1-char,固定长度字符
	 */
	Char("char"),
	/**
	 * 2-varchar，变长字符
	 */
	Varchar("varchar"),
	/**
	 * 3-short
	 */
	Short("tinyint"),
	/**
	 * 4-int
	 */
	Int("int"),
	/**
	 * 5-long
	 */
	Long("bigint"),
	/**
	 * 6-float
	 */
	Float("float"),
	/**
	 * 7-double
	 */
	Double("double"),
	/**
	 * 8-numberic
	 */
	Numeric("numeric"),
	/**
	 * 9-date日期
	 */
	Date("date"),
	/**
	 * 10-time时间
	 */
	Time("time"),
	/**
	 * 11-datetime日期时间
	 */
	DateTime("datetime"),
	/**
	 * 12-blob大字段
	 */
	Blob("blob"),
	/**
	 * 13-mediumblob中型大字段
	 */
	MediumBlob("mediumblob"),
	/**
	 * 14-int型自增
	 */
	IntAutoIncrement("int"),
	/**
	 * 15-UUID
	 */
	UUID("char(36) character set ascii");

	public String mysqlDataType;

	EDataType(String mysqlDataType) {
		this.mysqlDataType = mysqlDataType;
	}

	/**
	 * 获取该类型对应的mysql的数据类型
	 * 
	 * @return mysql的数据类型
	 */
	public String getMysqlDataType() {
		return this.mysqlDataType;
	}
}
