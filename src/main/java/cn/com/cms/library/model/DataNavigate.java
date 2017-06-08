package cn.com.cms.library.model;

import cn.com.cms.framework.base.tree.TreeNodeEntity;
import cn.com.cms.library.constant.EDataNavigateType;
import cn.com.cms.library.constant.ELibraryNodeType;

/**
 * 数据导航类
 * 
 * 包含数据库、数据库分类、数据标签三种类型
 * 
 * @author shishb
 * @see TreeEntity
 * @version 1.0
 */
public class DataNavigate extends TreeNodeEntity<DataNavigate> {
	private static final long serialVersionUID = 1L;
	private Integer realId;
	private Integer baseId;
	private String code;
	private String pathCode;
	private EDataNavigateType type;

	public DataNavigate() {
	}

	public DataNavigate(BaseLibrary<?> library) {
		this.id = library.getId();
		this.realId = library.getId();
		this.name = library.getName();
		this.parentID = library.getParentID();
		this.code = library.getCode();
		this.pathCode = library.getPathCode();
		if (ELibraryNodeType.Directory.equals(library.getNodeType())) {
			this.type = EDataNavigateType.DATA_CATEGORY;
		} else {
			this.type = EDataNavigateType.DATA_BASE;
		}
	}

	/**
	 * 利用标签的ID与数据库的最大ID相加解决两个ID可能重复的问题
	 * 
	 * @param dataSort
	 * @param maxDbId
	 */
	public DataNavigate(DataSort dataSort, int maxDbId) {
		this.id = maxDbId + dataSort.getId();
		this.realId = dataSort.getId();
		this.baseId = dataSort.getBaseId();
		this.name = dataSort.getName();
		if (dataSort.getParentID() == 0) {
			this.parentID = dataSort.getBaseId();
		} else {
			this.parentID = maxDbId + dataSort.getParentID();
		}
		this.code = dataSort.getCode();
		this.pathCode = dataSort.getPathCode();
		this.type = EDataNavigateType.DATA_SORT;
	}

	/**
	 * 数据分类与数据导航转换
	 * <p>
	 * 基于全局标签的情况下设置数据库ID为特殊形式
	 * 
	 * @param dataSort
	 * @param baseId
	 */
	public DataNavigate(DataSort dataSort, Integer baseId) {
		this.id = dataSort.getId();
		this.realId = dataSort.getId();
		if (null != dataSort && null != dataSort.getBaseId() && dataSort.getBaseId().intValue() == 0) {
			this.baseId = baseId;
		} else {
			this.baseId = dataSort.getBaseId();
		}
		this.name = dataSort.getName();
		this.parentID = dataSort.getBaseId();
		this.code = dataSort.getCode();
		this.pathCode = dataSort.getPathCode();
		this.type = EDataNavigateType.DATA_SORT;
	}

	public Integer getRealId() {
		return realId;
	}

	public Integer getBaseId() {
		return baseId;
	}

	public void setBaseId(Integer baseId) {
		this.baseId = baseId;
	}

	public void setRealId(Integer realId) {
		this.realId = realId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPathCode() {
		return pathCode;
	}

	public void setPathCode(String pathCode) {
		this.pathCode = pathCode;
	}

	public EDataNavigateType getType() {
		return type;
	}

	public void setType(EDataNavigateType type) {
		this.type = type;
	}

	public DataSort toDataSort() {
		DataSort dataSort = new DataSort();

		dataSort.setId(this.realId);
		dataSort.setName(this.name);
		dataSort.setCode(this.code);
		dataSort.setPathCode(this.pathCode);

		return dataSort;
	}

}
