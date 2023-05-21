package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {

	protected final Scene scene;

	/**
	 * simple constructor set scene
	 * 
	 * @param scene
	 */
	public RayTracerBase(Scene scene) {
		this.scene = scene;
	}

	/**
	 * TODO
	 * 
	 * @param ray
	 * @return
	 */
	public abstract Color traceRay(Ray ray);
}
