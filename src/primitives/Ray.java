package primitives;

import java.util.List;
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
	
	/**
	 * this function get list of points and return the closest point to p0 of the
	 * ray
	 * 
	 * @param list of point
	 * @return closest point to p0      
	 */
	public Point findClosestPoint(List<Point> points) {
		if (points.isEmpty() || points == null)
			return null;

		double minDistance = Double.POSITIVE_INFINITY;
		Point closestPoint = null;
		for (var p : points) {
			double distance = p.distanceSquared(p0);
			if (distance < minDistance) {
				minDistance = distance;
				closestPoint = p;
			}
		}
		return closestPoint;
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
