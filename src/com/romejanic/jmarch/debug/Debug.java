package com.romejanic.jmarch.debug;

public class Debug {

	public static boolean ENABLED = false;
	
	public static void print(Object message) {
		if(ENABLED) {
			System.out.print(message);
		}
	}
	
	public static void println(Object message) {
		if(ENABLED) {
			System.out.println(message);
		}
	}
	
	public static void println() {
		if(ENABLED) {
			System.out.println();
		}
	}
	
}