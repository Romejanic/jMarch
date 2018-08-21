package com.romejanic.jmarch.lighting;

import java.awt.Color;

import com.romejanic.jmarch.math.Vec3;

public abstract class Light {

	public Vec3 position;
	public Color color     = Color.white;
	public float intensity = 1f;
	
	public ShadowType shadowType = ShadowType.SOFT;
	public float shadowHardness  = 20f;
	
	public Light(Vec3 position) {
		this.position = position;
	}
	
	public abstract Vec3 getLightDirection(Vec3 p);
	public abstract float getAttenuation(Vec3 p, Vec3 direction, float distance);
	
}