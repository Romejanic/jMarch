package com.romejanic.jmarch.math;

import java.awt.Color;

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
	
	public static float mix(float a, float b, float x) {
		return mixUnclamped(a, b, clamp01(x));
	}
	
	public static float mixUnclamped(float a, float b, float x) {
		return (1f - x) * a + x * b;
	}
	
	public static float fresnelInverse(Vec3 i, Vec3 n, float power) {
		return clamp01(pow(Math.max(-Vec3.dot(i, n), 0f), power));
	}
	
	public static float fresnel(Vec3 i, Vec3 n, float power) {
		return 1f - fresnelInverse(i, n, power);
	}
	
	public static Color addColors(Color a, Color b) {
		int rr = Math.min(a.getRed() + b.getRed(), 255);
		int gg = Math.min(a.getGreen() + b.getGreen(), 255);
		int bb = Math.min(a.getBlue() + b.getBlue(), 255);
		return new Color(rr, gg, bb);
	}
	
	public static Color mixColors(Color a, Color b, float x) {
		float rr = mix((float)a.getRed() / 255f, (float)b.getRed() / 255f, x);
		float gg = mix((float)a.getGreen() / 255f, (float)b.getGreen() / 255f, x);
		float bb = mix((float)a.getBlue() / 255f, (float)b.getBlue() / 255f, x);
		return new Color(rr, gg, bb);
	}
	
	public static Color greyscale(float c) {
		c = clamp01(c);
		return new Color(c, c, c);
	}
	
}