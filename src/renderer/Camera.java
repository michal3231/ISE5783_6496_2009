package renderer;

import java.util.MissingResourceException;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import static primitives.Util.*;

/**
 * Represents a camera in a 3D scene. The camera is responsible for capturing
 * the scene and generating the image. It defines the position, orientation, and
 * parameters of the view plane. The camera uses a ray tracing algorithm to
 * construct rays for each pixel on the view plane. It also handles rendering
 * the image and writing it to an image file.
 * 
 * The Camera class supports the builder pattern for easy and fluent
 * configuration.
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

	// distance of the view plane from the camera
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
		// check if the up vector and to vector are orthogonal to each other
		if (!Util.isZero(up.dotProduct(to)))
			throw new IllegalArgumentException("the vectors are not orthogonal to each other");

		this.location = location;
		this.up = up.normalize();
		this.to = to.normalize();

		// calculate the right vector
		this.right = to.crossProduct(up).normalize();
	}

	// ****************** setters (builder pattern)***************//

	/**
	 * Sets the width and height of the view plane.
	 * 
	 * @param vpWidth  view plane width
	 * @param vpHeight view plane height
	 * @return this camera instance
	 */
	public Camera setVPSize(double vpWidth, double vpHeight) {
		if (alignZero(vpHeight) <= 0 || alignZero(vpHeight) <= 0)
			throw new IllegalArgumentException("ERROR: Negative argument");

		this.vpWidth = vpWidth;
		this.vpHeight = vpHeight;
		return this;
	}

	/**
	 * Sets the distance between the camera and the view plane.
	 * 
	 * @param distance view plane distance from the camera
	 * @return this camera instance
	 */
	public Camera setVPDistance(double distance) {
		if (alignZero(distance) <= 0)
			throw new IllegalArgumentException("ERROR: Negative argument");
		this.distance = distance;
		return this;
	}

	/**
	 * Sets the ray tracer for the camera.
	 * 
	 * @param rayTracerBase the ray tracer implementation to use
	 * @return this camera instance
	 */
	public Camera setRayTracer(RayTracerBase rayTracerBase) {
		this.rayTracer = rayTracerBase;
		return this;
	}

	/**
	 * Sets the image writer for the camera.
	 * 
	 * @param imageWriter the image writer to use
	 * @return this camera instance
	 */
	public Camera setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}

	// ******************** getters ***************//

	/**
	 * @return the location of the camera
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @return the up direction vector of the camera
	 */
	public Vector getUp() {
		return up;
	}

	/**
	 * @return the right direction vector of the camera
	 */
	public Vector getRight() {
		return right;
	}

	/**
	 * @return the to direction vector of the camera
	 */
	public Vector getTo() {
		return to;
	}

	/**
	 * @return the height of the view plane
	 */
	public double getVpHeight() {
		return vpHeight;
	}

	/**
	 * @return the width of the view plane
	 */
	public double getVpWidth() {
		return vpWidth;
	}

	/**
	 * @return the distance between the camera and the view plane
	 */
	public double getDistance() {
		return distance;
	}

	// ***************** Operations ******************** //

	/**
	 * Constructs a ray for a given pixel on the view plane.
	 * 
	 * @param nX X size of the view plane
	 * @param nY Y size of the view plane
	 * @param j  X coordinate of the pixel
	 * @param i  Y coordinate of the pixel
	 * @return the constructed ray for the pixel
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {

		// calculate the meeting point of the object in the vector in front of the
		// camera
		Point pixelPoint = location.add(to.scale(distance));

		// calculate the height and width of a pixel
		double y = vpHeight / nY;
		double x = vpWidth / nX;

		// calculate the distance from the camera to the pixel
		double xj = x * (j - ((nX - 1) / 2.0));
		double yi = -y * (i - ((nX - 1) / 2.0));

		// calculate the current pixel location
		if (!Util.isZero(xj))
			pixelPoint = pixelPoint.add(right.scale(xj));
		if (!Util.isZero(yi))
			pixelPoint = pixelPoint.add(up.scale(yi));

		return new Ray(location, pixelPoint.subtract(location));
	}

	/**
	 * Renders the image by constructing rays for each pixel on the view plane,
	 * finding intersections, calculating the color, and writing it to the image.
	 * 
	 * @return this camera instance
	 */
	public Camera renderImage() {
		// check if all camera properties are initialized
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

		int nx = imageWriter.getNx();
		int ny = imageWriter.getNy();

		// loop over all pixels
		for (int i = 0; i < this.imageWriter.getNy(); ++i) {
			for (int j = 0; j < this.imageWriter.getNx(); ++j) {
				// construct ray and send it to the ray tracer to get the color
				var color = rayTracer.traceRay(constructRay(nx, ny, j, i));

				// write the color to point J, I
				this.imageWriter.writePixel(j, i, color);
			}
		}

		return this;
	}

	/**
	 * Prints a grid of lines on the image for testing purposes.
	 * 
	 * @param interval the interval between grid lines
	 * @param color    the color of the grid lines
	 */
	public void printGrid(int interval, Color color) {
		if (imageWriter == null)
			throw new MissingResourceException("camera class", "imageWriter", "null");

		int nx = this.imageWriter.getNx();
		int ny = this.imageWriter.getNy();

		// print grid on image
		// write lines
		for (int j = 0; j < nx; j += interval) {
			for (int i = 0; i < ny; ++i)
				imageWriter.writePixel(j, i, color);
		}

		// write columns
		for (int i = 0; i < ny; i += interval)
			for (int j = 0; j < nx; ++j)
				imageWriter.writePixel(j, i, color);
	}

	/**
	 * Writes the image to the file specified by the image writer.
	 */
	public void writeToImage() {
		if (imageWriter == null)
			throw new MissingResourceException("camera class", "imageWriter", "null");

		imageWriter.writeToImage();
	}
}
