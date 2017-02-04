package cn.com.cms.framework.base;

import java.io.Serializable;
import java.util.List;

/**
 * 结果集工具类
 * 
 * @param <T>
 * @author shishb
 * @version 1.0
 */
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<T> list = null;
	private int totalCount = 0;

	public Result() {

	}

	public Result(List<T> list, int totalCount) {
		this.list = list;
		this.totalCount = totalCount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}