package com.romejanic.jmarch.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.romejanic.jmarch.Raymarcher;
import com.romejanic.jmarch.Scene;
import com.romejanic.jmarch.lighting.PointLight;
import com.romejanic.jmarch.lighting.materials.PhongMaterial;
import com.romejanic.jmarch.math.Mathf;
import com.romejanic.jmarch.math.Vec3;
import com.romejanic.jmarch.primitives.Floor;
import com.romejanic.jmarch.primitives.Sphere;
import com.romejanic.jmarch.render.SceneRenderer;

public class RealtimeTest extends JFrame {

	private static final long serialVersionUID = 5831148026916856319L;

	private Raymarcher raymarcher = new Raymarcher(320, 240);
	private Scene scene = new Scene();
	
	public RealtimeTest() {
		super("Raymarcher Test");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(this.raymarcher.getWidth() * 2, this.raymarcher.getHeight() * 2);
		
		ImagePane pane = new ImagePane(this.raymarcher.getColorBuffer());
		pane.setSize(this.getWidth(), this.getHeight());
		getContentPane().add(pane, BorderLayout.CENTER);
		
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		SceneRenderer.renderScene(this.scene, this.raymarcher);
		super.paint(g);
	}
	
	public static void main(String[] args) {
		RealtimeTest frame = new RealtimeTest();
		frame.raymarcher.maxBounces       = 2;
		frame.raymarcher.stepsPerRaymarch = 32;
		frame.raymarcher.stepsPerShadow   = 20;
		frame.raymarcher.maximumDistance  = 20f;
		frame.raymarcher.setClearColor(new Color(0.4f, 0.6f, 0.9f, 1f));
		
		Sphere[] spheres = new Sphere[10];
		float    gap     = 1f / (float)spheres.length;
		for(int i = 0; i < spheres.length; i++) {
			float theta = ((float)i*gap) * 2f * (float)Math.PI;
			float x     = Mathf.cos(theta) * 4f;
			float z     = Mathf.sin(theta) * 4f;
			float r     = gap + gap * i;
			
			Sphere sphere = new Sphere(new Vec3(x, 0f, z), r);
			sphere.setMaterial(new PhongMaterial(Color.red));
			spheres[i] = (Sphere)frame.scene.addObject(sphere);
		}
		//frame.scene.addObject(new Floor(Vec3.UP, 1f).setMaterial(new PhongMaterial(Color.green)));
		frame.scene.addLight(new PointLight(new Vec3(0f, 0f, -3f)));
		
		float t = 0f;
		long  l = System.currentTimeMillis();
		while(frame.isVisible()) {
			long time = System.currentTimeMillis();
			float dlt = (float)(time - l) / 1000f;
			l = time;
			t += dlt;
			
			float offset = 2f * (float)Math.PI * t;
			for(int i = 0; i < spheres.length; i++) {
				float theta = ((float)i*gap) * 2f * (float)Math.PI;
				float x     = Mathf.cos(theta + offset) * 4f;
				float z     = Mathf.sin(theta + offset) * 4f;
				
				spheres[i].position.set(x, 0f, z);
			}
			
			frame.repaint();
		}
		frame.dispose();
	}
	
	private class ImagePane extends JPanel {
		
		private static final long serialVersionUID = -7160879168262365918L;
		private Image image;
		
		public ImagePane(Image image) {
			this.image = image;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			int pw = this.getWidth();
			int ph = this.getHeight();
			int iw = this.image.getWidth(this);
			int ih = this.image.getHeight(this);
			g.drawImage(this.image, 0, 0, pw, ph, 0, 0, iw, ih, this);
		}
		
	}
	
}