package cn.com.cms.page.model;

import cn.com.cms.framework.base.BaseEntity;
import cn.com.cms.page.constant.EIndustryType;

/**
 * 客户对象
 * 
 * @author shishb
 * @version 1.0
 */
public class WebUser extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String name;
	private String pass;
	private String realName;
	private String company;
	private String address;
	private EIndustryType industry;
	private String position;
	private String telphone;
	private String mobile;
	private String email;
	private String postCode;
	private String fax;
	private String memo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public EIndustryType getIndustry() {
		return industry;
	}

	public void setIndustry(EIndustryType industry) {
		this.industry = industry;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
