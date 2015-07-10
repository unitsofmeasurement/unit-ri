/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright (c) 2005-2015, Jean-Marie Dautelle, Werner Keil, V2COM.
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

package tec.units.ri.internal.format.l10n;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Java ME compliant backport of {@linkplain java.text.NumberFormat}
 * <code>NumberFormat</code> has features designed to make it possible to format
 * numbers in any locale. It also supports different kinds of numbers, including
 * integers (123), fixed-point numbers (123.4), percentages (12%), and currency
 * amounts ($123). All of these can be localized.
 *
 * Usage:
 * 
 * <pre>
 * NumberFormat f = NumberFormat.getInstance();
 * StringBuffer sb = f.format(new Double(123.45), new StringBuffer());
 * </pre>
 * <p>
 *
 * Or eventually it's possible to change number of decimals displayed
 * 
 * <pre>
 * NumberFormat f = NumberFormat.getInstance();
 * f.setMaximumFractionDigits(2);
 * StringBuffer sb = f.format(new Double(123.45559), new StringBuffer());
 * </pre>
 * @version $Revision: 0.3 $
 * @author <a href="mailto:jasone@greenrivercomputing.com">Jason Essington</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 */
public class NumberFormat {
	private static final Logger logging = Logger.getLogger(NumberFormat.class
			.getName());
	
	private static final String COMMA = ",";
	private static final String PERIOD = ".";
	private static final char DASH = '-';
	private static final char LEFT_PAREN = '(';
	private static final char RIGHT_PAREN = ')';

	// k/m/b Shortcuts
	private static final String THOUSAND = "k";
	private static final String MILLION = "m";
	private static final String BILLION = "b";

	// currency position constants
	private static final int CUR_POS_LEFT_OUTSIDE = 0;
	private static final int CUR_POS_LEFT_INSIDE = 1;
	private static final int CUR_POS_RIGHT_INSIDE = 2;
	private static final int CUR_POS_RIGHT_OUTSIDE = 3;

	// negative format constants
	private static final int NEG_LEFT_DASH = 0;
	private static final int NEG_RIGHT_DASH = 1;
	private static final int NEG_PARENTHESIS = 2;

	// constant to signal that fixed precision is not to be used
	private static final int ARBITRARY_PRECISION = -1;

	private String inputDecimalSeparator = PERIOD; // decimal character used on
													// the original string

	private boolean showGrouping = true;
	private String groupingSeparator = COMMA; // thousands grouping character
	private String decimalSeparator = PERIOD; // decimal point character

	private boolean showCurrencySymbol = false;
	private String currencySymbol = "$";
	private int currencySymbolPosition = CUR_POS_LEFT_OUTSIDE;

	private int negativeFormat = NEG_LEFT_DASH;
	private boolean isNegativeRed = false; // wrap the output in html that will
											// display red?

	private int decimalPrecision = 0;
	private boolean useFixedPrecision = false;
	private boolean truncate = false; // truncate to decimalPrecision rather
										// than rounding?

	private boolean isPercentage = false; // should the result be displayed as a
											// percentage?
	/**
	 * Class name.
	 */
	private static final String classname = NumberFormat.class.getName();
	/**
	 * Upper limit on integer digits for a Java double.
	 */
	private final static int DOUBLE_INTEGER_DIGITS = 309;
	/**
	 * Upper limit on fraction digits for a Java double.
	 */
	private final static int DOUBLE_FRACTION_DIGITS = 340;
	/**
	 * Non localized percent sign.
	 */
	public final static char NONLOCALIZED_PERCENT_SIGN = '\u0025';

	/**
	 * Unicode INFINITY character.
	 */
	public final static char UNICODE_INFINITY = '\u221e';

	/**
	 * Styles of formatting j2se compatible.
	 */

	/**
	 * General number.
	 */
	public final static int NUMBERSTYLE = 0;
	/**
	 * Currency style.
	 */
	public final static int CURRENCYSTYLE = 1;
	/**
	 * Percent style.
	 */
	public final static int PERCENTSTYLE = 2;
	/**
	 * Integer style.
	 */
	public final static int INTEGERSTYLE = 3;

	private static final int STATUS_INFINITE = 0;
	private static final int STATUS_POSITIVE = 1;
	private static final int STATUS_LENGTH = 2;

	/**
	 * Holds initialized instance of DecimalFormatSymbols which encapsulate
	 * locale dependent informations like currency symbol, percent symbol etc.
	 */
	private NumberFormatSymbols symbols;

