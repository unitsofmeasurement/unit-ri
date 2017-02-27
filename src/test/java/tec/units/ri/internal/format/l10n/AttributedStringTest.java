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
package tec.units.ri.internal.format.l10n;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class AttributedStringTest {

  private static final Attribute[] internalAttributes() {
    Attribute[] atts = new Attribute[4];
    for (int i = 0; i < 4; i++) {
      atts[i] = new Attribute(String.valueOf(i));
    }
    return atts;
  }

  static AttributedString sut;

  @BeforeClass
  public static void init() {
    sut = new AttributedString("test");
  }

  @Test
  public void testGetIterator() {
    assertNotNull(sut.getIterator());
  }

  @Test
  public void testText() {
    assertEquals("test", sut.text);
  }

  @Test(expected = NullPointerException.class)
  public void testAttributedStringNull() {
    final String ns = null;
    AttributedString as = new AttributedString(ns);
  }

  @Test(expected = NullPointerException.class)
  public void testAttributedStringNull2() {
    final AttributedCharacterIterator ns = null;
    AttributedString as = new AttributedString(ns, 0, 1, internalAttributes());
  }

  @Test(expected = NullPointerException.class)
  public void testAttributedStringNullIteratorArr() {
    final AttributedCharacterIterator[] ns = null;
    AttributedString as = new AttributedString(ns);
  }

  @Test
  public void testAttributedStringEmptyIteratorArr() {
    final AttributedCharacterIterator[] es = new AttributedCharacterIterator[0];
    AttributedString as = new AttributedString(es);
  }

  @Test
  public void testAttributedStringIterator() {
    final AttributedCharacterIterator it = sut.getIterator(internalAttributes(), 0, 4);
    AttributedString as = new AttributedString(it, 0, 4);
  }

  public void testAddAttribute() {
    final Attribute a = new Attribute("x");
    sut.addAttribute(a, "y");
  }

  @Test(expected = NullPointerException.class)
  public void testAddAttributeBothNull() {
    final Attribute ns = null;
    sut.addAttribute(ns, null);
  }

  @Test(expected = NullPointerException.class)
  public void testAddAttributeNull() {
    final Attribute ns = null;
    sut.addAttribute(ns, "x");
  }

  @Test
  public void testLength() {
    assertEquals(4, sut.length());
  }

}
