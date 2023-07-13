package lighting;

import primitives.Color;

/**
 * Abstract class that represents a light source and its intensity.
 */
abstract class Light {
	/**
	 * The light intensity.
	 */
	protected final Color intensity;

	/**
	 * Simple constructor.
	 *
	 * @param intensity The light intensity.
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * Returns the intensity of the light.
	 *
	 * @return The light intensity.
	 */
	public Color getIntensity() {
		return this.intensity;
	}

}