	/**
	 * Is this <code>NumberFormat</code> instance for currency formatting?
	 */
	private boolean isCurrencyFormat = false;

	/**
	 * Is this <code>NumberFormat</code> instance of percentage formatting?
	 */
	private boolean isPercentageFormat = false;

	/**
	 * Digit list does most of formatting work.
	 */
	private DigitList digitList = new DigitList();

	/**
	 * Style of <code>NumberFormat</code>. Possible styles are:
	 * <ul>
	 * <li> {@link #NUMBERSTYLE}
	 * <li> {@link #CURRENCYSTYLE}
	 * <li> {@link #PERCENTSTYLE}
	 * <li> {@link #INTEGERSTYLE}
	 * </ul>
	 */
	private int style = NUMBERSTYLE;

	/**
	 * Create <code>NumberFormat</code> with given number pattern and set of
	 * locale numeric symbols.
	 *
	 * @param style
	 *            the style of <code>NumberFormat</code>
	 *            <ul>
	 *            <li> {@link #NUMBERSTYLE}
	 *            <li> {@link #CURRENCYSTYLE}
	 *            <li> {@link #PERCENTSTYLE}
	 *            <li> {@link #INTEGERSTYLE}
	 *            </ul>
	 *
	 * @param symbols
	 *            NumberFormatSymbols identifying numbers formatting for given
	 *            locale.
	 */
	public NumberFormat(int style, NumberFormatSymbols symbols) {
		this.style = style;
		this.symbols = symbols;
		isCurrencyFormat = (style == CURRENCYSTYLE);
		isPercentageFormat = (style == PERCENTSTYLE);
		applySymbols();
		if (logging.getLevel().intValue() <= Level.INFO.intValue()) {
			logging.log(Level.INFO, // LogChannels.LC_JSR238,
					classname + ": " + "NumberFormat created\n" + "style is "
							+ style + "\n" + "symbols is " + symbols);
		}
	}

	/**
	 * Returns a general-purpose number format.
	 *
	 * @return the {@code NumberFormat} instance for general-purpose number
	 *         formatting
	 */
	public static final NumberFormat getInstance() {
		return new NumberFormat(NUMBERSTYLE, new NumberFormatSymbols());
	}

	/**
	 * Set maximal number of decimals to be displayed.
	 *
	 * @param count
	 *            number of decimals to display
	 * @see #getMaximumFractionDigits
	 */
	public void setMaximumFractionDigits(int count) {
		if (symbols != null && count <= DOUBLE_FRACTION_DIGITS && count >= 0
				&& style != INTEGERSTYLE) {
			symbols.maximumFractionDigits[style] = count;
			if (symbols.minimumFractionDigits[style] < count) {
				symbols.minimumFractionDigits[style] = count;
			}
		}
	}

	/**
	 * How many decimals is used to display number.
	 *
	 * @return maximum number of decimals or <code>-1</code> if non-localized
	 *         formatting is used.
	 * @see #setMaximumFractionDigits
	 */
	public int getMaximumFractionDigits() {
		return (symbols != null) ? symbols.maximumFractionDigits[style] : -1;
	}

	/**
	 * Sets minimum number of decimals to be displayed.
	 *
	 * @param count
	 *            minimum number of decimals to display
	 * @see #getMinimumFractionDigits
	 */
	public void setMinimumFractionDigits(int count) {
		if (symbols != null && count >= 0 && style != INTEGERSTYLE) {
			symbols.minimumFractionDigits[style] = count;
			if (count > symbols.maximumFractionDigits[style]) {
				symbols.maximumFractionDigits[style] = count;
			}
		}
	}

	/**
	 * Sets multiplier to different value than symbols for this locale do.
	 *
	 * @param multiplier
	 *            new value for multiplier;
	 * @see #getMultiplier
	 */
	public void setMultiplier(int multiplier) {
		if (symbols != null) {
			symbols.multiplier[style] = multiplier;
		}
	}

	/**
	 * Gets actual multilier used by this locale for this number style. Usually
	 * (1 or 100).
	 *
	 * @return the multiplier
	 * @see #setMultiplier
	 */
	public int getMultiplier() {
		return (symbols != null) ? symbols.multiplier[style] : 1;
	}

	/**
	 * Sets if grouping is used.
	 *
	 * @param used
	 *            <code>true</code> if grouping should be used
	 */
	public void setGroupingUsed(boolean used) {
		if (symbols != null) {
			symbols.groupingUsed = used;
		}
	}

