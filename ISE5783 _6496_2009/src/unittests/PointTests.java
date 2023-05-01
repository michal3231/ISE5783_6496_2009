/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;;
/**
 * 
 */
class PointTests {

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {
		
		// ============ Equivalence Partitions Tests ==============
		//TC01: test that subtract is proper
		
		Point p1 = new Point(1,2,3);
		Point p2 = new Point(1,5,7);
		Vector vr = new Vector(0,-3,-4);
		assertEquals(p1.subtract(p2), vr, "Substract() is wrong");
		
		// =============== Boundary Values Tests ==================
		//TC11: test subtract same values
		
		assertThrows(IllegalArgumentException.class, () -> p1.subtract(new Point(1,2,3)), "Substract for 0 vector"
				+ " does not throw an exception");
	}

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		
		// ============ Equivalence Partitions Tests ==============
		//TC01: test that add is proper
		Vector v1 = new Vector(1,2,3);
		Point p1 = new Point(4,5,6);
		Vector vr = new Vector(5,7,9);
		assertEquals(p1.add(v1), vr, "Add() is wrong");
		
		
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
		
		// ============ Equivalence Partitions Tests ==============
		//TC01: test that DistanceSquared is proper
		Point p1 = new Point(1,2,3);
		Point p2 = new Point(4,5,6);
		assertEquals(p1.distanceSquared(p2),27, 0.00001, "DistanceSquared() is wrong");
		
	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
		// ============ Equivalence Partitions Tests ==============
		//TC01: test that Distance is proper
		Point p1 = new Point(0,0,3);
		Point p2 = new Point(0,4,0);
		assertEquals(5, p1.distance(p2), 0.00001, "Distance() is wrong");
	}

}
