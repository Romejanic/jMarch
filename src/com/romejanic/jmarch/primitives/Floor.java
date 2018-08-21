package com.romejanic.jmarch.primitives;

import com.romejanic.jmarch.math.Vec3;

public class Floor extends SceneObjectMaterial {

	public Vec3 normal;
	public float offset;
	
	public Floor(Vec3 normal, float offset) {
		this.normal = Vec3.normalize(normal, normal);
		this.offset = offset;
	}
	
	@Override
	public float getDistance(Vec3 p) {
		return Vec3.dot(this.normal, p) + this.offset;
	}

}