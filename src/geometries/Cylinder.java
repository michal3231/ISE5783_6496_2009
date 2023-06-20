package geometries;

import primitives.*;
import static primitives.Util.*;

/**
 * Cylinder class represents cylinder in 3D Cartesian coordinate system
 */
public class Cylinder extends Tube {

	private final double height;

	// ***************** Constructors ********************** //
	/**
	 * Constructs a Cylinder object with the specified axis ray, radius, and height.
	 * 
	 * @param axisRay the axis ray of the cylinder
	 * @param radius  the radius of the cylinder
	 * @param height  the height of the cylinder
	 */
	public Cylinder(Ray axisRay, double radius, double height) {
		super(radius, axisRay);
		this.height = height;
	}

	/**
	 * simple getter of filed "height"
	 * 
	 * @return high of the cylinder
	 */
	public double getHeight() {
		return height;
	}

	@Override
	public Vector getNormal(Point point) {
		Point p0 = axisRay.getP0();
		Vector dir = axisRay.getDirection();
		double t;

		// if the point is at the base of the cylinder
		try {
			t = dir.dotProduct(point.subtract(p0));
			if (isZero(t))
				return dir.scale(-1);
		} catch (IllegalArgumentException ignore) {
			// the point is in the center of the base
			return dir.scale(-1);
		}

		// if the point is at the top of the cylinder
		if (isZero(t - height))
			return dir;
		if (Util.isZero(dir.dotProduct(point.subtract(p0))))
			return dir;

		return super.getNormal(point);
	}

	@Override
	public String toString() {
		return "Cylinder{" + super.toString() + "height=" + height + '}';
	}
}