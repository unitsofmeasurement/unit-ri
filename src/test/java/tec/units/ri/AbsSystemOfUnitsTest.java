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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;

import org.junit.BeforeClass;
import org.junit.Test;

import tec.units.ri.quantity.QuantityDimension;

public class AbsSystemOfUnitsTest {

  private static AbstractSystemOfUnits system;
  // Get Logger for "tec.units.ri".
  private static Logger logger = Logger.getLogger("tec.units.ri");

  @BeforeClass
  public static void setUp() {
    // logger would log activities
    // with level FINEST and above.
    logger.setLevel(Level.FINEST);
    system = TestUnits.INSTANCE;
  }

  @Test
  public void testGetUnits() {
    assertNotNull(system);
    assertEquals("tec.units.ri.TestUnits", system.getClass().getName());
    assertEquals("Test units of measurement", system.getName());
    assertNotNull(system.getUnits());
    assertEquals(6, system.getUnits().size());
  }

  @Test
  public void testGetUnitsForDimension() {
    Set<? extends Unit<?>> units = system.getUnits(QuantityDimension.LENGTH);
    assertNotNull(units);
    assertEquals(3, units.size());
  }

  @Test
  public void testAddUnitLabel() {
    Unit<Length> l = TestUnits.YARD;
    assertNull(l.getName());
    assertNull(l.getSymbol());
    assertEquals("yd", l.toString());
  }

  @Test
  public void testAddUnitBoth() {
    Unit<Mass> mass = TestUnits.TONNE;
    assertEquals("Tonne", mass.getName());
    assertEquals("t", mass.getSymbol());
    assertEquals("t", mass.toString());
  }

  @Test
  public void testAddUnitBothSeparately() {
    Unit<Volume> v = TestUnits.SCHOPPEN_BAYERN;
    assertEquals("Schoppen Bayern", v.getName());
    assertEquals("sb", v.getSymbol());
    assertEquals("sch", v.toString());
  }
}
