package tec.units.ri.internal;

/**
 * 16.16 Fixed Point Math libray using 16-bit, 32-bit and 64-bit integer 
 * arithmetic and look-up tables.
 * <p>
 * Please note that:
 * <ul>
 * <li>This documentation denotes fixed point numbers as <CODE>FP int</CODE>s
 *  to distinguish them from integer numbers. However <CODE>FP int</CODE> are
 *  in fact plain <CODE>int</CODE> primitives.
 * <li>Its good programming practice to start all <CODE>FP int</CODE> variable
 *  and attribute names with 'f' or 'fp' to distinguish them from integer
 *  <CODE>int</CODE>s.
 * <li>Usage of Java built-in integer aritmetic operators (+, -, *, /, %, &, |,
 *  ^, <<, >>, >>>) for fixed point calculations should be kept to a minimum.
 *  Use the methods offered here instead.
 * <li>Built-in Java comparison operators (<, <=, ==, !=, >=, >) may be used to
 *  compare <CODE>FP int</CODE>s to other <CODE>FP int</CODE>s, but not to
 *  compare <CODE>FP int</CODE>s to other integer <CODE>int</CODE>s.
 * <li>All methods presented in this class are defined as <CODE>static</CODE>
 *  for performance reasons.
 * <li>The worst-case error of each method is mentioned in its description
 * 	and is measured in <CODE>EPSILON</CODE>s, the smallest positive non-zero
 *  number a <CODE>FP int</CODE> can have.
 * <li>The internal look-up tables are stored hard-coded and have been
 *  thoroughly optimized for size, resulting in a total of 560 bytes.
 * <li>Non-linear monotic functions are NOT guaranteed to produce monotonic
 * <CODE>FP int</CODE>results, but will never differ more than the wost-case
 * error of these methods.
 * </ul>
 * <br>
 * <p>
 * The source code license:
 * <p>
 * Copyright (c) 2005-2011, Giliam de Carpentier. All rights reserved. <br>
 * <br>
 * Redistribution and use in source and binary forms, with or without modification, <br>
 * are permitted provided that the following conditions are met: <br>
 * <br>
 * <ul>
 * <li>  Redistributions of source code must retain the above copyright <br>
 * 	     notice, this list of conditions and the following disclaimer. <br>
 * <br>
 * <li>   Redistributions in binary form must reproduce the above copyright <br>
 * 	     notice, this list of conditions and the following disclaimer in the <br>
 * 	     documentation and/or other materials provided with the distribution. <br>
 * </ul>
 * <br>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND <br>
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED <br>
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE <br>
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR <br>
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES <br>
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; <br>
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON <br>
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT <br>
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS <br>
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. <br>
 * <p>
 * @author Giliam de Carpentier 
 */
public final class FPMath {
	/**
	 * A <CODE>FP int</CODE> constant holding the smallest positive non-zero value a 
	 * <CODE>FP int</CODE> can have, 2<SUP>-16</SUP>. 
	 */
	public static final int EPSILON = 1;

	/**
	 * A <CODE>FP int</CODE> constant holding the maximum value a
	 * <CODE>FP int</CODE> can have, 2<SUP>15</SUP> - 2<SUP>-16</SUP>.  
	 */
	public static final int MAX_VALUE = 0x7FFFFFFF;

	/**
	 * A <CODE>FP int</CODE> constant holding the miminum value a
	 * <CODE>FP int</CODE> can have, -2<SUP>15</SUP>.  
	 */
	public static final int MIN_VALUE = 0x80000000;

	/**
	 * Returns the absolute value of the specified <CODE>FP int</CODE> as 
	 * a <CODE>FP int</CODE>.
	 * <p>
	 * Maximum deviation from definition: 0 <CODE>EPSILON</CODE>.
	 * 
	 * @param fp	The <CODE>FP int</CODE> value.
	 * @return A <CODE>FP int</CODE> containing the absolute value.
	 */
	public static int abs(int fp) { 
		if (fp >= 0) {return fp;} else {return -fp;} 
	}
	
	/**
	 * Returns the result of the addition of the two specified 
	 * <CODE>FP int</CODE> parameters as a <CODE>FP int</CODE>.
	 * <p>
	 * Maximum deviation from definition: 0 <CODE>EPSILON</CODE>.
	 * 
	 * @param fpA	First <CODE>FP int</CODE> parameter.
	 * @param fpB	Second <CODE>FP int</CODE> parameter.
	 * @return A <CODE>FP int</CODE> containing the result of this calculation.
	 */
	public static int add(int fpA, int fpB) {
		return fpA + fpB;
	}
	
