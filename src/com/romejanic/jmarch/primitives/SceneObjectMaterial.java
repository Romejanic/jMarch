package com.romejanic.jmarch.primitives;

import java.awt.Color;

import com.romejanic.jmarch.ISceneObject;
import com.romejanic.jmarch.Scene;
import com.romejanic.jmarch.lighting.Material;
import com.romejanic.jmarch.lighting.materials.PhongMaterial;
import com.romejanic.jmarch.math.RayHit;

public abstract class SceneObjectMaterial implements ISceneObject {

	private Material material;
	
	public SceneObjectMaterial() {
		this(new PhongMaterial(Color.white));
	}
	
	public SceneObjectMaterial(Material material) {
		this.material = material;
	}
	
	public Material getMaterial(RayHit hit, Scene scene) {
		return this.material;
	}
	
	public SceneObjectMaterial setMaterial(Material material) {
		this.material = material;
		return this;
	}

	@Override
	public Color shadePixel(RayHit hit, Scene scene, int bounces) {
		if(this.material != null) {
			return this.material.shadePixel(hit, scene, bounces);
		}
		return null;
	}

}