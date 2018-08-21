package com.romejanic.jmarch;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import com.romejanic.jmarch.lighting.Light;
import com.romejanic.jmarch.math.RayHit;
import com.romejanic.jmarch.math.Vec3;

public class Scene {
	
	private static final ArrayList<ISceneObject> objects = new ArrayList<ISceneObject>();
	private static final ArrayList<Light> lights = new ArrayList<Light>();
	
	public Color ambientColor = new Color(0.1f, 0.1f, 0.2f);
	
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
	
	public ISceneObject getObject(int id) {
		if(id < 0 || id >= objects.size()) {
			throw new IllegalArgumentException("ID out of bounds!");
		}
		return objects.get(id);
	}
	
	public Light addLight(Light light) {
		if(!lights.contains(light)) {
			lights.add(light);
		}
		return light;
	}
	
	public Light removeLight(Light light) {
		if(lights.contains(light)) {
			lights.remove(light);
		}
		return light;
	}

	public Iterator<Light> getLightIterator() {
		return lights.iterator();
	}
	
	public Distance getClosest(Vec3 p) {
		if(objects.isEmpty()) {
			return new Distance(0f, -1);
		}
		Distance d = new Distance(Float.MAX_VALUE, -1);
		for(int i = 0; i < objects.size(); i++) {
			ISceneObject object = objects.get(i);
			float dist = object.getDistance(p);
			if(dist < d.dist) {
				d.dist = dist;
				d.id   = i;
			}
		}
		return d;
	}
	
	public Vec3 calcSurfaceNormal(Vec3 p, RayHit hit) {
		Vec3 n  = new Vec3();
		float e = hit.distance * 0.01f;
		n.x = getClosest(offset(p, e, 0f, 0f)).dist - getClosest(offset(p, -e, 0f, 0f)).dist;
		n.y = getClosest(offset(p, 0f, e, 0f)).dist - getClosest(offset(p, 0f, -e, 0f)).dist;
		n.z = getClosest(offset(p, 0f, 0f, e)).dist - getClosest(offset(p, 0f, 0f, -e)).dist;
		return Vec3.normalize(n, n);
	}
	
	private Vec3 offset(Vec3 v, float x, float y, float z) {
		return Vec3.add(v, new Vec3(x, y, z));
	}
	
	public Color getAmbientColor(Vec3 direction) {
		return this.ambientColor;
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