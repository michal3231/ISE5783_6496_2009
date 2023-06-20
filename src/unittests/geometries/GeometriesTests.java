/**
 * 
 */
package unittests.geometries;

import geometries.*;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * test class to check all function of Geometries class
 * 
 * @author David
 *
 */
class GeometriesTests {

	/**
	 * test Find Intersections(ray)
	 */
	@Test
	void testFindIntersections() {
		Sphere sphere = new Sphere(1, new Point(0, 2, 2));
		Triangle triangle = new Triangle(new Point(3, 0, 0), new Point(-3, 0, 0), new Point(0, 0, 3));
		Plane plane = new Plane(new Point(2, 4, 0), new Point(-2, 4, 0), new Point(0, 4, 7));
		Geometries geometries = new Geometries(sphere, triangle, plane);

		// ============ Equivalence Partitions Tests ==============

		// TC01 cross part of the geometries (2 from 3)
		Ray ray = new Ray(new Point(0, -2, 4.1), new Vector(0, 4, -1.8));
		assertEquals(3, geometries.findIntersections(ray).size(), "ERROR TC01");

		// =============== Boundary Values Tests ==================

		// TC02 cross all geometries
		ray = new Ray(new Point(0, -2, 0.9), new Vector(0, 4, 1.4));
		assertEquals(4, geometries.findIntersections(ray).size(), "ERROR TC02");

		// TC03 cross only 1
		ray = new Ray(new Point(0, 2.3, 4.1), new Vector(0, -0.3, -1.8));
		assertEquals(2, geometries.findIntersections(ray).size(), "ERROR TC03");

		// TC04 cross nothing
		ray = new Ray(new Point(-2, -2.3, 4.1), new Vector(0, -0.2, -0.7));
		assertNull(geometries.findIntersections(ray), "ERROR TC04");

		// TC05 empty geometry
		assertNull(new Geometries().findIntersections(ray), "ERROR TC05");
	}

}
