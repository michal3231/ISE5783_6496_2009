package primitives;

/**
 * Vector class represents 3D vector
 */
public class Vector extends Point {

	/**
	 * Constructor to initialize point 3D get three double coordinates
	 *
	 * @param x first number value
	 * @param y second number value
	 * @param z third number value
	 */
	public Vector(double x, double y, double z) {
		super(x, y, z);
		if (xyz.equals(Double3.ZERO)) {
			throw new IllegalArgumentException("ZERO vector");
		}
	}

	/**
	 * constructor get Double3 object check if that points are not create the ZERO
	 * vector (using super constructor)
	 * 
	 * @param xyz a point in space
	 */
	Vector(Double3 xyz) {
		super(xyz);
		if (xyz.equals(Double3.ZERO)) {
			throw new IllegalArgumentException("ZERO vector");
		}
	}

	/**
	 * getter for xyz point
	 * 
	 * @return this xyz
	 */
	public Double3 getVector() {
		return this.xyz;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return (obj instanceof Vector) && super.equals(obj);
	}

	@Override
	public String toString() {
		return "Vector{}" + super.toString();
	}

	/**
	 * add vector to other vector
	 * 
	 * @param vector the other vector
	 * @return new vector
	 */
	public Vector add(Vector vector) {
		return new Vector(this.xyz.add(vector.xyz));

	}

	/**
	 * scale coordinate white coordinate
	 * 
	 * @param scalar scalar to scale whit vector
	 * @return new vector
	 */
	public Vector scale(double scalar) {
		return new Vector(this.xyz.scale(scalar));
	}

	/**
	 * Scalar product function
	 * 
	 * @param vector A vector for a scalar product
	 * @return Scalar product result
	 */
	public double dotProduct(Vector vector) {
		return this.xyz.d1 * vector.xyz.d1 + this.xyz.d2 * vector.xyz.d2 + this.xyz.d3 * vector.xyz.d3;
	}

	/**
	 * cross product between to vector
	 * 
	 * @param vector vector for vector multiplication
	 * @return new normalize vertical vector
	 */
	public Vector crossProduct(Vector vector) {
		return new Vector(xyz.d2 * vector.xyz.d3 - xyz.d3 * vector.xyz.d2,
				xyz.d3 * vector.xyz.d1 - xyz.d1 * vector.xyz.d3, xyz.d1 * vector.xyz.d2 - xyz.d2 * vector.xyz.d1);
	}

	/**
	 * calculate the square length
	 * 
	 * @return sqr length
	 */
	public double lengthSquared() {
		return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
	}

	/**
	 * calculate the vector length
	 * 
	 * @return legth
	 */
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}

	/**
	 * Builds a new unit vector in the same direction
	 * 
	 * @return normalized vector
	 */
	public Vector normalize() {
		return new Vector(this.xyz.reduce(this.length()));
	}
	
	  /**
     * create vector  normal to this vector
     * @return
     */
    public Vector createNormal() {
        if (Util.isZero(this.getX()))
            return new Vector(1, 0, 0);

        return new Vector(this.getY(), -this.getX(), 0).normalize();
    }
}