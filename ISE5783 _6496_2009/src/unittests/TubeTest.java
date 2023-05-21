package unittests;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

	/**
	 * test get normal
	 */
	@Test
	void TestGetNormal() {
		Ray ray = new Ray(new Point(0,0,0),new Vector(0, 0, 1));
		Tube tube = new Tube(ray,Math.sqrt(2));

		// =============== Equivalence Partitions Tests ==============
		// TC01: simple check
		assertEquals(new Vector(1, 1, 0), tube.getNormal(new Point(1, 1, 2)), "the normal is not correct");

		// =============== Boundary Values Tests ==================
		// TC11: checking if the
		assertEquals(new Vector(1, 1, 0), tube.getNormal(new Point(1, 1, 1)), "the normal is not correct");
	}
}