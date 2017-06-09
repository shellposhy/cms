package cn.com.cms.framework.esb.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;

/**
 * Annotation indicating that a method (or all methods on a class) trigger(s) a
 * {@link Cache#put(Object, Object)} operation. As opposed to {@link Cacheable}
 * annotation, this annotation does not cause the target method to be skipped -
 * rather it always causes the method to be invoked and its result to be placed
 * into the cache.
 *
 * @author shishb
 * @since 1.0
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface IgniteCachePut {
	/**
	 * Name of the caches in which the update takes place.
	 * <p>
	 * May be used to determine the target cache (or caches), matching the
	 * qualifier value (or the bean name(s)) of (a) specific bean definition.
	 */
	String[] value();

	/**
	 * Spring Expression Language (SpEL) attribute for computing the key
	 * dynamically.
	 * <p>
	 * Default is "", meaning all method parameters are considered as a key.
	 */
	String key() default "";

	/**
	 * The time default cache save time
	 * <p>
	 * Default 900s
	 */
	int timeout() default 900;
}
