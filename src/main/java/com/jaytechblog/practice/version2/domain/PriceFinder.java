package com.jaytechblog.practice.version2.domain;

import static java.util.Arrays.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jaytechblog.practice.version2.support.Discount;
import com.jaytechblog.practice.version2.support.Quote;

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

	public List<String> findPrices(String product) {
		return shops.stream()
			.map(shop -> shop.getPrice(product))
			.map(Quote::parse)
			.map(Discount::applyDiscount)
			.collect(Collectors.toList());
	}

	public List<String> findPrices2(String product) {
		List<CompletableFuture<String>> priceFutures = shops.stream()
			.map(shop -> CompletableFuture.supplyAsync(() ->
				shop.getPrice(product), executor))
			.map(future -> future.thenApply(Quote::parse))
			.map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() ->
				Discount.applyDiscount(quote), executor)))
			.collect(Collectors.toList());

		return priceFutures.stream()
			.map(CompletableFuture::join)
			.collect(Collectors.toList());
	}

	public Stream<CompletableFuture<String>> findPricesStream(String product) {
		return shops.stream()
			.map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice2(product), executor))
			.map(future -> future.thenApply(Quote::parse))
			.map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(
				() -> Discount.applyDiscount(quote), executor)));
	}
}