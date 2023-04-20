package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{

	Point q0;
	Vector normal;
	
	Plane(Vector v,Point p) {
		// TODO Auto-generated constructor stub
		this.q0=p;
		this.normal=v;
	}
	
	Plane(Point point1,Point point2,Point point3){
		this.normal=null;
		this.q0=point1;
	}
	
	public Vector getNormal() {
		return this.normal;
	}
	
	public Vector getNormal(Point p) {
		return this.normal;
	}
	
}
