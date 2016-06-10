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
package tec.units.ri.internal.format.l10n;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParsePositionTest {

    @Test
    public void testIndex() {
	final ParsePosition pp = new ParsePosition(0);
	assertEquals(0, pp.getIndex());
    }

    @Test
    public void testErrorIndex() {
	final ParsePosition pp = new ParsePosition(0);
	pp.setErrorIndex(1);
	assertEquals(0, pp.getIndex());
	assertEquals(1, pp.getErrorIndex());
    }

    @Test
    public void testEquals() {
	final ParsePosition pp = new ParsePosition(1);
	final ParsePosition pp2 = new ParsePosition(1);
	assertEquals(pp, pp2);
    }

    @Test
    public void testToString() {
	final ParsePosition pp = new ParsePosition(0);
	assertEquals(
		"tec.units.ri.internal.format.l10n.ParsePosition[index=0,errorIndex=-1]",
		pp.toString());
    }
}