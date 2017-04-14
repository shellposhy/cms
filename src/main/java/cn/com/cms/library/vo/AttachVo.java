package cn.com.cms.library.vo;

/**
 * 附件对象VO
 * 
 * @author shishb
 * @version 1.0
 */
public class AttachVo {
	private Integer id;
	private Integer tableId;
	private String uuid;
	private String fileName;

	public AttachVo(Integer id, Integer tableId, String uuid, String fileName) {
		this.id = id;
		this.tableId = tableId;
		this.uuid = uuid;
		this.fileName = fileName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
