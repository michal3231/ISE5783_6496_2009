/**
 * 
 */
package unittests;
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
	void testCrossProduct() {
		
		Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
		
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                     "crossProduct() for parallel vectors does not throw an exception");
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
	}

}
