/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright 2010-2014, Jean-Marie Dautelle, Werner Keil, V2COM and individual
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
package tec.units.ri;

import static javax.measure.format.FormatBehavior.LOCALE_NEUTRAL;

import java.text.ParsePosition;
import java.util.Comparator;
import java.util.Objects;

import javax.measure.Measurement;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.ParserException;

import tec.units.ri.format.MeasurementFormat;
import tec.units.ri.model.BaseMeasurement;

/**
 * <p> This class represents the immutable result of a measurement stated
 *     in a known unit.</p>
 *
 * <p> Measurements can be converted to different units.<br/><code>
 *         Measurement<Number, Velocity> milesPerHour = C.to(MILES_PER_HOUR);
 *         System.out.println(milesPerHour);
 *
 *         > 670616629.3843951324266284896206156 [mi_i]/h
 *     </code>
 *     If no precision is specified <code>double</code> precision is assumed.<code>
 *         Measurement<Double, Velocity> milesPerHour = C.to(MILES_PER_HOUR); // Use double implementation (fast).
 *         System.out.println(milesPerHour);
 *
 *         > 670616629.3843951 [mi_i]/h
 *     </code></p>
 *
 * <p> Applications may sub-class {@link AbstractMeasurement} for particular measurements
 *     types.<br/><code>
 *         // Measurement of type Mass based on <code>double</code> primitive types.
 *         public class MassAmount extends AbstractMeasurement<Mass> {
 *             private final double _kilograms; // Internal SI representation.
 *             private Mass(double kilograms) { _kilograms = kilograms; }
 *             public static Mass of(double value, Unit<Mass> unit) {
 *                 return new Mass(unit.getConverterTo(SI.KILOGRAM).convert(value));
 *             }
 *             public Unit<Mass> getUnit() { return SI.KILOGRAM; }
 *             public Double getValue() { return _kilograms; }
 *             ...
 *         }
 *
 *         // Complex numbers measurements.
 *         public class ComplexMeasurement<Q extends Quantity> extends Measurement<Q, V> {
 *             public Complex getValue() { ... } // Assuming Complex is a Number.
 *             ...
 *         }
 *
 *         // Specializations of complex numbers measurements.
 *         public class Current extends ComplexMeasurement<ElectricCurrent> {...}
 *         public class Tension extends ComplexMeasurement<ElectricPotential> {...}
 *         </code></p>
 *
 * <p> All instances of this class shall be immutable.</p>
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.3, $Date: 2014-06-19 $
 */
public abstract class AbstractMeasurement<Q extends Quantity<Q>> implements Measurement<Q> {

    /**
	 *
	 */
//	private static final long serialVersionUID = -4993173119977931016L;

	@SuppressWarnings("hiding")
	private static final class OurComparator<Measurement> implements Comparator<Measurement> {
		@SuppressWarnings("rawtypes")
		private static Comparator instance = null;

		public int compare(Object o1, Object o2) {
			// TODO Auto-generated method stub
			return 0;
		}

		@SuppressWarnings("rawtypes")
		static Comparator getInstance() {
			if (instance == null) {
				instance = new OurComparator();
			}
			return instance;
		}
	}

	private static final Comparator<Measurement> DEFAULT_COMPARATOR = OurComparator.getInstance();

	private final Unit<Q> unit;

	/**
	 * Holds a dimensionless measurement of none (exact).
	 */
//	public static final AbstractMeasurement<Dimensionless, ?> NONE = of(0, SI.ONE);

	/**
	 * Holds a dimensionless measurement of one (exact).
	 */
//	public static final AbstractMeasurement<Dimensionless, ?> ONE = of(1, SI.ONE);

	/**
     * constructor.
     */
    protected AbstractMeasurement(Unit<Q> unit) {
    	this.unit = unit;
    }

    /**
     * Returns the measurement numeric value.
     *
     * @return the measurement value.
     */
    public abstract Number getValue();

    /**
     * Returns the measurement unit.
     *
     * @return the measurement unit.
     */
    @Override
    public Unit<Q> getUnit() {
    	return unit;
    }

    /**
     * Convenient method equivalent to {@link #to(javax.measure.unit.Unit)
     * to(this.getUnit().toSI())}.
     *
     * @return this measure or a new measure equivalent to this measure but
     *         stated in SI units.
     * @throws ArithmeticException if the result is inexact and the quotient
     *         has a non-terminating decimal expansion.
     */
    public Measurement<Q> toSI() {
        return to(this.getUnit().getSystemUnit());
    }

