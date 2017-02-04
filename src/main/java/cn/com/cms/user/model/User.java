package cn.com.cms.user.model;

import java.util.List;

import cn.com.cms.framework.base.BaseEntity;
import cn.com.cms.library.constant.EStatus;
import cn.com.cms.user.constant.EGender;

/**
 * 系统用户
 * 
 * @author shishb
 * @version 1.0
 */
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private int userType;
	private String name;
	private String realName;
	private String dieName;
	private String password;
	private EGender sex;
	private String ipAddress;
	private Integer orderId;
	private Integer orgID;
	private String startIp;
	private String endIp;
	private String idCardNumber;
	private String position;
	private String phoneNumber;
	private String email;
	private String pic;
	private EStatus status;
	private boolean forSys;

	// 扩展属性
	private String groupNames;
	private List<UserGroup> userGroupList;
	private String treeSelId;
	private String oldPassword;

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
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

	public String getDieName() {
		return dieName;
	}

	public void setDieName(String dieName) {
		this.dieName = dieName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EGender getSex() {
		return sex;
	}

	public void setSex(EGender sex) {
		this.sex = sex;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getOrgID() {
		return orgID;
	}

	public void setOrgID(Integer orgID) {
		this.orgID = orgID;
	}

	public String getStartIp() {
		return startIp;
	}

	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}

	public String getEndIp() {
		return endIp;
	}

	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public EStatus getStatus() {
		return status;
	}

	public void setStatus(EStatus status) {
		this.status = status;
	}

	public boolean isForSys() {
		return forSys;
	}

	public void setForSys(boolean forSys) {
		this.forSys = forSys;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getGroupNames() {
		return groupNames;
	}

	public void setGroupNames(String groupNames) {
		this.groupNames = groupNames;
	}

	public List<UserGroup> getUserGroupList() {
		return userGroupList;
	}

	public void setUserGroupList(List<UserGroup> userGroupList) {
		this.userGroupList = userGroupList;
	}

	public String getTreeSelId() {
		return treeSelId;
	}

	public void setTreeSelId(String treeSelId) {
		this.treeSelId = treeSelId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}
