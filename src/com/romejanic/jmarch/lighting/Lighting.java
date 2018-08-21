package com.romejanic.jmarch.lighting;

import java.awt.Color;
import java.util.Iterator;

import com.romejanic.jmarch.Raymarcher;
import com.romejanic.jmarch.Scene;
import com.romejanic.jmarch.math.Mathf;
import com.romejanic.jmarch.math.Ray;
import com.romejanic.jmarch.math.Vec3;

public class Lighting {
	
	public static Color calculateLighting(Color albedo, Vec3 p, Vec3 n, Vec3 r, Ray ray, Scene scene, Raymarcher raymarcher) {
		return calculateLighting(Vec3.fromColor(albedo), p, n, r, ray, scene, raymarcher);
	}

	public static Color calculateLighting(Vec3 albedo, Vec3 p, Vec3 n, Vec3 r, Ray ray, Scene scene, Raymarcher raymarcher) {
		Vec3 totalDiffuse  = new Vec3();
		Vec3 totalSpecular = new Vec3();
		
		Iterator<Light> iter = scene.getLightIterator();
		while(iter.hasNext()) {
			Light light = iter.next();
			
			Vec3  lv = light.getLightDirection(p);
			float ld = lv.length();
			Vec3.mul(lv, 1f / ld, lv);
			
			float shado = calculateShadow(p, lv, ld, light, scene, raymarcher);
			float atten = Math.max(light.getAttenuation(p, lv, ld) * shado, 0f);
			float diff  = Math.max(Vec3.dot(lv, n), 0f) * atten;
			float spec  = Mathf.pow(Math.max(Vec3.dot(lv, r), 0f), 60f) * atten;
			
			Vec3 col = Vec3.mul(Vec3.fromColor(light.color), light.intensity);
			Vec3.add(totalDiffuse, Vec3.mul(col, diff), totalDiffuse);
			Vec3.add(totalSpecular, Vec3.mul(col, spec), totalSpecular);
		}
		
		return Vec3.toColor(Vec3.add(Vec3.mul(albedo, totalDiffuse), totalSpecular));
	}
	
	public static float calculateShadow(Vec3 p, Vec3 lv, float ld, Light light, Scene scene, Raymarcher raymarcher) {
		Ray ray = new Ray(p, lv);
		if(light instanceof DirectionalLight) {
			ld = raymarcher.maximumDistance;
		}
		switch(light.shadowType) {
		case SOFT:
			//Soft shadows by iq: https://www.shadertoy.com/view/Xds3zN
			float res = 1f;
			float   t = 0.05f;
			for(int i = 0; i < raymarcher.stepsPerRaymarch; i++) {
				float h = scene.getClosest(ray.getPoint(t)).dist;
				res = Math.min(res, light.shadowHardness*h/t);
				t += Mathf.clamp(h, 0.02f, 0.5f);
				if(h < 0.001f || t > ld) break;
			}
			return Mathf.clamp01(res);
		case HARD:
			if(raymarcher.marchRay(ray, scene, 0.05f, ld).distance < ld) {
				return 0f;
			}
		case NONE:
		default:
			return 1f;
		}
	}
	
}