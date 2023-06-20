package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The PointLight class represents a point light source in space.
 */
public class PointLight extends Light implements LightSource {

    private Point position;

    private double kc = 1;
    private double kl = 0;
    private double kq = 0;

    /**
     * Constructor for PointLight class.
     *
     * @param intensity The intensity of the light.
     * @param position  The position of the light source.
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) {
        double dSquared = p.distanceSquared(position);
        double d = Math.sqrt(dSquared);

        return getIntensity().reduce(kc + kl * d + kq * dSquared);
    }

    @Override
    public Vector getL(Point p) {
        if (p.equals(position)) {
            return null;
        }
        return p.subtract(position).normalize();
    }

    // ***************** Setters (builder pattern) ********************** //

    /**
	 * Sets the constant attenuation coefficient (kc) for the point light.
	 *
	 * @param kc The constant attenuation coefficient.
	 * @return This PointLight object.
	 */
    public PointLight setKc(double kc) {
        this.kc = kc;
        return this;
    }

    /**
     * Sets the linear attenuation coefficient (kl) for the point light.
     *
     * @param kl The linear attenuation coefficient.
     * @return This PointLight object.
     */
    public PointLight setKl(double kl) {
        this.kl = kl;
        return this;
    }

    /**
     * Sets the quadratic attenuation coefficient (kq) for the point light.
     *
     * @param kq The quadratic attenuation coefficient.
     * @return This PointLight object.
     */
    public PointLight setKq(double kq) {
        this.kq = kq;
        return this;
    }
}
