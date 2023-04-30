package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;


public class Cylinder extends  Tube {

	double height;
	
	public Cylinder(Ray axisRay ,double radius,double h) {
		// TODO Auto-generated constructor stub
		super(axisRay, radius);
		this.height=h;
	}
	
		
	public Vector getNormal(Point p) {
			return null;
	}

}