	/**
	 * Get minimum of decimals used to display number.
	 *
	 * @return minimum number of decimals or <code>-1</code> if non-localized
	 *         formatting is used.
	 * @see #setMinimumFractionDigits
	 */
	public int getMinimumFractionDigits() {
		return (symbols != null) ? symbols.minimumFractionDigits[style] : -1;
	}

	/**
	 * Sets minimum integer digits.
	 *
	 * @param count
	 *            the count of digits
	 * @see #getMinimumIntegerDigits
	 */
	public void setMinimumIntegerDigits(int count) {
		if (symbols != null && count > 0) {
			symbols.minimumIntegerDigits[style] = count;
		}
	}

	/**
	 * Gets minimum integer digits.
	 *
	 * @return number minimum of integer digits
	 * @see #setMinimumIntegerDigits
	 */
	public int getMinimumIntegerDigits() {
		return (symbols != null) ? symbols.minimumIntegerDigits[style] : -1;
	}

	/**
	 * Sets currency symbol.
	 *
	 * @param symbol
	 *            the currency symbol
	 * @return previously used currency symbol
	 */
	public String setCurrencySymbol(String symbol) {
		String oldsymbol = null;
		if (isCurrencyFormat) {
			if (symbols != null) {
				oldsymbol = symbols.currencySymbol;
				if (!symbol.equals(symbols.currencySymbol)) {
					symbols.currencySymbol = symbol;
					symbols.suffixes[style] = replSubStr(
							symbols.suffixes[style], oldsymbol, symbol);
					symbols.prefixes[style] = replSubStr(
							symbols.prefixes[style], oldsymbol, symbol);
					symbols.negativeSuffix[style] = replSubStr(
							symbols.negativeSuffix[style], oldsymbol, symbol);
					symbols.negativePrefix[style] = replSubStr(
							symbols.negativePrefix[style], oldsymbol, symbol);
					symbols.positiveSuffix[style] = replSubStr(
							symbols.positiveSuffix[style], oldsymbol, symbol);
					symbols.positivePrefix[style] = replSubStr(
							symbols.positivePrefix[style], oldsymbol, symbol);
				}
			}
		}
		return oldsymbol;
	}

	/**
	 * Replaces substring in the string onto new string.
	 *
	 * @param str
	 *            the changed string
	 * @param oldVal
	 *            the replaced substring
	 * @param newVal
	 *            the replacing string
	 * @return changed string
	 */
	private String replSubStr(String str, String oldVal, String newVal) {
		String res = str;
		if (str.length() > 0) {
			int pos = str.indexOf(oldVal);
			if (pos >= 0) {
				res = str.substring(0, pos);
				res = res.concat(newVal);
				res = res.concat(str.substring(pos + oldVal.length()));
				return res;
			}
		}
		return res;
	}

	/**
	 * Lookup table of supported currencies for appropriate symbol.
	 * 
	 * @param currencyCode
	 *            code ISO 4217.
	 * @return currency symbol or <code>null</code> if none was found.
	 */
	public String getCurrencySymbolForCode(String currencyCode) {
		if (symbols != null && symbols.currencies != null) {
			for (int i = 0; i < symbols.currencies.length; i++) {
				if (symbols.currencies[i].length > 0
						&& symbols.currencies[i][0].equals(currencyCode))
					if (symbols.currencies[i].length > 1) {
						return symbols.currencies[i][1];
					} else {
						return null;
					}
			}
		}
		return null;
	}

	/**
	 * Check if some attributes of <code>NumberFormatSymbols</code> are
	 * undefined and replace them with default values.
	 */
	private void applySymbols() {
		if (symbols != null) {
			if (symbols.maximumIntegerDigits[style] == -1) {
				symbols.maximumIntegerDigits[style] = DOUBLE_INTEGER_DIGITS;
			}
			if (symbols.maximumFractionDigits[style] == -1) {
				symbols.maximumFractionDigits[style] = DOUBLE_FRACTION_DIGITS;
			}
		}
	}

	/**
	 * Method formats long.
	 *
	 * @param value
	 *            long number to format
	 * @return formatted long number
	 */
	public String format(long value) {
		return format(new Long(value));
	}

