package cn.com.cms.framework.base;

/**
 * 节点对象
 * 
 * @author shishb
 * @version 1.0
 */
public class Node<I, N> {
	public I id;
	public N name;

	public I getId() {
		return id;
	}

	public N getName() {
		return name;
	}

	public void setName(N name) {
		this.name = name;
	}

}
