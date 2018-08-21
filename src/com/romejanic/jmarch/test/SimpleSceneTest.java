package com.romejanic.jmarch.test;

import com.romejanic.jmarch.Raymarcher;

public class SimpleSceneTest {

	public static void main(String[] args) {
		Raymarcher marcher = new Raymarcher(640, 480);
		System.out.println(marcher.getWidth());
		System.out.println(marcher.getHeight());
		System.out.println(marcher.getColorBuffer());
	}
	
}