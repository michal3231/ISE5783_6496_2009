package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Util;

/**
 * class Sphere represents sphere in 3D space
 */
public class Sphere extends RadialGeometry {

	/**
	 * The sphere center
	 */
	protected final Point center;

	/**
	 * constructor (using super constructor)
	 * 
	 * @param radius radius of sphere
	 * @param center the center of sphere
	 */
	public Sphere(double radius, Point center) {
		super(radius);
		this.center = center;
	}

	/**
	 * getter
	 * 
	 * @return Sphere center
	 */
	public Point getCenter() {
		return center;
	}

	@Override
	public Vector getNormal(Point point) {
		return point.subtract(center).normalize();
	}

	@Override
	public String toString() {
		return "Sphere{" + "center=" + center + ", radius=" + radius + '}';
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		Point p0 = ray.getP0();
		Vector v = ray.getDirection();
		Vector u;

		try {
			u = center.subtract(p0); // p0 == center the ray start from the center of the sphere
		} catch (IllegalArgumentException ignore) {
			return List.of(new GeoPoint(this, ray.getPoint(this.radius)));
		}

		double tm = v.dotProduct(u);
		double dSquared = u.lengthSquared() - tm * tm;
		double thSquared = Util.alignZero(this.radiusSquared - dSquared);
		if (thSquared <= 0)
			// no intersections because the line of the ray is outside or tangent to the
			// sphere
			return null;

		double th = Math.sqrt(thSquared);
		double t2 = Util.alignZero(tm + th);
		// ray starts at or after the sphere
		if (t2 <= 0)
			return null;

		double t1 = Util.alignZero(tm - th);
		// if the first point is behind the ray - include only the 2nd point
		// otherwise include both points
		return t1 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t2)))
				: List.of(new GeoPoint(this, ray.getPoint(t2)), new GeoPoint(this, ray.getPoint(t1)));
	}
}
