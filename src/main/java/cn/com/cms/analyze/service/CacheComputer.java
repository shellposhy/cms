package cn.com.cms.analyze.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.CancellationException;

import cn.com.cms.analyze.model.Computable;

public class CacheComputer<A, V> implements Computable<A, V> {

	private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private final Computable<A, V> computer;

	public CacheComputer(Computable<A, V> computer) {
		this.computer = computer;
	}

	public V compute(final A param) throws InterruptedException {
		while (true) {
			Future<V> future = cache.get(param);
			if (null == future) {
				Callable<V> callable = new Callable<V>() {
					public V call() throws InterruptedException {
						return computer.compute(param);
					}
				};
				FutureTask<V> task = new FutureTask<V>(callable);
				future = cache.putIfAbsent(param, task);
				if (null == future) {
					future = task;
					task.run();
				}
			}
			try {
				return future.get();
			} catch (CancellationException e) {
				cache.remove(param, future);
			} catch (ExecutionException e) {
				throw launderThrowable(e.getCause());
			}
		}
	}

	public static RuntimeException launderThrowable(Throwable t) {
		if (t instanceof RuntimeException)
			return (RuntimeException) t;
		else if (t instanceof Error)
			throw (Error) t;
		else
			throw new IllegalStateException("Not unchecked", t);
	}
}
