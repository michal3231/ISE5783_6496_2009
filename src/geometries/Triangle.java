package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

/**
 * class Triangle represents two-dimensional triangle in 3D space
 */
public class Triangle extends Polygon {
	/**
	 * constructor using at father constructor
	 * 
	 * @param point1 the first triangle point
	 * @param point2 the second triangle point
	 * @param point3 the third triangle point
	 */
	public Triangle(Point point1, Point point2, Point point3) {
		super(point1, point2, point3);
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		var intersection = plane.findGeoIntersectionsHelper(ray);
		if (intersection == null) // the plane of the triangle is not intersected
			return null;

		Vector dir = ray.getDirection();
		Point p0 = ray.getP0();

		// calculate the vectors from the ray head to all vertices
		// then calculate all the normals to the resulting pyramid wig
		// then get analyze the angles between the ray and these normals
		// by checking that all the angles are of the same sharpness
		// i.e. the angle cosines have the same sign

		// analyze the 1st side of the triangle
		Vector vector1 = vertices.get(0).subtract(p0);
		Vector vector2 = vertices.get(1).subtract(p0);
		Vector normal1 = vector1.crossProduct(vector2);
		// if the point is on the line of the 1st side of the triangle
		double cosine1 = alignZero(dir.dotProduct(normal1));
		if (cosine1 == 0)
			return null;

		// analyze the 2nd side of the triangle
		Vector vector3 = vertices.get(2).subtract(p0);
		Vector normal2 = vector2.crossProduct(vector3);
		double cosine2 = alignZero(dir.dotProduct(normal2));
		if (cosine1 * cosine2 <= 0)
			return null;

		// analyze the 2nd side of the triangle
		Vector normal3 = vector3.crossProduct(vector1);
		double cosine3 = alignZero(dir.dotProduct(normal3));
		if (cosine1 * cosine3 <= 0)
			return null;

		intersection.get(0).geometry = this;
		// the point is inside the triangle
		return intersection;
	}
}