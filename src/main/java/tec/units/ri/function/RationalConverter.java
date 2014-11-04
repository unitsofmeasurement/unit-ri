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
package tec.units.ri.function;

import javax.measure.UnitConverter;


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
