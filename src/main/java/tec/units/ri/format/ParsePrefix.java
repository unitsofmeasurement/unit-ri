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
package tec.units.ri.format;

import static tec.units.ri.function.MathHelper.pow;

import javax.measure.UnitConverter;

import tec.units.ri.AbstractConverter;
import tec.units.ri.function.RationalConverter;
import tec.units.ri.function.UnitConverterSupplier;

/**
 * This class represents the prefixes recognized when parsing/formatting.
 *
 * @version 0.4
 */
public enum ParsePrefix implements UnitConverterSupplier {

	   YOTTA(RationalConverter.of(1000000000000000000000000d, 1d)),
	    ZETTA(RationalConverter.of(1000000000000000000000d, 1d)),
	    EXA(RationalConverter.of(pow(10, 18), 1d)),
	    PETA(RationalConverter.of(pow(10, 15), 1d)),
	    TERA(RationalConverter.of(pow(10, 12), 1d)),
	    GIGA(RationalConverter.of(pow(10, 9), 1d)),
	    MEGA(RationalConverter.of(pow(10, 6), 1d)),
	    KILO(RationalConverter.of(pow(10, 3), 1d)),
	    HECTO(RationalConverter.of(100d, 1d)),
	    DEKA(RationalConverter.of(10d, 1d)),
	    DECI(RationalConverter.of(1d, 10d)),
	    CENTI(RationalConverter.of(1d, 100d)),
	    MILLI(RationalConverter.of(1d, 1000d)),
	    MICRO(RationalConverter.of(1d, pow(10, 6))),
	    NANO(RationalConverter.of(1d, pow(10, 9))),
	    PICO(RationalConverter.of(1d, pow(10, 12))),
	    FEMTO(RationalConverter.of(1d, pow(10, 15))),
	    ATTO(RationalConverter.of(1d, pow(10, 18))),
	    ZEPTO(RationalConverter.of(1d, pow(10, 21))),
	    YOCTO(RationalConverter.of(1d, pow(10, 24)));

    private final AbstractConverter converter;

    /**
     * Creates a new prefix.
     *
     * @param converter the associated unit converter.
     */
    ParsePrefix (AbstractConverter converter) {
        this.converter = converter;
    }

    /**
     * Returns the corresponding unit converter.
     *
     * @return the unit converter.
     */
    public UnitConverter getConverter() {
        return converter;
    }
}
