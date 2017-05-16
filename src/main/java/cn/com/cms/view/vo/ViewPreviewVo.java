package cn.com.cms.view.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.common.base.Strings;

import cn.com.cms.base.data.CmsData;
import cn.com.cms.base.data.FieldCodes;
import cn.com.cms.data.model.DataField;
import cn.com.cms.data.util.DataUtil;
import cn.com.cms.data.util.DataVo;
import cn.com.cms.library.constant.EDataType;
import cn.com.cms.view.constant.EModelType;
import cn.com.cms.view.model.ViewContent;
import cn.com.cms.view.model.ViewPage;
import cn.com.people.data.util.DateTimeUtil;

/**
 * 页面预览VO
 * 
 * @author shishb
 * @version 1.0
 *
 */
public class ViewPreviewVo {
	private String title = "";
	private String href = "#";
	private String img = "";
	private String summary = "";
	private Integer length;
	private List<Item> list = new ArrayList<Item>();

	private final static String DATA_PATH = "/page";
	private final static String DB_FILE_NAME = "index.html";

	public ViewPreviewVo() {
	}

	/**
	 * 初始化列表
	 * 
	 * @param content
	 * @param dataList
	 * @param appPath
	 * @param pathCode
	 * @param tableId
	 * @param dataField
	 * @param type
	 * @return
	 */
	public void initList(ViewContent content, List<CmsData> dataList, String appPath, String pathCode, Integer tableId,
			DataField dataField, EModelType type, ViewPage page) {
		this.title = content.getName();
		if (null != content.getNameLink() && !content.getNameLink().isEmpty()) {
			this.href = content.getNameLink();
		} else {
			if (EModelType.Subject.equals(type)) {
				this.href = appPath + pathCode;
			} else {
				this.href = appPath + pathCode + DB_FILE_NAME;
			}
		}
		for (CmsData data : dataList) {
			this.list.add(new Item(data, appPath + DATA_PATH + pathCode + tableId, dataField));
		}
	}

	/**
	 * 初始化列表
	 * 
	 * @param content
	 * @param dataList
	 * @param appPath
	 * @param pathCode
	 * @param tableId
	 * @param dataField
	 * @return
	 */
	public void initList(ViewContent content, List<CmsData> dataList, String appPath, String pathCode, Integer tableId,
			DataField dataField, ViewPage page) {
		this.title = (null == content.getName()) ? "" : content.getName();
		switch (content.getNameLinkType()) {
		case ColumnLink:
		case UserLink:
		default:
			this.href = appPath + "/static/" + page.getCode() + "/list" + pathCode + DB_FILE_NAME;
			break;
		}
		for (CmsData data : dataList) {
			this.list.add(new Item(data, appPath + DATA_PATH + pathCode + data.getTableId(), dataField));
		}
	}

	/**
	 * 初始化图片列表
	 * 
	 * @param dataList
	 * @param appPath
	 * @param pathCode
	 * @param tableId
	 * @param dataField
	 * @return
	 */
	public void initImgList(List<CmsData> dataList, String appPath, String pathCode, Integer tableId,
			DataField dataField) {
		for (CmsData data : dataList) {
			this.list.add(new Item(data, appPath + DATA_PATH + pathCode + tableId, dataField));
		}

	}

