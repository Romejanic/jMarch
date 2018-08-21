package com.romejanic.jmarch;

import com.romejanic.jmarch.math.Vec3;

public class Scene {

	public Distance getClosest(Vec3 p) {
		return new Distance(0f, -1); // TODO: stub method
	}
	
	public class Distance {
		public float dist;
		public int id;
		
		public Distance(float dist, int id) {
			this.dist = dist;
			this.id   = id;
		}
	}
	
}