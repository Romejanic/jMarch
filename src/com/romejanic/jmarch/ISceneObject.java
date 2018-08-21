package com.romejanic.jmarch;

import java.awt.Color;

import com.romejanic.jmarch.math.RayHit;
import com.romejanic.jmarch.math.Vec3;

public interface ISceneObject {

	float getDistance(Vec3 p);
	
	Color shadePixel(RayHit hit, Scene scene);
	
}