	/**
	 * Returns the inverse tangent of the specified <CODE>FP int</CODE>
	 * parameter as a <CODE>FP int</CODE>.
	 * <p>
	 * The result is specified in 1/65536ths of a full rotation and is
	 * returned in the range of 0.0 through 0.25 and 0.75 through 
	 * 1.0-<CODE>EPSILON</CODE>.  
	 * <p>
	 * Maximum deviation from definition: 2 <CODE>EPSILON</CODE>.
	 * 
	 * @param fp		The <CODE>FP int</CODE> containing the tangent. 
	 * @return A <CODE>FP int</CODE> containing the result of this calculation.
	 */
	public static int atan(int fp) {
		
		boolean neg = fp < 0;
		if (neg)
			fp = -fp;
		if (fp < 4) {
			return 0;
		}

		// Tranform input to logaritmic scale
		int num = log(fp);

		// Mirror logaritm to a positive number
		int abs = num;
		if (abs < 0)
			abs = -abs;

		// Get the look-up table index and interpolation parameter
		int im = ((abs >> 15) & 31) + 1, u = (abs & 0x7FFF);
		if (im == 1) {
			if ((abs & 0x4000) == 0)
				im = 0;
			u = (u << 1) & 0x7FFF;
		}

		// Retrieve first and third coefficient
		int a = ((int) LUT_ATAN_1[im] & 0xFFFF);
		int b, c = ((int) LUT_ATAN_2[im] & 0xFFFF);

		// Derive second coefficient from the current and next third coefficient
		b = ((((int) LUT_ATAN_2[im + 1] & 0xFFFF) - c) + (a >> 4));

		// Compute second-order polynomial
		int k = (((((b - ((u * a) >>> 19)) * u) >>> 15) + c + 4) >>> 3);

		// Correct for quadrant
		if (num < 0) {
			num = 0x2000 - k;
		} else {
			num = 0x2000 + k;
		}
		if (neg)
			num = -num;
		
		return num & 0xFFFF;
	}

	/**
	 * Returns the polar angle from the cartesian input vector, specified as
	 * an y-component <CODE>FP int</CODE> and x-component <CODE>FP int</CODE>, 
	 * as a <CODE>FP int</CODE>.
	 * <p>
	 * The result is specified in 1/65536ths of a full rotation and is returned
	 * in the range of 0.0 through (1.0-<CODE>EPSILON</CODE>).  
	 * <p>
	 * Maximum deviation from definition: 2 <CODE>EPSILON</CODE>.
	 * 
	 * @param fpY		The <CODE>FP int</CODE> containing the Y-component. 
	 * @param fpX		The <CODE>FP int</CODE> containing the X-component. 
	 * @return A <CODE>FP int</CODE> containing the result of this calculation.
	 */
	public static int atan2(int fpY, int fpX) {
		int ax, ay;
		int num;
		
		if (fpY < 0) {fpX = -fpX; ay = -fpY;} else {ay = fpY;}
		
		if (fpX < 0) {ax = -fpX;} else {ax = fpX;}

		if (ax < ay && ax >= 0x10000 || (ax >= ay && ax < 0x10000) || ay == 0) {
			if (ax == 0) {
				return 0;
			}
			num = atan((int) (((long) ay << 16) / ax));
			if (fpX < 0)
				num = 0x8000 - num;
		} else {
			num = 0x4000 - atan((int) (((long) fpX << 16) / ay));
		}

		if (fpY < 0)
			num += 0x8000;

		return num & 0xFFFF;
	}
	
	/**
	 * Returns the smallest integer that is equal or greater than specified
	 * <CODE>FP int</CODE> as a <CODE>FP int</CODE>.
	 * <p>
	 * Maximum deviation from definition: 0 <CODE>EPSILON</CODE>.
	 * 
	 * @param fp	The <CODE>FP int</CODE> value.
	 * @return A <CODE>FP int</CODE> containing the result of this calculation.
	 */
	public static int ceil(int fp) {
		return (fp + 0xFFFF) & 0xFFFF0000;
	}

