# jMarch
An open-source raymarching engine for Java.

Provides an interface for building a scene with objects and lights, and rendering it with the raymarching algorithm, which uses signed distance fields (SDFs) to define geometry, allowing for creative and interesting images, or realistic simulation of fog, light scatter, clouds and other volume-based objects.

The algorithms implemented in this library are based heavily on the work of Inigo Quilez (creator of [Shadertoy](http://shadertoy.com/)). If you would like to learn more about raymarching and SDFs, you can read up [here](http://iquilezles.org/www/material/nvscene2008/rwwtt.pdf) and [here](http://iquilezles.org/www/articles/raymarchingdf/raymarchingdf.htm).

# Features
- Fully-featured raymarching renderer.
- Raymarching of signed distance fields, with lighting and reflection support.
- Recursive reflection, allowing for realistic light interaction.
- Realistic soft shadow simulation on all lights.
- Directional and point light support.
- Material-based rendering system, along with manual implementation.
- Base classes for all scene objects/lights/materials, allowing for custom implementations.
- Anti-aliasing support.
- Render a frame with a single line of code.
- Save framebuffer to a file, or render file sequences.
- Export as PNG image, PNG sequence or animated GIF.
- Render options are completely customisable.

# Planned features
- Textures.
- Fog and light scattering.
- Refraction.
- Global illumination.
- Spotlights.
- More primitives.

# Example
### Drawing a sphere
```java
// make a 640x480 framebuffer with 2x antialiasing
Raymarcher raymarcher = new Raymarcher(640, 480, 2);
// set the clear color to a sky blue
raymarcher.setClearColor(new Color(0.4f, 0.6f, 0.9f));
// create a new scene
Scene scene = new Scene();

// add a sphere at (0,0,0) with radius 1
scene.addObject(new Sphere(new Vec3(0f, 0f, 0f), 1f));

// render the scene into the framebuffer
SceneRenderer.renderScene(scene, raymarcher);
// save the result to a PNG file
SceneRenderer.savePNGToFile(raymarcher, new File("render.png"));
```