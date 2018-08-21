package com.romejanic.jmarch.math;

import com.romejanic.jmarch.Raymarcher;

public class RayHit {

	public final Ray ray;
	public final Raymarcher raymarcher;
	public final float distance;
	public final int materialID;
	
	public RayHit(Ray ray, Raymarcher raymarcher, float distance, int materialID) {
		this.ray = ray;
		this.raymarcher = raymarcher;
		this.distance = distance;
		this.materialID = materialID;
	}
	
	public Vec3 getHitPosition() {
		return this.ray.getPoint(this.distance);
	}
	
}