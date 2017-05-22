package cn.com.cms.data.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.com.cms.base.config.AppConfig;
import cn.com.cms.base.data.CmsData;
import cn.com.cms.base.data.FieldCodes;
import cn.com.cms.data.model.DataField;
import cn.com.cms.data.util.DataUtil;
import cn.com.cms.framework.config.SystemConstant;
import cn.com.cms.framework.tree.Node;
import cn.com.cms.system.service.ImagePathService;
import cn.com.people.data.util.DateTimeUtil;
import cn.com.people.data.util.FileUtil;
import cn.com.people.data.util.PkUtil;

/**
 * Data Copy Processing Service
 * 
 * @author shishb
 * @version 1.0
 * @see ImagePathService
 */
@Service
public class DataCopyService {
	@Resource
	private ImagePathService imagePathService;
	@Resource
	private AppConfig appConfig;

	/**
	 * Wrap the result data by old result data
	 * 
	 * @param source
	 * @param target
	 * @param sourceList
	 * @param sourceFieldList
	 * @param targetFieldList
	 * @return
	 */
	public List<CmsData> wrapData(int source, int target, List<CmsData> sourceList, List<DataField> sourceFieldList,
			List<DataField> targetFieldList) {
		List<CmsData> result = null;
		if (null != sourceList && sourceList.size() > 0) {
			result = Lists.newArrayList();
			for (CmsData data : sourceList) {
				CmsData newData = new CmsData();
				if (null != targetFieldList && targetFieldList.size() > 0) {
					for (DataField dataField : targetFieldList) {
						if (targetFieldContainsSourceField(dataField, sourceFieldList)) {
							if (FieldCodes.ID.equals(dataField.getCode())) {
								newData.put(dataField.getCode(), 0);
							} else if (FieldCodes.UUID.equals(dataField.getCode())) {
								newData.put(dataField.getCode(), PkUtil.getShortUUID());
							} else if (FieldCodes.FINGER_PRINT.equals(dataField.getCode())) {
								newData.put(dataField.getCode(), newData.get(FieldCodes.UUID));
							} else {
								newData.put(dataField.getCode(), data.get(dataField.getCode()));
							}
						} else {
							newData.put(dataField.getCode(),
									DataUtil.getDefaultValue(dataField.getDataType(), dataField.isMand()));
						}
					}
				}
				// deal with the picture and file
				copyPicture(source, target, data, newData);
				copyFile(source, target, data, newData);
				newData.setBaseId(target);
				result.add(newData);
			}
		}
		return result;
	}

	/**
	 * Is the target data field in the source list
	 * 
	 * @param sourceList
	 * @param target
	 * @return
	 */
	public boolean targetFieldContainsSourceField(DataField target, List<DataField> sourceList) {
		boolean tag = false;
		if (null != sourceList && sourceList.size() > 0) {
			for (DataField source : sourceList) {
				if (source.getCode().equals(target.getCode())) {
					tag = true;
					break;
				}
			}
		}
		return tag;
	}

	/**
	 * Wrap the json String to node list
	 * 
	 * @param context
	 * @return
	 */
	public List<Node<Integer, Integer>> copyNode(String context) {
		List<Node<Integer, Integer>> result = null;
		if (!Strings.isNullOrEmpty(context)) {
			result = Lists.newArrayList();
			String[] dataIdStrs = context.split(SystemConstant.COMMA_SEPARATOR);
			if (null != dataIdStrs && dataIdStrs.length > 0) {
				for (String dataIdStr : dataIdStrs) {
					if (!Strings.isNullOrEmpty(dataIdStr)) {
						String[] dataIds = dataIdStr.split("_");
						if (null != dataIds && dataIds.length > 1) {
							Integer dataId = Integer.valueOf(dataIds[0]);
							Integer tableId = Integer.valueOf(dataIds[1]);
							Node<Integer, Integer> node = new Node<Integer, Integer>();
							node.id = dataId;
							node.name = tableId;
							result.add(node);
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Copy the source data file to target source
	 * <p>
	 * Before copy file,the target source need the uuid.
	 * 
	 * @param source
	 * @param target
	 * @param sourceData
	 * @param targetData
	 * @return
	 */
	public void copyFile(int source, int target, CmsData sourceData, CmsData targetData) {
		String oldAttach = (String) sourceData.get(FieldCodes.ATTACH);
		String oldUUID = (String) sourceData.get(FieldCodes.UUID);
		String newUUID = (String) targetData.get(FieldCodes.UUID);
		if (!Strings.isNullOrEmpty(oldUUID) && !Strings.isNullOrEmpty(oldAttach) && !Strings.isNullOrEmpty(newUUID)) {
			String[] attachs = oldAttach.split(SystemConstant.SEPARATOR);
			String createTime = null != sourceData.get(FieldCodes.CREATE_TIME)
					? DateTimeUtil.format((Date) sourceData.get(FieldCodes.CREATE_TIME), "yyyyMMdd")
					: DateTimeUtil.format(new Date(), "yyyyMMdd");
			String path = imagePathService.getUDocRealRoot(source, createTime) + oldUUID;
			if (null != attachs && attachs.length > 0) {
				String newPath = imagePathService.getUDocRealRoot(target, createTime) + newUUID;
				for (String filename : attachs) {
					File file = new File(path + "/" + filename);
					if (file.exists() && file.isFile()) {
						FileUtil.copyFile(file, newPath, filename, true);
					}
				}
			}
		}
	}

	/**
	 * copy source article picture to target article picture,and change the
	 * picture path address;
	 * <p>
	 * we need to delete the picture when move the article
	 * 
	 * @param source
	 * @param target
	 * @param sourceData
	 * @param targetData
	 * @return
	 */
	public void copyPicture(int source, int target, CmsData sourceData, CmsData targetData) {
		if (null != sourceData) {
			List<String> picList = null;
			String content = (String) sourceData.get(FieldCodes.CONTENT);
			try {
				List<String> contentPicList = DataUtil.getImgs(content);
				if (null != contentPicList && contentPicList.size() > 0) {
					picList = Lists.newArrayList();
					for (String path : contentPicList) {
						// the picture is uploaded by user
						if (!Strings.isNullOrEmpty(path) && path.startsWith("/pic/upic/")) {
							picList.add(path);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			// copy the picture
			if (null != picList && picList.size() > 0) {
				String createTime = null != sourceData.get(FieldCodes.CREATE_TIME)
						? DateTimeUtil.format((Date) sourceData.get(FieldCodes.CREATE_TIME), "yyyyMMdd")
						: DateTimeUtil.format(new Date(), "yyyyMMdd");
				for (String oldPath : picList) {
					File pic = new File(appConfig.getAppPathHome() + oldPath);
					String realPath = imagePathService.getUPicRealRoot(target, createTime);
					if (pic.exists() && pic.isFile()) {
						// Think about whether the picture is needed remove
						FileUtil.copyFile(pic, realPath, pic.getName(), true);
						// FileUtil.deleteFile(pic);
					}
				}
				// replace the content picture path
				content = content.replaceAll("/pic/upic/" + source, "/pic/upic/" + target);
				targetData.put(FieldCodes.CONTENT, content);
			}
		}
	}
}
