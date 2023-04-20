package primitives;

import java.util.Objects;

public class Point {

	Double3 xyz;
	
	public Point(double x,double y,double z){
		this.xyz=new Double3(x,y,z);
	}
	
	Point(Double3 xyz){
		this.xyz=xyz;
	}
	 
	public Vector subtract(Point p) {
		return new Vector(xyz.subtract(p.xyz));
	}
	 
	public Point add(Vector v) {
		return new Point(xyz.add(v.xyz));
	}
	
	public double distanceSquared(Point point) {
		Point pp=new Point(this.xyz.subtract(point.xyz));
		pp.xyz=pp.xyz.product(pp.xyz);
		return pp.xyz.d1+pp.xyz.d2+pp.xyz.d3;
	}
	//לבדוק בהרצה
	public double distance(Point point) {
		Point pp=new Point(this.xyz.subtract(point.xyz));
		return pp.xyz.d1+pp.xyz.d2+pp.xyz.d3;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point))
			return false;
		Point other = (Point) obj;
		return xyz.equals(other.xyz);
	}
	
	@Override
	public String toString() {
		return  this.xyz.toString();
	}
}
