/**
 * 
 */
package unittests.primitives;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertTrue;
import static primitives.Util.isZero;
import static org.junit.Assert.assertEquals;



/**
 * 
 */
class VectorTests {

	private Vector vec1 = new Vector(1, 2, 3);
	private Vector vec2 = new Vector(2, 4, 6);
	private Vector vec3 = new Vector(1, 2, 4);
	private Vector vec4 = new Vector(1, 4, -3);
	private Vector vec5 = new Vector(-1, -2, -3);
	
	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		
		try {
			// ============ Equivalence Partitions Tests ==============
			
			//TC01: test that the function is proper for 2 positives vectors:
		
			Vector v1= new Vector(2,4,6);
			Vector v2= new Vector(7,8,9);	
			Vector v3= new Vector(9,12,15);
			assertTrue(v3.equals(v1.add(v2)), "testAdd() is wrong for 2 positive vectors");
		
			//TC02: test that the function is proper for 2 positives vectors:
			
			Vector v4= new Vector(-1,-2,-3);
			Vector v5= new Vector(-7,-8,-9);	
			Vector v6= new Vector(-8,-10,-12);
			
			assertTrue(v6.equals(v4.add(v5)), "testAdd() is wrong for 2 negative vectors");
			
			//TC03: test that the function is proper for 1 positives and 1 negative vectors:
			
			Vector v7= new Vector(-1,2,-3);
			Vector v8= new Vector(7,-8,9);	
			Vector v9= new Vector(6,-6,6);
			assertTrue(v9.equals(v7.add(v8)), "testAdd() is wrong for 1 positives and 1 negative vectors");
			
		}
		catch(Exception e)
		{
			fail("testAdd() for vectors that not zero vector does not need throw an exception");
		}
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		// ============ Equivalence Partitions Tests ==============
		
		//TC01: test that the function is proper for positive scale:
		
		Vector v1 = new Vector(1,2,3);
		Vector v2 = new Vector(2.5,5,7.5);
		assertEquals(v1.scale(2.5),v2,"testScale() is wrong for a positive scale" );
		
		//TC02: test that the function is proper for negative scale:
		
		Vector v3 = new Vector(-2.5,-5,-7.5);
		assertEquals(v1.scale(-2.5),v3,"testScale() is wrong for a negative scale" );
		
		// =============== Boundary Values Tests ==================
		
		//TC11: test scale 0
		
		assertThrows(IllegalArgumentException.class, () -> v1.scale(0), "Scale() vector by 0" );
		
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		
		try 
		{
			Vector v1 = new Vector(1, 2, 3);
		    Vector v2 = new Vector(-2, -4, -6);
		    Vector v3 = new Vector(0, 3, -2);
		    
			// ============ Equivalence Partitions Tests ==============

		    assertTrue(isZero(v1.dotProduct(v3)),"ERROR: dotProduct() for orthogonal vectors is not zero");
		    
		    // =============== Boundary Values Tests ==================
		    
		    assertTrue(isZero(v1.dotProduct(v2) + 28),"ERROR: dotProduct() wrong value");
		} 
		catch (Exception e) 
		{
			fail("DotProduct() for vectors that not zero vector does not need throw an exception");
		}
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void crossProduct() {
		// TC:01: sharp angle
		assertEquals(new Vector(2, -1, 0), vec1.crossProduct(vec3), "ERROR: TC:01");
		// TC:02 Orthogonal angle
		assertEquals(new Vector(-18, 6, 2), vec1.crossProduct(vec4), "ERROR: TC:02");
		// TC:03 Obtuse angle
		assertEquals(new Vector(-22, 7, 2), vec3.crossProduct(vec4), "ERROR: TC:03");
		// TC:04 Inverted vector
		assertThrows(IllegalArgumentException.class, () -> vec1.crossProduct(vec5), "ERROR: TC:04");
		// TC:05 Two vectors with the same direction
		assertThrows(IllegalArgumentException.class, () -> vec1.crossProduct(vec2), "ERROR: TC:05");
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		
		Vector v1;
		try 
		{
			// ============ Equivalence Partitions Tests ==============
			v1 = new Vector(1, 2, 3);
			assertTrue("ERROR: lengthSquared() wrong value", isZero(v1.lengthSquared() - 14));
		} 
		catch (Exception e) 
		{
			fail("LengthSquared() for vectors that not zero vector does not need throw an exception");
		}
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		
		try
		{
			// ============ Equivalence Partitions Tests ==============
			assertTrue("length() is give wrong value", isZero(new Vector(0, 3, 4).length() - 5));
		}
		catch (Exception e) 
		{
			fail("Length() for vectors that not zero vector does not need throw an exception");
		}
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		// TC:01: A test of the vector normalize in a 3D
		assertEquals(new Vector(-1 / Math.sqrt(14), -2 / Math.sqrt(14), -3 / Math.sqrt(14)), new Vector(-1, -2, -3).normalize(),
				"ERROR TC:01");
	}

}