	/**
	 * Returns the cosine of the specified angle <CODE>FP int</CODE>
	 * parameter as a <CODE>FP int</CODE>.
	 * <p>
	 * Maximum deviation from definition: 2 <CODE>EPSILON</CODE>.
	 * 
	 * @param fp		The <CODE>FP int</CODE> containing the angle. This
	 * 					should be specified in 1/65536ths of a full rotation. 
	 * @return A <CODE>FP int</CODE> containing the result of this calculation.
	 */
	public static int cos(int fp) {
		// Mirror negative angles, making them positive.
		int abs = fp;
		if (abs < 0)
			abs = -abs;
		int i = abs & 0x7FFF;

		// Flag on 2nd and 3nd quadrant which should be negative.
		// Mirror sub-quadrant angles in the 2nd and 4th quadrant.
		// And exit early on the 1/4 and 3/4 angles, returning 0.
		boolean neg = (abs & 0x8000) > 0;
		if ((i & 0x4000) > 0) {
			if (i == 0x4000) {
				return 0;
			}
			i = 0x8000 - i;
			neg = !neg;
		}

		// Get the angle relative to its quadrant
		int im = (i >> 10) & 15, u = (i & 0x3FF);

		// Retrieve first and third coefficient
		int a = LUT_COS_1[im], b, c = ((int) LUT_COS_2[im] & 0xFFFF);

		// Derive second coefficient from the current and next third coefficient
		if (im < 15) {
			b = (((((int) LUT_COS_2[im + 1] & 0xFFFF) - c) << 6) - a);
		} else {
			b = (((-1 - c) << 6) - a);
		}

		// Compute second-order polynomial
		int num = (((((u * a) >> 10) + b) * u + 0x8000) >> 16) + (c + 1);

		// Make result negative if needed
		if (neg)
			return -num;
		else
			return num;
	}

	/**
	 * Returns the result of the division of the first specified 
	 * <CODE>FP int</CODE> parameter by the second specified <CODE>FP int</CODE>
	 * parameter as a (rounded down) <CODE>FP int</CODE>.
	 * <p>
	 * Maximum deviation from definition: 0 <CODE>EPSILON</CODE>.
	 * 
	 * @param fpA	First <CODE>FP int</CODE> parameter.
	 * @param fpB	Second <CODE>FP int</CODE> parameter.
	 * @return A <CODE>FP int</CODE> containing the result of this calculation.
	 */
	public static int div(int fpA, int fpB) {
		return (int) (((long) fpA << 16) / fpB);
	}
	
	/**
	 * Returns the Euler's number <I>e</I> raised to the power of the specified 
	 * <CODE>FP int</CODE> parameter as a <CODE>FP int</CODE>. Results greater 
	 * than MAX_VALUE are clamped to MAX_VALUE.
	 * <p>
	 * Maximum deviation from definition: <br>
	 * <ul>
	 * <li>1 <CODE>EPSILON</CODE> for "power <CODE>FP int</CODE> < -1"
	 * <li>2 <CODE>EPSILON</CODE> for "-1 <= power <CODE>FP int</CODE> < 0"
	 * <li>Returned <CODE>FP int</CODE> * 2<SUP>-14</SUP> for "power <CODE>FP int</CODE> >= 0"
	 * </ul>
	 * 
	 * @param fp		The <CODE>FP int</CODE> containing the power. 
	 * @return A <CODE>FP int</CODE> containing the result of this calculation.
	 */
	public static int exp(int fp) {
		// Algoritm is based on the following facts:
		// - exp([a][b]) = exp([a][0]*exp([0][b])
		// - lim(c -> 0) exp(c) = 1 - c

		int abs = fp;
		if (fp < 0) {
			if (fp < -700244) {
				// Bound FPs < ln(.5/65536) to 0
				if (fp < -772244)
					return 0;
				else 
					// Bound ln(.5/65536) < FPs < ln(1.5/65536) to 1
					return 1;
			}
			abs = -fp;
		} else {
			// Bound FPs greater than ln(.5*32768) to MAX_VALUE
			if (fp > 681390) {
				return 0x7FFFFFFF;
			}
			abs = fp;
		}

		int i = abs, im, k;

		i >>>= 5;
		im = (i & 31) - 1; // use bit 5 to 14
		if (im >= 0) {
			k = ((int) LUT_EXP_3[im] & 0xFFFF);
			i >>>= 5;
			im = (i & 31) - 1; // use bit 15 to 19
			if (im >= 0) {
				k = (((k * ((int) LUT_EXP_2[im] & 0xFFFF)) >>> 15) + 1) >>> 1;
			}
		} else {
			i >>>= 5;
			im = (i & 31) - 1; // use bit 15 to 19
			if (im >= 0) {
				k = ((int) LUT_EXP_2[im] & 0xFFFF);
			} else {
				k = 0x10000;
			}
		}

		im = abs & 31; // use bit 0 to 4
		if (im > 0) {
			k = (k * (0x10000 - im)) >>> 15;
			k = (k >>> 1) + (k & 1);
		}

		i >>>= 5;
		im = i & 31; // use bit 15 to 19
		int l = LUT_EXP_1[im];

		// combine integer exponent and inverse fractal exponent part
		if (fp < 0) {
			fp = (int) (((long) k << 17) / l);
		} else {
			fp = (int) (((long) l << 17) / k);
		}
		return ((fp + 1) >>> 1);
	}

