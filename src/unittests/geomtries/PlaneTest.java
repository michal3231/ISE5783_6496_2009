package unittests.geomtries;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
class PlaneTest {
	/**
	 * test for constructor
	 */
	@Test
	void testConstructor() {
		// =============== Boundary Values Tests ==================

		// check constructor two point the same
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(1, 2, 6.3), new Point(1, 2, 6.3), new Point(0, 0, 0)),
				"ERROR: ctor get two point the same");

		// check constructor all point on the same line
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(1, 2, 6.3), new Point(2, 4, 12.6), new Point(0.5, 1, 3.15)),
				"ERROR: ctor get all point on the same line");
	}

	/**
	 * tests for getNormal(Point)
	 */
	@Test
	void testGetNormal() {
		// plane to tests
		Plane p = new Plane(new Point(1, 1, 0), new Point(2, 1, 0), new Point(1, 2, 0));

		// ============ Equivalence Partitions Tests ==============

		// test normal to plane (its can be 2 sides)
		boolean bool = new Vector(0, 0, 1).equals(p.getNormal(new Point(3, 2, 0)))
				|| new Vector(0, 0, -1).equals(p.getNormal(new Point(3, 2, 0)));
		assertTrue(bool, " ERROR: getNormal() worng result");
	}
	
	   /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        Plane plane = new Plane(new Point(1,1,0),new Vector(0,1,0));
        List<Point> points;


        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane (1 points)
        points = plane.findIntersections(new Ray(new Point(0, 0, 0),new Vector(1, 1, 0)));
        assertEquals( List.of( new Point(1,1,0)),
                points,"ERROR: Ray intersects the plane");

        // TC02: Ray does not intersect the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(10,10,10), new Vector(1, 1, 0))),
                "ERROR: Ray does not intersect the plane");


        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // TC11: Ray included (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1,0,0),new Vector(1, 0, 0))),
                "ERROR When Ray parallel and included");

        // TC12: Ray not included in the plane(0 points)
        assertNull(plane.findIntersections(new Ray(new Point(10,10,10),new Vector(1,0, 0))),
                "ERROR When Ray parallel and not included in the plane");

        // **** Group: Ray is orthogonal to the plane
        // TC13: Ray starts before the plane (1 points)
        points = plane.findIntersections(new Ray(new Point(0,0,0),new Vector(0,1,0)));
        assertEquals( List.of(new Point(0,1,0)), points,"ERROR: Ray starts before the plane");

        // TC14: Ray starts in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(0,1,0), new Vector(0,17,0))), "ERROR: Ray starts in the plane");

        // TC15: Ray starts after the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(0,5,0),new Vector(0,1,0))), "ERROR: Ray starts after the plane");


        // **** Group: Ray is neither orthogonal nor parallel
        // TC16: Ray starts at the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(0,1,0),new Vector(1,1, 0))), "ERROR: Ray starts at the plane");

        // TC17: Ray starts begins in the same point which appears as reference point in the plane (0 points)
        assertThrows(IllegalArgumentException.class,()->plane.findIntersections(new Ray(new Point(1,1,0),new Vector(1,1, 0))),
                "Ray starts begins in the same point which appears as reference point in the plane");

    }
	
	
}