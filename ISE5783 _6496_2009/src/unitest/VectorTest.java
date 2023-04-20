/**
 * 
 */
package unitest;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Vector;

import org.junit.jupiter.api.Test;

import java.lang.Math;
/**
 * @author USER
 *
 */
class VectorTest {

	Vector v1=new Vector(1,2,3);
	Vector v2=new Vector(2,4,6);
	Vector v3=new Vector(0,-5,3);
	Vector v5=new Vector(-2,-1,-4);
	
	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		assertEquals(new Vector(3,6,9),v1.add(v2),"Vector wrong added");
		
	}
	/**
	 * Test method for {@link primitives.Vector#scale(java.lang.Double)}.
	 */
	@Test
	void testScale() {
		assertEquals(new Vector(4,8,12),v1.scale(4.0),"Wrong calculating at scale function");
		assertEquals(new Vector(0,0,0),v3.scale(null),"Wrong calculating at scale function");
		
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		assertEquals(28,v1.dotProduct(v2),"Wrong calculating at DotProduct function");
		assertEquals(1,v2.dotProduct(v3),"Wrong calculating at DotProduct function");
		assertEquals(-16,v1.dotProduct(v5),"Wrong calculating at DotProduct function");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		assertEquals(new Vector(0,0,0),v1.crossProduct(v2),"ERROR supposed to return a zero vector");
		assertEquals(new Vector(0,0,0),v2.crossProduct(v5),"ERROR supposed to return a zero vector");
		assertEquals(new Vector(-5,-2,3),v1.crossProduct(v5),"ERROR supposed to return a negative vector");
		assertEquals(new Vector(-21,3,5),v3.crossProduct(v1),"ERROR supposed to return new vector");
		
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		assertEquals(24,v3.lengthSquared(),"ERROR supposed to return a positive number");
		assertEquals(21,v5.lengthSquared(),"ERROR supposed to return a positive number");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		assertEquals(Math.sqrt(14),v1.length(),"ERROR supposed to return zero");
		assertEquals(Math.sqrt(21),v5.length(),"ERROR supposed to return five");
		assertEquals(Math.sqrt(34),v3.length(),"ERROR supposed to return 34");
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		
	}

}