	/**
	 * Returns the largest integer that is equal or smaller than specified
	 * <CODE>FP int</CODE> as a <CODE>FP int</CODE>.
	 * <p>
	 * Maximum deviation from definition: 0 <CODE>EPSILON</CODE>.
	 * 
	 * @param fp	The <CODE>FP int</CODE> value.
	 * @return A <CODE>FP int</CODE> containing the result of this calculation.
	 */
	public static int floor(int fp) {
		return fp & 0xFFFF0000;
	}

	/**
	 * Returns the fraction of the specified <CODE>FP int</CODE> in
	 * 1/65536ths as in <CODE>int</CODE> integer.
	 * <p>
	 * Maximum deviation from definition: 0 <CODE>EPSILON</CODE>.
	 * 
	 * @param fp	<CODE>FP int</CODE> parameter.
	 * @return A <CODE>int</CODE> containing the fraction in 1/65536ths.
	 */
	public static int fraction(int fp) {
		return fp & 0xFFFF;
	}

	/**
	 * Returns the natural logaritm (base Euler's number <I>e</I>) of the
	 * specified <CODE>FP int</CODE> parameter as a <CODE>FP int</CODE>.
	 * All non-positive parameters return <CODE>MIN_VALUE</CODE>.
	 * <p>
	 * Maximum deviation from definition: 4 <CODE>EPSILON</CODE>.
	 * 
	 * @param fp		The <CODE>FP int</CODE> containing the input number. 
	 * @return A <CODE>FP int</CODE> containing the result of this calculation.
	 */
	public static int log(int fp) {
		// Algoritm is based on the following facts:
		// - ln(exp(a)*b) = a + ln(b)
		// - ln([a][b]) = ln([a][0]) + ln([a][b] / [a][0])
		// - lim(c -> 1) ln(c) = 1 + c

		if (fp <= 0) {
			return MIN_VALUE;
		}

		// get MSB position
		int i = fp, im, j2, j1, j3, p = -16;
		if ((i & 0xFFFF0000) > 0) {i >>>= 16; p += 16;}
		if ((i & 0x0000FF00) > 0) {i >>>= 8; p += 8;}
		if ((i & 0x000000F0) > 0) {i >>>= 4; p += 4;}
		if ((i & 0x0000000C) > 0) {i >>>= 2; p += 2;}
		if ((i & 0x00000002) > 0) {i >>>= 1; p += 1;}

		// create log based on most significant bit (MSB) position
		int k = p * 45426;

		// create 3 parts of the 15 bits following the MSB
		if (p >= 0) {
			j3 = fp >>> (p + 1);
		} else {
			j3 = fp << (-1 - p);
		}
		j2 = j3 >>> 5;
		j1 = j2 >>> 5;

		// use bit MSB+1 to MSB+5
		im = (j1 & 31) - 1;
		if (im >= 0) {
			k += ((int) LUT_LOG_1[im] & 0xFFFF);
		}

		// use bit MSB+6 to MSB+10
		im = (j3 & 0x03E0);
		if (im >= j1) {
			im = im / j1;
			k += ((int) LUT_LOG_2[im - 1] & 0xFFFF);
			im = im * j1;
		} else {
			im = 0;
		}

		// use bit MSB+11 to MSB+16
		im = ((j3 & 0x3FF) - im) << 12;
		if (im >= j2) {
			i = im / j2;
			k += (i + 1) >>> 1;
		}

		return k;
	}

