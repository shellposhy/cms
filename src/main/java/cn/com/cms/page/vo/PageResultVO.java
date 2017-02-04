package cn.com.cms.page.vo;

import java.util.List;
import cn.com.cms.data.util.DataVO;

/**
 * 前端查询结果集Vo
 * 
 * @author shishb
 * @version 1.0
 */
public class PageResultVO {
	// 总记录数
	private int size;
	// 检索的数据库名
	private String dataBaseName;
	// 结果集
	private List<DataVO> dataVoList;
	// 文章列表
	private String docList;

	/**
	 * 索引数据处理
	 * 
	 * @param data
	 * @return
	 */

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public List<DataVO> getDataVoList() {
		return dataVoList;
	}

	public void setDataVoList(List<DataVO> dataVoList) {
		this.dataVoList = dataVoList;
	}

	public String getDocList() {
		return docList;
	}

	public void setDocList(String docList) {
		this.docList = docList;
	}

}
