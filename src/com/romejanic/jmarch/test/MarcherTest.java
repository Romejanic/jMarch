package com.romejanic.jmarch.test;

import java.awt.Color;

import com.romejanic.jmarch.ISceneObject;
import com.romejanic.jmarch.Raymarcher;
import com.romejanic.jmarch.Scene;
import com.romejanic.jmarch.math.Ray;
import com.romejanic.jmarch.math.RayHit;
import com.romejanic.jmarch.math.Vec3;

public class MarcherTest {

	public static void main(String[] args) {
		Raymarcher raymarcher = new Raymarcher(640, 480);
		raymarcher.setClearColor(new Color(0.4f, 0.6f, 0.9f, 1f));
		
		Scene scene = new Scene();
		scene.addObject(new Sphere(new Vec3(0f, 0f, 0f), 1f));
		
		Ray ray = new Ray(new Vec3(0f, 0f, -3f), new Vec3(0f, 0f, 1f));
		Color c = raymarcher.getRayColor(ray, scene);
		System.out.println(c);
		
		Ray ray1 = new Ray(new Vec3(2f, 0f, -3f), Vec3.normalize(new Vec3(0.1f, 0f, 0.75f)));
		Color c1 = raymarcher.getRayColor(ray1, scene);
		System.out.println(c1);
	}
	
	private static class Sphere implements ISceneObject {

		private Vec3 position;
		private float radius;
		
		public Sphere(Vec3 position, float radius) {
			this.position = position;
			this.radius = radius;
		}
		
		@Override
		public float getDistance(Vec3 p) {
			return Vec3.sub(p, this.position).length() - this.radius;
		}

		@Override
		public int getMaterialID() {
			return 0;
		}

		@Override
		public Color shadePixel(RayHit hit, Scene scene, int material) {
			return null;
		}
		
	}
	
}