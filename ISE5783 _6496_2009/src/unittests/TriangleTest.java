package unittests.geometries;

import org.junit.jupiter.api.Test;

import geometries.Triangle;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

	/**
	 * get normal
	 */
	@Test
	void getNormal() {
		// =============== Equivalence Partitions Tests ==============
		// TC01: constructor acting well
		assertThrows(IllegalArgumentException.class,() -> new Triangle(new Point(0, 1, 0), new Point(1, 0, 0), new Point(1, 1, 0));,"ERROR: TC01)")

		// TC02: simple check
		Triangle t = new Triangle(new Point(0, 1, 0), new Point(1, 0, 0), new Point(1, 1, 0));
		assertEquals(new Vector(-0, -0, -1), t.getNormal(new Point(0, 1, 0)), "ERROR: TC02");
	}

	/**
	 * 
	 */
	@Test
	void TestFindIntersections() {

	}

}