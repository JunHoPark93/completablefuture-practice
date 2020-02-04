package com.jaytechblog.practice.version1.view;

import com.jaytechblog.practice.version1.domain.PriceFinder;

public class Client {
	public static void main(String[] args) {

		//System.out.println(Runtime.getRuntime().availableProcessors());
		PriceFinder priceFinder = new PriceFinder();
		long start = System.nanoTime();
		System.out.println(priceFinder.findPrices("Mac"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("블로킹 호출 완료 시간:  " + duration + " msecs");

		start = System.nanoTime();
		System.out.println(priceFinder.findPrices2("Mac"));
		duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("병렬 스트림 블로킹 호출 완료 시간:  " + duration + " msecs");

		start = System.nanoTime();
		System.out.println(priceFinder.findPrices3("Mac"));
		duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("CompletableFuture 호출 완료 시간:  " + duration + " msecs");

		start = System.nanoTime();
		System.out.println(priceFinder.findPrices4("Mac"));
		duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("CompletableFuture 호출(executor 사용) 완료 시간:  " + duration + " msecs");
	}
}
