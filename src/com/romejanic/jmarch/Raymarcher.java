package com.romejanic.jmarch;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import com.romejanic.jmarch.Scene.Distance;
import com.romejanic.jmarch.math.Ray;
import com.romejanic.jmarch.math.RayHit;

public class Raymarcher {

	private int width;
	private int height;
	private int aaFactor;
	private BufferedImage buffer;
	
	private Color clearColor      = null;
	
	public float maximumDistance  = 1000f;
	public float minimumDistance  = 0.01f;
	public float distanceBias     = 0.85f;
	public int stepsPerRaymarch   = 512;
	public int stepsPerShadow     = 128;
	public int maxBounces         = 24;

	public Raymarcher(int width, int height) {
		this(width, height, 1);
	}
	
	public Raymarcher(int width, int height, int aa) {
		this.aaFactor = aa;
		this.resize(width, height);
	}

	public int getWidth() {
		return this.width * this.aaFactor;
	}

	public int getHeight() {
		return this.height * this.aaFactor;
	}
	
	public BufferedImage getColorBuffer() {
		return this.buffer;
	}
	
	public BufferedImage resolveAA() {
		if(this.aaFactor == 1) {
			return this.getColorBuffer();
		}
		BufferedImage out = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D    g2d = out.createGraphics();
		
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.drawImage(this.getColorBuffer(), 0, 0, this.width, this.height, 0, 0, getWidth(), getHeight(), null);
		g2d.dispose();
		
		return out;
	}
	
	public Color getClearColor() {
		return this.clearColor == null ? Color.black : this.clearColor;
	}
	
	public void setClearColor(Color color) {
		this.clearColor = color;
	}
	
	public void clearBuffer() {
		int color = this.getClearColor().getRGB();
		for(int x = 0; x < this.getWidth(); x++) {
			for(int y = 0; y < this.getHeight(); y++) {
				this.buffer.setRGB(x, y, color);
			}
		}
	}
	
	public void setBufferColor(int x, int y, int rgb) {
		this.buffer.setRGB(x, y, rgb);
	}

	public void resize(int width, int height) {
		this.width  = width;
		this.height = height;
		this.buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
	}
	
	public boolean hasAA() {
		return this.getAA() != 1;
	}
	
	public int getAA() {
		return this.aaFactor;
	}
	
	public void setAA(int aa) {
		this.aaFactor = Math.max(aa, 1);
		this.resize(this.width, this.height);
	}
	
	public RayHit marchRay(Ray ray, Scene scene) {
		return marchRay(ray, scene, 0f, this.maximumDistance);
	}
	
	public RayHit marchRay(Ray ray, Scene scene, float startDistance, float maxDistance) {
		float t = startDistance;
		int id  = -1;
		for(int i = 0; i < this.stepsPerRaymarch; i++) {
			Distance d = scene.getClosest(ray.getPoint(t));
			if(d.dist < this.minimumDistance || t > maxDistance) {
				id = t < maxDistance ? d.id : -1;
				break;
			}
			t += d.dist * this.distanceBias;
		}
		return new RayHit(ray, this, t, id);
	}
	
	public Color getRayColor(Ray ray, Scene scene, int bounce) {
		RayHit hit = marchRay(ray, scene);
		if(hit.materialID != -1 && bounce < this.maxBounces) {
			ISceneObject object = scene.getObject(hit.materialID);
			if(object != null) {
				return object.shadePixel(hit, scene, bounce);
			}
		}
		return getClearColor();
	}

}