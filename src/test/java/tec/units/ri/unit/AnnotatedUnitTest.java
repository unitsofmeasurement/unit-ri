/*
 * Units of Measurement Reference Implementation
 * Copyright (c) 2005-2016, Jean-Marie Dautelle, Werner Keil, V2COM.
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

import javax.measure.quantity.Length;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnnotatedUnitTest {

  private AnnotatedUnit<Length> annotatedUnit;
  private BaseUnit<Length> baseUnit;

  @Before
  public void setUp() throws Exception {
    baseUnit = new BaseUnit<Length>("m");
    annotatedUnit = new AnnotatedUnit<Length>(baseUnit, "Metre");
  }

  @Test
  public void testGetterUnmodified() {
    Assert.assertEquals(annotatedUnit.getAnnotation(), "Metre");
    Assert.assertEquals(annotatedUnit.getSymbol(), "m");
    Assert.assertEquals(annotatedUnit.getSystemUnit(), baseUnit);
    Assert.assertEquals(annotatedUnit.getActualUnit(), baseUnit);
  }

  @Test
  public void testEquality() {
    Assert.assertFalse(annotatedUnit.equals(baseUnit));
    Assert.assertFalse(baseUnit.equals(annotatedUnit));
    AnnotatedUnit<Length> anotherUnit = new AnnotatedUnit<Length>(baseUnit, "Metre");
    Assert.assertTrue(annotatedUnit.equals(anotherUnit));
    Assert.assertTrue(anotherUnit.equals(annotatedUnit));
    Assert.assertEquals(annotatedUnit.hashCode(), anotherUnit.hashCode());
    Assert.assertEquals(anotherUnit.hashCode(), annotatedUnit.hashCode());
    anotherUnit = new AnnotatedUnit<Length>(baseUnit, "KiloMetre");
    Assert.assertFalse(annotatedUnit.equals(anotherUnit));
    Assert.assertFalse(anotherUnit.equals(annotatedUnit));
  }
}
