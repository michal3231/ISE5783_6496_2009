package primitives;

import java.util.Objects;


public class Ray {
	
	final Point point;
	final Vector vector;
	
	public Ray(Vector v,Point p){
		this.vector=v.normalize();
		this.point=p;
	}

	@Override
	public int hashCode() {
		return Objects.hash(point, vector);
	}

	public Point getPoint(double length) {
		return Util.isZero(length) ? p0 : p0.add(direction.scale(length));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		 if (!(obj instanceof Ray))
			 return false;
		Ray other = (Ray) obj;
		return Objects.equals(point, other.point) && Objects.equals(vector, other.vector);
	}
	
	public String toString() {
		return this.vector.toString()+this.point.toString();
	}
	 
	public Vector getVec() {
		return vector;
	}
	
	public Point getPoint() {
		return point;
	}
}
