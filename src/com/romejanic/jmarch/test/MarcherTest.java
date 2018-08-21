package com.romejanic.jmarch.test;

import java.awt.Color;
import java.io.File;

import com.romejanic.jmarch.ISceneObject;
import com.romejanic.jmarch.Raymarcher;
import com.romejanic.jmarch.Scene;
import com.romejanic.jmarch.math.Mathf;
import com.romejanic.jmarch.math.RayHit;
import com.romejanic.jmarch.math.Vec3;
import com.romejanic.jmarch.render.SceneRenderer;

public class MarcherTest {

	public static void main(String[] args) throws Exception {
		Raymarcher raymarcher = new Raymarcher(640, 480);
		raymarcher.setClearColor(new Color(0.4f, 0.6f, 0.9f, 1f));
		
		Scene scene = new Scene();
		for(int i = 0; i < 10; i++) {
			float theta = ((float)i / 10f) * 2f * (float)Math.PI;
			float x     = Mathf.cos(theta) * 4f;
			float y     = Mathf.sin(theta) * 4f;
			float r     = 0.1f + i * 0.1f;
			
			scene.addObject(new Sphere(new Vec3(x, y, 10f), r));
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
			return Vec3.toColor(n);
		}
		
	}
	
}