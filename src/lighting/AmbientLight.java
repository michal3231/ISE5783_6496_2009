
package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * the class implements ambient lighting in the image
 * 
 * @author 
 *
 */
public class AmbientLight {
	private final Color intensity;
	/**  */
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

	// ******* constructors *******

	/**
	 * constructor calculate the intensity (Double3 object)
	 * 
	 * @param light light intensity according to the RGB component
	 * @param Ka    attenuation coefficient of the light on the surface
	 */
	public AmbientLight(Color light, Double3 Ka) {
		this.intensity = light.scale(Ka);
	}

	/**
	 * constructor calculate the intensity (java.double object)
	 * 
	 * @param light light intensity according to the RGB component
	 * @param Ka    attenuation coefficient of the light on the surface
	 */
	public AmbientLight(Color light, double Ka) {
		this.intensity = light.scale(Ka);
	}

	/**
	 * getter for intensity
	 * 
	 * @return this intensity
	 */
	public Color getInsensity() {
		return this.intensity;
	}
}