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
package tec.units.ri;

import java.util.Comparator;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Dimensionless;

import tec.units.ri.format.QuantityFormat;
import tec.units.ri.function.NaturalOrder;
import tec.units.ri.quantity.NumberQuantity;
import tec.uom.lib.common.function.UnitSupplier;
import tec.uom.lib.common.function.ValueSupplier;

/**
 * <p>
 * This class represents the immutable result of a scalar measurement stated in a known unit.
 * </p>
 * 
 * <p>
 * <code>
 *         public static final Quantity<Velocity> C = NumberQuantity.parse("299792458 m/s").asType(Velocity.class);
 *         // Speed of Light (exact).
 *    </code>
 * </p>
 * 
 * <p>
 * Quantities can be converted to different units.<br/>
 * <code>
 *         Quantity<Velocity> milesPerHour = C.to(MILES_PER_HOUR); // Use double implementation (fast).
 *         System.out.println(milesPerHour);
 * 
 *         > 670616629.3843951 m/h
 *     </code>
 * </p>
 * 
 * <p>
 * Applications may sub-class {@link AbstractQuantity} for particular quantity types.<br/>
 * <code>
 *         // Quantity of type Mass based on <code>double</code> primitive types.<br>
 * public class MassAmount extends AbstractQuantity<Mass> {<br>
 * private final double kilograms; // Internal SI representation.<br>
 * private Mass(double kg) { kilograms = kg; }<br>
 * public static Mass of(double value, Unit<Mass> unit) {<br>
 * return new Mass(unit.getConverterTo(SI.KILOGRAM).convert(value));<br>
 * }<br>
 * public Unit<Mass> getUnit() { return SI.KILOGRAM; }<br>
 * public Double getValue() { return _kilograms; }<br>
 * ...<br>
 * }<br>
 * </p>
 * <p>
 * // Complex numbers measurements.<br>
 * public class ComplexQuantity
 * <Q extends Quantity>extends AbstractQuantity
 * <Q>{<br>
 * public Complex getValue() { ... } // Assuming Complex is a Number.<br>
 * ...<br>
 * }<br>
 * <br>
 * // Specializations of complex numbers measurements.<br>
 * public final class Current extends ComplexQuantity<ElectricCurrent> {...}<br>
 * public final class Tension extends ComplexQuantity<ElectricPotential> {...} <br>
 * </code>
 * </p>
 * 
 * <p>
 * All instances of this class shall be immutable.
 * </p>
 * 
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.0.1, October 6, 2016
 * @since 1.0
 */
public abstract class AbstractQuantity<Q extends Quantity<Q>> implements Quantity<Q>, Comparable<Quantity<Q>>, UnitSupplier<Q>, ValueSupplier<Number> {

  /**
	 * 
	 */
  // private static final long serialVersionUID = -4993173119977931016L;

  private final Unit<Q> unit;

  /**
   * Holds a dimensionless quantity of none (exact).
   */
  public static final Quantity<Dimensionless> NONE = NumberQuantity.of(0, AbstractUnit.ONE);

  /**
   * Holds a dimensionless quantity of one (exact).
   */
  public static final Quantity<Dimensionless> ONE = NumberQuantity.of(1, AbstractUnit.ONE);

