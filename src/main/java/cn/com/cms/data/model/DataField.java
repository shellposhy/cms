package cn.com.cms.data.model;

import cn.com.cms.framework.base.BaseEntity;
import cn.com.cms.library.constant.EAccessType;
import cn.com.cms.library.constant.EDataFieldType;
import cn.com.cms.library.constant.EDataType;
import cn.com.cms.library.constant.EIndexType;

/**
 * 数据字段表
 * 
 * @author shishb
 * @version 1.0
 */
public class DataField extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String name;
	private String code;
	private String codeName;
	private String oldCode;
	private EDataType dataType;
	private boolean nosg;// 是否无符号
	private Integer leng;// 长度
	private Integer prec;// 精确度
	private boolean mand;// 是否必填
	private boolean uniq;// 是否唯一
	private boolean multiValue;// 是否多值
	private boolean useEnum;// 是否使用枚举
	private EIndexType indexType;
	private boolean indexStore;
	private boolean required;
	private EDataFieldType type;
	private boolean forDisplay;
	private Integer componentID;
	private boolean forOrder;
	private Integer orderId;
	private EAccessType accessType;
	private String memo;// 备注

	public DataField() {
	}

	public DataField(String code, EDataType dataType, EIndexType indexType, boolean indexStore) {
		super();
		this.code = code;
		this.dataType = dataType;
		this.indexType = indexType;
		this.indexStore = indexStore;
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		StringBuilder fullName = new StringBuilder();
		fullName.append(name).append(" | ").append(dataType.mysqlDataType);

		switch (this.dataType) {
		case Date:
		case Time:
		case DateTime:
			break;
		default:
			if (!leng.equals(0))
				fullName.append("(" + leng + ")");
			break;
		}
		return fullName.toString();
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

	public Integer getComponentID() {
		return componentID;
	}

	public void setComponentID(Integer componentID) {
		this.componentID = componentID;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

	public EDataType getDataType() {
		return dataType;
	}

	public void setDataType(EDataType dataType) {
		this.dataType = dataType;
	}

	public boolean isNosg() {
		return nosg;
	}

	public void setNosg(boolean nosg) {
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

	public boolean isMand() {
		return mand;
	}

	public void setMand(boolean mand) {
		this.mand = mand;
	}

	public boolean isUniq() {
		return uniq;
	}

	public void setUniq(boolean uniq) {
		this.uniq = uniq;
	}

	public boolean isMultiValue() {
		return multiValue;
	}

	public void setMultiValue(boolean multiValue) {
		this.multiValue = multiValue;
	}

	public boolean isUseEnum() {
		return useEnum;
	}

	public void setUseEnum(boolean useEnum) {
		this.useEnum = useEnum;
	}

	public EIndexType getIndexType() {
		return indexType;
	}

	public void setIndexType(EIndexType indexType) {
		this.indexType = indexType;
	}

	public boolean isIndexStore() {
		return indexStore;
	}

	public void setIndexStore(boolean indexStore) {
		this.indexStore = indexStore;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isForOrder() {
		return forOrder;
	}

	public void setForOrder(boolean forOrder) {
		this.forOrder = forOrder;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public EAccessType getAccessType() {
		return accessType;
	}

	public void setAccessType(EAccessType accessType) {
		this.accessType = accessType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public boolean isForDisplay() {
		return forDisplay;
	}

	public void setForDisplay(boolean forDisplay) {
		this.forDisplay = forDisplay;
	}

	public EDataFieldType getType() {
		return type;
	}

	public void setType(EDataFieldType type) {
		this.type = type;
	}

}
