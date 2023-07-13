package lighting;

import primitives.*;

import static primitives.Util.alignZero;

/**
 * The SpotLight class represents a spotlight in a scene. It is a type of point
 * light with an additional direction vector.
 */
public class SpotLight extends PointLight {

	private final Vector direction;

	/**
	 * Constructs a SpotLight object with the specified intensity, position, and
	 * direction.
	 *
	 * @param intensity The intensity of the spotlight.
	 * @param position  The position of the spotlight.
	 * @param direction The direction vector of the spotlight.
	 */
	public SpotLight(Color intensity, Point position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalize();
	}

	@Override
	public Color getIntensity(Point p) {
		double projection = alignZero(direction.dotProduct(getL(p)));
		return projection <= 0 ? Color.BLACK : super.getIntensity(p).scale(projection);
	}
}
