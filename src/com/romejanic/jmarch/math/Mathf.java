package com.romejanic.jmarch.math;

public class Mathf {

	public static float sin(float x) {
		return (float)Math.sin((double)x);
	}
	
	public static float cos(float x) {
		return (float)Math.cos((double)x);
	}
	
	public static float clamp(float x, float min, float max) {
		return x < min ? min : x > max ? max : x;
	}
	
	public static float clamp01(float x) {
		return clamp(x, 0f, 1f);
	}
	
	public static float pow(float x, float y) {
		return (float)Math.pow((double)x, (double)y);
	}
	
	public static float random() {
		return (float)Math.random();
	}
	
	public static float random(float min, float max) {
		return min + (max - min) * random();
	}
	
}