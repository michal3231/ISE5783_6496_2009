package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a directional light source in a 3D scene.
 */
public class DirectionalLight extends Light implements LightSource {

	private final Vector direction;

	/**
	 * Constructs a directional light with the specified intensity and direction.
	 *
	 * @param intensity the intensity of the light
	 * @param direction the direction of the light
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalize();
	}

	/**
	 * Returns the intensity of the light at the specified point.
	 *
	 * @param p the point at which to calculate the intensity
	 * @return the intensity of the light
	 */
	@Override
	public Color getIntensity(Point p) {
		return this.intensity;
	}

	/**
	 * Returns the direction of the light at the specified point.
	 *
	 * @param p the point at which to calculate the direction
	 * @return the direction of the light
	 */
	@Override
	public Vector getL(Point p) {
		return direction;
	}

	@Override
	public double getDistance(Point point) {
		// Directional Light had no location
		return Double.POSITIVE_INFINITY;
	}
}
