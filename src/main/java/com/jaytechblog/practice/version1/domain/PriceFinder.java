package com.jaytechblog.practice.version1.domain;

import static java.util.Arrays.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class PriceFinder {
	private final List<Shop> shops = asList(
		// 눈에 보이게 성능을 측정하고 싶다면 상점을 90여개를 추가해보세요.
		new Shop("CoolPang"),
		new Shop("HMarket"),
		new Shop("12th Street"),
		new Shop("YouMakePrice"),
		new Shop("FBay")
	);

	private final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), (Runnable r) -> {
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	});

	// 블로킹 호출
	public List<String> findPrices(String product) {
		return shops.stream()
			.map(shop -> String.format("%s 가격은 %.2f", shop.getName(), shop.getPrice(product)))
			.collect(Collectors.toList());
	}

	// 병렬 스트림 블로킹 호출
	public List<String> findPrices2(String product) {
		return shops.parallelStream()
			.map(shop -> String.format("%s 가격은 %.2f", shop.getName(), shop.getPrice(product)))
			.collect(Collectors.toList());
	}

	// CompletableFuture 호출
	public List<String> findPrices3(String product) {
		List<CompletableFuture<String>> priceFutures = shops.stream()
			.map(shop -> CompletableFuture.supplyAsync(() ->
				String.format("%s 가격은 %.2f", shop.getName(), shop.getPrice(product))))
			.collect(Collectors.toList());

		return priceFutures.stream()
			.map(CompletableFuture::join)
			.collect(Collectors.toList());
	}

	// CompletableFuture 호출 (Executor 의 사용)
	public List<String> findPrices4(String product) {
		List<CompletableFuture<String>> priceFutures = shops.stream()
			.map(shop -> CompletableFuture.supplyAsync(() ->
				String.format("%s 가격은 %.2f", shop.getName(), shop.getPrice(product)), executor))
			.collect(Collectors.toList());

		return priceFutures.stream()
			.map(CompletableFuture::join)
			.collect(Collectors.toList());
	}
}