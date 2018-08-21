package com.romejanic.jmarch.math;

public class Mathf {

	public static float clamp(float x, float min, float max) {
		return x < min ? min : x > max ? max : x;
	}
	
	public static float clamp01(float x) {
		return clamp(x, 0f, 1f);
	}
	
}