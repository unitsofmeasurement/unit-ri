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
package tec.uom.ri.function;

import javax.measure.function.UnitConverter;
import javax.measure.function.ValueSupplier;


/**
 * <p> This class represents a converter multiplying numeric values by an
 *     exact scaling factor (represented as the quotient of two
 *     <code>BigInteger</code> numbers).</p>
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.4, August 10, 2014
 */
public final class RationalConverter extends AbstractConverter 
	implements ValueSupplier<Double> {

    /**
	 * 
	 */
//	private static final long serialVersionUID = 1L;

	/**
     * Holds the converter dividend.
     */
    private final long dividend;

    /**
     * Holds the converter divisor (always positive).
     */
    private final long divisor;

    /**
     * Creates a rational converter with the specified dividend and
     * divisor.
     *
     * @param dividend the dividend.
     * @param divisor the positive divisor.
     * @throws IllegalArgumentException if <code>divisor &lt;= 0</code>
     * @throws IllegalArgumentException if <code>dividend == divisor</code>
     */
//    public RationalConverter(BigInteger dividend, BigInteger divisor) {
//        if (divisor.compareTo(BigInteger.ZERO) <= 0)
//            throw new IllegalArgumentException("Negative or zero divisor");
//        if (dividend.equals(divisor))
//            throw new IllegalArgumentException("Would result in identity converter");
//        this.dividend = dividend; // Exact conversion.
//        this.divisor = divisor; // Exact conversion.
//    }

    /**
     * Convenience method equivalent to
     * <code>RationalConverter.valueOf(BigInteger.valueOf(dividend), BigInteger.valueOf(divisor))</code>
     *
     * @param dividend the dividend.
     * @param divisor the positive divisor.
     * @throws IllegalArgumentException if <code>divisor &lt;= 0</code>
     * @throws IllegalArgumentException if <code>dividend == divisor</code>
     */
    public RationalConverter (long dividend, long divisor) {
        //this(BigInteger.valueOf(dividend), BigInteger.valueOf(divisor));
    	this.dividend = dividend;
    	this.divisor = divisor;
    }

    /**
     * Returns the integer dividend for this rational converter.
     *
     * @return this converter dividend.
     */
    public long getDividend() {
        return dividend;
    }

    /**
     * Returns the integer (positive) divisor for this rational converter.
     *
     * @return this converter divisor.
     */
    public long getDivisor() {
        return divisor;
    }

    @Override
    public double convert(double value) {
        return value * ((double) dividend / (double)divisor);
    }


//    @Override
//    public BigDecimal convert(BigDecimal value, MathContext ctx) throws ArithmeticException {
//        BigDecimal decimalDividend = new BigDecimal(dividend, 0);
//        BigDecimal decimalDivisor = new BigDecimal(divisor, 0);
//        return value.multiply(decimalDividend, ctx).divide(decimalDivisor, ctx);
//    }

    @Override
    public UnitConverter concatenate(UnitConverter converter) {
        if (!(converter instanceof RationalConverter))
            return super.concatenate(converter);
        RationalConverter that = (RationalConverter) converter;
        long newDividend = this.getDividend() * that.getDividend();
        long newDivisor = this.getDivisor() * that.getDivisor();
        long gcd = MathHelper.gcd(newDividend,newDivisor);
        newDividend = newDividend / gcd; // TODO clarify if this works with long
        newDivisor = newDivisor / gcd;
        return (newDividend == 1 && newDivisor == 1)
                ? IDENTITY : new RationalConverter(newDividend, newDivisor);
    }

    @Override
    public RationalConverter inverse() {
        return Math.signum((double)dividend) == -1 ? new RationalConverter(MathHelper.negateExact(getDivisor()), MathHelper.negateExact(getDividend()))
                : new RationalConverter(getDivisor(), getDividend());
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
        return (this.dividend == that.dividend
                && this.divisor == that.divisor);
    }

    @Override
    public int hashCode() {
        return Long.valueOf(dividend).hashCode() + Long.valueOf(dividend).hashCode();
    }

    @Override
    public boolean isLinear() {
        return true;
    }

    @Override
	public Double getValue() {
		return Double.valueOf((double)dividend / (double) divisor);
	}
}
