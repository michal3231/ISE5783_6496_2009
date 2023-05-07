package geometries;

import primitives.*;


import primitives.Point;

import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry{

	final Ray axisRay;
	public Tube(Ray r,double radius) {
		super(radius);
		this.axisRay=r;
	}
	
	//public Vector getNormal(Point p) {
	//	return null;
	//}
	
    @Override
    public Vector getNormal(Point point) {
        double t  =  axisRay.getVec().dotProduct(
                point.subtract(
                        axisRay.getPoint()));
        Point O = ( t == 0 ?axisRay.getPoint() :  axisRay.getPoint().add(
                axisRay.getVec().scale(t)));
        return point.subtract(O);
	}
}
