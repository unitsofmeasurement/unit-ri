/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright 2013-2014, Jean-Marie Dautelle, Werner Keil, V2COM and individual
 *  contributors by the @author tag.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package tec.units.ri.quantity;

import static javax.measure.format.FormatBehavior.LOCALE_NEUTRAL;

import java.util.Objects;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.ParserException;

import tec.units.ri.format.QuantityFormat;

/**
 * Singleton class for accessing {@link Quantity} instances.
 * @author werner
 * @author otaviojava
 */
public final class Quantities {
	/**
     * Private singleton constructor.
     */
    private Quantities() {}
    
    /**
     * Returns the
     * {@link #valueOf(java.math.BigDecimal, javax.measure.unit.Unit) decimal}
     * measure of unknown type corresponding to the specified representation.
     * This method can be used to parse dimensionless quantities.<br/><code>
     *     Quantity<Dimensionless> proportion = BaseQuantity.of("0.234").asType(Dimensionless.class);
     * </code>
     *
     * <p> Note: This method handles only
     * {@link javax.measure.format.UnitFormat#getStandard standard} unit format
     * (<a href="http://unitsofmeasure.org/">UCUM</a> based). Locale-sensitive
     * measure formatting and parsing are handled by the {@link QuantityFormat}
     * class and its subclasses.</p>
     *
     * @param csq the decimal value and its unit (if any) separated by space(s).
     * @return <code>QuantityFormat.getInstance(LOCALE_NEUTRAL).parse(csq, new ParsePosition(0))</code>
     */
    public static Quantity<?> getQuantity(CharSequence csq) {
        try {
            return QuantityFormat.getInstance(LOCALE_NEUTRAL).parse(csq);
        } catch (IllegalArgumentException | ParserException e) {
            throw new IllegalArgumentException(e); // TODO could we handle this differently?
        }
    }

    /**
     * Returns the scalar measurement.
     * in the specified unit.
     * @param value the measurement value.
     * @param unit the measurement unit.
     * @return the corresponding <code>numeric</code> quantity.
     * @throws NullPointerException when value or unit were null
     */
    public static <Q extends Quantity<Q>> Quantity<Q> getQuantity(Number value,
            Unit<Q> unit) {

        Objects.requireNonNull(value);
        Objects.requireNonNull(unit);
        if (Double.class.isInstance(value)) {
        	return new DoubleQuantity<>(value.doubleValue(), unit);
    	} else if (Long.class.isInstance(value)) {
            return new LongQuantity<>(Long.class.cast(value), unit);
        } else if (Integer.class.isInstance(value)) {
        	return new IntegerQuantity<>(Integer.class.cast(value), unit);
        } else if (Float.class.isInstance(value)) {
            	return new FloatQuantity<>(Float.class.cast(value), unit);
        }
        return new BaseQuantity<>(value, unit);
    }
}
