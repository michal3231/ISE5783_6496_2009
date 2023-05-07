package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry {

	Point center;

	public Sphere(Point p, double radius) {
		// TODO Auto-generated constructor stub
		super(radius);
		this.center = p;
	}

	public Vector getNormal(Point point) {
		return point.subtract(center).normalize();
	}
}
