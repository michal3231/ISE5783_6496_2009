package unittests.geometries;

import org.junit.jupiter.api.Test;
import java.util.List;
import geometries.Triangle;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test class to check all function of triangle class
 * 
 * @author David
 *
 */
class TriangleTest {

	/**
	 * get normal
	 */
	@Test
	void getNormal() {
		// =============== Equivalence Partitions Tests ==============
		// TC01: constructor acting well
		assertThrows(IllegalArgumentException.class,
				() -> new Triangle(new Point(0, 1, 0), new Point(0, 1, 0), new Point(1, 1, 0)), "ERROR: TC01)");

		// TC02: simple check
		Triangle t = new Triangle(new Point(0, 1, 0), new Point(1, 0, 0), new Point(1, 1, 0));

		boolean bool = new Vector(0, 0, -1).equals(t.getNormal(new Point(0, 1, 0)))
				|| new Vector(0, 0, 1).equals(t.getNormal(new Point(0, 1, 0)));
		assertTrue(bool, "ERROR: TC02");

	}

	/**
	 * 
	 * Tests the {@link Triangle#findIntersections(Ray)} method to verify its
	 * behavior
	 * 
	 * in different scenarios.
	 */
	void testFindIntersections() {

		Triangle triangle = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(-1, 0, 0));
		List<Point> points;

		// ============ Equivalence Partitions Tests ==============

		// TC01: Inside triangle
		points = triangle.findIntersections(new Ray(new Point(0, 2, 0.5), new Vector(0, -1, 0)));
		assertEquals(List.of(new Point(0, 0, 0.5)), points, "ERROR: TC01");

		// TC02: Outside against edge
		assertNull(triangle.findIntersections(new Ray(new Point(0.5, -2, -1), new Vector(0, 1, 0))), "ERROR: TC02");

		// TC03: Outside against vertex
		assertNull(triangle.findIntersections(new Ray(new Point(1.5, -2, -0.2), new Vector(0, 1, 0))), "ERROR: TC03");

		// =============== Boundary Values Tests ==================

		// TC11: Point on edge
		assertNull(triangle.findIntersections(new Ray(new Point(0.5, -2, 0), new Vector(0, 1, 0))), "ERROR: TC11");

		// TC12: Point in vertex
		assertNull(triangle.findIntersections(new Ray(new Point(1, -1, 0), new Vector(0, 1, 0))), "ERROR: TC12");

		// TC13: Point on edge's continuation
		assertNull(triangle.findIntersections(new Ray(new Point(2, -2, 0), new Vector(0, 1, 0))), "ERROR: TC13");
	}
}