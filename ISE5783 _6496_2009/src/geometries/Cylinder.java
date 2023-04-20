package geometries;

import primitives.Point;
import primitives.Vector;

public class Cylinder extends  RadialGeometry {

	double height;
	
	public Cylinder(double radius,double h) {
		// TODO Auto-generated constructor stub
		super(radius);
		this.height=h;
	}
	
		
	public Vector getNormal(Point p) {
			return null;
	}

}
