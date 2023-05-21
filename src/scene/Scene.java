package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

public class Scene {
	public String name;
	public Color background;
	public AmbientLight ambientLight;
	public Geometries geometries;

	/**
	 * simple constructor enter the name end for others enters default values
	 * 
	 * @param name the scene name
	 */
	public Scene(String name) {
		this.name = name;
		this.background = Color.BLACK;
		this.ambientLight = AmbientLight.NONE;
		this.geometries = new Geometries();
	}

	/******* setters (builder pattern) ********/

	/**
	 * background setter (builder pattern)
	 * 
	 * @param background
	 * @return this background object that we set now
	 */
	public Scene setBackground(Color background) {
		this.background = background;
		return this;
	}

	/**
	 * ambientLight setter (builder pattern)
	 * 
	 * @param ambientLight
	 * @return this ambientLight object that we set now
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;
	}

	/**
	 * geometries setter (builder pattern)
	 * 
	 * @param geometries
	 * @return this geometries object that we set now
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		return this;
	}

}