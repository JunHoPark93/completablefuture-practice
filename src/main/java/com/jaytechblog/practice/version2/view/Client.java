package com.jaytechblog.practice.version2.view;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import com.jaytechblog.practice.version2.domain.PriceFinder;

public class Client {
	public static void main(String[] args) {
		PriceFinder priceFinder = new PriceFinder();
		long start = System.nanoTime();
		System.out.println(priceFinder.findPrices("Mac"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("블로킹 호출 완료 시간:  " + duration + " msecs");

		start = System.nanoTime();
		System.out.println(priceFinder.findPrices2("Mac"));
		duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("동기 비동기 조합 호출 완료 시간:  " + duration + " msecs");

		Stream<CompletableFuture<String>> result = priceFinder.findPricesStream("Mac");

		CompletableFuture[] completableFutures = result.map(future -> future.thenAccept(System.out::println))
			.toArray(CompletableFuture[]::new);
		CompletableFuture.allOf(completableFutures).join();
	}
}
