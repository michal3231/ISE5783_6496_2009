package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;
import static primitives.Util.*;

/**
 * The RayTracerBasic class implements a basic ray tracing algorithm. It traces
 * rays through a scene and calculates the color of each ray intersection point.
 */
public class RayTracerBasic extends RayTracerBase {

	/**
	 * Constructor for RayTracerBasic class.
	 *
	 * @param scene The scene to trace rays in.
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	@Override
	public Color traceRay(Ray ray) {
		// Get all intersection points
		var intersectionsLst = scene.geometries.findGeoIntersectionsHelper(ray);

		// No intersection points
		return intersectionsLst == null ? scene.background
				// Return the color of the closest intersection point
				: calcColor(ray.findClosestGeoPoint(intersectionsLst), ray);
	}

	/**
	 * Calculates the color of a point in the scene.
	 *
	 * @param geoPoint The point on the geometry in the scene.
	 * @param ray      The ray from the camera to the intersection.
	 * @return The color of the point.
	 */
	private Color calcColor(GeoPoint geoPoint, Ray ray) {
		return scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission())
				.add(calcLocalEffects(geoPoint, ray));
	}

	/**
	 * Calculates the effect of different light sources on a point in the scene
	 * according to the Phong model.
	 *
	 * @param intersection The point on the geometry in the scene.
	 * @param ray          The ray from the camera to the intersection.
	 * @return The color of the point affected by local light sources.
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
		Vector v = ray.getDirection();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0)
			return Color.BLACK;

		int nShininess = intersection.geometry.getMaterial().nShininess;

		Double3 kd = intersection.geometry.getMaterial().kD, ks = intersection.geometry.getMaterial().kS;

		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lightSources) {
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l));

			if (nl * nv > 0) { // sign(nl) == sign(nv)
				Color lightIntensity = lightSource.getIntensity(intersection.point);
				color = color.add(calcDiffuse(kd, nl, lightIntensity),
						calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
			}
		}
		return color;
	}

	/**
	 * Calculates the diffuse component of light reflection.
	 *
	 * @param kd             The diffuse reflection coefficient.
	 * @param nl             The dot product between the normal vector and the light
	 *                       vector.
	 * @param lightIntensity The intensity of the light source.
	 * @return The color contribution from the diffuse reflection.
	 */
	private Color calcDiffuse(Double3 kd, double nl, Color lightIntensity) {
		return lightIntensity.scale(kd.scale(Math.abs(nl)));
	}

	/**
	 * Calculates the specular component of light reflection.
	 *
	 * @param ks             The specular reflection coefficient.
	 * @param l              The light vector.
	 * @param n              The normal vector.
	 * @param nl             The dot product between the normal vector and the light
	 *                       vector.
	 * @param v              The view vector.
	 * @param nShininess     The shininess coefficient.
	 * @param lightIntensity The intensity of the light source.
	 * @return The color contribution from the specular reflection.
	 */
	private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess,
			Color lightIntensity) {
		Vector r = l.add(n.scale(-2 * nl)); // nl must not be zero!
		double minusVR = -alignZero(r.dotProduct(v));
		if (minusVR <= 0) {
			return new primitives.Color(Color.BLACK.getColor()); // View from direction opposite to r vector
		}
		return lightIntensity.scale(ks.scale(Math.pow(minusVR, nShininess)));
	}
}
