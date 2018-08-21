package com.romejanic.jmarch.math;

public class RayHit {

	public final Ray ray;
	public final float distance;
	public final int materialID;
	
	public RayHit(Ray ray, float distance, int materialID) {
		this.ray = ray;
		this.distance = distance;
		this.materialID = materialID;
	}
	
}