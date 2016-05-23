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
package tec.units.ri.quantity;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.UnconvertibleException;
import javax.measure.UnitConverter;
import tec.units.ri.AbstractQuantity;
import tec.units.ri.format.QuantityFormat;

/**
 * An amount of quantity, consisting of a Number and a Unit. NumberQuantity objects are immutable.
 * 
 * @see AbstractQuantity
 * @see Quantity
 * @author <a href="mailto:werner@uom.technology">Werner Keil</a>
 * @param <Q>
 *          The type of the quantity.
 * @version 0.12, $Date: 2016-04-20 $
 */
public class NumberQuantity<Q extends Quantity<Q>> extends AbstractQuantity<Q> {

  /**
	 * 
	 */
  // private static final long serialVersionUID = 7312161895652321241L;

  private final Number value;

  /*
   * (non-Javadoc)
   * 
   * @see AbstractQuantity#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (obj == this)
      return true;
    if (this.getClass() == obj.getClass()) {
      return super.equals(obj);
    } else {
      if (obj instanceof Quantity) {
        @SuppressWarnings("rawtypes")
        Quantity m = (Quantity) obj;
        if (m.getValue().getClass() == this.getValue().getClass() && m.getUnit().getClass() == this.getUnit().getClass()) {
          return super.equals(obj);
        } else {
          // if (this.getQuantityUnit() instanceof AbstractUnit<?>) {
          // if
          // }
          return super.equals(obj);
        }
      }
      return false;
    }
  }

  /**
   * Indicates if this quantity is exact.
   */
  private final boolean isExact;

  /**
   * Holds the exact value (when exact) stated in this quantity's unit.
   */
  // private long exactValue;

  /**
   * Holds the minimum value stated in this quantity's unit. For inexact measures: minimum < maximum
   */
  // private double minimum;

  /**
   * Holds the maximum value stated in this quantity's unit. For inexact measures: maximum > minimum
   */
  // private double maximum;

  public NumberQuantity(Number number, Unit<Q> unit) {
    super(unit);
    value = number;
    isExact = false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see AbstractQuantity#doubleValue(javax.measure.Unit)
   */
  public double doubleValue(Unit<Q> unit) {
    Unit<Q> myUnit = getUnit();
    try {
      UnitConverter converter = unit.getConverterTo(myUnit);
      return converter.convert(getValue().doubleValue());
    } catch (UnconvertibleException e) {
      throw e;
    } // catch (IncommensurableException e) {
      // throw new IllegalArgumentException(e.getMessage());
      // }
  }

  /*
   * (non-Javadoc)
   * 
   * @see AbstractQuantity#longValue(javax.measure.Unit)
   */
  protected final long longValue(Unit<Q> unit) {
    // Extends AbstractQuantity
    // Unit<Q> myUnit = getUnit();
    try {
      // UnitConverter converter = unit.getConverterToAny(myUnit);
      // if ((getValue() instanceof BigDecimal || getValue() instanceof
      // BigInteger)
      // && converter instanceof AbstractConverter) {
      // return (((AbstractConverter) converter).convert(
      // BigDecimal.valueOf(getValue().longValue()),
      // MathContext.DECIMAL128)).longValue();
      // } else {
      double result = doubleValue(unit);
      if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
        throw new ArithmeticException("Overflow (" + result + ")");
      }
      return (long) result;
      // }
    } catch (UnconvertibleException e) {
      throw e;
    }
    // catch (IncommensurableException e) {
    // throw new IllegalArgumentException(e.getMessage());
    // }
  }

