package unittests;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import geometries.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class SphereTest {
	Sphere sphere1 = new Sphere(new Point(1, 2, 3), 10);// positive coordinate
	Point p1 = new Point(-1, -2, -4);// negative coordinate

	/***
	 * The function checks the integrity of the getNormal function
	 */
	@Test
	void getNormal() {
		// TC1 positive coordinate sphere and point
		assertEquals(new Vector(-2 / Math.sqrt(69), -4 / Math.sqrt(69), -7 / Math.sqrt(69)), sphere1.getNormal(p1),
				"ERROR: TC 01");

	}

	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	void testFindIntersections() {
		Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);

		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray's line is outside the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Vector(1, 1, 0), new Point(-1, 0, 0))),
				"Ray's line out of sphere");

		// TC02: Ray starts before and crosses the sphere (2 points)
		Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
		Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
		List<Point> points = sphere.findIntersections(new Ray(new Vector(3, 1, 0), new Point(-1, 0, 0)));

		if (points.get(0).getX() > points.get(1).getX())
			points = List.of(points.get(1), points.get(0));
		assertEquals(List.of(p1, p2), points, "Ray crosses sphere");

		// TC03: Ray starts inside the sphere (1 point)
		points = sphere.findIntersections(new Ray(new Vector(0, 1, 0), new Point(1, 0, 0)));
		assertEquals(List.of(new Point(1, 1, 0)), points,
				"Error find incorrect intersection when ray start inside the sphere");

		// TC04: Ray starts after the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Vector(1, 1, 1), new Point(10, 10, 10))),
				"ERROR ray start after the sphere");

		// =============== Boundary Values Tests ==================

		// ** Group: Ray'sphere line crosses the sphere (but not the center)
		// TC11: Ray starts at sphere and goes inside (1 points)
		points = sphere.findIntersections(new Ray(new Vector(1, 1, 0), new Point(0, 0, 0)));
		assertEquals(points, List.of(new Point(1, 1, 0)), "Error when Ray starts at sphere and goes inside  ");

		// TC12: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntersections(new Ray(new Vector(-1, -1, 0), new Point(0, 0, 0))),
				"ERROR when  Ray starts at sphere and goes outside");

		// ** Group: Ray'sphere line goes through the center

		// TC13: Ray starts before the sphere (2 points)
		points = sphere.findIntersections(new Ray(new Vector(1, 0, 0), new Point(-1, 0, 0)));
		// ray cross the sphere
		p1 = new Point(0, 0, 0);
		p2 = new Point(2, 0, 0);

		if (points.get(0).getX() > points.get(1).getX())
			points = List.of(points.get(1), points.get(0));

		assertEquals(List.of(p1, p2), points, "Ray crosses sphere");

		// TC14: Ray starts at sphere and goes inside (1 points)
		points = sphere.findIntersections(new Ray(new Vector(1, 0, 0), new Point(0, 0, 0)));

		assertEquals(List.of(new Point(2, 0, 0)), points, "ERROR Ray starts at sphere and goes inside");

		// TC15: Ray starts inside (1 points)
		points = sphere.findIntersections(new Ray(new Vector(1, 0, 0), new Point(0.5, 0, 0)));
		assertEquals(List.of(new Point(2, 0, 0)), points, "ERROR Ray starts inside");

		// TC16: Ray starts at the center (1 points)
		points = sphere.findIntersections(new Ray(new Vector(1, 0, 0), new Point(1, 0, 0)));
		assertEquals(List.of(new Point(2, 0, 0)), points, "ERROR Ray starts at the center");
		// TC17: Ray starts at sphere and goes outside (0 points)

		assertNull(sphere.findIntersections(new Ray(new Vector(-1, 0, 0), new Point(0, 0, 0))),
				"ERROR Ray starts at sphere and goes outside");

		// TC18: Ray starts after sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Vector(1, 0, 0), new Point(4, 0, 0))),
				"ERROR Ray starts after sphere");
		// ** Group: Ray'sphere line is tangent to the sphere (all tests 0 points)
		// TC19: Ray starts before the tangent point
		assertNull(sphere.findIntersections(new Ray(new Vector(1, 0, 0), new Point(0.5, 1, 0))),
				"ERROR Ray starts before the tangent point");
		// TC20: Ray starts at the tangent point
		assertNull(sphere.findIntersections(new Ray(new Vector(1, 0, 0), new Point(1, 1, 0))),
				"ERROR Ray starts at the tangent point");

		// TC21: Ray starts after the tangent point
		assertNull(sphere.findIntersections(new Ray(new Vector(1, 0, 0), new Point(2, 1, 0))),
				"ERROR Ray starts after the tangent point");
		// ** Group: Special cases
		// TC19: Ray'sphere line is outside, ray is orthogonal to ray start to
		// sphere'sphere center line
		assertNull(sphere.findIntersections(new Ray(new Vector(0, 1, 0), new Point(-0.5, 0, 0))),
				"ERROR Ray's line is outside");
	}
}