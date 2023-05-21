package primitives;

import java.util.Objects;


public class Ray {
	
	final Point p0;
	final Vector dir;
	
	public Ray(Point p ,Vector v){
		this.dir=v.normalize();
		this.p0=p;
	}

	@Override
	public int hashCode() {
		return Objects.hash(p0, dir);
	}

	public Point getPoint(double length) {
		return Util.isZero(length) ? p0 : p0.add(dir.scale(length));
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
		return Objects.equals(p0, other.p0) && Objects.equals(dir, other.dir);
	}
	
	public String toString() {
		return this.dir.toString()+this.p0.toString();
	}
	 
	public Vector getVec() {
		return dir;
	}
	
	public Point getPoint() {
		return p0;
	}
}
