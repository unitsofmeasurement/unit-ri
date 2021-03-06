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
package tec.units.ri.internal.format;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.measure.format.UnitFormat;
import javax.measure.spi.UnitFormatService;

import tec.units.ri.format.SimpleUnitFormat;
import tec.units.ri.format.SimpleUnitFormat.Flavor;
import tec.uom.lib.common.function.IntPrioritySupplier;

/**
 * Default format service.
 *
 * @author Werner Keil
 * @version 0.5, March 23, 2016
 */
public class DefaultUnitFormatService implements UnitFormatService, IntPrioritySupplier {

  private static final int PRIO = 1000;
  private static final String DEFAULT_FORMAT = Flavor.Default.name();

  private final Map<String, UnitFormat> formats = new HashMap<String, UnitFormat>();

  public DefaultUnitFormatService() {
    formats.put(DEFAULT_FORMAT, SimpleUnitFormat.getInstance());
    formats.put(Flavor.ASCII.name(), SimpleUnitFormat.getInstance(Flavor.ASCII));
    // formats.put("EBNF", EBNFUnitFormat.getInstance());
  }

  /*
   * (non-Javadoc)
   * 
   * @see UnitFormatService#getUnitFormat(String)
   */
  public UnitFormat getUnitFormat(String formatName) {
    if (formatName == null)
      throw new NullPointerException("Format name required");
    return formats.get(formatName);
  }

  /*
   * (non-Javadoc)
   * 
   * @see UnitFormatService#getUnitFormat()
   */
  public UnitFormat getUnitFormat() {
    return getUnitFormat(DEFAULT_FORMAT);
  }

  public Set<String> getAvailableFormatNames() {
    return formats.keySet();
  }

  public int getPriority() {
    return PRIO;
  }
}
