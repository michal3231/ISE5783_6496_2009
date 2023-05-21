package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

class RayTest {
	@Test
	void TestFindClosestPoint() {

		Ray ray = new Ray(new Point(1, 1, 1), new Vector(1, 1, 1));
		Point p1 = new Point(0.5, 0.5, 0.5);
		Point p2 = new Point(0, 0.5, 0.5);
		Point p3 = new Point(-1, 0.5, 0.5);

		// ============ Equivalence Partitions Tests ==============

		// TC01: closest point in the list
		assertEquals(p1, ray.findClosestPoint(List.of(p2, p1, p3)), "Error: TC01");

		// =============== Boundary Values Tests ==================

		// TC02: closest point in the top of the list
		assertEquals(p1, ray.findClosestPoint(List.of(p1, p2, p3)), "ERROR: TC02");

		// TC03: closest point in the end of the list
		assertEquals(p1, ray.findClosestPoint(List.of(p2, p3, p1)), "ERROR: TC03");

		// TC04: no point in the list
		assertNull(ray.findClosestPoint(List.of()), "ERROR: TC04");
	}
}
