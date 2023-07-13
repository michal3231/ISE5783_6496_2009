package primitives;

import java.util.LinkedList;
import java.util.List;
import static primitives.Util.*;
import geometries.Intersectable.GeoPoint;

/**
 * Ray class represents 3D ray
 */
public class Ray {
	private static final double DELTA = 0.00001; // Small value used for offset the ray origin
	private final Point p0;
	private final Vector direction;

	/**
	 * simple constructor
	 * 
	 * @param p0        start point
	 * @param direction direction vector
	 */
	public Ray(Point p0, Vector direction) {
		this.p0 = p0;
		this.direction = direction.normalize();
	}

	/**
	 * Constructs a new ray with a start point, direction, and normal vector.
	 *
	 * @param point     The start point of the ray.
	 * @param direction The direction vector of the ray.
	 * @param normal    The normal vector used to move the start point.
	 */
	public Ray(Point point, Vector direction, Vector normal) {
		// point + normal.scale(±DELTA)
		this.direction = direction.normalize();

		double nv = normal.dotProduct(direction);
		//עושים מכפלה בין בין הווקטור לווקטור כיוון 
		
		
		Vector normalDelta = normal.scale((nv > 0 ? DELTA : -DELTA));
		p0 = point.add(normalDelta);
	}

	/**
	 * return the p0 point
	 * 
	 * @return p0
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * getter
	 * 
	 * @return direction vector
	 */
	public Vector getDirection() {
		return direction;
	}

	/**
	 * get point on the ray
	 * 
	 * @param length distance from the start of the ray
	 * @return new Point3D
	 */
	public Point getPoint(double length) {
		return Util.isZero(length) ? p0 : p0.add(direction.scale(length));
	}

	@Override
	public String toString() {
		return p0 + "->" + direction;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return (obj instanceof Ray ray) && p0.equals(ray.p0) && direction.equals(ray.direction);
	}

	/**
	 * this function get list of points and return the closest point to p0 of the
	 * ray
	 * 
	 * @param points list of point for search
	 * @return closestPoint closest point to p0      
	 */
	public Point findClosestPoint(List<Point> points) {
		return points == null || points.isEmpty() ? null
				: findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
	}

	/**
	 * this function get list of geoPoints and return the closest geoPoint to p0 of
	 * the ray
	 * 
	 * @param geoPoints list of GeoPoints for search
	 * @return closestPoint closest GeoPoint to p0      
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
		if (geoPoints == null || geoPoints.isEmpty())
			return null;

		double minDistance = Double.POSITIVE_INFINITY;
		GeoPoint closestPoint = null;

		for (var p : geoPoints) {
			double distance = p.point.distanceSquared(p0);
			if (distance < minDistance) {
				minDistance = distance;
				closestPoint = p;
			}
		}
		return closestPoint;
	}

	/**
	 * get point on the ray
	 *
	 * @param length distance from the start of the ray
	 * @return new Point3D
	 */
	public Point getTargetPoint(double length) {
		return isZero(length) ? p0 : p0.add(direction.scale(length));
	}

	/**
	 *
	 * @param n         normal to the geometry
	 * @param radius    radius of the beam circle
	 * @param distance  distance of the eam circle
	 * @param numOfRays num of rays in the beam
	 * @return list of beam rays
	 */
	public List<Ray> generateBeam(Vector n, double radius, double distance, int numOfRays) {
		List<Ray> rays = new LinkedList<Ray>();
		rays.add(this);// Including the main ray
		if (numOfRays == 1 || isZero(radius))// The component (glossy surface /diffuse glass) is turned off
			return rays;

		// the 2 vectors that create the virtual grid for the beam
		Vector nX = direction.createNormal();
		Vector nY = direction.crossProduct(nX);

		Point centerCircle = this.getTargetPoint(distance);
		Point randomPoint;
		Vector v12;

		double rand_x, rand_y, delta_radius = radius / (numOfRays - 1);
		double nv = n.dotProduct(direction);

		for (int i = 1; i < numOfRays; i++) {
			randomPoint = centerCircle;
			rand_x = random(-radius, radius);
			rand_y = randomSign() * Math.sqrt(radius * radius - rand_x * rand_x);

			try {
				randomPoint = randomPoint.add(nX.scale(rand_x));
			} catch (Exception ex) {
			}

			try {
				randomPoint = randomPoint.add(nY.scale(rand_y));
			} catch (Exception ex) {
			}

			v12 = randomPoint.subtract(p0).normalize();

			double nt = alignZero(n.dotProduct(v12));

			if (nv * nt > 0) {
				rays.add(new Ray(p0, v12));
			}
			radius -= delta_radius;
		}

		return rays;
	}

}