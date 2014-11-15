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

import tec.units.ri.AbstractConverter;


/**
 * <p>
 * This class represents a converter multiplying numeric values by π (Pi).
 * </p>
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Pi"> Wikipedia: Pi</a>
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.3, August 10, 2014
 */
public final class PiMultiplierConverter extends AbstractConverter implements
		ValueSupplier<String> {

	/**
	 * Creates a Pi multiplier converter.
	 */
	public PiMultiplierConverter() {
	}

	@Override
	public double convert(double value) {
		return value * PI;
	}

//	@Override
//	public BigDecimal convert(BigDecimal value, MathContext ctx)
//			throws ArithmeticException {
//		int nbrDigits = ctx.getPrecision();
//		if (nbrDigits == 0)
//			throw new ArithmeticException(
//					"Pi multiplication with unlimited precision");
//		BigDecimal pi = Pi.pi(nbrDigits);
//		return value.multiply(pi, ctx).scaleByPowerOfTen(1 - nbrDigits);
//	}

	@Override
	public AbstractConverter inverse() {
		return new PiDivisorConverter();
	}

	@Override
	public final String toString() {
		return "(π)";
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof PiMultiplierConverter);
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean isLinear() {
		return true;
	}

	/**
	 * Pi calculation with Machin's formula.
	 * 
	 * @see <a
	 *      href="http://en.literateprograms.org/Pi_with_Machin's_formula_(Java)"
	 *      >Pi with Machin's formula</a>
	 * 
	 */
	static final class Pi {

		private Pi() {
		}

		public static Double pi(int numDigits) {
			int calcDigits = numDigits + 10;
			return 4 * (
					(4 * (arccot(5, calcDigits))) - arccot(
							239, calcDigits)); /*).setScale(numDigits,
					RoundingMode.DOWN);*/
		}

		private static double arccot(double x, int numDigits) {
			double unity = 1; /*BigDecimal.ONE.setScale(numDigits,
					RoundingMode.DOWN);*/
			double sum = unity / x;
			double xpower = sum;
			Double term = null;
			boolean add = false;
			for (double n = 3; term == null
					|| !term.equals(Double.valueOf(0)); n += 2) {
				xpower = xpower / ((long)x^2);
				term = xpower / n;
				sum = add ? sum + term : sum - term;
				add = !add;
			}
			return sum;
		}
	}

	//private static final BigDecimal TWO = new BigDecimal("2");

	//private static final BigDecimal FOUR = new BigDecimal("4");

	//private static final BigDecimal FIVE = new BigDecimal("5");

	//private static final BigDecimal TWO_THIRTY_NINE = new BigDecimal("239");

	@Override
	public String getValue() {
		return toString();
	}

}
