package com.jaytechblog.practice.version1.practice;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureMain {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<Double> future = executorService.submit(new Callable<Double>() {
			public Double call() {
				return someLongComputation();
			}
		});
		doSomethingElse();
		try {
			Double result = future.get(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// handle e
		} catch (ExecutionException e) {
			// handle e
		} catch (TimeoutException e) {
			// handle e
		}
	}

	private static Double someLongComputation() {
		// do something
		return 1d;
	}

	private static void doSomethingElse() {
		// do something else
	}
}
