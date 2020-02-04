package com.jaytechblog.practice.version2.domain;

import static com.jaytechblog.practice.version2.support.AppUtil.*;

import java.util.Random;

import com.jaytechblog.practice.version2.support.Discount;

public class Shop {

	private final String name;
	private final Random random;

	public Shop(String name) {
		this.name = name;
		random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
	}

	public String getPrice(String product) {
		double price = calculatePrice(product);
		Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
		return name + ":" + price + ":" + code;
	}

	public String getPrice2(String product) {
		double price = calculatePrice(product);
		Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
		return name + ":" + price + ":" + code;
	}

	public double calculatePrice(String product) {
		delay();
		return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
	}

	public double calculatePrice2(String product) {
		randomDelay();
		return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
	}

	public String getName() {
		return name;
	}
}
