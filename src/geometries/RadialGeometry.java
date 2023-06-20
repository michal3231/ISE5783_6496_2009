package geometries;

/**
 * An abstract class from which all bodies that hold a radius field inherit
 */
public abstract class RadialGeometry extends Geometry {

	/** the radius of a rounded part of the geometry body */
	protected final double radius;
	/** the squared radius of a rounded part of the geometry body */
	protected final double radiusSquared;

	/**
	 * constructor (using super constructor)
	 * 
	 * @param radius radius of geometry
	 */
	public RadialGeometry(double radius) {
		this.radius = radius;
		this.radiusSquared = radius * radius;
	}

	/**
	 * Returns the radius of the RadialGeometry.
	 * 
	 * @return radius the radius of RadialGeometry.
	 */
	public double getRadius() {
		return radius;
	}
}
