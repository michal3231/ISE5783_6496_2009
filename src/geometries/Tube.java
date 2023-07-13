package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

/**
 * class Tube represents tube in 3D A Tube is a type of RadialGeometry that has
 * a radius and an axis ray.
 */
public class Tube extends RadialGeometry {
	/** the axis line of the tube */
	protected final Ray axisRay;

	/**
	 * 
	 * Constructs a Tube object with the specified radius and axis ray.
	 * 
	 * @param radius  the radius of the tube
	 * @param axisRay the axis ray of the tube
	 */
	public Tube(double radius, Ray axisRay) {
		super(radius);
		this.axisRay = axisRay;
	}

	/********************* getters ************************/

	/**
	 * Returns the axis ray of the tube.
	 * 
	 * @return axisRay the axis ray
	 */
	public Ray getAxisRay() {
		return axisRay;
	}

	/**
	 * Returns the radius of the tube.
	 * 
	 * @return radius the radius of tube
	 */
	public double getRadius() {
		return radius;
	}

	@Override
	public Vector getNormal(Point point) {
		Point p0 = axisRay.getP0();
		Vector vector = axisRay.getDirection();
		double t = vector.dotProduct(point.subtract(p0));
		Point o = isZero(t) ? p0 : p0.add(vector.scale(t));
		return point.subtract(o);
	}

	@Override
	public String toString() {
		return "Tube{" + "axisRay=" + axisRay + ", radius=" + radius + '}';
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

		// Extract the origin and direction of the ray
		Point rayOrigin = ray.getP0();
		Vector rayDirection = ray.getDirection();

		// Calculate the discriminant of the quadratic equation
		double[] abc = discriminantParam(rayDirection, rayOrigin, ray, radius);

		double discriminant = abc[1] * abc[1] - 4 * abc[0] * abc[2];

		// If the discriminant is negative or all intersection points are beyond the
		// maximum distance,
		// the ray does not intersect the cylinder
		if (discriminant < 0) {
			return null;
		}

		// Calculate the roots of the quadratic equation
		double t1 = (-abc[1] - Math.sqrt(discriminant)) / (2 * abc[0]);
		double t2 = (-abc[1] + Math.sqrt(discriminant)) / (2 * abc[0]);

		// Check if both intersection points are beyond the maximum distance
		if (t1 > maxDistance && t2 > maxDistance) {
			return null;
		}

		// Calculate the intersection points
		Point intersectionPoint1 = ray.getP0().add(ray.getDirection().scale(t1));
		Point intersectionPoint2 = ray.getP0().add(ray.getDirection().scale(t2));

		// Add the intersection points to the list
		return List.of(new GeoPoint(this, intersectionPoint1), new GeoPoint(this, intersectionPoint2));
	}

}
