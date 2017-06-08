package cn.com.cms.framework.base.table;

import java.util.List;

/**
 * Datatables插件，封装实体类
 * 
 * @author shishb
 * @param <T>
 * 
 */
public class DataTablesVo<T> {

	private int sEcho;
	private int iTotalRecords;
	private int iTotalDisplayRecords;
	private List<T> aaData;

	public int getsEcho() {
		return sEcho;
	}

	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}

	public List<T> getAaData() {
		return aaData;
	}

	public void setAaData(List<T> aaData) {
		this.aaData = aaData;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public DataTablesVo() {
		super();
	}

	public DataTablesVo(int sEcho, int iTotalRecords, int iTotalDisplayRecords, List<T> aaData) {
		super();
		this.sEcho = sEcho;
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords;
		this.aaData = aaData;
	}

}
