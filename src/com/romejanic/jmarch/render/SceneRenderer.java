package com.romejanic.jmarch.render;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import com.romejanic.jmarch.Raymarcher;
import com.romejanic.jmarch.Scene;
import com.romejanic.jmarch.debug.Debug;
import com.romejanic.jmarch.math.Camera;
import com.romejanic.jmarch.math.Mat3;
import com.romejanic.jmarch.math.Mathf;
import com.romejanic.jmarch.math.Ray;
import com.romejanic.jmarch.math.Vec3;

import elliotkroo.gifwriter.GifSequencer;

public class SceneRenderer {

	public static final Camera DEFAULT_CAMERA = new Camera(0f, 0f, -3f);
	public static float globalTime = 0f;

	public static boolean flipX = false;
	public static boolean flipY = true;

	public static void renderScene(Scene scene, Camera camera, Raymarcher raymarcher) {
		raymarcher.clearBuffer();

		Vec3 ori = camera.position;
		Vec3 dir = new Vec3(0f);
		Mat3 cam = camera.getLookMatrix();

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
				Mat3.transform(cam, dir, dir);
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

	public static void renderFileSequencePNG(Raymarcher raymarcher, Camera camera, Scene scene, File directory, String prefix, float duration, int framesPerSecond, Runnable onPrepareFrame) throws IOException {
		if(!directory.exists() && !directory.mkdirs()) {
			throw new IOException("Failed to create directory " + directory.getAbsolutePath());
		}
		float time  = 0f;
		float delta = 1f / (float)framesPerSecond;
		int nFrames = (int)(duration * (float)framesPerSecond);

		for(int n = 1; n <= nFrames; n++) {
			if(Debug.ENABLED) {
				float prog = Mathf.clamp01((float)n/(float)nFrames);
				Debug.println("* Starting frame " + n + " of " + nFrames + " (" + String.format("%.2f", prog) + "%)...");
			}
			SceneRenderer.globalTime = time;
			if(onPrepareFrame != null) {
				onPrepareFrame.run();
			}

			renderScene(scene, camera, raymarcher);
			savePNGToFile(raymarcher, new File(directory, prefix + "_" + n + ".png"));

			time += delta;
		}
	}

	public static void renderSequenceGif(Raymarcher raymarcher, Camera camera, Scene scene, File output, boolean loop, float duration, int framesPerSecond, Runnable onPrepareFrame) throws IOException {
		ImageOutputStream out = new FileImageOutputStream(output);
		GifSequencer gif = new GifSequencer(out, raymarcher.getColorBuffer().getType(), (int)((1f/(float)framesPerSecond) * 1000f), loop);

		float time  = 0f;
		float delta = 1f / (float)framesPerSecond;
		int nFrames = (int)(duration * (float)framesPerSecond);

		for(int n = 0; n < nFrames; n++) {
			Debug.println("* Starting frame " + n + " of " + nFrames + "...");
			SceneRenderer.globalTime = time;
			if(onPrepareFrame != null) {
				onPrepareFrame.run();
			}

			renderScene(scene, camera, raymarcher);
			gif.writeToSequence(raymarcher.resolveAA());
			time += delta;
		}

		gif.close();
		out.flush();
		out.close();
	}

	public static void savePNGToFile(Raymarcher raymarcher, File file) throws IOException {
		if(!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
			throw new IOException("Failed to create parent directory: " + file.getParent());
		}
		if((!file.exists() && !file.createNewFile()) || file.isDirectory()) {
			throw new IllegalArgumentException("Given file cannot be a directory, or could not be created!");
		}
		savePNGToStream(raymarcher, new FileOutputStream(file));
	}

	public static void savePNGToStream(Raymarcher raymarcher, OutputStream stream) throws IOException {
		ImageIO.write(raymarcher.resolveAA(), "PNG", stream);
	}

}