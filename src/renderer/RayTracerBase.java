package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Base class for Ray Tracers. A Ray Tracer is responsible for rendering images
 * by tracing rays in a scene.
 * 
 * @author David
 *
 */
public abstract class RayTracerBase {

	/**
	 * scene
	 */
	protected final Scene scene;

	/**
	 * Constructs a RayTracerBase object with the specified scene.
	 * 
	 * @param scene the scene to render
	 */
	public RayTracerBase(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Traces a ray in the scene and returns the color of the intersection point.
	 * 
	 * @param ray the ray to trace
	 * @return the color of the intersection point
	 */
	public abstract Color traceRay(Ray ray);
}
