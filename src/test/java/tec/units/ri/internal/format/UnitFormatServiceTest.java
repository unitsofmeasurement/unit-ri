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
package tec.units.ri.internal.format;

import static org.junit.Assert.*;

import javax.measure.spi.Bootstrap;
import javax.measure.spi.UnitFormatService;

import org.junit.Before;
import org.junit.Test;

import tec.units.ri.internal.format.DefaultUnitFormatService;

/**
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 *
 */
public class UnitFormatServiceTest {
	private UnitFormatService sut;

	@Before
	public void init() {
		sut = Bootstrap.getService(UnitFormatService.class);
	}

	@Test
	public void testGetFormat() {
		assertNotNull(sut.getUnitFormat());
		assertEquals("tec.units.ri.format.SimpleUnitFormat$DefaultFormat", sut.getUnitFormat().getClass().getName());
	}
	
	@Test
	public void TestGetFormatNames() {
		DefaultUnitFormatService service = new DefaultUnitFormatService();
		assertNotNull(service.getAvailableFormatNames());
		assertEquals(3, service.getAvailableFormatNames().size());
	}
	
	   @Test
	    public void testGetFormatFound() throws Exception {
	        assertNotNull(sut);
	        assertNotNull(sut.getUnitFormat("EBNF"));
	    }
	    
	    @Test
	    public void testGetFormatNotFound() throws Exception {
	        assertNotNull(sut);
	        assertNull(sut.getUnitFormat("XYZ"));
	    }
}
