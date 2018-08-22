package com.romejanic.jmarch.test;

import java.awt.Color;
import java.io.File;

import com.romejanic.jmarch.Raymarcher;
import com.romejanic.jmarch.Scene;
import com.romejanic.jmarch.debug.Debug;
import com.romejanic.jmarch.lighting.DirectionalLight;
import com.romejanic.jmarch.lighting.Light;
import com.romejanic.jmarch.lighting.PointLight;
import com.romejanic.jmarch.lighting.materials.PhongMaterial;
import com.romejanic.jmarch.math.Camera;
import com.romejanic.jmarch.math.Mathf;
import com.romejanic.jmarch.math.Vec3;
import com.romejanic.jmarch.primitives.Floor;
import com.romejanic.jmarch.primitives.Sphere;
import com.romejanic.jmarch.render.SceneRenderer;

public class HighDefTest {

	public static final int WIDTH  = 3840;
	public static final int HEIGHT = 2160;
	public static final int AA     = 4;
	
	public static void main(String[] args) throws Exception {
		Raymarcher raymarcher = new Raymarcher(WIDTH, HEIGHT, AA);
		Scene scene = new Scene();
		Camera camera = new Camera(0f, 0f, -3f);
		
		Sphere sphere = new Sphere(Vec3.ZERO, 1f) {
			public float getDistance(Vec3 p) {
				float dx = Mathf.sin(p.x * 10f);
				float dz = Mathf.cos(p.y * 10f);
				return super.getDistance(p) - (dx + dz) * 0.15f;
			}
		};
		sphere.setMaterial(new PhongMaterial(Color.red));
		scene.addObject(sphere);
		scene.addObject(new Floor(Vec3.UP, 1f));
		
		scene.addLight(new DirectionalLight(new Vec3(45f, 30, -45f)));
		
		Light light  = scene.addLight(new PointLight(new Vec3(1f, 0f, -1f), 15f));
		light.color  = Color.yellow;
		Light light1 = scene.addLight(new PointLight(new Vec3(0f, 0f, 2f), 10f));
		light1.color = Color.green;
		
		Debug.ENABLED = true;
		SceneRenderer.renderScene(scene, camera, raymarcher);
		SceneRenderer.savePNGToFile(raymarcher, new File("render/high_def_" + WIDTH + "x" + HEIGHT + ".png"));
	}
	
}