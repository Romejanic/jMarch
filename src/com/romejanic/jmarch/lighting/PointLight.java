package com.romejanic.jmarch.lighting;

import com.romejanic.jmarch.math.Vec3;

public class PointLight extends Light {

	public float range = 10f;
	
	public PointLight(Vec3 position) {
		super(position);
	}
	
	public PointLight(Vec3 position, float range) {
		this(position);
		this.range = range;
	}

	@Override
	public Vec3 getLightDirection(Vec3 p) {
		return Vec3.sub(this.position, p);
	}
	
	@Override
	public float getAttenuation(Vec3 p, Vec3 direction, float distance) {
		return 1f - ((distance*distance)/(this.range*this.range));
	}

}
