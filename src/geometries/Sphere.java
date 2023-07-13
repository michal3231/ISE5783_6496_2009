package geometries;

import java.util.List;

import primitives.BoundingBox;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

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
		
        this.boundingBox = new BoundingBox(center.add(new Vector(1,1,1).scale(radius)),center.add(new Vector(-1,-1,-1).scale(radius)));
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
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
		
		Point p0 = ray.getP0();
		Vector v = ray.getDirection();
		Vector u;

		try {
			u = center.subtract(p0); // p0 == center the ray starts from the center of the sphere
		} catch (IllegalArgumentException ignore) {
			return alignZero(this.radius - maxDistance) > 0 ? null
					: List.of(new GeoPoint(this, ray.getPoint(this.radius)));
		}

		double tm = v.dotProduct(u);
		double dSquared = u.lengthSquared() - tm * tm;
		double thSquared = alignZero(this.radiusSquared - dSquared);
		if (thSquared <= 0)
			return null;

		double th = Math.sqrt(thSquared);
		double t2 = alignZero(tm + th);
		// Ray starts at or after the sphere
		if (t2 <= 0)
			return null;

		double t1 = alignZero(tm - th);
		if (alignZero(t1 - maxDistance) > 0)
			return null;

		// If the first point is behind the ray - include only the 2nd point
		// Otherwise, include both points
		if (alignZero(t2 - maxDistance) > 0)
			// t2 is further than max distance
			return t1 <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t1)));
		else // t2 is inside
			return t1 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t2)))
					: List.of(new GeoPoint(this, ray.getPoint(t2)), new GeoPoint(this, ray.getPoint(t1)));
	}

}
