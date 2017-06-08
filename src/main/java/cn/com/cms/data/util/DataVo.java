package cn.com.cms.data.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.document.Document;

import com.google.common.base.Strings;

import cn.com.cms.framework.base.table.CmsData;
import cn.com.cms.framework.base.table.FieldCodes;
import cn.com.people.data.util.DateTimeUtil;

/**
 * 数据VO
 * 
 * @author shishb
 * @version 1.0
 */
public class DataVo {
	private Integer id;
	private String title = "";
	private String authors = "";
	private String docTime = "";
	private String memo = "";
	private String summary = "";
	private String content = "";
	private int status;
	private String img = "";
	private String keywords = "";
	private String uuid = "";
	private String tags = "";
	private String createTime = "";
	private String updateTime = "";
	private String creator = "";
	private String updater = "";
	private String tableId = "";
	private String year = "";
	private String month = "";
	private String day = "";
	private String attach = "";
	private Map<String, String> fieldMap = new HashMap<String, String>();

	public DataVo() {
	}

	/**
	 * CmsData数据处理
	 * 
	 * @param data
	 * @return
	 */
	public DataVo(CmsData data) {
		this.setId(data.getId());
		this.setTitle((String) data.get(FieldCodes.TITLE));
		this.setAuthors((String) data.get(FieldCodes.AUTHORS));
		this.setDocTime(null != data.get(FieldCodes.DOC_TIME)
				? DateTimeUtil.format((Date) data.get(FieldCodes.DOC_TIME), "yyyy年MM月dd日") : "");
		this.setContent((String) data.get(FieldCodes.CONTENT));
		this.setTableId(data.getTableId().toString());
		this.setSummary(null != data.get(FieldCodes.SUMMARY) ? (String) data.get(FieldCodes.SUMMARY) : "");
	}

	/**
	 * 索引数据处理
	 * 
	 * @param data
	 * @return
	 */
	public DataVo(Document data) {
		this.setId(Integer.parseInt(data.get(FieldCodes.ID)));
		this.setTitle((String) data.get(FieldCodes.TITLE));
		this.setAuthors((String) data.get(FieldCodes.AUTHORS));
		if (!Strings.isNullOrEmpty(data.get(FieldCodes.DOC_TIME))) {
			this.setDocTime(DateTimeUtil.formatDateTimeStr(data.get(FieldCodes.DOC_TIME), "yyyyMMddHHmmss",
					"yyyy年MM月dd日 HH:mm:ss"));
		} else {
			this.setDocTime("");
		}
		this.setContent((String) data.get(FieldCodes.CONTENT));
		this.setTableId(data.get(FieldCodes.TABLE_ID));
		this.setSummary(null != data.get(FieldCodes.SUMMARY) ? (String) data.get(FieldCodes.SUMMARY) : "");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getDocTime() {
		return docTime;
	}

	public void setDocTime(String docTime) {
		this.docTime = docTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Map<String, String> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(Map<String, String> fieldMap) {
		this.fieldMap = fieldMap;
	}

	public void putFieldMap(String key, String value) {
		this.fieldMap.put(key, value);
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}
}
