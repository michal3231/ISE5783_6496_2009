package renderer;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

/**
 * The RayTracerBasic class implements a basic ray tracing algorithm. It traces
 * rays through a scene and calculates the color of each ray intersection point.
 */
public class RayTracerBasic extends RayTracerBase {

	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final Double3 INITIAL_K = Double3.ONE;

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
		var intersection = this.findClosestIntersection(ray);
		// No intersection points or the color of the closest intersection point
		return intersection == null ? scene.background : calcColor(intersection, ray);
		//אם יש חיתוך יחשב הצבע ואם אין יחזיר את הרקע
	}

	/**
	 * get point in scene and calculate its color
	 * 
	 * @param geoPoint
	 * @return
	 */
	private Color calcColor(GeoPoint geoPoint, Ray ray) {
		return scene.ambientLight.getIntensity().add(calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K));
	}

	/**
	 * get point in scene and calculate its color
	 * 
	 * @param gp
	 * @param ray
	 * @param level
	 * @param k
	 * @return
	 */
	private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
		Vector v = ray.getDirection();
		Vector n = gp.geometry.getNormal(gp.point);
		double vn = v.dotProduct(n);
		if (isZero(vn))
			return Color.BLACK;
// חיושב צבע של אפקטים לוקאלים
		Color color = calcLocalEffects(gp, v, n, vn, k).add(gp.geometry.getEmission());
