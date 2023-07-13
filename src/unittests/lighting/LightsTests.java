package unittests.lighting;

import org.junit.jupiter.api.Test;

import lighting.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;
import static java.awt.Color.*;

/**
 * Test rendering a basic image
 * 
 * @author Dan
 */
public class LightsTests {
	private Scene scene1 = new Scene("Test scene");
	private Scene scene2 = new Scene("Test scene") //
			.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
	private Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(150d, 150) //
			.setVPDistance(1000);
	private Camera camera2 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(200d, 200) //
			.setVPDistance(1000);

	private Point[] p = { // The Triangles' vertices:
			new Point(-110, -110, -150), // the shared left-bottom
			new Point(95, 100, -150), // the shared right-top
			new Point(110, -110, -150), // the right-bottom
			new Point(-75, 78, 100) }; // the left-top
	private Point trPL = new Point(30, 10, -100); // Triangles test Position of Light
	private Point spPL = new Point(-50, -50, 25); // Sphere test Position of Light
	private Color trCL = new Color(800, 500, 250); // Triangles test Color of Light
	private Color spCL = new Color(800, 500, 0); // Sphere test Color of Light
	private Vector trDL = new Vector(-2, -2, -2); // Triangles test Direction of Light
	private Material material = new Material().setKd(0.5).setKs(0.5).setShininess(300);
	private Geometry triangle1 = new Triangle(p[0], p[1], p[2]).setMaterial(material);
	private Geometry triangle2 = new Triangle(p[0], p[1], p[3]).setMaterial(material);
	private Geometry sphere = new Sphere(50d, new Point(0, 0, -50)) //
			.setEmission(new Color(BLUE).reduce(2)) //
			.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));

	/**
	 * Produce a picture of a sphere lighted by a directional light
	 */
	@Test
	public void sphereDirectional() {
		scene1.geometries.add(sphere);
		scene1.lightSources.add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));

		ImageWriter imageWriter = new ImageWriter("lightSphereDirectional2", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a sphere lighted by a point light
	 */
	@Test
	public void spherePoint() {
		scene1.geometries.add(sphere);
		scene1.lightSources.add(new PointLight(spCL, spPL).setKl(0.001).setKq(0.0002));

		ImageWriter imageWriter = new ImageWriter("lightSpherePoint2", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void sphereSpot() {
		scene1.geometries.add(sphere);
		scene1.lightSources.add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5)).setKl(0.001).setKq(0.0001));

		ImageWriter imageWriter = new ImageWriter("lightSphereSpot2", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a two triangles lighted by a directional light
	 */
	@Test
	public void trianglesDirectional() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.lightSources.add(new DirectionalLight(trCL, trDL));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional2", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a two triangles lighted by a point light
	 */
	@Test
	public void trianglesPoint() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.lightSources.add(new PointLight(trCL, trPL).setKl(0.001).setKq(0.0002));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint2", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light
	 */
	@Test
	public void trianglesSpot() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.lightSources.add(new SpotLight(trCL, trPL, trDL).setKl(0.001).setKq(0.0001));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot2", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a sphere lighted by a narrow spot light
	 */
	@Test
	public void sphereSpotSharp() {
		scene1.geometries.add(sphere);
		scene1.lightSources.add(
				new SpotLight(spCL, spPL, new Vector(1, 1, -0.5))/* .setNarrowBeam(10) */.setKl(0.001).setKq(0.00004));

		ImageWriter imageWriter = new ImageWriter("lightSphereSpotSharp2", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a two triangles lighted by a narrow spot light
	 */
	@Test
	public void trianglesSpotSharp() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.lightSources.add(new SpotLight(trCL, trPL, trDL)/* .setNarrowBeam(10) */.setKl(0.001).setKq(0.00004));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesSpotSharp2", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * test for multi lights scene
	 */
	@Test
	public void multiLight() {

		Camera camera3 = new Camera(new Point(0, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1)) //
				.setVPSize(50d, 50) //
				.setVPDistance(100);

		Geometry sphere1 = new Sphere(2, new Point(20, -1.5, 0.8))
				.setMaterial(new Material().setKd(0.85).setKs(0.15).setShininess(100))
				.setEmission(new Color(yellow).reduce(4.0));

		Geometry sphere2 = new Sphere(2.6, new Point(30, 1.5, 0.5))
				.setMaterial(new Material().setKd(0).setKs(1).setShininess(300)).setEmission(new Color(red).scale(2));

		Scene scene3 = new Scene("towSphere")
				.setAmbientLight(new AmbientLight(new Color(white).scale(0.8), new Double3(0.8)))
				.setBackground(new Color(BLACK).scale(0.7));
		scene3.geometries.add(sphere1, sphere2);

		scene3.lightSources
				.add(new PointLight(new Color(green).scale(5), new Point(25, 0, 0)).setKq(0.7).setKl(0.2).setKc(0.1));

		scene3.lightSources.add(new DirectionalLight(new Color(white).reduce(6.2), new Vector(0, -1, 1)));

		scene3.lightSources
				.add(new SpotLight(new Color(white).reduce(2), new Point(18.5, -9, 8.5), new Vector(-1.5, 7.5, -7.7)));

		ImageWriter imageWriter = new ImageWriter("multilights2", 1000, 1000);
		camera3.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene3)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a two triangles lighted by a narrow spot light
	 */
	@Test
	public void trianglesMultiLight() {
		scene2.geometries.add(triangle1, triangle2);

		scene2.lightSources.add(
				new SpotLight(trCL, new Point(10, 30, -100), trDL)/* .setNarrowBeam(10) */.setKl(0.001).setKq(0.00004));

		scene2.lightSources.add(new DirectionalLight(new Color(white).reduce(2), new Vector(0, 0, -1)));
		ImageWriter imageWriter = new ImageWriter("trianglesMultiLight2", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a two triangles lighted by a narrow spot light
	 */
	@Test
	public void trianglesSpotSharp1() {
		Camera camera3 = new Camera(new Point(3, 3, 100), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(50d, 50) //
				.setVPDistance(100);

		Geometry triangle1 = new Triangle(new Point(0, 0, 20), new Point(10, 10, 0), new Point(-10, 10, 0))
				.setMaterial(new Material().setKd(0.1).setKs(0.9).setShininess(100))
				.setEmission(new Color(yellow).reduce(4.0));
		Geometry triangle2 = new Triangle(new Point(0, 0, 20), new Point(-10, 10, 0), new Point(-10, -10, 0))
				.setMaterial(new Material().setKd(0.1).setKs(0.9).setShininess(100))
				.setEmission(new Color(orange).reduce(4.0));
		Geometry triangle3 = new Triangle(new Point(0, 0, 20), new Point(-10, -10, 0), new Point(10, -10, 0))
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100))
				.setEmission(new Color(red).reduce(4.0));
		Geometry triangle4 = new Triangle(new Point(0, 0, 20), new Point(10, -10, 0), new Point(10, 10, 0))
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100))
				.setEmission(new Color(blue).reduce(8.0));

		Scene scene3 = new Scene("pyramid2")
				.setAmbientLight(new AmbientLight(new Color(white).scale(0.8), new Double3(0.8)))
				.setBackground(new Color(BLACK).scale(0.7));
		scene3.geometries.add(triangle1, triangle2, triangle3, triangle4);

		scene3.lightSources
				.add(new PointLight(new Color(white).reduce(2), new Point(2, 2, 20)).setKq(0.7).setKl(0.2).setKc(0.1));

		// scene3.lights.add(new DirectionalLight( new Color( white).reduce(6.2), new
		// Vector(30,25,-1)) );

		scene3.lightSources.add(new SpotLight(new Color(white).reduce(2), new Point(0, 0, 20), new Vector(0, 0, -1)));

		ImageWriter imageWriter = new ImageWriter("pyramid2", 500, 500);
		camera3.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene3)) //
				.renderImage() //
				.writeToImage(); //
	}

	/*
	 * create sphere with multi light
	 */
	@Test
	public void sphereMultiLight() {
		scene1.geometries.add(sphere);
		scene1.lightSources.add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));

		scene1.lightSources.add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5)).setKl(0.001).setKq(0.0001));

		scene1.lightSources.add(new SpotLight(new Color(green).scale(3), new Point(50, 50, 25), new Vector(-1, -1, -0.5))
				.setKl(0.001).setKq(0.0001));

		ImageWriter imageWriter = new ImageWriter("sphereMultiLight2", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}

}