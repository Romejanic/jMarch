package com.romejanic.jmarch.math;

public class Mat3 {

	private final float[] matrix = new float[9];

	public Mat3() {
		this(1f);
	}

	public Mat3(float initial) {
		this.setIdentity(initial);
	}

	public Mat3(float... values) {
		if(values.length != 9) {
			throw new IllegalArgumentException("Length of array does not equal 9! (" + values.length + ")");
		}
		for(int i = 0; i < this.matrix.length; i++) {
			this.matrix[i] = values[i];
		}
	}

	public Mat3 setIdentity() {
		return this.setIdentity(1f);
	}

	public Mat3 setIdentity(float initial) {
		m00(initial); m01(0f); m02(0f);
		m10(0f); m11(initial); m02(0f);
		m20(0f); m21(0f); m22(initial);
		return this;
	}

	@Override
	public String toString() {
		return "[" + m00() + "," + m01() + "," + m02() + "\n"
				+ " " + m10() + "," + m11() + "," + m12() + "\n"
				+ " " + m20() + "," + m21() + "," + m22() + "]";
	}

	public float m00() {
		return this.matrix[0];
	}

	public float m01() {
		return this.matrix[1];
	}

	public float m02() {
		return this.matrix[2];
	}

	public float m10() {
		return this.matrix[3];
	}

	public float m11() {
		return this.matrix[4];
	}

	public float m12() {
		return this.matrix[5];
	}

	public float m20() {
		return this.matrix[6];
	}

	public float m21() {
		return this.matrix[7];
	}

	public float m22() {
		return this.matrix[8];
	}

	public void m00(float value) {
		this.matrix[0] = value;
	}

	public void m01(float value) {
		this.matrix[1] = value;
	}

	public void m02(float value) {
		this.matrix[2] = value;
	}

	public void m10(float value) {
		this.matrix[3] = value;
	}

	public void m11(float value) {
		this.matrix[4] = value;
	}

	public void m12(float value) {
		this.matrix[5] = value;
	}

	public void m20(float value) {
		this.matrix[6] = value;
	}

	public void m21(float value) {
		this.matrix[7] = value;
	}

	public void m22(float value) {
		this.matrix[8] = value;
	}

	public float m(int r, int c) {
		return this.matrix[3*r+c];
	}

	public void m(int r, int c, float m) {
		this.matrix[3*r+c] = m;
	}

	public static Mat3 rotate(float angle, float axisX, float axisY, float axisZ) {
		return rotate(angle, axisX, axisY, axisZ, new Mat3());
	}
	
	public static Mat3 rotate(float angle, float axisX, float axisY, float axisZ, Mat3 dest) {
		float sin = Mathf.sin(angle), cos = Mathf.cos(angle), oneMinusCos = 1f - cos;
		
		dest.m00(cos + (axisX * axisX) * oneMinusCos);
		dest.m01(axisX * axisY * oneMinusCos - axisZ * sin);
		dest.m02(axisX * axisZ * oneMinusCos + axisY * sin);
		
		dest.m10(axisY * axisX * oneMinusCos + axisZ * sin);
		dest.m11(cos + (axisY * axisY) * oneMinusCos);
		dest.m12(axisY * axisZ * oneMinusCos - axisX * sin);
		
		dest.m20(axisZ * axisX * oneMinusCos - axisY * sin);
		dest.m21(axisZ * axisY * oneMinusCos + axisX * sin);
		dest.m22(cos + (axisZ * axisZ) * oneMinusCos);
		
		return dest;
	}

	public static Mat3 mul(Mat3 a, Mat3 b) {
		return mul(a, b, new Mat3());
	}

	public static Mat3 mul(Mat3 a, Mat3 b, Mat3 dest) {
		dest.m00(a.m00() * b.m00() + a.m01() * b.m10() + a.m02() * b.m20());
		dest.m01(a.m00() * b.m01() + a.m01() * b.m11() + a.m02() * b.m21());
		dest.m02(a.m00() * b.m02() + a.m01() * b.m12() + a.m02() * b.m22());

		dest.m10(a.m10() * b.m00() + a.m11() * b.m10() + a.m12() * b.m20());
		dest.m11(a.m10() * b.m01() + a.m11() * b.m11() + a.m12() * b.m21());
		dest.m12(a.m10() * b.m02() + a.m11() * b.m12() + a.m12() * b.m22());

		dest.m20(a.m20() * b.m00() + a.m21() * b.m10() + a.m22() * b.m20());
		dest.m21(a.m20() * b.m01() + a.m21() * b.m11() + a.m22() * b.m21());
		dest.m22(a.m20() * b.m02() + a.m21() * b.m12() + a.m22() * b.m22());

		return dest;
	}

	public static Vec3 transform(Mat3 m, Vec3 v) {
		return transform(m, v, new Vec3());
	}

	public static Vec3 transform(Mat3 m, Vec3 v, Vec3 dest) {
		float x = m.m00() * v.x + m.m01() * v.y + m.m02() * v.z;
		float y = m.m10() * v.x + m.m11() * v.y + m.m12() * v.z;
		float z = m.m20() * v.x + m.m21() * v.y + m.m22() * v.z;
		return dest.set(x, y, z);
	}

}