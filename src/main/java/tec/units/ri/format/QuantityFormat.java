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
package tec.units.ri.format;

import java.io.IOException;

import javax.measure.MeasurementException;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.ParserException;
import tec.units.ri.AbstractQuantity;
import tec.units.ri.AbstractUnit;
import tec.units.ri.quantity.NumberQuantity;
import tec.uom.lib.common.function.Parser;

/**
 * <p>
 * This class provides the interface for formatting and parsing {@link Quantity quantities}.
 * </p>
 * 
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.0.1, $Date: 2017-07-10 $
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
public abstract class QuantityFormat implements Parser<CharSequence, Quantity> {
// TODO JavaDoc for later see https://github.com/unitsofmeasurement/unit-ri/issues/65
// <p>
// Instances of this class should be able to format quantities stated in {@link CompoundUnit}. See {@link #formatCompound formatCompound(...)}.
// </p>
  
  /**
   * 
   */
  // private static final long serialVersionUID = -4628006924354248662L;

  /**
   * Holds the default format instance.
   */
  private static final QuantityFormat DEFAULT = new Standard();

  /**
   * Holds the Number-Space-Unit format instance.
   */
  // private static final QuantityFormat NUM_SPACE = new NumberSpaceUnit(NumberFormat.getInstance(), SimpleUnitFormat.getInstance());

  // TODO use it as an option (after fixing parse())

  /**
   * Returns the quantity format for the default locale. The default format assumes the quantity is composed of a decimal number and a {@link Unit}
   * separated by whitespace(s).
   * 
   * @return <code>MeasureFormat.getInstance(NumberFormat.getInstance(), UnitFormat.getInstance())</code>
   */
  public static QuantityFormat getInstance() {
    return DEFAULT;
  }

  /**
   * Formats the specified quantity into an <code>Appendable</code>.
   * 
   * @param quantity
   *          the quantity to format.
   * @param dest
   *          the appendable destination.
   * @return the specified <code>Appendable</code>.
   * @throws IOException
   *           if an I/O exception occurs.
   */
  public abstract Appendable format(Quantity<?> quantity, Appendable dest) throws IOException;

  /**
   * Parses a portion of the specified <code>CharSequence</code> from the specified position to produce an object. If parsing succeeds, then the index
   * of the <code>cursor</code> argument is updated to the index after the last character used.
   * 
   * @param csq
   *          the <code>CharSequence</code> to parse.
   * @param index
   *          the current parsing index.
   * @return the object parsed from the specified character sub-sequence.
   * @throws IllegalArgumentException
   *           if any problem occurs while parsing the specified character sequence (e.g. illegal syntax).
   */
  abstract Quantity<?> parse(CharSequence csq, int index) throws IllegalArgumentException, ParserException;

  /**
   * Convenience method equivalent to {@link #format(AbstractQuantity, Appendable)} except it does not raise an IOException.
   * 
   * @param q
   *          the quantity to format.
   * @param dest
   *          the appendable destination.
   * @return the specified <code>StringBuilder</code>.
   */
  public final StringBuilder format(Quantity<?> q, StringBuilder dest) {
    try {
      return (StringBuilder) this.format(q, (Appendable) dest);
    } catch (IOException ex) {
      throw new MeasurementException(ex); // Should not happen.
    }
  }

  /**
   * Formats an object to produce a string. This is equivalent to <blockquote> {@link #format(Unit, StringBuilder) format}<code>(unit,
   *         new StringBuilder()).toString();</code> </blockquote>
   *
   * @param obj
   *          The object to format
   * @return Formatted string.
   * @exception IllegalArgumentException
   *              if the Format cannot format the given object
   */
  public final String format(Quantity q) {
    if (q instanceof AbstractQuantity) {
      return format((AbstractQuantity<?>) q, new StringBuilder()).toString();
    } else {
      return (this.format(q, new StringBuilder())).toString();
    }
  }

  static int getFractionDigitsCount(double d) {
    if (d >= 1) { // we only need the fraction digits
      d = d - (long) d;
    }
    if (d == 0) { // nothing to count
      return 0;
    }
    d *= 10; // shifts 1 digit to left
    int count = 1;
    while (d - (long) d != 0) { // keeps shifting until there are no more
      // fractions
      d *= 10;
      count++;
    }
    return count;
  }

  // Holds standard implementation.
  private static final class Standard extends QuantityFormat {

    /**
     * 
     */
    // private static final long serialVersionUID = 2758248665095734058L;

    @Override
    public Appendable format(Quantity q, Appendable dest) throws IOException {
      Unit unit = q.getUnit();
      // if (unit instanceof CompoundUnit)
      // return formatCompound(q.doubleValue(unit),
      // (CompoundUnit) unit, dest);
      // else {

      Number number = q.getValue();
      dest.append(number.toString());
      // }
      if (q.getUnit().equals(AbstractUnit.ONE))
        return dest;
      dest.append(' ');
      return SimpleUnitFormat.getInstance().format(unit, dest);
      // }
    }

    @SuppressWarnings("unchecked")
    @Override
    Quantity<?> parse(CharSequence csq, int index) throws ParserException {
      int startDecimal = index; // cursor.getIndex();
      while ((startDecimal < csq.length()) && Character.isWhitespace(csq.charAt(startDecimal))) {
        startDecimal++;
      }
      int endDecimal = startDecimal + 1;
      while ((endDecimal < csq.length()) && !Character.isWhitespace(csq.charAt(endDecimal))) {
        endDecimal++;
      }
      Double decimal = new Double(csq.subSequence(startDecimal, endDecimal).toString());
      // cursor.setIndex(endDecimal + 1);
      int startUnit = endDecimal + 1;// csq.toString().indexOf(' ') + 1;
      Unit unit = SimpleUnitFormat.getInstance().parse(csq, startUnit);
      return NumberQuantity.of(decimal.doubleValue(), unit);
    }

    public Quantity<?> parse(CharSequence csq) throws ParserException {
      return parse(csq, 0);
    }
  }
}
