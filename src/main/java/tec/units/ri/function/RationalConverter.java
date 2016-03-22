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
package tec.units.ri.function;

import static tec.units.ri.internal.MathUtil.gcd;
import static tec.units.ri.internal.MathUtil.negateExact;

import javax.measure.UnitConverter;

import tec.units.ri.AbstractConverter;
import tec.uom.lib.common.function.ValueSupplier;

/**
 * <p>
 * This class represents a converter multiplying numeric values by an exact scaling factor (represented as the quotient of two <code>double</code>
 * numbers).
 * </p>
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.7, December 28, 2015
 */
public final class RationalConverter extends AbstractConverter implements ValueSupplier<Double> {

  /**
	 * 
	 */
  // private static final long serialVersionUID = 1L;

  /**
   * Holds the converter dividend.
   */
  private final double dividend;

  /**
   * Holds the converter divisor (always positive).
   */
  private final double divisor;

  /**
   * Constructor
   *
   * @param dividend
   *          the dividend.
   * @param divisor
   *          the positive divisor.
   * @throws IllegalArgumentException
   *           if <code>divisor &lt;= 0</code>
   * @throws IllegalArgumentException
   *           if <code>dividend == divisor</code>
   */
  public RationalConverter(double dividend, double divisor) {
    this.dividend = dividend;
    this.divisor = divisor;
  }

  /**
   * Convenience method equivalent to <code>new RationalConverter(dividend, divisor)</code>
   *
   * @param dividend
   *          the dividend.
   * @param divisor
   *          the positive divisor.
   * @throws IllegalArgumentException
   *           if <code>divisor &lt;= 0</code>
   * @throws IllegalArgumentException
   *           if <code>dividend == divisor</code>
   */
  public static final RationalConverter of(long dividend, long divisor) {
    return new RationalConverter((double) dividend, (double) divisor);
  }

  /**
   * Convenience method equivalent to <code>new RationalConverter(dividend, divisor)</code>
   *
   * @param dividend
   *          the dividend.
   * @param divisor
   *          the positive divisor.
   * @throws IllegalArgumentException
   *           if <code>divisor &lt;= 0</code>
   * @throws IllegalArgumentException
   *           if <code>dividend == divisor</code>
   */
  public static final RationalConverter of(double dividend, double divisor) {
    return new RationalConverter(dividend, divisor);
  }

  /**
   * Returns the integer dividend for this rational converter.
   *
   * @return this converter dividend.
   */
  public double getDividend() {
    return dividend;
  }

  /**
   * Returns the integer (positive) divisor for this rational converter.
   *
   * @return this converter divisor.
   */
  public double getDivisor() {
    return divisor;
  }

  @Override
  public double convert(double value) {
    return value * ((double) dividend / (double) divisor);
  }

  @Override
  public UnitConverter concatenate(UnitConverter converter) {
    if (!(converter instanceof RationalConverter))
      return super.concatenate(converter);
    RationalConverter that = (RationalConverter) converter;
    double newDividend = this.getDividend() * that.getDividend();
    double newDivisor = this.getDivisor() * that.getDivisor();
    double gcd = gcd(newDividend, newDivisor);
    newDividend = newDividend / gcd; // TODO clarify if this works with long
    newDivisor = newDivisor / gcd;
    return (newDividend == 1 && newDivisor == 1) ? IDENTITY : new RationalConverter(newDividend, newDivisor);
  }

  @Override
  public RationalConverter inverse() {
    return Math.signum(dividend) == -1 ? new RationalConverter(negateExact(getDivisor()), negateExact(getDividend())) : new RationalConverter(
        getDivisor(), getDividend());
  }

  @Override
  public final String toString() {
    return "RationalConverter(" + dividend + "," + divisor + ")";
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof RationalConverter))
      return false;
    RationalConverter that = (RationalConverter) obj;
    return (this.dividend == that.dividend && this.divisor == that.divisor);
  }

  @Override
  public int hashCode() {
    return Double.valueOf(dividend).hashCode() + Double.valueOf(dividend).hashCode();
  }

  public boolean isLinear() {
    return true;
  }

  public double getAsDouble() {
    return (double) dividend / (double) divisor;
  }

  public Double getValue() {
    return Double.valueOf(getAsDouble());
  }
}
