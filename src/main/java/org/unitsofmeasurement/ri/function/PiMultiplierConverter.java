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
package org.unitsofmeasurement.ri.function;

import javax.measure.function.ValueSupplier;

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
