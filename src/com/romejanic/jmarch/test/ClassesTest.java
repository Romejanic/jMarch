package com.romejanic.jmarch.test;

import com.romejanic.jmarch.Raymarcher;
import com.romejanic.jmarch.math.Mat3;
import com.romejanic.jmarch.math.Vec3;

public class ClassesTest {

	public static void main(String[] args) {
		Raymarcher marcher = new Raymarcher(640, 480);
		System.out.println(marcher.getWidth());
		System.out.println(marcher.getHeight());
		System.out.println(marcher.getColorBuffer());
		
		Vec3 v = new Vec3(0f, 0.5f, 10f);
		System.out.println(v);
		System.out.println(v.lengthSqr() + " " + v.length());
		System.out.println(Vec3.normalize(v, v));
		
		Vec3 v2 = Vec3.normalize(new Vec3(0f, 0.3f, 0.7f));
		System.out.println(v2);
		System.out.println(Vec3.dot(v, v2));
		
		Mat3 m1 = new Mat3(-1, -2, -3, 0, 2, -4, 6, 5, 4);
		Mat3 m2 = new Mat3(7, -2, -1, -3, 2, 8, 0, 1, 3);
		System.out.println("m1 = " + m1.toString());
		System.out.println("m2 = " + m2.toString());
		
		Mat3 m3 = Mat3.mul(m1, m2);
		System.out.println("m1 x m2 = " + m3.toString());
	}
	
}