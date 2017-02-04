package cn.com.cms.common;

/**
 * 系统静态常量类
 * 
 * @author shishb
 * @version 1.0
 */
public class SystemConstant {

	/**
	 * 属于系统数据
	 */
	public static final boolean IS_SYS_DATA = true;

	/**
	 * 属于用户数据库
	 */
	public static final boolean IS_USER_DATA = false;

	/** 分隔符-▓ **/
	public static final String SEPARATOR = "▓";

	/**
	 * 用于分割字符串
	 */
	public static final String COMMA_SEPARATOR = ",";

	/**
	 * 空格分割字符串
	 */
	public static final String SPACE_SEPARATOR = " ";

	/**
	 * 密码用户
	 */
	public static final int PWD_USER = 0;

	/**
	 * ip用户
	 */
	public static final int IP_USER = 1;

	/**
	 * IP+pw用户
	 * 
	 */
	public static final int PW_IP_USER = 2;

	/**
	 * 首页图片连播图片上限
	 */
	public static final int MAX_INDEX_IMG = 5;

	/**
	 * 无限管理权限
	 */
	public static final boolean ALL_ADMIN_VOTE_YES = true;
	public static final boolean ALL_ADMIN_VOTE_NO = false;
	/**
	 * 无限数据权限
	 */
	public static final boolean ALL_DATA_VOTE_YES = true;
	public static final boolean ALL_DATA_VOTE_NO = false;

	/**
	 * 无限前权限
	 */
	public static final boolean ALL_Front_VOTE_YES = true;
	public static final boolean ALL_Front_VOTE_NO = false;

	/**
	 * 权限验证过滤相关
	 */
	public static final String SA_USER = "sa";
	public static final String GREEN_LIGHT = "GREEN_LIGHT";
	public static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

	/**
	 * 数据库索引服务实例名称
	 */
	public static final String PDS3_DB_INDEX_SERVICE = "PDS3_DB_INDEX_SERVICE";

	/**
	 * 收藏夹索引服务实例名称
	 */
	public static final String PDS3_FAV_INDEX_SERVICE = "PDS3_FAV_INDEX_SERVICE";
	public static final String INDEX_DATE_FORMAT = "yyyyMMddHHmmss";
	public static final String PDS_IP_USER = "PDS_IP_USER";
	public static final String PDS_PHONE_USER = "PDS_PHONE_USER";
	public static final String PDS_IP_USER_PASSWORD = "IPUSERPASSWORD";
	public static final String PDS_PHONE_USER_PASSWORD = "PHONEUSERPASSWORD";

	/**
	 * Session中当前用户的KEY
	 */
	public static final String CURRENT_USER = "currentUser";

	/**
	 * 高亮前缀
	 */
	public static final String HIGHT_LIGHT_PRE_TAG = "<span class=\"highLight\">";

	/**
	 * 高亮后缀
	 */
	public static final String HIGHT_LIGHT_POST_TAG = "</span>";

	/**
	 * 保存查询-处理时间字段时使用
	 */
	public static final String VAR_DATE_TIME_TODAT_START = "<Var:DateTime_Today_S>";
	public static final String VAR_DATE_TIME_TODAT_END = "<Var:DateTime_Today_E>";
	public static final String VAR_DATE_TIME_MONTH_START = "<Var:DateTime_Month_S>";
	public static final String VAR_DATE_TIME_MONTH_END = "<Var:DateTime_Month_E>";

}
