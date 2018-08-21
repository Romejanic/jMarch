package com.romejanic.jmarch.test;

import java.awt.Color;
import java.io.File;

import com.romejanic.jmarch.ISceneObject;
import com.romejanic.jmarch.Raymarcher;
import com.romejanic.jmarch.Scene;
import com.romejanic.jmarch.lighting.DirectionalLight;
import com.romejanic.jmarch.lighting.Lighting;
import com.romejanic.jmarch.lighting.PointLight;
import com.romejanic.jmarch.math.Mathf;
import com.romejanic.jmarch.math.RayHit;
import com.romejanic.jmarch.math.Vec3;
import com.romejanic.jmarch.render.SceneRenderer;

public class MarcherTest {

	public static void main(String[] args) throws Exception {
		Raymarcher raymarcher = new Raymarcher(640, 480);
		raymarcher.setClearColor(new Color(0.4f, 0.6f, 0.9f, 1f));
		
		Scene scene = new Scene();
		scene.addObject(new Plane(Vec3.UP, 5f));
		for(int i = 0; i < 10; i++) {
			float theta = ((float)i / 10f) * 2f * (float)Math.PI;
			float x     = Mathf.cos(theta) * 4f;
			float y     = Mathf.sin(theta) * 4f;
			float r     = 0.1f + i * 0.1f;
			
			scene.addObject(new Sphere(new Vec3(x, y, 10f), r));
		}
		
		scene.addLight(new DirectionalLight(new Vec3(45f, 30f, -45f)));
		PointLight pl = (PointLight)scene.addLight(new PointLight(new Vec3(0f, 0f, 10f), 30f));
		pl.color = Color.green;
		
		for(int i = 0; i < 50; i++) {
			float x = Mathf.random(-50f, 50f);
			float z = Mathf.random(-50f, 50f);
			PointLight l = (PointLight)scene.addLight(new PointLight(new Vec3(x, 0f, z)));
			l.range = Mathf.random(2f, 20f);
			l.color = new Color(Mathf.random(), Mathf.random(), Mathf.random());
			l.intensity = Mathf.random(0.2f, 3f);
		}
		
		SceneRenderer.renderScene(scene, raymarcher);
		SceneRenderer.savePNGToFile(raymarcher, new File("render/marchertest.png"));
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
		public Color shadePixel(RayHit hit, Scene scene) {
			Vec3 p = hit.getHitPosition();
			Vec3 n = scene.calcSurfaceNormal(p, hit);
			Vec3 r = Vec3.normalize(Vec3.reflect(hit.ray.direction, n));
			
			Vec3 albedo = new Vec3(1f, 0.2f, 0.2f);
			return Lighting.calculateLighting(albedo, p, n, r, hit.ray, scene, hit.raymarcher);
		}
		
	}
	
	private static class Plane implements ISceneObject {

		private Vec3 normal;
		private float offset;
		
		public Plane(Vec3 normal, float offset) {
			this.normal = normal;
			this.offset = offset;
		}
		
		@Override
		public float getDistance(Vec3 p) {
			return Vec3.dot(p, this.normal) + this.offset;
		}

		@Override
		public Color shadePixel(RayHit hit, Scene scene) {
			Vec3 p = hit.getHitPosition();
			Vec3 n = scene.calcSurfaceNormal(p, hit);
			Vec3 r = Vec3.normalize(Vec3.reflect(hit.ray.direction, n));
			
			Vec3 albedo = new Vec3(0.2f, 1f, 0.2f);
			return Lighting.calculateLighting(albedo, p, n, r, hit.ray, scene, hit.raymarcher);
		}
		
	}
	
}