package unittests;

import org.junit.jupiter.api.Test;

import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
class TriangleTest {

	/**
	 * get normal
	 */
	@Test
	void getNormal() {
		// =============== Equivalence Partitions Tests ==============
		// TC01: constructor acting well
		assertThrows(IllegalArgumentException.class,()-> new Triangle(new Point(0, 1, 0), new Point(0, 1, 0), new Point(1, 1, 0)),"ERROR: TC01)");

		// TC02: simple check
		Triangle t = new Triangle(new Point(0, 1, 0), new Point(1, 0, 0), new Point(1, 1, 0));
		assertEquals(new Vector(-0, -0, -1), t.getNormal(new Point(0, 1, 0)), "ERROR: TC02");
	}
	
	 @Test
	    void testFindIntersections() {

	        Triangle triangle =new Triangle(new Point(0,0,1),new Point(1,0,0), new Point(-1,0,0));
	        List<Point> points ;
	        // ============ Equivalence Partitions Tests ==============

	        //TC01: Inside triangle
	        points = triangle.findIntersections((new Ray( new Vector(0, -1, 0),new Point(0, 2, 0.5))));

	        assertEquals(List.of(new Point(0,0,0.5)),points,"ERROR: Ray Inside when the triangle");

	        //TC02: Outside against edge
	        assertNull(triangle.findIntersections((new Ray( new Vector(0, 1, 0),new Point(0.5, -2, -1)))),
	                "Ray starts outside against edge");

	        //TC03: Outside against vertex
	        assertNull(triangle.findIntersections((new Ray( new Vector(0, 1, 0),new Point(1.5, -2, -0.2)))),
	                "Ray starts outside against vertex");

	        // =============== Boundary Values Tests ==================

	        //TC11: the point is on edge
	        assertNull( triangle.findIntersections((new Ray( new Vector(0, 1, 0),new Point(0.5, -2, 0)))),
	                "Ray's point is on the edge");

	        //TC12: the point is in vertex
	        assertNull( triangle.findIntersections((new Ray( new Vector(0, 1, 0),new Point(1, -1, 0)))),
	                "Ray's point is in vertex");

	        //TC13: the point is On edge's continuation
	        assertNull(triangle.findIntersections((new Ray( new Vector(0, 1, 0),new Point(2, -2, 0)))),
	                "Ray's point is On edge's continuation");
	    }

}