	/**
	 * Returns the larger of the two specified <CODE>FP int</CODE>s.
	 * <p>
	 * Maximum deviation from definition: 0 <CODE>EPSILON</CODE>.
	 * 
	 * @param fpA	The first <CODE>FP int</CODE> value.
	 * @param fpB	The second <CODE>FP int</CODE> value.
	 * @return A <CODE>FP int</CODE> containing the largest value of the two.
	 */
	public static int max(int fpA, int fpB) { 
		if (fpA >= fpB) {return fpA;} else {return fpB;} 
	}

	/**
	 * Return the smaller of the two specified <CODE>FP int</CODE>s.
	 * <p>
	 * Maximum deviation from definition: 0 <CODE>EPSILON</CODE>.
	 * 
	 * @param fpA	The first <CODE>FP int</CODE> value.
	 * @param fpB	The second <CODE>FP int</CODE> value.
	 * @return A <CODE>FP int</CODE> containing the smallest value of the two.
	 */
	public static int min(int fpA, int fpB) { 
		if (fpA < fpB) {return fpA;} else {return fpB;} 
	}
	
	/**
	 * Returns the result of the multiplication the two specified
	 * <CODE>FP int</CODE> parameters as a (rounded down) <CODE>FP int</CODE>.
	 * <p>
	 * Maximum deviation from definition: 0 <CODE>EPSILON</CODE>.
	 * 
	 * @param fpA	First <CODE>FP int</CODE> parameter.
	 * @param fpB	Second <CODE>FP int</CODE> parameter.
	 * @return A <CODE>FP int</CODE> containing the result of this calculation.
	 */
	public static int mul(int fpA, int fpB) {
		return (int) (((long) fpA * fpB) >> 16);
	}

	/**
	 * Returns the base specified by the first <CODE>FP int</CODE> parameter 
	 * raised to the power specified by the second <CODE>FP int</CODE> parameter
	 * as a <CODE>FP int</CODE>. However, all negative bases return 0.
	 * <p>
	 * Maximum deviation from definition: Depends on both parameters.
	 * 
	 * @param fpBase	The <CODE>FP int</CODE> containing the base. 
	 * @param fpPower	The <CODE>FP int</CODE> containing the power. 
	 * @return A <CODE>FP int</CODE> containing the result of this calculation.
	 */
	public static int pow(int fpBase, int fpPower) {
		if (fpBase <= 0) {return 0;}
		return exp((int)(((long)log(fpBase) * fpPower) >> 16));
	}

	/**
	 * Returns the integer closest to the specified <CODE>FP int</CODE>
	 * as a <CODE>FP int</CODE>.
	 * <p>
	 * Maximum deviation from definition: 0
	 * 
	 * @param fp	The <CODE>FP int</CODE> value.
	 * @return A <CODE>FP int</CODE> containing the result of this calculation.
	 */
	public static int round(int fp) {
		return (fp + 0x8000) & 0xFFFF0000;
	}

	/**
	 * Returns the integer closest to the specified <CODE>FP int</CODE>
	 * as a <CODE>int</CODE> integer.
	 * <p>
	 * Maximum deviation from definition: 0
	 * 
	 * @param fp	The <CODE>FP int</CODE> value.
	 * @return A <CODE>int</CODE> integer containing the result of this calculation.
	 */
	public static int roundToInt(int fp) {
		return (fp + 0x8000) >> 16;
	}
	
	/**
	 * Returns the sine of the specified angle <CODE>FP</CODE> parameter
	 * as a <CODE>FP int</CODE>.
	 * <p>
	 * Maximum deviation from definition: 2 <CODE>EPSILON</CODE>.
	 * 
	 * @param fp		The <CODE>FP int</CODE> containing the angle. This 
	 * 					should be specified in 1/65536ths of a full rotation. 
	 * @return This <CODE>FP int</CODE> containing the result of this calculation.
	 */
	public static int sin(int fp) {
		return cos(fp - 0x4000);
	}