	/**
	 * Method formats double.
	 *
	 * @param value
	 *            double value to format
	 * @return formatted double number
	 */
	public String format(double value) {
		if (symbols != null) {
			if (Double.isNaN(value)) {
				return symbols.NaN;
			}
			if (Double.isInfinite(value)) {
				String prefix = (value > 0.0) ? ""
						: symbols.negativePrefix[style];
				String suffix = (value > 0.0) ? ""
						: symbols.negativeSuffix[style];
				return prefix + symbols.infinity + suffix;
			}
		} else {
			if (Double.isNaN(value)) {
				return "NaN";
			}
			if (Double.isInfinite(value)) {
				String prefix = (value > 0.0) ? "" : "-";
				return prefix + UNICODE_INFINITY;
			}
		}
		return format(new Double(value));
	}

	/**
	 * Method formats integer.
	 *
	 * @param value
	 *            integer value to format
	 * @return formatted integer number
	 */
	public String format(int value) {
		return format(new Long(value));
	}

	/**
	 * Method formats float.
	 *
	 * @param value
	 *            float value to format
	 * @return formatted float number
	 */
	public String format(float value) {
		return format((double) value);
	}

	/**
	 * Does formatting. Result is appended to parameter
	 * <code>StringBuffer appendTo</code>.
	 *
	 * @param o
	 *            object to format
	 * @return buffer with appended formatted text
	 */
	public String format(Object o) {
		StringBuffer appendTo = new StringBuffer();
		if (o == null) {
			return "";
		}
		if (symbols != null) {
			if (o instanceof Double) {
				format(((Double) o).doubleValue(), appendTo);
			}
			if (o instanceof Long) {
				format(((Long) o).longValue(), appendTo);
			}
		} else {
			if (isPercentageFormat) {
				if (o instanceof Double) {
					appendTo.append(Double.toString(((Double) o).doubleValue() * 100.0));
				} else if (o instanceof Long) {
					long value = ((Long) o).longValue();
					appendTo.append(Long.toString(value));
					if (value != 0)
						appendTo.append("00");
				}
				appendTo.append(NONLOCALIZED_PERCENT_SIGN);
			} else {
				return o.toString();
			}
		}
		return appendTo.toString();
	}

	/**
	 * Formats double number.
	 *
	 * @param number
	 *            the double number to formatt
	 * @param result
	 *            formatted number
	 * @return buffer with appended formatted number
	 */
	private StringBuffer format(double number, StringBuffer result) {
		if (Double.isNaN(number)) {
			result.append(symbols.NaN);
			return result;
		}
		boolean isNegative = (number < 0.0)
				|| (number == 0.0 && 1 / number < 0.0);
		if (isNegative) {
			number = -number;
		}

		if (symbols.multiplier[style] != 1) {
			number *= symbols.multiplier[style];
		}

		if (Double.isInfinite(number)) {
			if (isNegative) {
				result.append(symbols.negativePrefix[style]);
			} else {
				result.append(symbols.positivePrefix[style]);
			}
			result.append(symbols.infinity);

			if (isNegative) {
				result.append(symbols.negativeSuffix[style]);
			} else {
				result.append(symbols.positiveSuffix[style]);
			}
			return result;
		}

		digitList.set(number, symbols.maximumFractionDigits[style]);
		result = subformat(result, isNegative, false);

		return result;
	}

	/**
	 * Format a long to produce a string.
	 *
	 * @param number
	 *            The long to format
	 * @param result
	 *            where the text is to be appended
	 * @return The formatted number
	 */
	private StringBuffer format(long number, StringBuffer result) {
		boolean isNegative = (number < 0);
		if (isNegative) {
			number = -number;
		}

		if (symbols.multiplier[style] != 1 && symbols.multiplier[style] != 0) {
			boolean useDouble = false;

			if (number < 0) {
				// This can only happen if number == Long.MIN_VALUE

				long cutoff = Long.MIN_VALUE / symbols.multiplier[style];
				useDouble = (number < cutoff);
			} else {
				long cutoff = Long.MAX_VALUE / symbols.multiplier[style];
				useDouble = (number > cutoff);
			}

			if (useDouble) {
				double dnumber = (double) (isNegative ? -number : number);
				return format(dnumber, result);
			}
		}

		number *= symbols.multiplier[style];
		synchronized (digitList) {
			digitList.set(number, 0);

			return subformat(result, isNegative, true);
		}
	}

