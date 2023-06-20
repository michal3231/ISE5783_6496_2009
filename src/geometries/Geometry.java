package geometries;

import primitives.*;

/**
 * The Geometry class represents a geometric object in a scene. It is an
 * abstract class that provides common functionality and properties for
 * geometries.
 */
public abstract class Geometry extends Intersectable {

	/**
	 * The emission color of the geometry.
	 */
	protected Color emission = Color.BLACK;

	/**
	 * The material of the geometry.
	 */
	private Material material = new Material();

	// ***************** Getters ********************** //

	/**
	 * Returns the emission color of the geometry.
	 *
	 * @return The emission color.
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * Returns the material of the geometry.
	 *
	 * @return The geometry material.
	 */
	public Material getMaterial() {
		return material;
	}

	// ***************** Setters (builder pattern) ********************** //

	/**
	 * Sets the emission color of the geometry.
	 *
	 * @param emission The new emission color.
	 * @return This Geometry object (builder pattern).
	 */
	public Geometry setEmission(Color emission) {
		this.emission = emission;
		return this;
	}

	/**
	 * Sets the material of the geometry.
	 *
	 * @param material The new material.
	 * @return This Geometry object (builder pattern).
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}

	/*************************** Operations *******************/

	/**
	 * Calculates the normal vector of the geometry at a given point on its surface.
	 *
	 * @param point The point on the surface of the geometry.
	 * @return The normal vector.
	 */
	public abstract Vector getNormal(Point point);
}
