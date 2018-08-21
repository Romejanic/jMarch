package com.romejanic.jmarch;

import java.awt.image.BufferedImage;

public class Raymarcher {

	private int width;
	private int height;
	private BufferedImage buffer;

	public Raymarcher(int width, int height) {
		this.resize(width, height);
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void resize(int width, int height) {
		this.width  = width;
		this.height = height;
		this.buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}
	
	public BufferedImage getColorBuffer() {
		return this.buffer;
	}

}