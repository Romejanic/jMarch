package com.romejanic.jmarch.test;

import java.awt.Color;
import java.io.File;

import com.romejanic.jmarch.Raymarcher;
import com.romejanic.jmarch.Scene;
import com.romejanic.jmarch.debug.Debug;
import com.romejanic.jmarch.lighting.DirectionalLight;
import com.romejanic.jmarch.lighting.PointLight;
import com.romejanic.jmarch.lighting.materials.PhongMaterial;
import com.romejanic.jmarch.math.Camera;
import com.romejanic.jmarch.math.Mathf;
import com.romejanic.jmarch.math.Vec3;
import com.romejanic.jmarch.primitives.Floor;
import com.romejanic.jmarch.primitives.Sphere;
import com.romejanic.jmarch.render.SceneRenderer;

public class SequenceTest {

	public static boolean FILE_SEQUENCE = false;
	
	public static void main(String[] args) throws Exception {
		Raymarcher raymarcher = new Raymarcher(320, 200);
		Scene scene = new Scene();
		Camera camera = new Camera(0f, 0f, -3f);
		
		scene.addObject(new Floor(Vec3.UP, 5f).setMaterial(new PhongMaterial(Color.green)));
		
		Sphere[] spheres = new Sphere[10];
		float    gap     = 1f / (float)spheres.length;
		for(int i = 0; i < spheres.length; i++) {
			float theta = ((float)i*gap) * 2f * (float)Math.PI;
			float x     = Mathf.cos(theta) * 4f;
			float y     = Mathf.sin(theta) * 4f;
			float r     = gap + gap * i;
			
			Sphere sphere = new Sphere(new Vec3(x, y, 10f), r);
			sphere.setMaterial(new PhongMaterial(Color.red));
			spheres[i] = (Sphere)scene.addObject(sphere);
		}
		
		scene.addLight(new DirectionalLight(new Vec3(45f, 30f, -45f)));
		scene.addLight(new PointLight(new Vec3(0f, 0f, 10f)));
		
		Runnable updateLoop = new Runnable() {
			@Override
			public void run() {
				float offset = 2f * (float)Math.PI * SceneRenderer.globalTime;
				for(int i = 0; i < spheres.length; i++) {
					float theta = ((float)i*gap) * 2f * (float)Math.PI;
					float x     = Mathf.cos(theta + offset) * 4f;
					float y     = Mathf.sin(theta + offset) * 4f;
					
					spheres[i].position.set(x, y, 10f);
				}
				
				camera.rotation.y = Mathf.deg(offset);
			}
		};
		
		Debug.ENABLED = true;
		if(FILE_SEQUENCE) {
			SceneRenderer.renderFileSequencePNG(raymarcher, camera, scene, new File("render/sequence"), "test", 1f, 24, updateLoop);
		} else {
			SceneRenderer.renderSequenceGif(raymarcher, camera, scene, new File("render/sequence_1.gif"), true, 1f, 24, updateLoop);
		}
	}
	
}