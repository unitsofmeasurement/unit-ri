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
import javax.measure.format.ParserException;

import tec.units.ri.format.QuantityFormat;

/**
 * Singleton class for accessing {@link Quantity} instances.
 * 
 * @author werner
 * @author otaviojava
 * @version 1.0, October 6, 2016
 * @since 1.0
 */
public final class Quantities {
  /**
   * Private singleton constructor.
   */
  private Quantities() {
  }

  /**
   * Returns the {@link #of(Number, javax.measure.Unit) numeric} quantity of unknown type corresponding to the specified representation. This method
   * can be used to parse dimensionless quantities.<br/>
   * <code>
   *     Quantity<Dimensionless> proportion = NumberQuantity.of("0.234").asType(Dimensionless.class);
   * </code>
   *
   * <p>
   * Note: This method handles only {@link javax.measure.format.UnitFormat#getStandard standard} unit format (<a
   * href="http://unitsofmeasure.org/">UCUM</a> based). Locale-sensitive measure formatting and parsing are handled by the {@link QuantityFormat}
   * class and its subclasses.
   * </p>
   *
   * @param csq
   *          the decimal value and its unit (if any) separated by space(s).
   * @return <code>QuantityFormat.getInstance(LOCALE_NEUTRAL).parse(csq)</code>
   */
  public static Quantity<?> getQuantity(CharSequence csq) {
    try {
      return QuantityFormat.getInstance().parse(csq);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e); // TODO could we handle this
      // differently?
    } catch (ParserException e) {
      throw new IllegalArgumentException(e); // TODO could we handle this
      // differently?
    }
  }

  /**
   * Returns the scalar measurement in the specified unit.
   * 
   * @param value
   *          the measurement value.
   * @param unit
   *          the measurement unit.
   * @return the corresponding <code>numeric</code> quantity.
   * @throws NullPointerException
   *           when value or unit were null
   */
  public static <Q extends Quantity<Q>> Quantity<Q> getQuantity(Number value, Unit<Q> unit) {

    if (value == null)
      throw new NullPointerException();
    if (unit == null)
      throw new NullPointerException();
    if (Double.class.isInstance(value)) {
      return new DoubleQuantity<Q>(value.doubleValue(), unit);
    } else if (Long.class.isInstance(value)) {
      return new LongQuantity<Q>(Long.class.cast(value), unit);
    } else if (Short.class.isInstance(value)) {
      return new ShortQuantity<Q>(Short.class.cast(value), unit);
    } else if (Integer.class.isInstance(value)) {
      return new IntegerQuantity<Q>(Integer.class.cast(value), unit);
    } else if (Float.class.isInstance(value)) {
      return new FloatQuantity<Q>(Float.class.cast(value), unit);
    }
    return new NumberQuantity<Q>(value, unit);
  }
}
