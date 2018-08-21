package com.romejanic.jmarch.lighting;

import com.romejanic.jmarch.math.Vec3;

public class DirectionalLight extends Light {

	public DirectionalLight(Vec3 direction) {
		super(direction);
	}

	@Override
	public Vec3 getLightDirection(Vec3 p) {
		return Vec3.normalize(this.position);
	}

	@Override
	public float getAttenuation(Vec3 p, Vec3 direction, float distance) {
		return 1f;
	}

}
