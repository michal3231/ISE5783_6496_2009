/**
 * 
 */
package geometries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * 
 * @author 
 *
 */
public class Geometries implements Intersectable {

	private List<Intersectable> geometList = new LinkedList<Intersectable>();

	/**
	 *  
	 */
	public Geometries() {
	}

	/**
	 *  
	 * @param geometries
	 */
	public Geometries(Intersectable... geometries) {
		add(geometries);
	}

	/**
	 * add object to list
	 * 
	 * @param geometries objects to add
	 */
	public void add(Intersectable... geometries) {
		for (Intersectable geo : geometries) {
			this.geometList.add(geo);
		}
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> intersectionList = null;

		for (Intersectable geometry : this.geometList) {
			List<Point> tempIntersections = geometry.findIntersections(ray);
			if (geometry.findIntersections(ray) != null) {
				if (intersectionList == null)
					intersectionList = new LinkedList<>();
				intersectionList.addAll(tempIntersections);
			}
		}
		return intersectionList;
	}
}