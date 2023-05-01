package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {

	private final Point q0;
	private final Vector normal;

	public Plane (Point point1, Point point2, Point point3){
		this.q0=point1;
		try {
			normal = point1.subtract(point2).crossProduct(point1.subtract(point3)).normalize();
		}
		catch (IllegalArgumentException e ){
			throw new IllegalArgumentException ("the point are on the same vector");
		}
	}

	Plane(Vector v, Point p) {
		// TODO Auto-generated constructor stub
		this.q0 = p;
		this.normal = v;
	}

	public Vector getNormal() {
		return this.normal;
	}

	public Vector getNormal(Point p) {
		return this.normal;
	}

}
