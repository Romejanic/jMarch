package com.romejanic.jmarch.test;

import com.romejanic.jmarch.Raymarcher;
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
	}
	
}