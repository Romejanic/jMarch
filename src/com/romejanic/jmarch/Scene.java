package com.romejanic.jmarch;

import java.util.ArrayList;

import com.romejanic.jmarch.math.Vec3;

public class Scene {
	
	private static final ArrayList<ISceneObject> objects = new ArrayList<ISceneObject>();
	
	public ISceneObject addObject(ISceneObject object) {
		if(!objects.contains(object)) {
			objects.add(object);
		}
		return object;
	}
	
	public ISceneObject removeObject(ISceneObject object) {
		if(objects.contains(object)) {
			objects.remove(object);
		}
		return object;
	}

	public Distance getClosest(Vec3 p) {
		if(objects.isEmpty()) {
			return new Distance(0f, -1);
		}
		Distance d = new Distance(Float.MAX_VALUE, -1);
		for(ISceneObject object : objects) {
			float dist = object.getDistance(p);
			if(dist < d.dist) {
				d.dist = dist;
				d.id   = object.getMaterialID();
			}
		}
		return d;
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