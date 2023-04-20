/**
 * 
 */
package unitest;

import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author USER
 *
 */
class PointTest {

	Point p1=new Point(1,2,0);
	Point p2=new Point(2,4,7);
	Point p3=new Point(1,2,3);
	
	Vector v1=new Vector(1,2,3);
	Vector v2=new Vector(2,4,6);
	Vector v3=new Vector(0,-5,3);
	Vector v5=new Vector(-2,-1,-4);
	
	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void Subtract() {
		assertEquals(new Point(-1,-2,-7),p1.subtract(p2),"ERROR test faild at subtract function");
		assertEquals(new Point(0,0,-3) ,p3.subtract(p1),"ERROR test faild at subtract function");
		assertEquals(new Point(1,2,7),p2.subtract(p1),"ERROR test faild at subtract function");
	}

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		assertEquals(new Point(-1,-2,-7),p1.add(v2),"ERROR test faild at add function");
		assertEquals(new Point(0,0,-3) ,p3.add(v1),"ERROR test faild at add function");
		assertEquals(new Point(1,2,7),p2.add(v1),"ERROR test faild at add function");
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
		//assertEquals(new Point(),p2.distanceSquared(p1),"ERROR test fail at add function");
	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {

	}

}
