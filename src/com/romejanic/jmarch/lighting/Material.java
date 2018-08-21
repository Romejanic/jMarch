package com.romejanic.jmarch.lighting;

import java.awt.Color;

import com.romejanic.jmarch.Scene;
import com.romejanic.jmarch.math.RayHit;

public interface Material {
	
	Color shadePixel(RayHit hit, Scene scene, int bounces);
	
}