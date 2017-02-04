package cn.com.cms.user.vo;

import cn.com.cms.user.model.User;

/**
 * 用户VO对象
 * 
 * @author shishb
 * @version 1.0
 */
public class UserVo {

	private Integer id;
	private String name;
	private String realName;
	private String groupNames;
	private String ipSegment;
	private String userTypeName;;
	private Integer hasMyLib;

	public UserVo() {
		super();
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public String getIpSegment() {
		return ipSegment;
	}

	public void setIpSegment(String ipSegment) {
		this.ipSegment = ipSegment;
	}

	/**
	 * @return the hasMyLib
	 */
	public Integer getHasMyLib() {
		return hasMyLib;
	}

	/**
	 * @param hasMyLib
	 *            the hasMyLib to set
	 */
	public void setHasMyLib(Integer hasMyLib) {
		this.hasMyLib = hasMyLib;
	}

	/**
	 * 共用的转换方法（ip,pw,ip+pw）
	 * 
	 * @param user
	 * @return
	 */
	public static UserVo convertUserVo(User user) {
		UserVo userVo = new UserVo();
		userVo.setId(user.getId());
		userVo.setName(user.getName());
		userVo.setRealName(user.getRealName());
		if (null == user.getRealName() || "".equals(user.getRealName())) {
			userVo.setRealName("无");
		}
		userVo.setGroupNames(user.getGroupNames());
		switch (user.getUserType()) {
		case 0:
			userVo.setUserTypeName("密码");
			break;
		case 1:
			userVo.setUserTypeName("IP");
			break;
		case 2:
			userVo.setUserTypeName("IP+密码");
			break;
		}
		return userVo;
	}

	// IP用户
	public static UserVo ipUserVo(User user) {
		StringBuilder ipSegment = new StringBuilder();
		UserVo userVo = new UserVo();
		userVo.setId(user.getId());
		userVo.setRealName(user.getRealName());
		userVo.setName(user.getName());
		userVo.setIpSegment(ipSegment.toString());
		userVo.setGroupNames(user.getGroupNames());
		return userVo;
	}

	// 密码用户
	public static UserVo pwUserVo(User user) {
		UserVo userVo = new UserVo();
		userVo.setId(user.getId());
		userVo.setName(user.getName());
		userVo.setRealName(user.getRealName());
		userVo.setGroupNames(user.getGroupNames());
		return userVo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGroupNames() {
		return groupNames;
	}

	public void setGroupNames(String groupNames) {
		this.groupNames = groupNames;
	}

}
