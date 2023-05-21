package geometries;

import static primitives.Util.isZero;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import primitives.*;


public class Cylinder extends  Tube {

	double height;
	
	public Cylinder(Ray axisRay ,double radius,double h) {
		// TODO Auto-generated constructor stub
		super(axisRay, radius);
		this.height=h;
	}
	
		
	//public Vector getNormal(Point p) {
	//		return null;
	//}
	 public Vector getNormal(Point point) {

			Point p0 = axisRay.getPoint();
			Vector dir = axisRay.getVec();
			Point pTop = p0.add(dir.scale(height));

			// if the point is at the top of the cylinder
			if (point.equals(pTop) || Util.isZero(dir.dotProduct(point.subtract(pTop))))
				return dir;

			// if the point is at the base of the cylinder
			if (point.equals(p0) || Util.isZero(dir.dotProduct(point.subtract(p0))))
				return dir.scale(-1);

			return super.getNormal(point);
	    }
}