    /**
     * Returns this measure after conversion to specified unit. The default
     * implementation returns
     * <code>Measure.valueOf(doubleValue(unit), unit)</code>. If this measure is
     * already stated in the specified unit, then this measure is returned and
     * no conversion is performed.
     *
     * @param unit the unit in which the returned measure is stated.
     * @return this measure or a new measure equivalent to this measure but
     *         stated in the specified unit.
     * @throws ArithmeticException if the result is inexact and the quotient has
     *         a non-terminating decimal expansion.
     */
    public Measurement<Q> to(Unit<Q> unit) {
        if (unit.equals(this.getUnit())) {
            return this;
        }
        return BaseMeasurement.of(doubleValue(unit), unit);
    }

//    /**
//     * Returns this measure after conversion to specified unit. The default
//     * implementation returns
//     * <code>Measure.valueOf(decimalValue(unit, ctx), unit)</code>. If this
//     * measure is already stated in the specified unit, then this measure is
//     * returned and no conversion is performed.
//     *
//     * @param unit the unit in which the returned measure is stated.
//     * @param ctx the math context to use for conversion.
//     * @return this measure or a new measure equivalent to this measure but
//     *         stated in the specified unit.
//     * @throws ArithmeticException if the result is inexact but the rounding
//     *         mode is <code>UNNECESSARY</code> or
//     *         <code>mathContext.precision == 0</code> and the quotient has
//     *         a non-terminating decimal expansion.
//     */
//    public Measurement<Q, V> to(Unit<Q> unit, MathContext ctx) {
//        if (unit.equals(this.getUnit())) {
//            return this;
//        }
//        return new BaseMeasurement(doubleValue(unit), unit);
//    }

    /**
     * Compares this measure to the specified Measurement quantity.
     *
     * @return a negative integer, zero, or a positive integer as this measure
     *         is less than, equal to, or greater than the specified Measurement
     *         quantity.
     * @return <code>Objects.compare(this)),
     *         that, DEFAULT_COMPARATOR)</code>
     */
    @SuppressWarnings("unchecked")
	public int compareTo(Measurement<Q> that) {
//        Unit<Q> unit = getUnit();
        return Objects.compare(this, that, OurComparator.getInstance());
    }

    /**
     * Compares this measure against the specified object for <b>strict</b>
     * equality (same unit and same amount).
     *
     * <p> Similarly to the {@link Double#equals} method which consider 2.0
     *     and 2.00 as different objects because of different internal scales,
     *     measurements such as <code>Measure.valueOf(3.0, KILOGRAM)</code>
     *     <code>Measure.valueOf(3, KILOGRAM)</code> and
     *     <code>Measure.valueOf("3 kg")</code> might not be considered equals
     *     because of possible differences in their implementations.</p>
     *
     * <p> To compare measures stated using different units or using different
     *     amount implementations the {@link #compareTo compareTo} or
     *     {@link #equals(javax.measure.Measurement, double, javax.measure.unit.Unit)
     *      equals(Measurement, epsilon, epsilonUnit)} methods should be used.</p>
     *
     * @param obj the object to compare with.
     * @return <code>this.getUnit.equals(obj.getUnit())
     *         && this.getValue().equals(obj.getValue())</code>
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractMeasurement<?>)) {
            return false;
        }
        AbstractMeasurement<?> that = (AbstractMeasurement<?>) obj;
        return this.getUnit().equals(that.getUnit()) && this.getValue().equals(that.getValue());
    }

    /**
     * Returns the hash code for this measure.
     *
     * @return the hash code value.
     */
    @Override
    public int hashCode() {
        return getUnit().hashCode() + getValue().hashCode();
    }

    /**
     * Returns the <code>String</code> representation of this measure. The
     * string produced for a given measure is always the same; it is not
     * affected by locale. This means that it can be used as a canonical string
     * representation for exchanging measure, or as a key for a Hashtable, etc.
     * Locale-sensitive measure formatting and parsing is handled by the
     * {@link MeasurementFormat} class and its subclasses.
     *
     * @return <code>UnitFormat.getInternational().format(this)</code>
     */
    @Override
    public String toString() {
        //return MeasureFormat.getStandard().format(this); TODO improve MeasureFormat
    	return String.valueOf(getValue()) + " " + String.valueOf(getUnit());
    }

    public abstract  double doubleValue(Unit<Q> unit)
            throws ArithmeticException;

    // Implements AbstractMeasurement
    public final int intValue(Unit<Q> unit) throws ArithmeticException {
        long longValue = longValue(unit);
        if ((longValue < Integer.MIN_VALUE) || (longValue > Integer.MAX_VALUE)) {
            throw new ArithmeticException("Cannot convert " + longValue + " to int (overflow)");
        }
        return (int) longValue;
    }

    protected long longValue(Unit<Q> unit) throws ArithmeticException {
        double result = doubleValue(unit);
        if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
            throw new ArithmeticException("Overflow (" + result + ")");
        }
        return (long) result;
    }

    protected final float floatValue(Unit<Q> unit) {
        return (float) doubleValue(unit);
    }

    /**
     * Casts this measure to a parameterized unit of specified nature or throw a
     * <code>ClassCastException</code> if the dimension of the specified
     * quantity and this measure unit's dimension do not match. For
     * example:<br/><code>
     *     Measure<Length> length = Measure.valueOf("2 km").asType(Length.class);
     * </code>
     *
     * @param type the quantity class identifying the nature of the measure.
     * @return this measure parameterized with the specified type.
     * @throws ClassCastException if the dimension of this unit is different
     *         from the specified quantity dimension.
     * @throws UnsupportedOperationException
     *             if the specified quantity class does not have a public static
     *             field named "UNIT" holding the SI unit for the quantity.
     * @see Unit#asType(Class)
     */
    @SuppressWarnings("unchecked")
    public final <T extends Quantity<T>> AbstractMeasurement<T> asType(Class<T> type)
            throws ClassCastException {
        this.getUnit().asType(type); // Raises ClassCastException is dimension
        // mismatches.
        return (AbstractMeasurement<T>) this;
    }
}
