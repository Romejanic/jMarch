package com.romejanic.jmarch.render;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.romejanic.jmarch.Raymarcher;
import com.romejanic.jmarch.Scene;
import com.romejanic.jmarch.math.Ray;
import com.romejanic.jmarch.math.Vec3;

public class SceneRenderer {

	public static Vec3 CAMERA_POS = new Vec3(0f, 0f, -3f);
	
	public static boolean flipX = false;
	public static boolean flipY = true;
	
	public static void renderScene(Scene scene, Raymarcher raymarcher) {
		raymarcher.clearBuffer();
		
		Vec3 ori = CAMERA_POS; // TODO: implement a camera class and find this
		Vec3 dir = new Vec3(0f);
		for(int x = 0; x < raymarcher.getWidth(); x++) {
			for(int y = 0; y < raymarcher.getHeight(); y++) {
				float u = ((float)x-(float)raymarcher.getWidth()/2f)/(float)raymarcher.getHeight();
				float v = ((float)y-(float)raymarcher.getHeight()/2f)/(float)raymarcher.getHeight();
				u *= flipX ? -1f : 1f;
				v *= flipY ? -1f : 1f;
				dir.set(u, v, 1f);
				Vec3.normalize(dir, dir);
				
				Color c = raymarcher.getRayColor(new Ray(ori, dir), scene, 0);
				raymarcher.setBufferColor(x, y, c.getRGB());
			}
		}
	}
	
	public static void savePNGToFile(Raymarcher raymarcher, File file) throws IOException {
		if((!file.exists() && !file.createNewFile()) || file.isDirectory()) {
			throw new IllegalArgumentException("Given file cannot be a directory, or could not be created!");
		}
		savePNGToStream(raymarcher, new FileOutputStream(file));
	}
	
	public static void savePNGToStream(Raymarcher raymarcher, OutputStream stream) throws IOException {
		ImageIO.write(raymarcher.resolveAA(), "PNG", stream);
	}
	
}