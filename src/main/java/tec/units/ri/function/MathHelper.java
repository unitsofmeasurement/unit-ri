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
package tec.units.ri.function;

class MathHelper {
	/**
	 * Returns the negation of the argument, throwing an exception if the result
	 * overflows a {@code long}.
	 *
	 * @param a
	 *            the value to negate
	 * @return the result
	 * @throws ArithmeticException
	 *             if the result overflows a long
	 */
	static long negateExact(long a) {
		if (a == Long.MIN_VALUE) {
			throw new ArithmeticException("long overflow");
		}

		return -a;
	}

	static long gcd(long a, long b) {
		if (b == 0)
			return a;
		return gcd(b, a % b);
	}
}
