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
package tec.units.ri;

import static org.junit.Assert.*;

import javax.measure.quantity.Length;
import javax.measure.quantity.Temperature;

import org.junit.BeforeClass;
import org.junit.Test;

import tec.units.ri.AbstractUnit;
import tec.units.ri.unit.BaseUnit;
import tec.units.ri.unit.Units;

public class AbsUnitTest {
  private static final AbstractUnit<Length> sut = new BaseUnit<Length>("m");

  @BeforeClass
  public static void init() {
    sut.setName("Test");
  }

  @Test
  public void testName() {
    assertEquals("Test", sut.getName());
  }

  @Test
  public void testAlternate() {
    assertEquals("n", sut.alternate("n").toString());
  }

  @Test
  public void testShift0() {
    assertEquals(sut, sut.shift(0));
  }

  @Test
  public void testIsComp() {
    assertTrue(sut.isCompatible(sut));
    assertFalse(sut.isCompatible(DimensionlessUnit.ONE));
  }

  @Test
  public void testDivide1() {
    assertEquals(sut, sut.divide(1));
  }

  @Test
  public void testMult1() {
    assertEquals(sut, sut.multiply(1));
  }

  @Test
  public void testCompareTo() {
    final AbstractUnit<Temperature> cel = (AbstractUnit<Temperature>) Units.CELSIUS;
    assertEquals(-1, cel.compareTo(Units.KELVIN));
  }
}