  protected final int intValue(Unit<Q> unit) throws ArithmeticException {
    long longValue = longValue(unit);
    if ((longValue < Integer.MIN_VALUE) || (longValue > Integer.MAX_VALUE)) {
      throw new ArithmeticException("Cannot convert " + longValue + " to int (overflow)");
    }
    return (int) longValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.measure.Quantity#getValue()
   */
  public Number getValue() {
    return value;
  }

  /**
   * Indicates if this measured quantity is exact. An exact quantity is guaranteed exact only when stated in this quantity's unit (e.g.
   * <code>this.longValue()</code>); stating the quantity in any other unit may introduce conversion errors.
   * 
   * @return <code>true</code> if this quantity is exact; <code>false</code> otherwise.
   */
  public boolean isExact() {
    return isExact;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected Quantity<Q> add(AbstractQuantity<Q> that) {
    final Quantity<Q> thatToUnit = that.to(getUnit());
    return new NumberQuantity(this.getValue().doubleValue() + thatToUnit.getValue().doubleValue(), getUnit());
  }

  public String toString() {
    return String.valueOf(getValue()) + " " + String.valueOf(getUnit());
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Quantity<?> multiply(Quantity<?> that) {
    final Unit<?> unit = getUnit().multiply(that.getUnit());
    return new NumberQuantity((getValue().doubleValue() * that.getValue().doubleValue()), unit);
  }

  public Quantity<Q> multiply(Number that) {
    return (NumberQuantity<Q>) NumberQuantity.of((getValue().doubleValue() * that.doubleValue()), getUnit());
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Quantity<Q> divide(Quantity<?> that) {
    final Unit<?> unit = getUnit().divide(that.getUnit());
    return new NumberQuantity((getValue().doubleValue() / that.getValue().doubleValue()), unit);
  }

  public Quantity<Q> divide(Number that) {
    return NumberQuantity.of(getValue().doubleValue() / that.doubleValue(), getUnit());
  }

  public Quantity<Q> inverse() {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    final Quantity<Q> m = new NumberQuantity(1d / getValue().doubleValue(), getUnit().inverse());
    return m;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Quantity<Q> subtract(Quantity<Q> that) {
    final Quantity<Q> thatToUnit = (Quantity<Q>) that.to(getUnit());
    return new NumberQuantity(this.getValue().doubleValue() - thatToUnit.getValue().doubleValue(), getUnit());
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Quantity<Q> add(Quantity<Q> that) {
    final Quantity<Q> thatToUnit = (Quantity<Q>) that.to(getUnit());
    return new NumberQuantity(this.getValue().doubleValue() + thatToUnit.getValue().doubleValue(), getUnit());
  }

  /**
   * Returns the scalar quantity for the specified <code>long</code> stated in the specified unit.
   *
   * @param longValue
   *          the quantity value.
   * @param unit
   *          the measurement unit.
   * @return the corresponding <code>int</code> quantity.
   */
  public static <Q extends Quantity<Q>> AbstractQuantity<Q> of(long longValue, Unit<Q> unit) {
    return new LongQuantity<Q>(longValue, unit);
  }

  /**
   * Returns the scalar quantity for the specified <code>int</code> stated in the specified unit.
   *
   * @param intValue
   *          the quantity value.
   * @param unit
   *          the measurement unit.
   * @return the corresponding <code>int</code> quantity.
   */
  public static <Q extends Quantity<Q>> AbstractQuantity<Q> of(int intValue, Unit<Q> unit) {
    return new IntegerQuantity<Q>(intValue, unit);
  }

  /**
   * Returns the scalar quantity for the specified <code>short</code> stated in the specified unit.
   *
   * @param value
   *          the quantity value.
   * @param unit
   *          the measurement unit.
   * @return the corresponding <code>short</code> quantity.
   */
  public static <Q extends Quantity<Q>> AbstractQuantity<Q> of(short value, Unit<Q> unit) {
    return new ShortQuantity<Q>(value, unit);
  }

  /**
   * Returns the scalar quantity for the specified <code>float</code> stated in the specified unit.
   *
   * @param floatValue
   *          the measurement value.
   * @param unit
   *          the measurement unit.
   * @return the corresponding <code>float</code> quantity.
   */
  public static <Q extends Quantity<Q>> AbstractQuantity<Q> of(float floatValue, Unit<Q> unit) {
    return new FloatQuantity<Q>(floatValue, unit);
  }

  /**
   * Returns the scalar quantity for the specified <code>double</code> stated in the specified unit.
   *
   * @param doubleValue
   *          the measurement value.
   * @param unit
   *          the measurement unit.
   * @return the corresponding <code>double</code> quantity.
   */
  public static <Q extends Quantity<Q>> AbstractQuantity<Q> of(double doubleValue, Unit<Q> unit) {
    return new DoubleQuantity<Q>(doubleValue, unit);
  }

  /**
   * Returns the decimal quantity of unknown type corresponding to the specified representation. This method can be used to parse dimensionless
   * quantities.<br/>
   * <code>
   *     Quatity<Dimensionless> proportion = AbstractQuantity.of("0.234").asType(Dimensionless.class);
   * </code>
   *
   * <p>
   * Note: This method handles only {@link tec.units.ri.SimpleUnitFormat.UnitFormat#getStandard standard} unit format (<a
   * href="http://unitsofmeasure.org/">UCUM</a> based). Locale-sensitive quantity formatting and parsing are handled by the {@link MeasurementFormat}
   * class and its subclasses.
   * </p>
   *
   * @param csq
   *          the decimal value and its unit (if any) separated by space(s).
   * @return <code>QuantityFormat.getInstance().parse(csq)</code>
   */
  public static Quantity<?> parse(CharSequence csq) {
    return QuantityFormat.getInstance().parse(csq);
  }
}
