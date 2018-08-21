package com.romejanic.jmarch.primitives;

import com.romejanic.jmarch.math.Vec3;

public class Sphere extends SceneObjectMaterial {

	public Vec3 position;
	public float radius;
	
	public Sphere(Vec3 position, float radius) {
		this.position = position;
		this.radius   = radius;
	}
	
	@Override
	public float getDistance(Vec3 p) {
		return Vec3.sub(this.position, p).length() - this.radius;
	}

}