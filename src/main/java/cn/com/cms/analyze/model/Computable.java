package cn.com.cms.analyze.model;

/**
 * Base the compute interface
 * 
 * @author shishb
 * @version 1.0
 */
public interface Computable<A, V> {
	V compute(A param) throws InterruptedException;
}