	/**
	 * Returns the square root of the specified <CODE>FP</CODE> parameter 
	 * as a <CODE>FP int</CODE>. Negative parameters return 0.
	 * <p>
	 * Maximum deviation from definition: 1 <CODE>EPSILON</CODE>.
	 * 
	 * @param fp			The <CODE>FP int</CODE> containing the input number. 
	 * @return This <CODE>FP int</CODE> containing the result of this calculation.
	 */
	public static int sqrt(int fp) {
		if (fp <= 0) {
			return 0;
		}

		// get MSB position
		int i = fp, im, k = 0, p = -16;
		if ((i & 0xFFFF0000) > 0) {i >>>= 16; p += 16;}
		if ((i & 0x0000FF00) > 0) {i >>>= 8; p += 8;}
		if ((i & 0x000000F0) > 0) {i >>>= 4; p += 4;}
		if ((i & 0x0000000C) > 0) {i >>>= 2; p += 2;}
		if ((i & 0x00000002) > 0) {i >>>= 1; p += 1;}

		// Look up square root multipier based on bits MSB+0 to MSB+3 and
		// correct odd MSB positions using sqrt(2)
		if (p >= -11) {
			i = fp >> (11 + p);
		} else {
			i = fp << (-11 - p);
		}
		im = (i & 31) - 1;
		if (im >= 0) {
			k = ((int) LUT_SQRT[im] & 0xFFFF);
			if ((p & 1) > 0) {
				k = ((k * 92682) >>> 16);
			}
		}
		if ((p & 1) > 0) {
			k += 92682;
		} else {
			k += 0x10000;
		}

		// Shift square root estimate based on the halved MSB position
		if (p >= 0) {
			k <<= p >> 1;
		} else {
			k >>= (1 - p) >> 1;
		}

		// Do two Newtonian square root iteration steps to increase precision
		long longNum = (long) (fp) << 16;
		k += (int) (longNum / k);
		k = (k + (int) ((longNum << 2) / k) + 2) >> 2;

		return k;
	}

	/**
	 * Returns the result of the substraction of the second specified 
	 * <CODE>FP int</CODE> parameter from the first specified <CODE>FP</CODE>
	 * parameter as a <CODE>FP int</CODE>.
	 * <p>
	 * Maximum deviation from definition: 0 <CODE>EPSILON</CODE>.
	 *
	 * @param fpA	First <CODE>FP int</CODE> parameter.
	 * @param fpB	Second <CODE>FP int</CODE> parameter.
	 * @return A <CODE>FP int</CODE> containing the result of this calculation.
	 */
	public static int sub(int fpA, int fpB) {
		return fpA - fpB;
	}

	/**
	 * Returns the <CODE>FP int</CODE> representation of the integer
	 * specified by the <CODE>int</CODE> integer.
	 * <p>
	 * Maximum deviation from definition: 0 <CODE>EPSILON</CODE>.
	 * 
	 * @param integer	The integer value.
	 * @return A <CODE>FP int</CODE> containing the specified integer value.
	 */
	public static int toFP(int integer) { 
		return integer << 16;
	}

	/**
	 * Returns the largest integer number smaller than the specified
	 * <CODE>FP int</CODE> as a <CODE>int</CODE>.
	 * <p>
	 * Maximum deviation from definition: 0 <CODE>EPSILON</CODE>.
	 * 
	 * @param fp	The <CODE>FP int</CODE> value.
	 * @return An <CODE>int</CODE> containing the integer value.
	 */
	public static int toInt(int fp) { 
		return fp >> 16; 
	}

	/**
	 * Returns the <CODE>FP int</CODE> value representing the floating
	 * point number indicated by the <CODE>String</CODE> parameter.
	 * Does not support floating point E notations. 
	 * <p>
	 * Maximum deviation from definition: 0.5 <CODE>EPSILON</CODE>.
	 * 
	 * @param s		<CODE>String</CODE> containing a floating point number.
	 * @return This <CODE>FP int</CODE> containing the closest 16.16
	 * fixed point to the specified floating point number.
	 */
	public static int toFP(String s) throws NumberFormatException {
		String intString = s;
		int len = s.length();
		boolean neg = s.charAt(0) == '-';
		int num;

		int dotpos = s.indexOf('.');

		if ((dotpos != 0 && !neg) || (dotpos != 1 && neg)) {
			if (dotpos >= 0) {
				intString = intString.substring(0, dotpos);
			}
			if (neg)
				intString = intString.substring(1);

			try {
				num = Integer.parseInt(intString) << 16;
			} catch (NumberFormatException e) {
				throw new NumberFormatException("For input string: \"" + s
						+ "\"");
			}
		} else {
			num = 0;
		}

		dotpos++;
		if (dotpos < len && dotpos != 0) {
	
			if (s.charAt(dotpos) == '-') {
				throw new NumberFormatException("For input string: \"" + s + "\"");
			}
	
			int frac, endpos = dotpos + 8;
			if (endpos > len) {
				endpos = len;
			}
			try {
				frac = Integer.parseInt(s.substring(dotpos, endpos));
			} catch (NumberFormatException e) {
				throw new NumberFormatException("For input string: \"" + s + "\"");
			}
	
			int divs[] = { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000,
					100000000 };
			int div = divs[endpos - dotpos];
	
			num += (int) ((((long) frac << 16) + (div >> 1)) / div);
		}

		if (neg)
			return -num;
		else 
			return num;
	}