	/**
	 * Formats content of DigitList.
	 *
	 * @param result
	 *            buffer to append formatted number to
	 * @param isNegative
	 *            <code>true</code> if number is negative
	 * @param isInteger
	 *            <code>true</code> if integer number will be formatted
	 * @return buffer with appended formatted number
	 */
	private StringBuffer subformat(StringBuffer result, boolean isNegative,
			boolean isInteger) {

		char zero = symbols.zeroDigit;
		int zeroDelta = zero - '0';
		// '0' is the DigitList representation of zero
		char grouping = symbols.groupingSeparator;
		char decimal = isCurrencyFormat ? symbols.monetarySeparator
				: symbols.decimalSeparator;

		if (digitList.isZero()) {
			digitList.decimalAt = 0;
			// Normalize
		}

		int fieldStart = result.length();

		if (isNegative) {
			result.append(symbols.negativePrefix[style]);
		} else {
			result.append(symbols.positivePrefix[style]);
		}

		String prefix = symbols.prefixes[style];
		result.append(prefix);

		int count = symbols.minimumIntegerDigits[style];
		int digitIndex = 0;
		// Index into digitList.fDigits[]
		if (digitList.decimalAt > 0 && count < digitList.decimalAt) {
			count = digitList.decimalAt;
		}

		if (count > symbols.maximumIntegerDigits[style]) {
			count = symbols.maximumIntegerDigits[style];
			digitIndex = digitList.decimalAt - count;
		}

		if (logging.getLevel().intValue() <= Level.INFO.intValue()) {
			logging.log(Level.INFO, // LogChannels.LC_JSR238,
					classname + " :" + "grouping used " + symbols.groupingUsed
							+ "\n" + "grouping separator \"" + grouping
							+ "\"\n" + "decimal separator \"" + decimal
							+ "\"\n" + "digit count " + count);
		}

		int sizeBeforeIntegerPart = result.length();
		for (int i = count - 1; i >= 0; --i) {
			if (i < digitList.decimalAt && digitIndex < digitList.count) {
				// Output a real digit
				result.append((char) (digitList.digits[digitIndex++] + zeroDelta));
			} else {
				// Output a leading zero
				result.append(zero);
			}

			// Output grouping separator if necessary. Don't output a
			// grouping separator if i==0 though; that's at the end of
			// the integer part.
			if (symbols.groupingUsed && i > 0 && (symbols.groupingCount != 0)
					&& (i % symbols.groupingCount == 0)) {
				int gStart = result.length();
				result.append(grouping);
				if (logging.getLevel().intValue() <= Level.INFO.intValue()) {
					logging.log(Level.INFO, // LogChannels.LC_JSR238,
							classname + ": " + "add grouping at "
									+ (digitIndex - 1));
				}
			}
		}// for

		boolean fractionPresent = (symbols.minimumFractionDigits[style] > 0)
				|| (!isInteger && digitIndex < digitList.count);

		if (!fractionPresent && result.length() == sizeBeforeIntegerPart) {
			result.append(zero);
		}
		// Output the decimal separator if we always do so.
		int sStart = result.length();
		if (symbols.decimalSeparatorAlwaysShown || fractionPresent) {
			result.append(decimal);
		}

		for (int i = 0; i < symbols.maximumFractionDigits[style]; ++i) {
			if (i >= symbols.minimumFractionDigits[style]
					&& (isInteger || digitIndex >= digitList.count)) {
				break;
			}

			if (-1 - i > (digitList.decimalAt - 1)) {
				result.append(zero);
				continue;
			}

			if (!isInteger && digitIndex < digitList.count) {
				result.append((char) (digitList.digits[digitIndex++] + zeroDelta));
			} else {
				result.append(zero);
			}
		}

		String suffix = symbols.suffixes[style];
		result.append(suffix);

		if (isNegative) {
			result.append(symbols.negativeSuffix[style]);
		} else {
			result.append(symbols.positiveSuffix[style]);
		}

		return result;
	}
	
