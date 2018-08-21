package com.romejanic.jmarch.math;

public class Vec3 {

	public static final Vec3 ZERO = new Vec3(0f);
	public static final Vec3 ONE  = new Vec3(1f);
	public static final Vec3 UP   = new Vec3(0f, 1f, 0f);
	
	public float x, y, z;
	
	public Vec3() {
		this(0f);
	}
	
	public Vec3(float v) {
		this(v, v, v);
	}
	
	public Vec3(float x, float y, float z) {
		this.set(x, y, z);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[x=" + x + ",y=" + y + ",z=" + z + "]";
	}
	
	public Vec3 set(float v) {
		return set(v, v, v);
	}
	
	public Vec3 set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public float lengthSqr() {
		return (x*x) + (y*y) + (z*z);
	}
	
	public float length() {
		return (float)Math.sqrt(lengthSqr());
	}
	
	public static Vec3 add(Vec3 a, Vec3 b) {
		return add(a, b, new Vec3());
	}
	
	public static Vec3 add(Vec3 a, Vec3 b, Vec3 dest) {
		float x = a.x + b.x;
		float y = a.y + b.y;
		float z = a.z + b.z;
		return dest.set(x, y, z);
	}
	
	public static Vec3 sub(Vec3 a, Vec3 b) {
		return sub(a, b, new Vec3());
	}
	
	public static Vec3 sub(Vec3 a, Vec3 b, Vec3 dest) {
		float x = a.x - b.x;
		float y = a.y - b.y;
		float z = a.z - b.z;
		return dest.set(x, y, z);
	}
	
	public static Vec3 mul(Vec3 a, Vec3 b) {
		return mul(a, b, new Vec3());
	}
	
	public static Vec3 mul(Vec3 a, float b) {
		return mul(a, b, new Vec3());
	}
	
	public static Vec3 mul(Vec3 a, float b, Vec3 dest) {
		float x = a.x * b;
		float y = a.y * b;
		float z = a.z * b;
		return dest.set(x, y, z);
	}
	
	public static Vec3 mul(Vec3 a, Vec3 b, Vec3 dest) {
		float x = a.x * b.x;
		float y = a.y * b.y;
		float z = a.z * b.z;
		return dest.set(x, y, z);
	}
	
	public static Vec3 div(Vec3 a, Vec3 b) {
		return div(a, b, new Vec3());
	}
	
	public static Vec3 div(Vec3 a, Vec3 b, Vec3 dest) {
		float x = a.x / b.x;
		float y = a.y / b.y;
		float z = a.z / b.z;
		return dest.set(x, y, z);
	}
	
	public static Vec3 negate(Vec3 v) {
		return negate(v, new Vec3());
	}
	
	public static Vec3 negate(Vec3 v, Vec3 dest) {
		return mul(v, -1f, dest);
	}
	
	public static Vec3 normalize(Vec3 v) {
		return normalize(v, new Vec3());
	}
	
	public static Vec3 normalize(Vec3 v, Vec3 dest) {
		return mul(v, 1f / v.length(), dest);
	}
	
	public static float dot(Vec3 a, Vec3 b) {
		return (a.x * b.x) + (a.y * b.y) + (a.z * b.z);
	}
	
}