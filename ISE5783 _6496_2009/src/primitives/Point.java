package primitives;


public class Point {

	Double3 xyz;

	public Point(double x, double y, double z) {
		this.xyz = new Double3(x, y, z);
	}

	Point(Double3 xyz) {
		this.xyz = xyz;
	}

	public Vector subtract(Point p) {
		return new Vector(xyz.subtract(p.xyz));
	}

	public Point add(Vector v) {
		return new Point(xyz.add(v.xyz));
	}

	public double distanceSquared(Point point) {
		double d1 = this.xyz.d1 - point.xyz.d1;
		double d2 = this.xyz.d2 - point.xyz.d2;
		double d3 = this.xyz.d3 - point.xyz.d3;

		return d1 * d1 + d2 * d2 + d3 * d3;
	}

	public double distance(Point point) {
		return Math.sqrt(distanceSquared(point));
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
		return this.xyz.toString();
	}
}
