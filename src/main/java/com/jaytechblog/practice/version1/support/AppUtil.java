package com.jaytechblog.practice.version1.support;

public class AppUtil {
	public static void delay() {
		int delay = 1000;
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
