package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

/**
 * Plane class represents two-dimensional plane in 3D Cartesian coordinate
 * system
 */
public class Plane extends Geometry {

	/**
	 * reference point on the plane
	 */
	protected final Point p0;

	/**
	 * normal vector of the plane
	 */
	protected final Vector normal;

	/**
	 * Constructs plane by three point on the plane
	 * 
	 * @param point1 1st point
	 * @param point2 2nd point
	 * @param point3 3rd point
	 */
	public Plane(Point point1, Point point2, Point point3) {
		p0 = point1;
		try { // try for case the constructor get all point on the same vector or at least two
				// point are the same
			normal = point1.subtract(point2).crossProduct(point1.subtract(point3)).normalize();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("your points are on the same vector");
		}
	}

	/**
	 * 
	 * Constructs a plane using a reference point and a normal vector.
	 * 
	 * @param point  reference point on the plane
	 * @param normal normal vector of the plane
	 */
	public Plane(Point point, Vector normal) {
		this.p0 = point;
		this.normal = normal.normalize();
	}

	/**
	 * getter return the center of plane
	 * 
	 * @return point the point that declared the plane
	 */
	public Point getPoint() {
		return p0;
	}

	/**
	 * return normal vector
	 * 
	 * @return normal the normal of plane
	 */
	public Vector getNormal() {
		return normal;
	}

	@Override
	public Vector getNormal(Point point) {
		return normal;
	}

	@Override
	public String toString() {
		return "Plane{" + "point=" + p0 + ", normal=" + normal + '}';
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
		Point rayP0 = ray.getP0();
		Vector rayDirection = ray.getDirection();

		// Ray starts begins in the same point which appears as reference point in the
		// plane (0 points)
		if (rayP0.equals(this.p0))
			return null;

		// The cosine of the angle between the ray and the normal to the plane
		double dotProduct = this.normal.dotProduct(rayDirection);

		// If the ray is parallel to the plane - there are no intersections
		if (isZero(dotProduct)) // cosine = 0 for a right angle (90 degrees)
			return null;

		// Direction to plane p0 from ray p0
		Vector p0direction = p0.subtract(rayP0);

		// Distance from the ray head to the intersection point
		double distance = alignZero(this.normal.dotProduct(p0direction) / dotProduct);

		// Check if the distance is within the maximum distance
		if (distance <= 0 || alignZero(distance - maxDistance) > 0)
			return null;

		return List.of(new GeoPoint(this, ray.getPoint(distance)));
	}

}
