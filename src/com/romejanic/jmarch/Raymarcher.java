package com.romejanic.jmarch;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.romejanic.jmarch.Scene.Distance;
import com.romejanic.jmarch.math.Ray;
import com.romejanic.jmarch.math.RayHit;

public class Raymarcher {

	private int width;
	private int height;
	private BufferedImage buffer;
	
	private Color clearColor      = null;
	
	public float maximumDistance  = 1000f;
	public float minimumDistance  = 0.01f;
	public float distanceBias     = 0.85f;
	public float stepsPerRaymarch = 512;

	public Raymarcher(int width, int height) {
		this.resize(width, height);
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
	
	public BufferedImage getColorBuffer() {
		return this.buffer;
	}
	
	public Color getClearColor() {
		return this.clearColor == null ? Color.black : this.clearColor;
	}
	
	public void setClearColor(Color color) {
		this.clearColor = color;
	}

	public void resize(int width, int height) {
		this.width  = width;
		this.height = height;
		this.buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}
	
	public RayHit marchRay(Ray ray, Scene scene) {
		float t = 0f;
		int id  = -1;
		for(int i = 0; i < this.stepsPerRaymarch; i++) {
			Distance d = scene.getClosest(ray.getPoint(t));
			if(d.dist < this.minimumDistance || t > this.maximumDistance) {
				id = d.id;
				break;
			}
			t += d.dist * this.distanceBias;
		}
		return new RayHit(ray, t, id);
	}
	
	public Color getRayColor(Ray ray, Scene scene) {
		RayHit hit = marchRay(ray, scene);
		if(hit.materialID != -1) {
			return Color.red;
		}
		return getClearColor();
	}

}