package geometries;

import java.util.List;

import primitives.BoundingBox;
import primitives.Point;
import primitives.Ray;

/**
 * Interface for objects that can be intersected by a ray.
 */
public abstract class Intersectable {

	private final boolean boundingBoxOn = true;
	protected BoundingBox boundingBox = new BoundingBox();
	
	/**
	 * Represents a point on a geometry shape along with the geometry itself.
	 */
	public static class GeoPoint {

		/**
		 * The geometry shape.
		 */
		public Geometry geometry;

		/**
		 * The point on the geometry shape.
		 */
		public Point point;

		/**
		 * Constructs a GeoPoint object with the specified geometry and point.
		 *
		 * @param geometry The geometry shape.
		 * @param point    The point on the geometry shape.
		 */
		public GeoPoint(Geometry geometry, Point point) {
			this.geometry = geometry;
			this.point = point;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			return obj instanceof GeoPoint other && this.geometry == other.geometry && this.point.equals(other.point);
		}

		@Override
		public String toString() {
			return "GeoPoint{" + "geometry=" + geometry + ", point=" + point + '}';
		}
	}

	/**
	 * Finds intersections between the geometry object and a ray.
	 *
	 * @param ray The ray to intersect.
	 * @return The list of intersection points, or null if no intersections occur.
	 */
	public final List<Point> findIntersections(Ray ray) {
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}

	/**
	 * Finds the geometric intersection points between the geometry object and a
	 * ray, considering a maximum distance.
	 *
	 * @param ray The ray to intersect.
	 * @return The list of geometric intersection points, or null if no
	 *         intersections occur.
	 */
	public final List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
	}

	/**
	 * Finds the geometric intersection points between the geometry object and a
	 * ray, up to a maximum distance.
	 *
	 * @param ray      The ray to intersect.
	 * @param maxDistance The maximum distance to consider for intersections.
	 * @return The list of geometric intersection points, or null if no
	 *         intersections occur.
	 */
	public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		if(boundingBoxOn && !this.boundingBox.intersectionBox(ray))
            return null;
		return findGeoIntersectionsHelper(ray, maxDistance);
	}

	/**
	 * Finds the geometric intersection points between the geometry object and a
	 * ray.
	 *
	 * @param ray      The ray to intersect.
	 * @param distance The maximum distance to consider for intersections.
	 * @return The list of geometric intersection points, or null if no
	 *         intersections occur.
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double distance);
}
