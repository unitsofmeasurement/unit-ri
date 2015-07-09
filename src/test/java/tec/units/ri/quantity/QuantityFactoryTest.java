/**
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
package tec.units.ri.quantity;

import static org.junit.Assert.*;
import static tec.units.ri.unit.Units.*;

import javax.measure.Quantity;
import javax.measure.quantity.*;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Werner Keil
 *
 */
public class QuantityFactoryTest {

	@Test
	public void testLength() {
		Quantity<Length> l =  DefaultQuantityFactory.getInstance(Length.class).create(23.5, METRE); // 23.0 km
		assertEquals(Double.valueOf(23.5d), l.getValue());
		assertEquals(METRE, l.getUnit());
		assertEquals("m", l.getUnit().getSymbol());
	}
	
	@Test
	@Ignore
	public void testMass() {
		Quantity<Mass> m = DefaultQuantityFactory.getInstance(Mass.class).create(10, KILOGRAM); // 10 kg
		assertEquals(Integer.valueOf(10), m.getValue());
		assertEquals(KILOGRAM, m.getUnit());
		assertEquals("kg", m.getUnit().getSymbol());
		assertEquals("10 kg", m.toString());
	}
	
	@Test
	public void testTime() {
		Quantity<Time> t = DefaultQuantityFactory.getInstance(Time.class).create(40, MINUTE); // 40 min
		assertEquals(Integer.valueOf(40), t.getValue());
		assertEquals(MINUTE, t.getUnit());
		assertNull(t.getUnit().getSymbol());
//		assertEquals("s", t.getUnit().getSymbol()); // FIXME this should be "min", tweak for TransformedUnit
		assertEquals("40 min", t.toString());
	}

}
