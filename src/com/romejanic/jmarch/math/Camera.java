package com.romejanic.jmarch.math;

public class Camera {

	public Vec3 position  = new Vec3();
	public Vec3 rotation  = new Vec3(0f, 0f, 0f);
	
	public Camera(Vec3 position) {
		this(position.x, position.y, position.z);
	}
	
	public Camera(float x, float y, float z) {
		this.position.set(x, y, z);
	}
	
	public Camera(Vec3 position, Vec3 rotation) {
		this(position);
		this.rotation.set(rotation.x, rotation.y, rotation.z);
	}
	
	public Mat3 getLookMatrix() {
		float rx = Mathf.rad(this.rotation.x);
		float ry = Mathf.rad(this.rotation.y);
		float rz = Mathf.rad(this.rotation.z);
		return Mathf.rotateXYZ(rx, ry, rz);
	}
	
}