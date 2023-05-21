package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;


public class RayTracerBasic extends RayTracerBase {

	/**
	 * Constructor use super class constructor
	 * 
	 * @param scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	@Override
	public Color traceRay(Ray ray) {
		// get all intersection points
		//כאשר יש סצנה צריך להחזיר ההשתקפות הקרובה ביותר יש כאן שתי בדיקות כאשר יש גוף.אוביקט ואזנמצא את הקרן של הגוף הקרובה ביותר 
		var IntersectionsLst = scene.geometries.findIntersections(ray);//אם יש חיתוך יחזיר את החיתוך של האוביקט הקרוב ביותר 

		// no intersection points
		return IntersectionsLst == null ? scene.background//אם אין חיתוך יחזיר את הרקע (שחור)
				// return the color of the point
				: calcColor(ray.findClosestPoint(IntersectionsLst));
	}

	/**
	 * get point in scene and calculate its color
	 * 
	 * @param point the point we want to get the color of
	 * @return the color of the point
	 */
	private Color calcColor(Point point) {
		return scene.ambientLight.getInsensity();//מחשב את הצבע עי מכפלה של חיתוך האוביקט עם התאורה הסביבתית
	}

}
