package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * interface of Intersectable by ray
 */
public abstract class Intersectable {

	/**
	 * class contain point on geometry and the geometry
	 */
	public static class GeoPoint {

		/**
		 * geometry shape
		 */
		public Geometry geometry;

		/**
		 * point on geometry shape
		 */
		public Point point;

		/**
		 * Constructs a GeoPoint object with the specified geometry and point.
		 * 
		 * @param geometry the geometry
		 * @param point    the point
		 */
		public GeoPoint(Geometry geometry, Point point) {
			this.geometry = geometry;
			this.point = point;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null)
				return false;
			if (this == obj)
				return true;
			if (!(obj instanceof GeoPoint))
				return false;

			GeoPoint other = (GeoPoint) obj;
			return this.geometry.equals(other.geometry) && this.point.equals(other.point);
		}

		@Override
		public String toString() {
			return "GeoPoint{" + "geometry=" + geometry + ", point=" + point + '}';
		}
	}

	/**
	 * Find intersections between the geometry object and a ray
	 * 
	 * @param ray the ray to intersect
	 * @return the intersections' list
	 */
	public List<Point> findIntersections(Ray ray) {//מוצא חיתוך של גוף מהקרן
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}

	/**
	 * get ray for intersect return list of geoPoints
	 * 
	 * @param ray direction ray
	 * @return all Intersect points or null if no intersect
	 */
	protected List<GeoPoint> findGeoIntersections(Ray ray) {//מוצא של גוף הקרוב ביותר מהקרן 
		return findGeoIntersectionsHelper(ray);
	}

	/**
	 * get ray for intersect return list of geoPoints
	 * 
	 * @param ray direction ray
	 * @return all Intersect points or null if no intersect
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}
