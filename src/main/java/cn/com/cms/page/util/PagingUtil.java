package cn.com.cms.page.util;

/**
 * 分页工具类
 * 
 * @author shishb
 * @version 1.0
 */
public class PagingUtil {
	private int curNum;
	private int preNum;
	private int nextNum;
	private int pageStart;
	private int pageEnd;
	private int pageCount;

	public PagingUtil() {
	}

	public PagingUtil(int size, int curNum, int count) {
		this.curNum = curNum;
		this.pageCount = count % size == 0 && count > size ? count / size : count / size + 1;
		if (getPageCount() > 5) {
			if (curNum - 2 > 0) {
				this.pageStart = curNum - 2;
			} else {
				this.pageStart = 1;
			}
			if (curNum + 2 >= getPageCount()) {
				this.pageEnd = getPageCount();
			} else {
				this.pageEnd = curNum + 2;
			}
			if (curNum > 1) {
				this.preNum = curNum - 1;
			} else {
				this.preNum = 1;
			}
			if (curNum + 1 >= getPageCount()) {
				this.nextNum = getPageCount();
			} else {
				this.nextNum = curNum + 1;
			}
		} else {
			this.pageStart = 1;
			this.pageEnd = getPageCount();
			if (curNum > 1) {
				this.preNum = curNum - 1;
			} else {
				this.preNum = 1;
			}
			if (curNum + 1 >= getPageCount()) {
				this.nextNum = getPageCount();
			} else {
				this.nextNum = curNum + 1;
			}
		}
	}

	public int getCurNum() {
		return curNum;
	}

	public void setCurNum(int curNum) {
		this.curNum = curNum;
	}

	public int getPreNum() {
		return preNum;
	}

	public void setPreNum(int preNum) {
		this.preNum = preNum;
	}

	public int getNextNum() {
		return nextNum;
	}

	public void setNextNum(int nextNum) {
		this.nextNum = nextNum;
	}

	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public int getPageEnd() {
		return pageEnd;
	}

	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

}
