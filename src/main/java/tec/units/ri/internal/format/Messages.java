/*
 * Units of Measurement Reference Implementation
 * Copyright (c) 2005-2015, Jean-Marie Dautelle, Werner Keil, V2COM.
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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import tec.units.ri.internal.format.l10n.L10nResources;

/**
 * Default resource bundle (English / United States).
 */
public class Messages extends L10nResources {
	private final Map<String, String> strings = new HashMap<String, String>(30);

	public Messages() {
		strings.put("tec.units.ri.unit.Units.AMPERE", "A");
		// #tec.units.ri.unit.Units.AMPERE_TURN ", "At");
		strings.put("tec.units.ri.unit.Units.BECQUEREL", "Bq");
		strings.put("tec.units.ri.unit.Units.CANDELA", "cd");
		strings.put("tec.units.ri.unit.Units.CELSIUS", "\u00B0C");
		strings.put("tec.units.ri.unit.Units.CELSIUS.1", "\u2103");
		strings.put("tec.units.ri.unit.Units.CELSIUS.2", "Celsius");
		strings.put("tec.units.ri.unit.Units.COULOMB", "C");
		strings.put("tec.units.ri.unit.Units.FARAD", "F");
		strings.put("tec.units.ri.unit.Units.GRAM", "g");
		strings.put("tec.units.ri.unit.Units.GRAY", "Gy");
		strings.put("tec.units.ri.unit.Units.HENRY", "H");
		strings.put("tec.units.ri.unit.Units.HERTZ", "Hz");
		strings.put("tec.units.ri.unit.Units.HERTZ.2", "hertz");
		strings.put("tec.units.ri.unit.Units.JOULE", "J");
		strings.put("tec.units.ri.unit.Units.KATAL", "kat");
		strings.put("tec.units.ri.unit.Units.KELVIN", "K");
		strings.put("tec.units.ri.unit.Units.KILOGRAM", "kg");
		strings.put("tec.units.ri.unit.Units.KILOGRAM.1", "kilogram");
		strings.put("tec.units.ri.unit.Units.KILOMETRES_PER_HOUR", "kph");
		strings.put("tec.units.ri.unit.Units.LITRE", "l");
		strings.put("tec.units.ri.unit.Units.LUMEN", "lm");
		strings.put("tec.units.ri.unit.Units.LUX", "lx");
		strings.put("tec.units.ri.unit.Units.METRE", "m");
		strings.put("tec.units.ri.unit.Units.MOLE", "mol");
		strings.put("tec.units.ri.unit.Units.NEWTON", "N");
		strings.put("tec.units.ri.unit.Units.OHM", "\u03A9");
		strings.put("tec.units.ri.unit.Units.PASCAL", "Pa");
		strings.put("tec.units.ri.unit.Units.RADIAN", "rad");
		// #tec.units.ri.unit.Units.ROENTGEN", "R");
		strings.put("tec.units.ri.unit.Units.SECOND", "s");
		strings.put("tec.units.ri.unit.Units.MINUTE", "min");
		strings.put("tec.units.ri.unit.Units.SIEMENS", "S");
		strings.put("tec.units.ri.unit.Units.SIEVERT", "Sv");
		strings.put("tec.units.ri.unit.Units.STERADIAN", "sr");
		strings.put("tec.units.ri.unit.Units.TESLA", "T");
		strings.put("tec.units.ri.unit.Units.VOLT", "V");
		strings.put("tec.units.ri.unit.Units.WATT", "W");
		strings.put("tec.units.ri.unit.Units.WEBER", "Wb");
/*
		strings.put("tec.units.ri.unit.US.FAHRENHEIT", "\u00B0F");
		strings.put("tec.units.ri.unit.US.FAHRENHEIT.1", "\u2109");
		strings.put("tec.units.ri.unit.US.FOOT", "ft");
		strings.put("tec.units.ri.unit.US.HORSEPOWER", "hp");
		strings.put("tec.units.ri.unit.US.GALLON_LIQUID", "gal");
		strings.put("tec.units.ri.unit.US.GALLON_DRY", "gal_dry");
		strings.put("tec.units.ri.unit.US.INCH", "in");
		strings.put("tec.units.ri.unit.US.KNOT", "kn");
		strings.put("tec.units.ri.unit.US.LITER", "l");
		strings.put("tec.units.ri.unit.US.MILE", "mi");
		strings.put("tec.units.ri.unit.US.MILES_PER_HOUR", "mph");
		strings.put("tec.units.ri.unit.US.POUND", "lb");
*/
		strings.put("tec.units.ri.unit.MetricPrefix.ATTO", "a");
		strings.put("tec.units.ri.unit.MetricPrefix.CENTI", "c");
		strings.put("tec.units.ri.unit.MetricPrefix.DECI", "d");
		strings.put("tec.units.ri.unit.MetricPrefix.DEKA", "da");
		strings.put("tec.units.ri.unit.MetricPrefix.EXA", "E");
		strings.put("tec.units.ri.unit.MetricPrefix.FEMTO", "f");
		strings.put("tec.units.ri.unit.MetricPrefix.GIGA", "G");
		strings.put("tec.units.ri.unit.MetricPrefix.HECTO", "h");
		strings.put("tec.units.ri.unit.MetricPrefix.KILO", "k");
		strings.put("tec.units.ri.unit.MetricPrefix.MEGA", "M");
		strings.put("tec.units.ri.unit.MetricPrefix.MICRO", "\u00B5");
		strings.put("tec.units.ri.unit.MetricPrefix.MILLI", "m");
		strings.put("tec.units.ri.unit.MetricPrefix.NANO", "n");
		strings.put("tec.units.ri.unit.MetricPrefix.PETA", "P");
		strings.put("tec.units.ri.unit.MetricPrefix.PICO", "p");
		strings.put("tec.units.ri.unit.MetricPrefix.TERA", "T");
		strings.put("tec.units.ri.unit.MetricPrefix.YOCTO", "y");
		strings.put("tec.units.ri.unit.MetricPrefix.YOTTA", "Y");
		strings.put("tec.units.ri.unit.MetricPrefix.ZEPTO", "z");
		strings.put("tec.units.ri.unit.MetricPrefix.ZETTA", "Z");
	}

	protected String handleGetString(String key) {
		return strings.get(key);
	}

	/**
	 * Returns an <code>Enumeration</code> of the keys contained in this
	 * <code>ResourceBundle</code> and its parent bundles.
	 *
	 * @return an <code>Enumeration</code> of the keys contained in this
	 *         <code>ResourceBundle</code> and its parent bundles.
	 * @see #keySet()
	 */
	public Iterator<String> getKeys() {
		return strings.keySet().iterator();
	}

	@Override
	public Set<String> keySet() {
		return strings.keySet();
	}
}
