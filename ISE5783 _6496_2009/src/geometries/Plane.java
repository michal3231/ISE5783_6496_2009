package geometries;

import java.util.List;
import primitives.Point;
import primitives.Util;
import primitives.Vector;
import primitives.Ray;

public class Plane implements Geometry {

	private final Point p0;
	private final Vector normal;

	public Plane (Point point1, Point point2, Point point3){
		this.p0=point1;
		try {
			normal = point1.subtract(point2).crossProduct(point1.subtract(point3)).normalize();
		}
		catch (IllegalArgumentException e ){
			throw new IllegalArgumentException ("the point are on the same vector");
		}
	}

	public Plane(Vector v, Point p) {
		// TODO Auto-generated constructor stub
		this.p0 = p;
		this.normal = v;
	}

	public Vector getNormal() {
		return this.normal;
	}

	public Vector getNormal(Point p) {
		return this.normal;
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		Point rayP0 = ray.getPoint();
		Vector rayDirection = ray.getVec();
		
		/**
		 * calculate the dotProduct between the ray direction and normal plane
		 */
		double dotProduct = this.normal.dotProduct(rayDirection);
		
		// Checking whether the plane and the ray intersect each other or are parallel to each other
		if(Util.isZero(dotProduct)) {
			return null;
		}
		
		// direction to plane p0 from ray p0
		Vector p0direction = p0.subtract(rayP0);
		
		/** checking if direction of ray is to plane
		 * if directionRayScale < 0 the ray direction of the beam is not to the surface of plane//לא על המשטח
		 * if directionRayScale = 0 the ray is on surface of plane//על המשטח
		 * if directionRayScale > 0 the ray direction of the beam is cut the surface of plane//חותך את המשטח
		 */
		double directionRayScale = Util.alignZero(this.normal.dotProduct(p0direction)/dotProduct);
		
		if(directionRayScale > 0 ) {
			// find the intersection by dot product between the direction to plane from the po ray and 
			// directionRayScale (which calculates the distance between the point and the surface in the given direction)
			return List.of(rayP0.add(rayDirection.scale(directionRayScale)));
		}
		
		return null;
	}
}
