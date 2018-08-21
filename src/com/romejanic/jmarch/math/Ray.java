package com.romejanic.jmarch.math;

public class Ray {

	public Vec3 origin;
	public Vec3 direction;
	
	public Ray(Vec3 origin) {
		this(origin, new Vec3(0f, 0f, 1f));
	}
	
	public Ray(Vec3 origin, Vec3 direction) {
		this.origin    = origin;
		this.direction = Vec3.normalize(direction);
	}
	
	public Vec3 getUnitDirection() {
		if(this.direction.lengthSqr() != 1f) {
			Vec3.normalize(this.direction, this.direction);
		}
		return this.direction;
	}
	
	public Vec3 getPoint(float d) {
		return Vec3.add(this.origin, Vec3.mul(getUnitDirection(), d));
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[origin=" + this.origin + ",direction=" + this.direction + "]";
	}
	
}