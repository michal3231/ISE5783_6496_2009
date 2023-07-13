package primitives;

/**
 * The Material class represents the material properties of an object in a
 * scene. It includes the diffuse reflection coefficient (kD), the specular
 * reflection coefficient (kS), the shininess (nShininess), and other optical
 * properties of the material.
 */
public class Material {

	/**
	 * The diffuse reflection coefficient (kD).
	 */
	public Double3 kD = Double3.ZERO;

	/**
	 * The specular reflection coefficient (kS).
	 */
	public Double3 kS = Double3.ZERO;

	/**
	 * The transmission coefficient (kt) of the material.
	 */
	public Double3 kt = Double3.ZERO;

	/**
	 * The reflection coefficient (kr) of the material.
	 */
	public Double3 kr = Double3.ZERO;

	/**
	 * The shininess of the material.
	 */
	public int nShininess = 1;

	/**
	 * The reflection coefficient (krDouble3) of the material.
	 */
	public Double3 krDouble3 = Double3.ZERO;

	// Parameters for blur glass
	public int numOfRays = 1;
	public double blurGlassDistance = 1, blurGlassRadius = 1;

	// ***************** Setter methods (Builder pattern) ********************** //

	/**
	 * Sets the diffuse reflection coefficient (kD) to the specified value.
	 *
	 * @param kD The value of the diffuse reflection coefficient.
	 * @return This Material object.
	 */
	public Material setKd(double kD) {
		this.kD = new Double3(kD);
		return this;
	}

	/**
	 * Sets the diffuse reflection coefficient (kD) to the specified Double3 object.
	 *
	 * @param kD The Double3 object representing the diffuse reflection coefficient.
	 * @return This Material object.
	 */
	public Material setKd(Double3 kD) {
		this.kD = kD;
		return this;
	}

	/**
	 * Sets the specular reflection coefficient (kS) to the specified value.
	 *
	 * @param kS The value of the specular reflection coefficient.
	 * @return This Material object.
	 */
	public Material setKs(double kS) {
		this.kS = new Double3(kS);
		return this;
	}

	/**
	 * Sets the specular reflection coefficient (kS) to the specified Double3
	 * object.
	 *
	 * @param kS The Double3 object representing the specular reflection
	 *           coefficient.
	 * @return This Material object.
	 */
	public Material setKs(Double3 kS) {
		this.kS = kS;
		return this;
	}

	/**
	 * Sets the transmission coefficient (kt) of the material.
	 *
	 * @param kt The transmission coefficient to set.
	 * @return The updated Material object.
	 */
	public Material setKt(Double3 kt) {
		this.kt = kt;
		return this;
	}

	/**
	 * Sets the transmission coefficient (kt) of the material.
	 *
	 * @param kt The transmission coefficient to set.
	 * @return The updated Material object.
	 */
	public Material setKt(double kt) {
		this.kt = new Double3(kt);
		return this;
	}

	public Material setKr(double kr) {
		this.kr = new Double3(kr);
		return this;
	}

	public Material setKr(Double3 kr) {
		this.kr = kr;
		return this;
	}

	/**
	 * Sets the shininess of the material.
	 *
	 * @param nShininess The shininess value.
	 * @return This Material object.
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}

	// *************************** operation *************************//

	/**
	 * Sets the number of rays for blur glass rendering.
	 *
	 * @param numOfRays The number of rays to set.
	 * @return This Material object.
	 * @throws IllegalArgumentException if numOfRays is less than 1.
	 */
	public Material setNumOfRays(int numOfRays) {
		if (numOfRays < 1)
			throw new IllegalArgumentException("Illegal argument in setNumOfRays");
		this.numOfRays = numOfRays;
		return this;
	}

	/**
	 * Sets the distance for blur glass rendering.
	 *
	 * @param blurGlassDistance The distance to set.
	 * @return This Material object.
	 * @throws IllegalArgumentException if blurGlassDistance is less than or equal
	 *                                  to 0.
	 */
	public Material setBlurGlassDistance(double blurGlassDistance) {
		if (blurGlassDistance <= 0)
			throw new IllegalArgumentException("Illegal argument in setBlurGlassDistance");
		this.blurGlassDistance = blurGlassDistance;
		return this;
	}

	/**
	 * Sets the radius for blur glass rendering.
	 *
	 * @param blurGlassRadius The radius to set.
	 * @return This Material object.
	 * @throws IllegalArgumentException if blurGlassRadius is less than or equal to
	 *                                  0.
	 */
	public Material setBlurGlassRadius(double blurGlassRadius) {
		if (blurGlassRadius <= 0)
			throw new IllegalArgumentException("Illegal argument in setBlurGlassRadius");
		this.blurGlassRadius = blurGlassRadius;
		return this;
	}

	/**
	 * Sets the parameters for blur glass rendering.
	 *
	 * @param numOfRays The number of rays to set.
	 * @param distance  The distance to set.
	 * @param radius    The radius to set.
	 * @return This Material object.
	 * @throws IllegalArgumentException if any of the parameters is invalid.
	 */
	public Material setBlurGlass(int numOfRays, double distance, double radius) {
		if (numOfRays < 1 || distance <= 0 || radius <= 0)
			throw new IllegalArgumentException("Illegal argument in setBlurGlass");

		this.numOfRays = numOfRays;
		this.blurGlassDistance = distance;
		this.blurGlassRadius = radius;

		return this;
	}
}
