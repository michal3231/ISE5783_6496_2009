package renderer;

import java.util.MissingResourceException;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Util;

/**
 * implement the camera model
 * 
 * @author David
 *
 */
public class Camera {

	// camera location
	private final Point location;

	// camera direction
	private final Vector up;
	private final Vector to;
	private final Vector right;

	// distance of the view plan from the camera
	private double distance;

	// view plane size
	private double vpWidth;
	private double vpHeight;

	// the creator of the image
	private ImageWriter imageWriter;

	// Finding the cuts from the camera towards each pixel in the view plane
	private RayTracerBase rayTracer;

	/**
	 * A simple constructor that checks whether the vectors to and up are
	 * perpendicular to each other and produces the vector right. Throws an
	 * exception if the vectors are not perpendicular. keeps them normal.
	 * 
	 * @param location location of camera
	 * @param up       height direction of the camera
	 * @param to       direction of camera
	 */
	public Camera(Point location, Vector to, Vector up) {
		// check if the up vector and to vector are orthogonal each other
		if (!Util.isZero(up.dotProduct(to)))
			throw new IllegalArgumentException("the vector are not orthogonal to each other");

		this.location = location;
		this.up = up.normalize();
		this.to = to.normalize();

		// calculate the right vector
		this.right = to.crossProduct(up).normalize();
	}

	// ****** setters (builder pattern)*****//

	/**
	 * set width and height of the view plane return this camera (builder pattern)
	 *
	 * @param vpWidth  view plane width
	 * @param vpHeight view plane high
	 * @return this camera
	 */
	public Camera setVPSize(double vpWidth, double vpHeight) {
		if (Util.alignZero(vpHeight) <= 0 || Util.alignZero(vpHeight) <= 0)
			throw new IllegalArgumentException("ERROR negativ argument");

		this.vpWidth = vpWidth;
		this.vpHeight = vpHeight;
		return this;
	}

	/**
	 * set distance the camera from view plane (builder pattern)
	 * 
	 * @param distance view plane destination from camera
	 * @return this camera
	 */
	public Camera setVPDistance(double distance) {
		if (Util.alignZero(distance) <= 0)
			throw new IllegalArgumentException("ERROR negativ argument");
		this.distance = distance;
		return this;
	}

	/**
	 * rayTracerBase setter (builder pattern)
	 * 
	 * @param rayTracerBase Intersection of the camera beam with each pixel in the
	 *                      view plane
	 * @return this camera
	 */
	public Camera setRayTracer(RayTracerBase rayTracerBase) {
		this.rayTracer = rayTracerBase;
		return this;
	}

	/**
	 * imageWriter setter (builder pattern)
	 * 
	 * @param imageWriter The creator of the image
	 * @return this camera
	 */
	public Camera setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}

	// ******* getters ******//

	/**
	 * @return the location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @return the up
	 */
	public Vector getUp() {
		return up;
	}

	/**
	 * @return the right
	 */
	public Vector getRight() {
		return right;
	}

	/**
	 * @return the to
	 */
	public Vector getTo() {
		return to;
	}

	/**
	 * @return the high
	 */
	public double getVpHeight() {
		return vpHeight;
	}

	/**
	 * @return the width
	 */
	public double getVpWidth() {
		return vpWidth;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	// ****** Operations ******* //

	/**
	 * get the size of view plan by pixels and specific index on vp construct ray
	 * through this pixel
	 * 
	 * @param nX X size of view plan
	 * @param nY Y size of view plan
	 * @param j  X coordinate
	 * @param i  Y coordinate
	 * @return ray through the pixel
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {

		// calculate the meeting point of the object in the vector in front of the
		// camera
		Point pixelPoint = location.add(to.scale(distance));

		// calculate the high of pixel
		double y = vpHeight / nY;
		// calculate the width of pixel
		double x = vpWidth / nX;

		// calculate the distance from to the pixel
		double XJ = x * (j - ((nX - 1) / 2.0));
		double YI = -y * (i - ((nX - 1) / 2.0));
		// calculate the current pixel location
		if (!Util.isZero(XJ))
			pixelPoint = pixelPoint.add(right.scale(XJ));
		if (!Util.isZero(YI))
			pixelPoint = pixelPoint.add(up.scale(YI));

		return new Ray(location, pixelPoint.subtract(location));

	}

	/**
	 * the function checks that a non-empty value has been entered in all the
	 * fields, and in case of lack, an exception will be thrown. loop over all
	 * pixels on view plane, construct ray, find intersections, calculate the color,
	 * and write to image
	 */
	public void renderImage() {
		// check all camera properties Initialized
		if (this.vpHeight == 0.0)
			throw new MissingResourceException("camera class", "vpHeight", "0.0");

		if (this.vpWidth == 0.0)
			throw new MissingResourceException("camera class", "vpWidth", "0.0");

		if (this.distance == 0.0)
			throw new MissingResourceException("camera class", "distance", "0.0");

		if (this.imageWriter == null)
			throw new MissingResourceException("camera class", "imageWriter", "null");

		if (this.rayTracer == null)
			throw new MissingResourceException("camera class", "rayTracerBase", "null");

		// loop on all pixels
		for (int i = 0; i < this.imageWriter.getNy(); ++i) {

			for (int j = 0; j < this.imageWriter.getNx(); ++j) {

				// construct ray and send to ray tracer to get color
				var color = rayTracer.traceRay(constructRay(imageWriter.getNx(), imageWriter.getNy(), j, i));

				// write the color in point J,I
				this.imageWriter.writePixel(j, i, color);
			}
		}
	}

	/**
	 * the function will first check that a non-empty value has been entered in the
	 * field of the image producer and will create a grid of lines and print them
	 * for testing
	 * 
	 * @param interval interval between the grid lines
	 * @param color    color of grid lines
	 */
	public void printGrid(int interval, Color color) {
		if (imageWriter == null)
			throw new MissingResourceException("camera class", "imageWriter", "null");

		// print grid on image

		// write lines
		for (int j = 0; j < this.imageWriter.getNx(); j += interval) {
			for (int i = 0; i < this.imageWriter.getNy(); ++i)
				imageWriter.writePixel(j, i, color);
		}

		// writes columns
		for (int i = 0; i < this.imageWriter.getNy(); i += interval)
			for (int j = 0; j < this.imageWriter.getNx(); ++j)
				imageWriter.writePixel(j, i, color);
	}

	/**
	 * the function will first check that a non-empty value has been entered in the
	 * field of the image producer and will create image
	 */
	public void writeToImage() {
		if (imageWriter == null)
			throw new MissingResourceException("camera class", "imageWriter", "null");

		imageWriter.writeToImage();
	}

}