	private static double subparse(String val, String inputDecimalValue) {
		String newVal = val;
		boolean isPercentage = false;
		// remove % if there is one
		if (newVal.indexOf('%') != -1) {
			newVal = newVal.replaceAll("\\%", "");
			isPercentage = true;
		}

		// convert abbreviations for thousand, million and billion
		newVal = newVal.toLowerCase().replaceAll(BILLION, "000000000");
		newVal = newVal.replaceAll(MILLION, "000000");
		newVal = newVal.replaceAll(THOUSAND, "000");

		// remove any characters that are not digit, decimal separator, +, -, (,
		// ), e, or E
		String re = "[^\\" + inputDecimalValue + "\\d\\-\\+\\(\\)eE]";
		newVal = newVal.replaceAll(re, "");

		// ensure that the first decimal separator is a . and remove the rest.
		int index = newVal.indexOf(inputDecimalValue);
		if (index != -1) {
			newVal = newVal.substring(0, index)
					+ PERIOD
					+ (newVal.substring(index + inputDecimalValue.length())
							.replaceAll("\\" + inputDecimalValue, ""));
		}

		// convert right dash and paren negatives to left dash negative
		if (newVal.charAt(newVal.length() - 1) == DASH) {
			newVal = newVal.substring(0, newVal.length() - 1);
			newVal = DASH + newVal;
		} else if (newVal.charAt(0) == LEFT_PAREN
				&& newVal.charAt(newVal.length() - 1) == RIGHT_PAREN) {
			newVal = newVal.substring(1, newVal.length() - 1);
			newVal = DASH + newVal;
		}

		Double parsed;
		try {
			parsed = new Double(newVal);
			if (parsed.isInfinite() || parsed.isNaN())
				parsed = new Double(0);
		} catch (NumberFormatException e) {
			parsed = new Double(0);
		}

		return isPercentage ? parsed.doubleValue() / 100 : parsed.doubleValue();
	}

	/**
	 * Parses text from a string to produce a <code>Number</code>.
	 * <p>
	 * The method attempts to parse text starting at the index given by
	 * <code>pos</code>. If parsing succeeds, then the index of <code>pos</code>
	 * is updated to the index after the last character used (parsing does not
	 * necessarily use all characters up to the end of the string), and the
	 * parsed number is returned. The updated <code>pos</code> can be used to
	 * indicate the starting point for the next call to this method. If an error
	 * occurs, then the index of <code>pos</code> is not changed, the error
	 * index of <code>pos</code> is set to the index of the character where the
	 * error occurred, and null is returned.
	 * <p>
	 * The subclass returned depends on the value of {@link #isParseBigDecimal}
	 * as well as on the string being parsed.
	 * <ul>
	 * <li>If <code>isParseBigDecimal()</code> is false (the default), most
	 * integer values are returned as <code>Long</code> objects, no matter how
	 * they are written: <code>"17"</code> and <code>"17.000"</code> both parse
	 * to <code>Long(17)</code>. Values that cannot fit into a <code>Long</code>
	 * are returned as <code>Double</code>s. This includes values with a
	 * fractional part, infinite values, <code>NaN</code>, and the value -0.0.
	 * <code>DecimalFormat</code> does <em>not</em> decide whether to return a
	 * <code>Double</code> or a <code>Long</code> based on the presence of a
	 * decimal separator in the source string. Doing so would prevent integers
	 * that overflow the mantissa of a double, such as
	 * <code>"-9,223,372,036,854,775,808.00"</code>, from being parsed
	 * accurately.
	 * <p>
	 * Callers may use the <code>Number</code> methods <code>doubleValue</code>,
	 * <code>longValue</code>, etc., to obtain the type they want.
	 * <li>If <code>isParseBigDecimal()</code> is true, values are returned as
	 * <code>BigDecimal</code> objects. The values are the ones constructed by
	 * {@link java.math.BigDecimal#BigDecimal(String)} for corresponding strings
	 * in locale-independent format. The special cases negative and positive
	 * infinity and NaN are returned as <code>Double</code> instances holding
	 * the values of the corresponding <code>Double</code> constants.
	 * </ul>
	 * <p>
	 * <code>DecimalFormat</code> parses all Unicode characters that represent
	 * decimal digits, as defined by <code>Character.digit()</code>. In
	 * addition, <code>DecimalFormat</code> also recognizes as digits the ten
	 * consecutive characters starting with the localized zero digit defined in
	 * the <code>DecimalFormatSymbols</code> object.
	 *
	 * @param text
	 *            the string to be parsed
	 * @return the parsed value, or <code>null</code> if the parse fails
	 * @exception NullPointerException
	 *                if <code>text</code> or <code>pos</code> is null.
	 */
	public double parse(String text) {
		return subparse(text, inputDecimalSeparator);
	}

	/**
	 * Static routine that attempts to create a double out of the supplied text.
	 * This routine is a bit smarter than Double.parseDouble()
	 * 
	 * @param num
	 * @return
	 */
	public static double parseDouble(String num, String decimalChar) {
		return subparse(num, decimalChar);
	}

	public static double parseDouble(String num) {
		return parseDouble(num, PERIOD);
	}
}
