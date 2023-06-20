package primitives;

import java.util.List;

import geometries.Intersectable.GeoPoint;

/**
 * Ray class represents 3D ray
 */
public class Ray {
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
		if (geoPoints.isEmpty() || geoPoints == null)
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
}