package com.romejanic.jmarch.test;

import java.awt.Color;
import java.io.File;

import com.romejanic.jmarch.Raymarcher;
import com.romejanic.jmarch.Scene;
import com.romejanic.jmarch.lighting.DirectionalLight;
import com.romejanic.jmarch.lighting.PointLight;
import com.romejanic.jmarch.lighting.materials.PhongMaterial;
import com.romejanic.jmarch.math.Mathf;
import com.romejanic.jmarch.math.Vec3;
import com.romejanic.jmarch.primitives.Floor;
import com.romejanic.jmarch.primitives.Sphere;
import com.romejanic.jmarch.render.SceneRenderer;

public class MarcherTest {

	public static void main(String[] args) throws Exception {
		//Debug.ENABLED = true;
		
		Raymarcher raymarcher = new Raymarcher(640, 480);
		raymarcher.setClearColor(new Color(0.4f, 0.6f, 0.9f, 1f));
		
		Scene scene = new Scene();
		scene.addObject(new Floor(Vec3.UP, 5f).setMaterial(new PhongMaterial(Color.green)));
		for(int i = 0; i < 10; i++) {
			float theta = ((float)i / 10f) * 2f * (float)Math.PI;
			float x     = Mathf.cos(theta) * 4f;
			float y     = Mathf.sin(theta) * 4f;
			float r     = 0.1f + i * 0.1f;
			
			scene.addObject(new Sphere(new Vec3(x, y, 10f), r).setMaterial(new PhongMaterial(Color.red)));
		}
		
		scene.addLight(new DirectionalLight(new Vec3(45f, 30f, -45f)));
		PointLight pl = (PointLight)scene.addLight(new PointLight(new Vec3(0f, 0f, 10f), 30f));
		pl.color = Color.green;
		
		SceneRenderer.renderScene(scene, raymarcher);
		SceneRenderer.savePNGToFile(raymarcher, new File("render/marchertest.png"));
	}
	
}