	/**
	 * 初始化文章
	 * 
	 * @param content
	 * @param dataList
	 * @param appPath
	 * @param pathCode
	 * @param tableId
	 * @param dataField
	 * @return
	 */
	public void initHeadline(ViewContent content, List<CmsData> dataList, String appPath, String pathCode,
			Integer tableId, DataField dataField) {
		if (null != dataList && dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				if (0 == i) {
					String title = (null == dataList.get(i).get(FieldCodes.TITLE)) ? ""
							: dataList.get(i).get(FieldCodes.TITLE).toString();
					this.setTitle(title);
					this.setHref(appPath + DATA_PATH + pathCode + tableId + "_" + dataList.get(i).getId());
					this.setSummary(null == dataList.get(i).get(FieldCodes.CONTENT) ? ""
							: dataList.get(i).get(FieldCodes.CONTENT).toString());
				}
			}
		}
	}

	/**
	 * 增加列表项
	 * 
	 * @param title
	 * @param href
	 * @param pubTime
	 * @param img
	 * @return
	 */
	public void addListItem(String title, String href, String pubTime, String img) {
		this.list.add(new Item(title, href, pubTime, img));
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public List<Item> getList() {
		return list;
	}

	public void setList(List<Item> list) {
		this.list = list;
	}

	public void addList(Item item) {
		this.list.add(item);
	}

	public void addListItem(String title, String img, String href) {
		this.list.add(new Item(title, img, href));
	}

	public void addListItem(DataVo dataVO, String pathCode) {
		this.list.add(new Item(dataVO, pathCode));
	}

	public void removeList(int index) {
		this.list.remove(index);
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getItemIds() {
		StringBuilder result = new StringBuilder();
		for (Item item : list) {
			result.append(item.id);
			result.append(",");
		}
		if (result.length() > 0) {
			result.deleteCharAt(result.length() - 1);
		}
		return result.toString();
	}

	public class Item {
		private String id = "";
		private String title = "";
		private String href = "#";
		private String tag = "";
		private String img = "";
		private String summary = "";
		private Integer type;
		private String pubTime = "";
		private String content = "";

		public Item() {
		}

		public Item(CmsData data, String pathCode, DataField dataField) {
			this.title = data.get(FieldCodes.TITLE).toString();
			if (null != data.get(FieldCodes.SUMMARY)
					&& !Strings.isNullOrEmpty(data.get(FieldCodes.SUMMARY).toString())) {
				this.summary = data.get(FieldCodes.SUMMARY).toString();
			}
			if (null != data.get(FieldCodes.CONTENT)
					&& !Strings.isNullOrEmpty(data.get(FieldCodes.CONTENT).toString())) {
				this.content = data.get(FieldCodes.CONTENT).toString();
			}
			String dateStr = DataUtil.getDataTypeString(data.get(FieldCodes.DOC_TIME), EDataType.DateTime);
			if (null != dateStr && !dateStr.isEmpty()) {
				this.pubTime = DateTimeUtil.formatDateTimeStr(dateStr, "yyyy-MM-dd HH:mm:ss", "MM-dd");
			}
			if (null != dataField && null != data.get(dataField.getCode())) {
				if (FieldCodes.AUTHORS.equals(dataField.getCode())) {
					String temp = data.get(dataField.getCode()).toString();
					if (temp.isEmpty() || !temp.contains(";")) {
						this.tag = temp;
					} else {
						this.tag = data.get(dataField.getCode()).toString().split(";")[0];
					}
				} else {
					this.tag = toStringForIndex(data.get(dataField.getCode()), dataField.getDataType());
				}
			} else {
				this.tag = "";
			}
			this.img = data.get(FieldCodes.IMGS).toString();
			this.href = pathCode + "_" + data.getId();
		}

		public Item(DataVo dataVO, String pathCode) {
			this.title = dataVO.getTitle();
			this.href = pathCode + DATA_PATH + "/" + dataVO.getTableId() + "_" + dataVO.getId();
			if (null != dataVO.getImg()) {
				this.img = dataVO.getImg();
			}
			if (null != dataVO.getSummary()) {
				this.summary = dataVO.getSummary();
			}
			if (null != dataVO.getContent()) {
				this.content = dataVO.getContent();
			}
			if (null != dataVO.getTags()) {
				this.tag = dataVO.getTags();
			}
			this.id = dataVO.getTableId() + "_" + dataVO.getId();
		}

		public Item(String title, String img, String href) {
			this.title = title;
			this.img = img;
			this.href = href;
		}

		public Item(String title, String href, String pubTime, String img) {
			this.title = title;
			this.img = img;
			this.href = href;
			this.pubTime = pubTime;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getHref() {
			return href;
		}

		public void setHref(String href) {
			this.href = href;
		}

		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
			this.tag = tag;
		}

		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
		}

		public String getSummary() {
			return summary;
		}

		public void setSummary(String summary) {
			this.summary = summary;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public String getPubTime() {
			return pubTime;
		}

		public void setPubTime(String pubTime) {
			this.pubTime = pubTime;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		private String toStringForIndex(Object object, EDataType dataType) {
			switch (dataType) {
			case Date:
			case DateTime:
			case Time:
				return DateTimeUtil.format((Date) object, "yyyy.MM.dd");
			default:
				return null == object ? "" : object.toString();
			}
		}
	}
}