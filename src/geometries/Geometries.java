package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.BoundingBox;
import primitives.Point;
import primitives.Ray;

/**
 * The department implements operations for several geometric bodies
 * 
 * @author michal
 *
 */
public class Geometries extends Intersectable {

	private List<Intersectable> geometList = new LinkedList<>();

	/**
	 * The default constructor (the list will be empty)
	 */
	public Geometries() {
	}

	/**
	 * a constructor that receives a list of geometric objects and adds them to the
	 * list
	 * 
	 * @param geometries geometries Intersectable
	 */
	public Geometries(Intersectable... geometries) {
		if (geometries != null)
			add(geometries);
	}

	/**
	 * add object to list
	 * 
	 * @param geometries objects to add
	 */
	public void add(Intersectable... geometries) {
		this.geometList.addAll(List.of(geometries));
		
	    //build a bounding box
        //search in all new geometries
        //for the min and max X,Y,Z (if they bigger then the current x,y,z bounding box)
        double xMax = Double.NEGATIVE_INFINITY;
        double xMin =  Double.MAX_VALUE;

        double yMax =  Double.NEGATIVE_INFINITY;
        double yMin = Double.MAX_VALUE;

        double zMax =  Double.NEGATIVE_INFINITY;
        double zMin = Double.MAX_VALUE;


        for (Intersectable g : this.geometList) {

            //check x
            if (g.boundingBox.getxMin() < xMin)
                xMin = g.boundingBox.getxMin();

            if (g.boundingBox.getxMax() > xMax)
                xMax = g.boundingBox.getxMax();

            //check y
            if (g.boundingBox.getyMin() < yMin)
                yMin = g.boundingBox.getyMin();

            if (g.boundingBox.getyMax() > yMax)
                yMax = g.boundingBox.getyMax();

            //check z
            if (g.boundingBox.getzMin() < zMin)
                zMin = g.boundingBox.getzMin();

            if (g.boundingBox.getzMax() > zMax)
                zMax = g.boundingBox.getzMax();
        }
        boundingBox = new BoundingBox(new Point(xMin,yMin,zMin), new Point(xMax,yMax,zMax));
	}
	
    // ***************** Operations ******************** //
    public Geometries setBoundingBox(Point p1, Point p2) {
        this.boundingBox = new BoundingBox(p1, p2);
        return this;
    }

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double distance) {
		
		  if (this.boundingBox!=null && !this.boundingBox.intersectionBox(ray))
	            return null;
		
		List<GeoPoint> intersectionList = null;

		// find all intersection between all geometries list and ray
		for (Intersectable geometry : this.geometList) {
			var tempIntersections = geometry.findGeoIntersections(ray, distance);

			if (tempIntersections != null) {
				if (intersectionList == null)
					intersectionList = new LinkedList<>(tempIntersections);
				else
					intersectionList.addAll(tempIntersections);
			}
		}
		return intersectionList;
	}
}