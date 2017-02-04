package cn.com.cms.library.vo;

import cn.com.cms.data.model.DataField;

/**
 * 字段列表VO
 * 
 * @author shishb
 * @version 1.0
 */
public class DataFieldVO {
	private Integer id;
	private String name;
	private String code;
	private String codeName;
	private String oldCode;
	private String dataType;
	private String nosg;// 是否无符号
	private Integer leng;// 长度
	private Integer prec;// 精确度
	private String mand;// 是否必填
	private String uniq;// 是否唯一
	private String multiValue;// 是否多值
	private String useEnum;// 是否使用枚举
	private String indexType;
	private String indexStore;
	private String required;
	private String type;
	private String forDisplay;
	private Integer componentID;
	private String forOrder;
	private Integer orderId;
	private String accessType;
	private String memo;// 备注

	public DataFieldVO(DataField dataField) {
		this.id = dataField.getId();
		this.name = dataField.getName();
		this.code = dataField.getCode();
		this.codeName = dataField.getCodeName();
		this.dataType = null != dataField.getDataType() ? dataField.getDataType().getMysqlDataType() : "";
		this.nosg = dataField.isNosg() ? "是" : "否";
		this.leng = dataField.getLeng();
		this.prec = dataField.getPrec();
		this.mand = dataField.isMand() ? "是" : "否";
		this.uniq = dataField.isUniq() ? "是" : "否";
		this.multiValue = dataField.isMultiValue() ? "是" : "否";
		this.useEnum = dataField.isUseEnum() ? "是" : "否";
		this.indexType = null != dataField.getIndexType() ? dataField.getIndexType().getTitle() : "";
		this.required = dataField.isRequired() ? "是" : "否";
		this.type = null != dataField.getType() ? dataField.getType().getTitle() : "";
		this.forDisplay = dataField.isForDisplay() ? "是" : "否";
		this.forOrder = dataField.isForOrder() ? "是" : "否";
		this.accessType = null != dataField.getAccessType() ? dataField.getAccessType().getTitle() : "";
		this.memo = dataField.getMemo();
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getNosg() {
		return nosg;
	}

	public void setNosg(String nosg) {
		this.nosg = nosg;
	}

	public Integer getLeng() {
		return leng;
	}

	public void setLeng(Integer leng) {
		this.leng = leng;
	}

	public Integer getPrec() {
		return prec;
	}

	public void setPrec(Integer prec) {
		this.prec = prec;
	}

	public String getMand() {
		return mand;
	}

	public void setMand(String mand) {
		this.mand = mand;
	}

	public String getUniq() {
		return uniq;
	}

	public void setUniq(String uniq) {
		this.uniq = uniq;
	}

	public String getMultiValue() {
		return multiValue;
	}

	public void setMultiValue(String multiValue) {
		this.multiValue = multiValue;
	}

	public String getUseEnum() {
		return useEnum;
	}

	public void setUseEnum(String useEnum) {
		this.useEnum = useEnum;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public String getIndexStore() {
		return indexStore;
	}

	public void setIndexStore(String indexStore) {
		this.indexStore = indexStore;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getForDisplay() {
		return forDisplay;
	}

	public void setForDisplay(String forDisplay) {
		this.forDisplay = forDisplay;
	}

	public Integer getComponentID() {
		return componentID;
	}

	public void setComponentID(Integer componentID) {
		this.componentID = componentID;
	}

	public String getForOrder() {
		return forOrder;
	}

	public void setForOrder(String forOrder) {
		this.forOrder = forOrder;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
