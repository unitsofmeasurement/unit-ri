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
package tec.units.ri.spi;

import static org.junit.Assert.*;

import java.util.List;

import javax.measure.spi.ServiceProvider;

import org.junit.Test;

public class ServiceProviderTest {

  @Test
  public void testAvailable() {
    List<ServiceProvider> providers = ServiceProvider.available();
    assertNotNull(providers);
    assertEquals(1, providers.size());
  }

  @Test
  public void testCurrent() {
    ServiceProvider provider = ServiceProvider.current();
    assertNotNull(provider);
    assertEquals("tec.units.ri.spi.DefaultServiceProvider", provider.getClass().getName());

    assertNotNull(provider.getUnitFormatService());
    assertNotNull(provider.getUnitFormatService().getAvailableFormatNames());
    assertEquals(2, provider.getUnitFormatService().getAvailableFormatNames().size());
    assertNotNull(provider.getSystemOfUnitsService());
    assertNotNull(provider.getSystemOfUnitsService().getAvailableSystemsOfUnits());
    assertEquals(1, provider.getSystemOfUnitsService().getAvailableSystemsOfUnits().size());
  }
}
