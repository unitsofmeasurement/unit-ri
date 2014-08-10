package org.unitsofmeasurement.ri.function;

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
