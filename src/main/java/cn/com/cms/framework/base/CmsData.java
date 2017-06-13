package cn.com.cms.framework.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.cms.framework.base.table.FieldCodes;
import cn.com.cms.library.constant.EDataStatus;

/**
 * 数据实体
 * 
 * @author Administrator
 */
public class CmsData implements Serializable {
	private static final long serialVersionUID = 2361174804559756603L;
	private Integer id;
	private Integer tableId;
	private Integer baseId;
	private EDataStatus dataStatus;
	private List<Integer> refBaseIdList;
	private Map<String, Object> lowerFieldMap;
	private String description;
	private Boolean isArtificialData;

	/**
	 * 构造
	 */
	public CmsData() {
		lowerFieldMap = new HashMap<String, Object>();
	}

	/**
	 * 放入字段和值
	 * 
	 * @param field
	 *            字段名
	 * @param value
	 *            值
	 */
	public void put(String field, Object value) {
		if (FieldCodes.BASE_ID.equalsIgnoreCase(field)) {
			this.baseId = (Integer) value;
		} else if (FieldCodes.TABLE_ID.equalsIgnoreCase(field)) {
			this.tableId = (Integer) value;
		} else if (FieldCodes.ID.equalsIgnoreCase(field)) {
			this.id = Integer.parseInt(value.toString());
		}
		lowerFieldMap.put(field.toLowerCase(), value);
	}

	/**
	 * 获取字段的值
	 * 
	 * @param field
	 *            字段名
	 * @return
	 */
	public Object get(String field) {
		return lowerFieldMap.get(field.toLowerCase());
	}

	public void setLowerFieldMap(Map<String, Object> lowerFieldMap) {
		this.lowerFieldMap = lowerFieldMap;
	}

	/**
	 * 删除字段
	 * 
	 * @param field
	 *            字段名
	 */
	public void remove(String field) {
		lowerFieldMap.remove(field.toLowerCase());
	}

	/**
	 * 获取字段名的集合set
	 * 
	 * @return
	 */
	public Set<String> getLowerFieldSet() {
		return lowerFieldMap.keySet();
	}

	/**
	 * 获取字段值的集合Collection
	 * 
	 * @return
	 */
	public Collection<Object> getValueSet() {
		return lowerFieldMap.values();
	}

	/**
	 * 获取引用库的id列表
	 * 
	 * @return
	 */
	public List<Integer> getRefBaseIdList() {
		return refBaseIdList;
	}

	/**
	 * 设置引用库的id列表
	 * 
	 * @param refBaseIdList
	 */
	public void setRefBaseIdList(List<Integer> refBaseIdList) {
		this.refBaseIdList = refBaseIdList;
	}

	/**
	 * 获取字段数量
	 * 
	 * @return
	 */
	public int fieldCount() {
		return lowerFieldMap.size();
	}

	/**
	 * 获取id
	 * 
	 * @return
	 */
	public Integer getId() {
		if (this.id == null) {
			String idStr = (String) this.get(FieldCodes.ID);
			if (idStr != null)
				this.id = Integer.valueOf(idStr);
			else
				return null;
		}
		return this.id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		lowerFieldMap.put(FieldCodes.ID.toLowerCase(), id);
		this.id = id;
	}

	/**
	 * 获取数据状态
	 * 
	 * @return
	 */
	public EDataStatus getDataStatus() {
		if (dataStatus == null) {
			Short tmp = getDataStatusValue();
			if (tmp == null) {
				return null;
			} else {
				return EDataStatus.values()[tmp];
			}
		}
		return dataStatus;
	}

	/**
	 * 获取数据状态的short值
	 * 
	 * @return
	 */
	public Short getDataStatusValue() {
		return (Short) this.get(FieldCodes.DATA_STATUS);
	}

	/**
	 * 设置数据状态
	 * 
	 * @param dataStatus
	 */
	public void setDataStatus(EDataStatus dataStatus) {
		put(FieldCodes.DATA_STATUS, (short) dataStatus.ordinal());
		this.dataStatus = dataStatus;
	}

	/**
	 * 获取描述
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public Integer getBaseId() {
		return baseId;
	}

	public void setBaseId(Integer baseId) {
		this.baseId = baseId;
	}

	public Boolean getIsArtificialData() {
		return isArtificialData;
	}

	public void setIsArtificialData(Boolean isArtificialData) {
		this.isArtificialData = isArtificialData;
	}
}