	/**
	 * Returns a <CODE>String</CODE> containing a floating point number
	 * representation of the specified <CODE>FP int</CODE> number.
	 * 
	 * @param fp	The <CODE>FP int</CODE> containing the input number. 
	 * @return A <CODE>String</CODE> representation of this <CODE>FP int</CODE>.
	 */
	public static String toString(int fp) {
		String str;
		int i;
		if (fp < 0) {
			i = -fp;
			str = "-";
		} else {
			i = fp;
			str = "";
		}

		str += Integer.toString(i >> 16) + ".";

		i = ((i & 0xFFFF) << 1) + 1;
		if (i == 1) {
			return str + "0";
		}

		for (int j = 0; (i = (i & 0x1FFFF) * 10) > 0 && j < 5; j++) {
			str += (i >> 17);
		}

		return str;
	}
	
	private FPMath() {}

	private static final short LUT_ATAN_1[] = {
	    2558, 6941, -25147, -28189, 26543, 17079, 10588, 6474, 3938, 2391, 
	    1451, 880, 534, 324, 196, 119, 72, 44, 27, 16, 10, 6};
	private static final short LUT_ATAN_2[] = {
	    0, 10323, 20042, -29415, -18319, -11225, -6834, -4151, -2519, -1528, 
	    -927, -562, -341, -207, -125, -76, -46, -28, -17, -10, -6, -4, -2};
	private static final short LUT_COS_1[] = {
	    -20185, -19990, -19603, -19028, -18269, -17334, -16232, -14974, -13571, 
	    -12038, -10389, -8640, -6808, -4910, -2965, -992};
	private static final short LUT_COS_2[] = {
	    -1, -317, -1260, -2823, -4990, -7739, -11046, -14877, -19196, -23961, 
	    -29127, 30892, 25079, 19023, 12784, 6423};
	private static final int LUT_EXP_1[] = {
	    65536, 108051, 178145, 293712, 484249, 798392, 1316326, 2170254, 
	    3578144, 5899363, 9726405, 16036130, 26439109, 43590722, 71868951, 
	    118491868, 195360063, 322094291, 531043708, 875543058, 1443526462, 
	    2147483647, 2147483647, 2147483647};
	private static final short LUT_EXP_2[] = {
	    -1016, -2016, -3001, -3971, -4925, -5865, -6790, -7701, -8597, -9480, 
	    -10349, -11205, -12047, -12876, -13693, -14497, -15288, -16067, -16834, 
	    -17589, -18332, -19064, -19784, -20494, -21192, -21880, -22556, -23223, 
	    -23879, -24525, -25160};
	private static final short LUT_EXP_3[] = {
	    -32, -64, -96, -128, -160, -192, -224, -256, -287, -319, -351, -383, 
	    -415, -446, -478, -510, -542, -573, -605, -637, -669, -700, -732, -764, 
	    -795, -827, -858, -890, -921, -953, -985};
	private static final short LUT_LOG_1[] = {
	    2017, 3973, 5873, 7719, 9515, 11262, 12965, 14624, 16242, 17821, 19364, 
	    20870, 22343, 23783, 25193, 26573, 27924, 29248, 30546, 31818, -32469, 
	    -31244, -30042, -28861, -27701, -26561, -25441, -24340, -23256, -22191, 
	    -21142};
	private static final short LUT_LOG_2[] = {
	    64, 128, 192, 256, 319, 383, 446, 510, 573, 637, 700, 764, 827, 890, 
	    953, 1016, 1079, 1142, 1205, 1268, 1330, 1393, 1456, 1518, 1581, 1643, 
	    1706, 1768, 1830, 1892, 1955};
	private static final short LUT_SQRT[] = {
	    1016, 2017, 3003, 3975, 4934, 5880, 6814, 7735, 8646, 9545, 10433, 
	    11312, 12180, 13039, 13888, 14729, 15561, 16384, 17199, 18006, 18806, 
	    19598, 20382, 21160, 21931, 22695, 23452, 24203, 24948, 25686, 26419};
}