package cn.com.cms.data.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import cn.com.cms.common.SystemConstant;
import cn.com.cms.data.dao.DataFieldMapper;
import cn.com.cms.data.model.DataField;
import cn.com.cms.framework.base.Result;
import cn.com.cms.library.constant.EAccessType;
import cn.com.cms.library.constant.EDataFieldType;
import cn.com.cms.library.constant.ELibraryNodeType;
import cn.com.cms.library.dao.LibraryMapper;
import cn.com.cms.library.model.BaseLibrary;

/**
 * 数据字段的服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class DataFieldService {
	@Resource
	private DataFieldMapper dataFieldMapper;
	@Resource
	private LibraryMapper<?> libraryMapper;

	/**
	 * 根据编号获得字段
	 * 
	 * @param code
	 * @return
	 */
	public DataField getByCode(String code) {
		return dataFieldMapper.getByCode(code);
	}

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	public DataField find(int id) {
		return dataFieldMapper.find(id);
	}

	/**
	 * 获取所有数据字段编码
	 * 
	 * @return
	 */
	public LinkedHashMap<String, DataField> findAllDataFieldIndexCode() {
		LinkedHashMap<String, DataField> result = new LinkedHashMap<String, DataField>();
		List<DataField> dataFields = dataFieldMapper.findAll();
		for (DataField dataField : dataFields) {
			result.put(dataField.getCode(), dataField);
		}
		return result;
	}

	/**
	 * 获取所有字段的Id
	 * 
	 * @return
	 */
	public LinkedHashMap<Integer, DataField> findAllDataFieldIndexId() {
		LinkedHashMap<Integer, DataField> result = new LinkedHashMap<Integer, DataField>();
		List<DataField> dataFields = dataFieldMapper.findAll();
		for (DataField dataField : dataFields) {
			result.put(dataField.getId(), dataField);
		}
		return result;
	}

	/**
	 * 获取所有数据字段列表
	 * 
	 * @return
	 */
	public List<DataField> findAllDataField() {
		return dataFieldMapper.findAll();
	}

	/**
	 * 获得所有的必填字段<code>Map</code>
	 * 
	 * @param required
	 * @return
	 */
	public LinkedHashMap<Integer, DataField> findRequiredDataFieldIndexId(boolean required) {
		LinkedHashMap<Integer, DataField> result = new LinkedHashMap<Integer, DataField>();
		List<DataField> dataFields = dataFieldMapper.findByRequired(required);
		for (DataField dataField : dataFields) {
			result.put(dataField.getId(), dataField);
		}
		return result;
	}

	/**
	 * 获得数据库模板字段列表
	 * 
	 * @param modelId
	 * @return
	 */
	public List<DataField> findFieldsByModelId(@Param("modelId") Integer modelId) {
		return dataFieldMapper.findFieldsByModelId(modelId);
	}

	/**
	 * 获得所有的必填字段
	 * 
	 * @param required
	 * @return
	 */
	public List<DataField> findByRequired(boolean required) {
		return dataFieldMapper.findByRequired(required);
	}

	/**
	 * 根据数据库编号获得所有的字段
	 * 
	 * @param dbId
	 * @return
	 */
	public List<DataField> findFieldsByDBId(Integer dbId) {
		return dataFieldMapper.findFieldsByDBId(dbId);
	}

	/**
	 * 获得所有需要显示的字段
	 * 
	 * @param dbId
	 * @return
	 */
	public List<DataField> findDisplayFieldsByDBId(Integer dbId) {
		return dataFieldMapper.findDisplayFieldsByDBId(dbId);
	}

	/**
	 * 获得多个数据库均含有的可排序字段
	 * 
	 * 
	 * @param dbIds
	 * @return
	 */
	public List<DataField> findFieldsInEveryBase(String dbsStr) {
		List<Integer> dbIds = new ArrayList<Integer>();
		String[] dbIdStr = dbsStr.split(SystemConstant.COMMA_SEPARATOR);
		for (String idStr : dbIdStr) {
			BaseLibrary<?> library = libraryMapper.find(Integer.parseInt(idStr));
			if (ELibraryNodeType.Lib.equals(library.getNodeType())) {
				dbIds.add(Integer.parseInt(idStr));
			}
		}
		if (dbIds.size() == 1) {
			return dataFieldMapper.findFieldsInBase(true, dbIds.get(0));
		}
		return dataFieldMapper.findFieldsInEveryBase(true, dbIds, dbIds.size() - 1);
	}

	/**
	 * 获得多个数据库可查询字段
	 * 
	 * 
	 * @param dbsStr
	 * @return
	 */
	public List<DataField> findFieldsInBasesByAccess(String dbsStr) {
		List<Integer> dbIds = new ArrayList<Integer>();
		String[] dbIdStr = dbsStr.split(SystemConstant.COMMA_SEPARATOR);
		for (String idStr : dbIdStr) {
			BaseLibrary<?> library = libraryMapper.find(Integer.parseInt(idStr));
			if (ELibraryNodeType.Lib.equals(library.getNodeType())) {
				dbIds.add(Integer.parseInt(idStr));
			}
		}
		if (null != dbIds && dbIds.size() == 1) {
			return dataFieldMapper.findFieldsInBaseByAccess(EAccessType.Modifiable.ordinal(), dbIds.get(0));
		}
		return dataFieldMapper.findFieldsInBasesByAccesses(EAccessType.Modifiable.ordinal(), dbIds);
	}

	/**
	 * 获得多个数据库均含有的字段
	 * 
	 * @param dbIds
	 * @return
	 */
	public List<DataField> findFieldsByDBIds(String dbstr) {
		List<Integer> dbIds = new ArrayList<Integer>();
		String[] dbIdStr = dbstr.split(SystemConstant.COMMA_SEPARATOR);
		for (String idStr : dbIdStr) {
			dbIds.add(Integer.parseInt(idStr));
		}
		return dataFieldMapper.findFieldsByDBIds(dbIds);
	}

	/**
	 * 根据字段的id串获取字段的列表
	 * 
	 * @param idStr
	 * @return
	 */
	public List<DataField> getDataFieldListByIdStr(String idStr) {
		List<DataField> result = new ArrayList<DataField>();
		LinkedHashMap<Integer, DataField> dataFieldMapIndexId = findAllDataFieldIndexId();
		String[] ids = idStr.split(SystemConstant.COMMA_SEPARATOR);
		List<DataField> requiredFields = findByRequired(true);
		List<String> requiredFieldCodes = new ArrayList<String>();
		for (DataField field : requiredFields) {
			requiredFieldCodes.add(field.getCode());
		}
		result.addAll(requiredFields);
		for (String id : ids) {
			DataField field = dataFieldMapIndexId.get(Integer.parseInt(id));
			if (field != null && !requiredFieldCodes.contains(field.getCode())) {
				result.add(field);
			}
		}
		return result;
	}

	/**
	 * 根据是否显示和必填项获得数据字段
	 * 
	 * @param forDisplay
	 * @param required
	 * @return
	 */
	public List<DataField> findByIsDisplayAndRequire(boolean forDisplay, boolean required) {
		return dataFieldMapper.findByIsDisplayAndRequire(forDisplay, required);
	}

	/**
	 * 根据字段类型查询
	 * 
	 * @param type
	 * @return
	 */
	public List<DataField> findByType(EDataFieldType type) {
		return dataFieldMapper.findByType(type);
	}

	/**
	 * 根据字段类型和是否显示查询
	 * 
	 * @param type
	 * @param forDisplay
	 * @return
	 */
	public List<DataField> findByTypeAndForDisplay(EDataFieldType type, boolean forDisplay) {
		return dataFieldMapper.findByTypeAndForDisplay(type, forDisplay);
	}

	/**
	 * 获得显示字段
	 * 
	 * @param modelId
	 * @return
	 */
	public List<DataField> findDisplayFieldsByModelId(@Param("modelId") Integer modelId) {
		return dataFieldMapper.findDisplayFieldsByModelId(modelId);
	}

	/**
	 * 分页查询
	 * 
	 * @param qs
	 * @param first
	 * @param size
	 * @return
	 */
	public Result<DataField> findByPage(String qs, Integer first, Integer size) {
		Result<DataField> result = new Result<DataField>();
		result.setList(dataFieldMapper.findByPage(qs, first, size));
		result.setTotalCount(dataFieldMapper.count(qs));
		return result;
	}
}
