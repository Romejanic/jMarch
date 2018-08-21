package com.romejanic.jmarch.lighting.materials;

import java.awt.Color;

import com.romejanic.jmarch.Scene;
import com.romejanic.jmarch.lighting.Lighting;
import com.romejanic.jmarch.lighting.Material;
import com.romejanic.jmarch.math.Mathf;
import com.romejanic.jmarch.math.RayHit;
import com.romejanic.jmarch.math.Vec3;

public class PhongMaterial implements Material {

	public Color albedoColor  = Color.white;
	
	public float fresnelMul   = 0.8f;
	public float fresnelPower = 1.25f;
	public float shininess    = 60f;
	
	public PhongMaterial(Color albedoColor) {
		this(albedoColor, 60f);
	}
	
	public PhongMaterial(Color albedoColor, float shininess) {
		this.albedoColor = albedoColor;
		this.shininess   = shininess;
	}
	
	@Override
	public Color shadePixel(RayHit hit, Scene scene, int bounces) {
		Vec3 p = hit.getHitPosition();
		Vec3 n = scene.calcSurfaceNormal(p, hit);
		Vec3 r = Vec3.normalize(Vec3.reflect(hit.ray.direction, n));
		
		Color light = Lighting.calculateLighting(this.albedoColor, p, n, r, hit.ray, scene, hit.raymarcher);
		Color refl  = Lighting.calculateReflections(p, r, hit, scene, bounces);

		return Mathf.mixColors(light, refl, this.fresnelMul * Mathf.fresnel(hit.ray.direction, n, this.fresnelPower));
	}

}
