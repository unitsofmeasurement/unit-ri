/*
 *  Unit-API - Units of Measurement API for Java
 *  Copyright (c) 2005-2015, Jean-Marie Dautelle, Werner Keil, V2COM.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-363 nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tec.units.ri.unit;

import javax.measure.Unit;
import javax.measure.quantity.Dimensionless;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import tec.units.ri.AbstractConverter;
import tec.units.ri.AbstractUnit;
import static org.junit.Assert.*;
import static tec.units.ri.unit.MetricPrefix.KILO;
import static tec.units.ri.unit.Units.GRAM;
import static tec.units.ri.unit.Units.METRE;

/**
 *
 * @author Werner Keil
 */
public class UnitsTest {
	Unit<Dimensionless> one;

	// TODO most of these are empty
	public UnitsTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		// super.setUp();
		one = Units.ONE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@After
	public void tearDown() throws Exception {
		// super.tearDown();
		one = null;
	}

	@Test
	public void testParse() {
		assertEquals(KILO(GRAM), AbstractUnit.parse("kg")); // TODO: Problem
															// with kg...
	}

    /**
     * Test method for {@link javax.measure.Unit#transform}.
     */
    @Test
    public void testTransform() {
        Unit<?> result = one.transform(AbstractConverter.IDENTITY);
        assertEquals(result, one);
    }

    /**
     * Test method for {@link javax.measure.Unit#shift(double)}.
     */
    @Test
    public void testShift() {
    	Unit<?> result = one.shift(10);
        assertNotSame(result, one);
    }

    /**
     * Test method for {@link javax.measure.Unit#multiply(long)}.
     */
    @Test
    public void testMultiplyLong() {
    	Unit<?> result = one.multiply(2L);
        assertNotSame(result, one);
    }

	/**
	 * Test method for {@link javax.measure.Unit#asType(java.lang.Class)}.
	 */
	@Test
	public void testAsType() {
		one.asType(Dimensionless.class);
		try {
			METRE.asType(Dimensionless.class);
			fail("Should have raised ClassCastException");
		} catch (ClassCastException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testGetConverterTo() {
	}

	@Test
	public void testGetConverterToAny() {
	}

	@Test
	public void testAlternate() {
	}

	@Test
	public void testAdd() {
	}

	@Test
	public void testMultiply_double() {
	}

	@Test
	public void testMultiply_ErrorType() {
	}

	@Test
	public void testInverse() {
	}

	@Test
	public void testDivide_double() {
	}

	@Test
	public void testDivide_ErrorType() {
	}

	@Test
	public void testRoot() {
	}

	@Test
	public void testPow() {
	}

	@Test
	public void testHashCode() {
	}

	@Test
	public void testEquals() {
	}

	@Test
	public void testOne() {
		Unit<Dimensionless> one = Units.ONE;
		assertTrue(one.isCompatible(Units.ONE));
		Unit<Dimensionless> two = one.shift(1);
		assertTrue(two.isCompatible(Units.ONE));
		assertFalse(two.equals(Units.ONE));
	}
}