package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * LightSource interface to Light Source operations
 * 
 * @author David
 *
 */
public interface LightSource {

	// ***************** getters ********************** //

	/**
	 * get point and return the Intensity of this light on this point
	 * 
	 * @param p The point for which we want to know the light intensity
	 * @return color of this point
	 */
	public Color getIntensity(Point p);

	/**
	 * get point and return the vector from the light to the point
	 * 
	 * @param p The point for which we want to know the direction to the light
	 *          source
	 * @return vector direction to light source
	 */
	public Vector getL(Point p);

	/**
	 * Returns the squared distance between the source of light and the specified
	 * point.
	 *
	 * @param point The point to calculate the distance to.
	 * @return The squared distance between the light source and the point.
	 */
	public abstract double getDistance(Point point);

}
