package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import primitives.Ray;
import primitives.Point;
import primitives.Vector;

/**
 * 
 * The {@code RayTest} class contains unit tests for the {@link Ray} class.
 * 
 * It tests the functionality of the {@link Ray#findClosestPoint(List)} method.
 */
class RayTest {

	/**
	 * objects for test
	 */
	private Ray ray = new Ray(new Point(1, 1, 1), new Vector(1, 1, 1));
	private Point p1 = new Point(0.5, 0.5, 0.5);
	private Point p2 = new Point(0, 0.5, 0.5);
	private Point p3 = new Point(-1, 0.5, 0.5);

	/**
	 * 
	 * Test method for {@link Ray#findClosestPoint(List)}.
	 */
	@Test
	void TestFindClosestPoint() {

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
