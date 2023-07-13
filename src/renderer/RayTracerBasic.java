package renderer;

import geometries.Geometry;
import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase{

    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


    //constructor
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    //override finding the point and
    @Override
    public Color traceRay(Ray ray) {
        return findClosestIntersection(ray) != null ? calcColor(findClosestIntersection(ray), ray) : scene.background;
      /*
        List<GeoPoint> points = scene.geometries.findGeoIntersections(ray);
        if(points == null)
            return scene.background;
        GeoPoint point = ray.findClosestGeoPoint(points);
        return calcColor(point,ray);*/
    }
/*
    public Color calcColor(GeoPoint point,Ray ray){
        return scene.ambientLight.getIntensity().add(point.geometry.getEmission()).add(calcLocalEffects(point, ray)).add(calcGlobalEffects(point, ray));;
    }
*/

    /**
     *
     * @param geopoint
     * @param ray
     * @return
     */
    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(findClosestIntersection(ray), ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K))
                .add(scene.ambientLight.getIntensity());
    }

    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = gp.geometry.getEmission()
                      .add(calcLocalEffects(gp, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }


    /**
     *
     * @param gp
     * @param ray
     * @return
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        Double3 kkr = material.kR.product(k);
        if (!(kkr.lowerThan(MIN_CALC_COLOR_K)))
            color = calcGlobalEffect(reflection(gp, ray.getVector(), n), level, material.kR, kkr);
        Double3 kkt = material.kT.product(k);
        if (!(kkt.lowerThan(MIN_CALC_COLOR_K)))
            color = color.add(calcGlobalEffect(refraction(gp, ray, n), level, material.kT, kkt));
        return color;
    }

    /**
     *
     * @param ray
     * @param level
     * @param kx
     * @param kkx
     * @return
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection (ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)
        ).scale(kx);
    }



    /**
     * Calculate the effects of lights
     *
     * @param intersection
     * @param ray
     * @return The color resulted by local effecrs calculation
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) {
        Vector v = ray.getVector();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = n.dotProduct(v);
        if (Util.isZero(nv))
            return Color.BLACK;
        int nShininess = intersection.geometry.getMaterial().nShininess;

        Double3 kd = intersection.geometry.getMaterial().kD, ks = intersection.geometry.getMaterial().kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // checks if nl == nv
                Double3 ktr = transparency(intersection, lightSource, l.scale(-1), n);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }


    /**
     * Calculates diffusive light
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return The color of diffusive effects
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        double ln = alignZero(l.dotProduct(n));
        if (ln < 0)
            ln = ln * -1;
        return lightIntensity.scale( kd.scale(ln));
    }

    /**
     * Calculate specular light
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return The color of specular reflection
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        double vr = alignZero(v.scale(-1).dotProduct(r));
        vr = Math.max(0, vr);
        vr = Math.pow(vr, nShininess);
        return lightIntensity.scale(ks.scale(vr));
    }

    /**
     * Checks if there is no shade between a point and a light source
     *
     * @param l
     * @param n
     * @param gp
     * @param ls
     * @return Boolean value if the unshaded check was successful
     */
    private boolean unshaded(GeoPoint gp, LightSource ls, Vector l, Vector n){
        if(gp.geometry.getMaterial().kT.equals(Double3.ZERO)) {
            Ray lightRay = new Ray(gp.point, l, n);
            var intersections = scene.geometries.findGeoIntersections(lightRay, ls.getDistance(lightRay.getPoint()));
            return intersections == null;
        }
        return true;
    }


    /**
     * Checks if there are shades between a point and a light source
     * שnd calculates the transparency coefficient
     *
     * @param gp
     * @param ls
     * @param l
     * @param n
     * @return
     */
    private Double3 transparency(GeoPoint gp, LightSource ls, Vector l, Vector n){
        Ray lightRay = new Ray(gp.point, l, n);
        var intersections = scene.geometries.findGeoIntersections(lightRay, ls.getDistance(gp.point));
        if (intersections == null) return Double3.ONE;
        Double3 ktr = Double3.ONE;
        for (var intersection : intersections) {
            ktr = ktr.product(intersection.geometry.getMaterial().kT);
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
        }
        return ktr;
    }

    /**
     *
     * @param gp  intersection point
     * @param l  vector from light to point
     * @param n  vector normal to the geometry
     * @return the reflaction ray
     */
    private Ray reflection(GeoPoint gp, Vector l, Vector n){

        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        return new Ray(gp.point, r, n);
    }

    /**
     *
     * @param gp  intersection point
     * @param ray  ray from light to point
     * @return the refraction ray
     */
    private Ray refraction(GeoPoint gp, Ray ray, Vector n){

        return new Ray(gp.point, ray.getVector(), n);
    }


    /**
     * finds the closest point that intersects with the ray for 'calcColor'
     *
     * @param ray
     * @return the closest intersection
     */
    private GeoPoint findClosestIntersection(Ray ray){
        List<GeoPoint> points = this.scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(points);
    }
}
