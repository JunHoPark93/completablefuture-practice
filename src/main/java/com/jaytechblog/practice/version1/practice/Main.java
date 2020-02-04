package com.jaytechblog.practice.version1.practice;

import java.util.concurrent.Future;

import com.jaytechblog.practice.version1.domain.Shop;

public class Main {
	public static void main(String[] args) {
		Shop shop = new Shop("Jay Shop");
		long start = System.nanoTime();
		Future<Double> futurePrice = shop.getAsyncPrice("Jay's Mac");

		doSomethingElse();

		try {
			System.out.println("Price is " + futurePrice.get());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void doSomethingElse() {
		// do something else
	}
}
