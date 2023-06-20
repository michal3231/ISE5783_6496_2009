package lighting;

import primitives.Color;

/**
 * abstract class implement the light and intensity light
 */
abstract class Light {
	/**
	 * The light intensity
	 */
	protected final Color intensity;

	/**
	 * Simple constructor
	 * 
	 * @param intensity the light intensity
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * getter for intensity
	 * 
	 * @return this intensity
	 */
	public Color getIntensity() {
		return this.intensity;
	}

}
