/*
 * Units of Measurement Reference Implementation
 * Copyright (c) 2005-2017, Jean-Marie Dautelle, Werner Keil, V2COM.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-363 nor the names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
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

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Power;
import javax.measure.quantity.Time;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tec.units.ri.AbstractConverter;
import tec.units.ri.AbstractUnit;
import tec.units.ri.quantity.Quantities;

import static org.junit.Assert.*;
import static tec.units.ri.unit.MetricPrefix.KILO;
import static tec.units.ri.unit.Units.GRAM;
import static tec.units.ri.unit.Units.KILOGRAM;
import static tec.units.ri.unit.Units.METRE;
import static tec.units.ri.unit.Units.WATT;

/**
 *
 * @author Werner Keil
 */
public class UnitsTest {
  Unit<Dimensionless> one;

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
    one = AbstractUnit.ONE;
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
   * Test method for {@link javax.measure.Unit#multiply(double)} using a long.
   */
  @Test
  public void testMultiplyLong() {
    Unit<?> result = one.multiply(2L);
    assertNotSame(result, one);
  }

  /**
   * Test method for {@link javax.measure.Unit#asType(java.lang.Class)}.
   */
  @Test(expected = ClassCastException.class)
  public void testAsType() {
    one.asType(Dimensionless.class);
    METRE.asType(Mass.class);
  }

  @Test
  public void testOne() {
    Unit<Dimensionless> one = AbstractUnit.ONE;
    assertTrue(one.isCompatible(AbstractUnit.ONE));
    Unit<Dimensionless> two = one.shift(1);
    assertTrue(two.isCompatible(AbstractUnit.ONE));
    assertFalse(two.equals(AbstractUnit.ONE));
  }

  /**
   * Test method for {@link javax.measure.Unit#multiply(double)}.
   */
  @Test
  public void testMultiplyDouble() {
    Unit<?> result = one.multiply(2.1);
    assertNotSame(result, one);
  }

  /**
   * Test method for {@link javax.measure.Unit#multiply(javax.measure.Unit)}.
   */
  @Test
  public void testMultiplyUnitOfQ() {
    AbstractUnit<?> result = (AbstractUnit<?>) one.multiply(one);
    assertEquals(result, one);
  }

  /**
   * Test method for {@link javax.measure.Unit#inverse()}.
   */
  @Test
  public void testInverse() {
    Unit<?> result = one.inverse();
    assertEquals(result, one);
  }

  /**
   * Test method for {@link javax.measure.Unit#divide(double)} using a long.
   */
  @Test
  public void testDivideLong() {
    Unit<?> result = one.divide(2L);
    assertNotSame(result, one);
  }

  /**
   * Test method for {@link javax.measure.Unit#divide(double)}.
   */
  @Test
  public void testDivideDouble() {
    Unit<?> result = one.divide(3.2);
    assertNotSame(result, one);
  }

  /**
   * Test method for {@link javax.measure.Unit#divide(javax.measure.Unit)}.
   */
  @Test
  public void testDivideUnitOfQ() {
    Unit<?> result = one.divide(one);
    assertEquals(result, one);
  }

  /**
   * Test method for {@link javax.measure.Unit#root(int)}.
   */
  @Test
  public void testRoot() {
    Unit<?> result = one.root(2);
    assertEquals(result, one);
  }

  /**
   * Test method for {@link javax.measure.Unit#pow(int)}.
   */
  @Test
  public void testPow() {
    Unit<?> result = one.pow(10);
    assertEquals(result, one);
  }

  @Test
  public void testKiloIsAThousand() {
    Quantity<Power> w2000 = Quantities.getQuantity(2000.0, WATT);
    Quantity<Power> kW2 = Quantities.getQuantity(2, MetricPrefix.KILO(WATT));
    // assertThat(w2000, is(kW2)); XXX: Need to find the org.hamcrest
    // assertion libs
    assertEquals(w2000, kW2.to(WATT));
  }

  @Test
  public void testOf() {
    assertEquals(MetricPrefix.KILO(GRAM).toString(), AbstractUnit.parse("kg").toString());
    // TODO try to clarify equals for units with the same meaning
  }

  @Test
  public void testParse() {
    assertEquals(KILO(GRAM).toString(), AbstractUnit.parse("kg").toString());
    // TODO try to clarify equals for units with the same meaning
  }

  @Test
  public void testGetSymbol() {
    // TODO see https://github.com/unitsofmeasurement/uom-se/issues/54 /
    // https://java.net/jira/browse/UNITSOFMEASUREMENT-109
    assertEquals("kg", KILOGRAM.getSymbol());
    // assertEquals("kg", SI.GRAM.getSymbol()); //"g"
    // assertEquals("kg", UCUM.POUND.getSymbol()); //"lb"
    // assertEquals("kg", UCUM.OUNCE.getSymbol());//"oz"
    assertNull(MetricPrefix.KILO(Units.GRAM).getSymbol());
    // assertEquals("kg", UCUM.GRAM.getSymbol()); //"g"
    // assertEquals("kg", US.POUND.getSymbol()); //"lb"
    assertNull(GRAM.getSymbol());
    // assertNull(UCUM.OUNCE.getSymbol());
    // assertNull(US.POUND.getSymbol());
  }

  @Test
  public void testGetParentUnit() {
    assertEquals("tec.units.ri.unit.TransformedUnit", GRAM.getClass().getName());
    assertEquals("kg", ((TransformedUnit<Mass>) GRAM).getParentUnit().getSymbol());
    // assertEquals("kg", UCUM.POUND.getSymbol()); //"lb"
    // assertEquals("kg", UCUM.OUNCE.getSymbol());//"oz"
    // assertEquals("kg", MetricPrefix.KILO(UCUM.GRAM).getSymbol());
    // assertEquals("kg", UCUM.GRAM.getSymbol()); //"g"
    // assertEquals("kg", US.POUND.getSymbol()); //"lb"
  }

  @Test
  public void testByClassTime() {
    Unit result = Units.getInstance().getUnit(Time.class);
    assertEquals("s", result.toString());
  }
}