  /**
   * constructor.
   */
  protected AbstractQuantity(Unit<Q> unit) {
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
  public Unit<Q> getUnit() {
    return unit;
  }

  /**
   * Convenient method equivalent to {@link #to(javax.measure.unit.Unit) to(this.getUnit().toSI())}.
   *
   * @return this measure or a new measure equivalent to this measure but stated in SI units.
   * @throws ArithmeticException
   *           if the result is inexact and the quotient has a non-terminating decimal expansion.
   */
  public Quantity<Q> toSI() {
    return to(this.getUnit().getSystemUnit());
  }

  /**
   * Returns this measure after conversion to specified unit. The default implementation returns <code>Measure.valueOf(doubleValue(unit), unit)</code>
   * . If this measure is already stated in the specified unit, then this measure is returned and no conversion is performed.
   *
   * @param unit
   *          the unit in which the returned measure is stated.
   * @return this measure or a new measure equivalent to this measure but stated in the specified unit.
   * @throws ArithmeticException
   *           if the result is inexact and the quotient has a non-terminating decimal expansion.
   */
  public Quantity<Q> to(Unit<Q> unit) {
    if (unit.equals(this.getUnit())) {
      return this;
    }
    return NumberQuantity.of(doubleValue(unit), unit);
  }

  /**
   * Compares this measure to the specified Measurement quantity. The default implementation compares the {@link AbstractQuantity#doubleValue(Unit)}
   * of both this measure and the specified Measurement stated in the same unit (this measure's {@link #getUnit() unit}).
   *
   * @return a negative integer, zero, or a positive integer as this measure is less than, equal to, or greater than the specified Measurement
   *         quantity.
   * @see {@link NaturalOrder}
   */
  public int compareTo(Quantity<Q> that) {
    final Comparator<Quantity<Q>> comparator = new NaturalOrder<Q>();
    return comparator.compare(this, that);
  }

  /**
   * Compares this measure against the specified object for <b>strict</b> equality (same unit and same amount).
   *
   * <p>
   * Similarly to the {@link BigDecimal#equals} method which consider 2.0 and 2.00 as different objects because of different internal scales,
   * measurements such as <code>Measure.valueOf(3.0, KILOGRAM)</code> <code>Measure.valueOf(3, KILOGRAM)</code> and
   * <code>Quantities.getQuantity("3 kg")</code> might not be considered equals because of possible differences in their implementations.
   * </p>
   *
   * <p>
   * To compare measures stated using different units or using different amount implementations the {@link #compareTo compareTo} or
   * {@link #equals(javax.measure.Measurement, double, javax.measure.unit.Unit) equals(Measurement, epsilon, epsilonUnit)} methods should be used.
   * </p>
   *
   * @param obj
   *          the object to compare with.
   * @return <code>this.getUnit.equals(obj.getUnit())
   *         && this.getValue().equals(obj.getValue())</code>
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof AbstractQuantity<?>)) {
      return false;
    }
    AbstractQuantity<?> that = (AbstractQuantity<?>) obj;
    return this.getUnit().equals(that.getUnit()) && this.getValue().equals(that.getValue());
  }

  /**
   * Compares this measure and the specified Measurement to the given accuracy. Measurements are considered approximately equals if their absolute
   * differences when stated in the same specified unit is less than the specified epsilon.
   *
   * @param that
   *          the Measurement to compare with.
   * @param epsilon
   *          the absolute error stated in epsilonUnit.
   * @param epsilonUnit
   *          the epsilon unit.
   * @return <code>abs(this.doubleValue(epsilonUnit) - that.doubleValue(epsilonUnit)) &lt;= epsilon</code>
   */
  public boolean equals(AbstractQuantity<Q> that, double epsilon, Unit<Q> epsilonUnit) {
    return Math.abs(this.doubleValue(epsilonUnit) - that.doubleValue(epsilonUnit)) <= epsilon;
  }

  /**
   * Returns the hash code for this quantity.
   *
   * @return the hash code value.
   */
  @Override
  public int hashCode() {
    return getUnit().hashCode() + getValue().hashCode();
  }

  final boolean isBig() {
    return false;
  }

  /**
   * Returns the <code>String</code> representation of this quantity. The string produced for a given quantity is always the same; it is not affected
   * by locale. This means that it can be used as a canonical string representation for exchanging quantity, or as a key for a Hashtable, etc.
   * Locale-sensitive quantity formatting and parsing is handled by the {@link QuantityFormat} class and its subclasses.
   *
   * @return <code>UnitFormat.getInternational().format(this)</code>
   */
  @Override
  public String toString() {
    // return MeasureFormat.getStandard().format(this); TODO improve
    // MeasureFormat
    // return String.valueOf(getValue()) + " " + String.valueOf(getUnit());
    return QuantityFormat.getInstance().format(this);
  }

  public abstract double doubleValue(Unit<Q> unit) throws ArithmeticException;

  protected long longValue(Unit<Q> unit) throws ArithmeticException {
    double result = doubleValue(unit);
    if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
      throw new ArithmeticException("Overflow (" + result + ")");
    }
    return (long) result;
  }

  protected float floatValue(Unit<Q> unit) {
    return (float) doubleValue(unit);
  }

  /**
   * Casts this quantity to a parameterized unit of specified nature or throw a <code>ClassCastException</code> if the dimension of the specified
   * quantity and this measure unit's dimension do not match. For example: <br/>
   * <code>
   *     Measure<Length> length = Quantities.getQuantity("2 km").asType(Length.class);
   * </code>
   *
   * @param type
   *          the quantity class identifying the nature of the quantity.
   * @return this quantity parameterized with the specified type.
   * @throws ClassCastException
   *           if the dimension of this unit is different from the specified quantity dimension.
   * @throws UnsupportedOperationException
   *           if the specified quantity class does not have a public static field named "UNIT" holding the SI unit for the quantity.
   * @see Unit#asType(Class)
   */
  @SuppressWarnings("unchecked")
  public final <T extends Quantity<T>> AbstractQuantity<T> asType(Class<T> type) throws ClassCastException {
    this.getUnit().asType(type); // Raises ClassCastException is dimension
    // mismatches.
    return (AbstractQuantity<T>) this;
  }
}