// הופסת 
		return 1 == level ? color : color.add(calcGlobalEffects(gp, v, n, vn, level, k));
	}

	/**
	 * Calculate global effect reflected and refracted
	 * 
	 * @param gp
	 * @param v
	 * @param n
	 * @param vn
	 * @param level
	 * @param k
	 * @return
	 */
	private Color calcGlobalEffects(GeoPoint gp, Vector v, Vector n, double vn, int level, Double3 k) {
		Material material = gp.geometry.getMaterial();
		Ray reflectedRay = constructReflectedRay(gp.point, v, n, vn);//מוצא את הקרן שנשברת בגוף המשתקפת  
		Ray refractedRay = constructRefractedRay(gp.point, v, n);
		return calcGlobalEffect(material, n, refractedRay, level, material.kt, k)//מחשב את הצבע לפי השקיפות בנקודה 
				.add(calcGlobalEffect(material, n, reflectedRay, level, material.kr, k));//בודק את ההשתקפות
	}

	private Color calcGlobalEffect(Material material, Vector n, Ray ray, int level, Double3 kx, Double3 k) {
		Double3 kkx = kx.product(k);//בודקת את המקדמי הנחתה  
		if (kkx.lowerThan(MIN_CALC_COLOR_K))
			return Color.BLACK;

		var rays = ray.generateBeam(n, material.blurGlassRadius, material.blurGlassDistance, material.numOfRays);
		return calcAverageColor(rays, level - 1, kkx).scale(kx);
	}

	/**
	 * Calculate the effect of different light sources on a point in the scene
	 * according to the Phong model.
	 *
	 * @param gp Point on geometry in the scene.
	 * @param v
	 * @param n
	 * @param vn
	 * @param k  The transparency coefficient.
	 * @return The color contribution from the local light effects.
	 */
	private Color calcLocalEffects(GeoPoint gp, Vector v, Vector n, double vn, Double3 k) {
		int nShininess = gp.geometry.getMaterial().nShininess;//
		Double3 ks = gp.geometry.getMaterial().kS;//מקדם הנחתה של transmission- 
		Double3 kd= gp.geometry.getMaterial().kD;//מקדם הנחתה של deffuse - פיזור של אור

		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lightSources) {//בודק את כל ההשפעה את התאורה על הגוף
			Vector l = lightSource.getL(gp.point);//לוקח את הווקטןר לכיוון המנורה
			double nl = alignZero(n.dotProduct(l));//מכפלה סקלרית שמחשבת את הזווית בין הנורמל לכיוון האור

			if (nl * vn > 0) { // sign(nl) == sign(vn)// אם הסימנים כלומר יש השפעה של מקור האור על הגוף
				Double3 ktr = transparency(gp, lightSource, l, n);//מחשב את ההשפעה של האור על הנקודה כלומר השקיפות של הגוף
				if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
					Color lightIntensity = lightSource.getIntensity(gp.point).scale(ktr);
					color = color.add(calcDiffuse(kd, nl, lightIntensity),//חישוב של הברק ומריחת הצבע על פני החומר
							calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
				}
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
		return lightIntensity.scale(kd.scale(nl < 0 ? -nl : nl));//
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
		return minusVR <= 0 ? Color.BLACK : lightIntensity.scale(ks.scale(Math.pow(minusVR, nShininess)));
	}

	/**
	 * Checks if a given point is unshaded by a light source.
	 *
	 * @param geoPoint The geometric point to be checked.
	 * @param light    The light source.
	 * @param toLight  The vector representing the direction from the point to the
	 *                 light source.
	 * @param normal   The surface normal at the point.
	 * @return {@code true} if the point is unshaded by the light source,
	 *         {@code false} otherwise.
	 */
	@SuppressWarnings("unused")//בודק אם יש/אין צל בגוף
	private boolean unshaded(GeoPoint geoPoint, LightSource light, Vector toLight, Vector normal) {
		Vector lightDirection = toLight.scale(-1); // The direction from point to light source
		Ray lightRay = new Ray(geoPoint.point, lightDirection, normal);
		//יוצרים קרן חדשה במכפלה בין שני הווקטורים  מכיוון האור לגוף , קרן נוספת הנורמל של הגוף 
		//הקרן תהיה ממקור האור לעבר הגוף
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,
				light.getDistance(geoPoint.point));
		//הזזנו את קרן האור המרכזית לנקודת חיתוך על פני הגוף ומצאנו את המרחק ואנחשב חיתוכים בתוך הטווח של האור 
		
		if (intersections == null)//לא מצאנו חיתוכים כלומר שאין צל לגוף
			return true;

		for (GeoPoint g : intersections)// עובר בכל החיתוכים שמצאנו בגוף הספציפי
			if (g.geometry.getMaterial().kt == Double3.ZERO)//זא שהגוף אטום ולכן יש צל 
				return false;

		return true;
	}

	/**
	 * get light and gp and move ao all the objects between them and calculate the
	 * transparency
	 * 
	 * @param gp
	 * @param light
	 * @param l
	 * @param n
	 * @return
	 */
	private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {

		//מחשב את 
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(gp.point, lightDirection, n);

		//מוצא את כל החיתוכים ומוריד כל פעם את רמת השקיפות
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
		Double3 ktr = new Double3(1d);
		if (intersections == null)//אם לא מצאנו חיתוך מחזיר את מקדם השקיפות הנוכחי
			return ktr;

		for (GeoPoint p : intersections) {//אם מצא חיתוכים מחשב את ההשפעה של השקיפות
			ktr = ktr.product(p.geometry.getMaterial().kt);//מחשב את רמת לפי מקדם הנחתה 
			if (ktr.lowerThan(MIN_CALC_COLOR_K))
				return Double3.ZERO;
		}

		return ktr;
	}

	/**
	 * Calculate refracted ray
	 * 
	 * @param pointGeo
	 * @param v
	 * @param n
	 * @param n
	 * @return
	 */
	//מחזירקה את כיוון השקיפות על פי ווקטור ונורמל בנקודת חיתוך
	private Ray constructRefractedRay(Point pointGeo, Vector v, Vector n) {
		return new Ray(pointGeo, v, n);//בודקים אם הזווית חיובית מוסיפים דלתא חיובית אחרת מפחיתים כדי שהיא לא תחתוך את הכדור
	}

	/**
	 * Calculate Reflected ray

	 * 
	 * @param pointGeo
	 * @param v
	 * @param n
	 * @param vn
	 * @return
	 */
	//עושה מכפלה בין הווקטור לונרמל מוצאים את הקרן של השתקפות לתוך עי הנורמל
	private Ray constructReflectedRay(Point pointGeo, Vector v, Vector n, double vn) {
		// 𝒓=𝒗 −𝟐∙(𝒗∙𝒏)∙𝒏   
		//הזווית בין הווקטור לנורמל כפול הנורמל
		Vector r = v.subtract(n.scale(2 * vn));
		return new Ray(pointGeo, r, n);
	}

	/**
	 * get ray and return the closet intersection geoPoint
	 * 
	 * @param ray
	 * @return
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
	}

	/**
	 * get list of ray
	 * 
	 * @param rays
	 * @param level
	 * @param kkx
	 * @return average color of the intersection of the rays
	 */
	Color calcAverageColor(List<Ray> rays, int level, Double3 kkx) {
		Color color = Color.BLACK;

		for (Ray ray : rays) {
			GeoPoint intersection = findClosestIntersection(ray);
			color = color.add(intersection == null ? scene.background : calcColor(intersection, ray, level - 1, kkx));
		}

		return color.reduce(rays.size());
	}

}
