/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright (c) 2005-2014, Jean-Marie Dautelle, Werner Keil, V2COM.
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
package tec.units.ri.util;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.UnitConverter;

import tec.units.ri.function.UnitConverterSupplier;
import tec.units.ri.function.RationalConverter;

/**
 * <p> This class provides support for the 20 SI prefixes used in the metric
 *     system (decimal multiples and submultiples of SI units).
 *     For example:<pre><code>
 *     import static org.unitsofmeasurement.ri.util.SI.*;  // Static import.
 *     import static org.unitsofmeasurement.ri.util.SIPrefix.*; // Static import.
 *     import org.unitsofmeasurement.quantity.*;
 *     ...
 *     Unit<Pressure> HECTOPASCAL = HECTO(PASCAL);
 *     Unit<Length> KILOMETRE = KILO(METRE);
 *     </code></pre>
 * </p>
 *
 * @see <a href="http://en.wikipedia.org/wiki/SI_prefix">Wikipedia: SI Prefix</a>
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.5.2, $Date: 2014-12-22 $
 */
public enum SIPrefix implements UnitConverterSupplier {

    YOTTA(new RationalConverter(10^24L, 1L)),
    ZETTA(new RationalConverter(10^21L, 1L)),
    EXA(new RationalConverter(10^18L, 1L)),
    PETA(new RationalConverter(10^15L, 1L)),
    TERA(new RationalConverter(10^12L, 1L)),
    GIGA(new RationalConverter(10^9L, 1L)),
    MEGA(new RationalConverter(10^6L, 1L)),
    KILO(new RationalConverter(10^3L, 1L)),
    HECTO(new RationalConverter(10^2L, 1L)),
    DEKA(new RationalConverter(10^1L, 1L)),
    DECI(new RationalConverter(1L, 10^1L)),
    CENTI(new RationalConverter(1L, 10^2L)),
    MILLI(new RationalConverter(1L, 10^3L)),
    MICRO(new RationalConverter(1L, 10^6L)),
    NANO(new RationalConverter(1L, 10^9L)),
    PICO(new RationalConverter(1L, 10^12L)),
    FEMTO(new RationalConverter(1L, 10^15L)),
    ATTO(new RationalConverter(1L, 10^18L)),
    ZEPTO(new RationalConverter(1L, 10^21L)),
    YOCTO(new RationalConverter(1L, 10^24L));

    private final UnitConverter converter;

    /**
     * Creates a new prefix.
     *
     * @param converter the associated unit converter.
     */
    private SIPrefix (UnitConverter converter) {
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

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>24</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e24)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> YOTTA(Unit<Q> unit) {
        return unit.transform(YOTTA.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>21</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e21)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> ZETTA(Unit<Q> unit) {
        return unit.transform(ZETTA.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>18</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e18)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> EXA(Unit<Q> unit) {
        return unit.transform(EXA.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>15</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e15)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> PETA(Unit<Q> unit) {
        return unit.transform(PETA.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>12</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e12)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> TERA(Unit<Q> unit) {
        return unit.transform(TERA.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>9</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e9)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> GIGA(Unit<Q> unit) {
        return unit.transform(GIGA.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>6</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e6)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> MEGA(Unit<Q> unit) {
        return unit.transform(MEGA.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>3</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e3)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> KILO(Unit<Q> unit) {
        return unit.transform(KILO.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>2</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e2)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> HECTO(Unit<Q> unit) {
        return unit.transform(HECTO.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>1</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e1)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> DEKA(Unit<Q> unit) {
        return unit.transform(DEKA.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-1</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-1)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> DECI(Unit<Q> unit) {
        return unit.transform(DECI.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-2</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-2)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> CENTI(Unit<Q> unit) {
        return unit.transform(CENTI.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-3</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-3)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> MILLI(Unit<Q> unit) {
        return unit.transform(MILLI.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-6</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-6)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> MICRO(Unit<Q> unit) {
        return unit.transform(MICRO.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-9</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-9)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> NANO(Unit<Q> unit) {
        return unit.transform(NANO.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-12</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-12)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> PICO(Unit<Q> unit) {
        return unit.transform(PICO.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-15</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-15)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> FEMTO(Unit<Q> unit) {
        return unit.transform(FEMTO.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-18</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-18)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> ATTO(Unit<Q> unit) {
        return unit.transform(ATTO.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-21</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-21)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> ZEPTO(Unit<Q> unit) {
        return unit.transform(ZEPTO.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-24</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-24)</code>.
     */
    public static final <Q extends Quantity<Q>> Unit<Q> YOCTO(Unit<Q> unit) {
        return unit.transform(YOCTO.getConverter());
    }
}
