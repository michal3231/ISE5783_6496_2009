package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry{

	final Ray axisRay;
	public Tube(Ray r,double radius) {
		super(radius);
		this.axisRay=r;
	}
	
	public Vector getNormal(Point p) {
		return null;
	}
}
