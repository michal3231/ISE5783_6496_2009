/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.Camera;

/**
 * Integration tests between beamforming cameras Calculation of sections of a
 * beam with geometric bodies
 * 
 * @author David
 *
 */
class itegrationTest {

	private Camera camera;
	private Intersectable shape;

	/**
	 * 
	 * @return sum of intersections between camera and shape
	 */
	private int findIntersactions() {

		Ray ray;
		int sum = 0;
		List<Point> intersactions;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				// get the ray from camera to pixel
				ray = camera.constructRay(3, 3, j, i);

				// get the intersections with this ray
				intersactions = shape.findIntersections(ray);

				if (intersactions != null)
					sum += intersactions.size();
			}
		}
		return sum;
	}

	/**
	 * case of camera intersections with sphere
	 */
	@Test
	void sphereCameraIntersactionTest() {

		// TC01 1 ray only in front of camera
		camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(3d, 3d)
				.setVPDistance(1);
		shape = new Sphere(new Point(0, 0, -3), 1);
		assertEquals(2, findIntersactions(), "ERROR TC01");

		// TC02 sphere in front of all camera rays
		camera = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(3d, 3d)
				.setVPDistance(1);
		shape = new Sphere(new Point(0, 0, -2.5), -2.5);
		assertEquals(18, findIntersactions(), "ERROR TC02");

		// TC03 sphere in front of part camera rays
		shape = new Sphere(new Point(0, 0, -2), 2);
		assertEquals(10, findIntersactions(), "ERROR TC03");

		// TC04 the VP inside the sphere
		camera = new Camera(new Point(0, 0, 1), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(3d, 3d)
				.setVPDistance(1);
		shape = new Sphere(new Point(0, 0, 0), 4);
		assertEquals(9, findIntersactions(), "ERROR TC04");

		// TC05 sphere behind camera
		camera = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(3d, 3d)
				.setVPDistance(1);
		shape = new Sphere(new Point(0, 0, 1), 0.5);
		assertEquals(0, findIntersactions(), "ERROR TC05");

	}

	/**
	 * case of camera intersections with plane
	 */
	@Test
	void planeCameraIntersactionTest() {

		// plan standing in front of view plane (9 points)
		camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(3d, 3d)
				.setVPDistance(1);
		shape = new Plane(new Point(1, -3.2, -3.5), new Point(1.12, 2.3, -3.5), new Point(3.4, 12.5, -3.5));
		assertEquals(9, findIntersactions(), "ERROR 01");

		// plane standing diagonally in front of camera (9 points)
		shape = new Plane(new Point(1, 2, -3.5), new Point(1, 3, -3), new Point(4, 2, -3.5));
		assertEquals(9, findIntersactions(), "ERROR 02");
		// plane standing diagonally in front of camera (6 points)
		shape = new Plane(new Point(1, 2, -3.5), new Point(1, 3, -2.5), new Point(4, 2, -3.5));
		assertEquals(6, findIntersactions(), "ERROR 03");

	}

	/**
	 * cases of camera intersection with triangle
	 */
	@Test
	void triangleCameraIntersactionTest() {

		// triangle standing in front of view plane (1 point)
		camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(3d, 3d)
				.setVPDistance(1);
		shape = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
		assertEquals(1, findIntersactions(), "ERROR TC01");

		// triangle standing in front of view plane (2 points)
		shape = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
		assertEquals(2, findIntersactions(), "ERROR TC2");
	}
}