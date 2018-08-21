package com.romejanic.jmarch.render;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.romejanic.jmarch.Raymarcher;
import com.romejanic.jmarch.Scene;
import com.romejanic.jmarch.debug.Debug;
import com.romejanic.jmarch.math.Mathf;
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

		Debug.print("[---------------] 0% Complete");

		int width    = raymarcher.getWidth();
		int height   = raymarcher.getHeight();
		int total    = width * height;
		int complete = 0;
		long start   = System.currentTimeMillis();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				float u = ((float)x-(float)width*0.5f)/(float)height;
				float v = ((float)y-(float)height*0.5f)/(float)height;
				u *= flipX ? -1f : 1f;
				v *= flipY ? -1f : 1f;
				dir.set(u, v, 1f);
				Vec3.normalize(dir, dir);

				Color c = raymarcher.getRayColor(new Ray(ori, dir), scene, 0);
				raymarcher.setBufferColor(x, y, c.getRGB());

				if(Debug.ENABLED) {
					complete++;
					float progress = Mathf.clamp01((float)complete / (float)total);
					int bars = (int)(progress * 15f);
					
					String out = "\r[";
					for(int i = 0; i < 15; i++) {
						out += i < bars ? "█" : '-';
					}
					out += "] ";
					out += String.format("%.2f", progress * 100f);
					out += "% Complete (" + complete + "/" + total + ")";
					
					Debug.print(out);
				}
			}
		}
		
		long time = System.currentTimeMillis() - start;
		Debug.println();
		Debug.println("\rFrame complete! (took " + (time/1000) + "s)");
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