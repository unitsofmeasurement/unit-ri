package tec.units.ri.format.internal.l10n;

/*
 *   
 *
 * Copyright  1990-2008 Sun Microsystems, Inc. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version
 * 2 only, as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details (a copy is
 * included at /legal/license.txt).
 * 
 * You should have received a copy of the GNU General Public License
 * version 2 along with this work; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA
 * 
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa
 * Clara, CA 95054 or visit www.sun.com if you need additional
 * information or have any questions.
 */

import java.text.DigitList;
import java.text.ParsePosition;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <code>NumberFormat</code> has features designed to make it possible to
 * format numbers in any locale. It also supports different kinds of numbers,
 * including integers (123), fixed-point numbers (123.4), percentages (12%),
 * and currency amounts ($123). All of these can be localized. <p>
 *
 * To obtain a <code>NumberFormat</code> for a specific locale call one of
 * <code>NumberFormat</code>'s factory methods, such as:
 * <ul>
 *    <li> <code>getPercentageInstance(locale)</code>
 *    <li> <code>getIntegerInstance(locale)</code>
 *    <li> <code>getCurrencyInstance(locale)</code>
 *    <li> <code>getDecimalInstance(local)</code>
 *  </ul>
 *  <p>
 *
 * Usage: <pre>
 * NumberFormat f = NumberFormat.getCurrencyInstance(loc);
 * StringBuffer sb = f.format(new Double(123.45), new StringBuffer());
 * </pre> <p>
 *
 * Or eventualy it's possible to change number of decimals displayed <pre>
 * NumberFormat f = NumberFormat.getCurrencyInstance(loc);
 * f.setMaximumFractionDigits(2);
 * StringBuffer sb = f.format(new Double(123.45559), new StringBuffer());
 * </pre>
 *
 */
public class NumberFormat {
	private static final Logger logging = Logger.getLogger(NumberFormat.class.getName());
	
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
    private static final int STATUS_LENGTH   = 2;


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
     *   <li> {@link #NUMBERSTYLE}
     *   <li> {@link #CURRENCYSTYLE}
     *   <li> {@link #PERCENTSTYLE}
     *   <li> {@link #INTEGERSTYLE}
     * </ul>
     */
    private int style = NUMBERSTYLE;


    /**
     * Create <code>NumberFormat</code> with given number pattern and set of
     * locale numeric symbols.
     *
     * @param  style    the style of <code>NumberFormat</code>
     *      <ul>
     *        <li> {@link #NUMBERSTYLE}
     *        <li> {@link #CURRENCYSTYLE}
     *        <li> {@link #PERCENTSTYLE}
     *        <li> {@link #INTEGERSTYLE}
     *      </ul>
     *
     * @param  symbols  NumberFormatSymbols identifying numbers formatting for
     *      given locale.
     */
    public NumberFormat(int style, NumberFormatSymbols symbols) {
        this.style = style;
        this.symbols = symbols;
        isCurrencyFormat = (style == CURRENCYSTYLE);
        isPercentageFormat = (style == PERCENTSTYLE);
        applySymbols();
        if (logging.getLevel().intValue() <= Level.INFO.intValue()) {
            logging.log(Level.INFO, //LogChannels.LC_JSR238,
                           classname + ": " +
                           "NumberFormat created\n" +
                           "style is " + style + "\n" +
                           "symbols is " + symbols);
        }
    }

    /**
     * Returns a general-purpose number format for the current default
     * {@link java.util.Locale.Category#FORMAT FORMAT} locale.
     *
     * @return the {@code NumberFormat} instance for general-purpose number
     * formatting
     */
    public static final NumberFormat getInstance() {
    	return new NumberFormat(NUMBERSTYLE, new NumberFormatSymbols());
    }

    /**
     * Set maximal number of decimals to be displayed.
     *
     * @param  count  number of decimals to display
     * @see #getMaximumFractionDigits
     */
    public void setMaximumFractionDigits(int count) {
        if (symbols != null &&
                count <= DOUBLE_FRACTION_DIGITS &&
            count >= 0 &&
                style != INTEGERSTYLE) {
            symbols.maximumFractionDigits[style] = count;
            if (symbols.minimumFractionDigits[style] < count) {
                symbols.minimumFractionDigits[style] = count;
            }
        }
    }


    /**
     * How many decimals is used to display number.
     *
     * @return    maximum number of decimals or <code>-1</code> if non-localized
     *      formatting is used.
     * @see #setMaximumFractionDigits
     */
    public int getMaximumFractionDigits() {
        return (symbols != null) ?
                symbols.maximumFractionDigits[style] :
                -1;
    }


    /**
     * Sets minimum number of decimals to be displayed.
     *
     * @param  count  minimum number of decimals to display
     * @see #getMinimumFractionDigits
     */
    public void setMinimumFractionDigits(int count) {
        if (symbols != null &&
                count >= 0 &&
                style != INTEGERSTYLE) {
            symbols.minimumFractionDigits[style] = count;
            if (count > symbols.maximumFractionDigits[style]) {
                symbols.maximumFractionDigits[style] = count;
            }
        }
    }


    /**
     * Sets multiplier to different value than symbols for this locale do.
     *
     * @param  multiplier  new value for multiplier;
     * @see #getMultiplier
     */
    public void setMultiplier(int multiplier) {
        if (symbols != null) {
            symbols.multiplier[style] = multiplier;
        }
    }


    /**
     * Gets actual multilier used by this locale for this number style. Usually
     *  (1 or 100).
     *
     * @return    the multiplier
     * @see #setMultiplier
     */
    public int getMultiplier() {
        return (symbols != null) ? symbols.multiplier[style] : 1;
    }


    /**
     * Sets if grouping is used.
     *
     * @param  used <code>true</code> if grouping should be used
     */
    public void setGroupingUsed(boolean used) {
        if (symbols != null) {
            symbols.groupingUsed = used;
        }
    }


    /**
     * Get minimum of decimals used to display number.
     *
     * @return    minimum number of decimals or <code>-1</code> if non-localized
     *      formatting is used.
     * @see #setMinimumFractionDigits
     */
    public int getMinimumFractionDigits() {
        return (symbols != null) ?
                symbols.minimumFractionDigits[style] :
                -1;
    }


    /**
     * Sets minimum integer digits.
     *
     * @param  count the count of digits
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
        return (symbols != null) ?
                symbols.minimumIntegerDigits[style] :
                -1;
    }

    /**
     * Sets currency symbol.
     *
     * @param symbol the currency symbol
     * @return previously used currency symbol
     */
    public String setCurrencySymbol(String symbol) {
    	String oldsymbol = null;
        if (isCurrencyFormat) {
            if (symbols != null) {
            	oldsymbol = symbols.currencySymbol;
                if (!symbol.equals(symbols.currencySymbol)) {
                    symbols.currencySymbol = symbol;
                    symbols.suffixes[style] =
                            replSubStr(symbols.suffixes[style], oldsymbol,
                                       symbol);
                    symbols.prefixes[style] =
                            replSubStr(symbols.prefixes[style], oldsymbol,
                                       symbol);
                    symbols.negativeSuffix[style] =
                            replSubStr(symbols.negativeSuffix[style], oldsymbol,
                                       symbol);
                    symbols.negativePrefix[style] =
                            replSubStr(symbols.negativePrefix[style], oldsymbol,
                                       symbol);
                    symbols.positiveSuffix[style] =
                            replSubStr(symbols.positiveSuffix[style], oldsymbol,
                                       symbol);
                    symbols.positivePrefix[style] =
                            replSubStr(symbols.positivePrefix[style], oldsymbol,
                                       symbol);
                }
            }
        }
        return oldsymbol;
    }
    /**
     * Replaces substring in the string onto new string.
     *
     * @param str the changed string
     * @param oldVal the replaced substring
     * @param newVal the replacing string
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
     * @param currencyCode code ISO 4217. 
     * @return currency symbol or <code>null</code> if none was found.
     */
    public String getCurrencySymbolForCode(String currencyCode) {
    	if (symbols != null && symbols.currencies != null){
	        for (int i = 0; i < symbols.currencies.length; i++) {
	            if (symbols.currencies[i].length>0 && symbols.currencies[i][0].equals(currencyCode))
	                if (symbols.currencies[i].length>1){ 
	                	return  symbols.currencies[i][1];
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
     * @param  value  long number to format
     * @return        formatted long number
     */
    public String format(long value) {
        return format(new Long(value));
    }


    /**
     * Method formats double.
     *
     * @param  value  double value to format
     * @return        formatted double number
     */
    public String format(double value) {
        if (symbols != null) {
            if (Double.isNaN(value)) {
                return symbols.NaN;
            }
            if (Double.isInfinite(value)) {
                String prefix = (value > 0.0) ? "" : symbols.negativePrefix[style];
                String suffix = (value > 0.0) ? "" : symbols.negativeSuffix[style];
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
     * @param  value  integer value to format
     * @return        formatted integer number
     */
    public String format(int value) {
        return format(new Long(value));
    }


    /**
     * Method formats float.
     *
     * @param  value  float value to format
     * @return        formatted float number
     */
    public String format(float value) {
        return format((double)value);
    }


    /**
     * Does formatting. Result is appended to parameter
     * <code>StringBuffer appendTo</code>.
     *
     * @param  o         object to format
     * @return           buffer with appended formatted text
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
                    appendTo.append(Double.toString(
                                    ((Double)o).doubleValue() * 100.0));
                } else if (o instanceof Long) {
                    long value = ((Long) o).longValue();
                    appendTo.append(Long.toString(value));
                    if (value != 0) appendTo.append("00");
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
     * @param  number  the double number to formatt
     * @param  result  formatted number
     * @return         buffer with appended formatted number
     */
    private StringBuffer format(double number, StringBuffer result) {
        if (Double.isNaN(number)) {
            result.append(symbols.NaN);
            return result;
        }
        boolean isNegative = (number < 0.0) ||
                             (number == 0.0 && 1 / number < 0.0);
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
     * @param  number  The long to format
     * @param  result  where the text is to be appended
     * @return         The formatted number
     */
    private StringBuffer format(long number, StringBuffer result) {
        boolean isNegative = (number < 0);
        if (isNegative) {
            number = -number;
        }

        if (symbols.multiplier[style] != 1 &&
                symbols.multiplier[style] != 0) {
            boolean useDouble = false;

            if (number < 0) {
                //  This can only happen if number == Long.MIN_VALUE

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
     * @param  result      buffer to append formatted number to
     * @param  isNegative  <code>true</code> if number is negative
     * @param  isInteger   <code>true</code> if integer number will be formatted
     * @return             buffer with appended formatted number
     */
    private StringBuffer subformat(StringBuffer result,
            boolean isNegative, boolean isInteger) {

        char zero = symbols.zeroDigit;
        int zeroDelta = zero - '0';
        //  '0' is the DigitList representation of zero
        char grouping = symbols.groupingSeparator;
        char decimal = isCurrencyFormat ?
                symbols.monetarySeparator :
                symbols.decimalSeparator;

        if (digitList.isZero()) {
            digitList.decimalAt = 0;
            //  Normalize
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
        //  Index into digitList.fDigits[]
        if (digitList.decimalAt > 0 && count < digitList.decimalAt) {
            count = digitList.decimalAt;
        }

        if (count > symbols.maximumIntegerDigits[style]) {
            count = symbols.maximumIntegerDigits[style];
            digitIndex = digitList.decimalAt - count;
        }

        if (logging.getLevel().intValue() <= Level.INFO.intValue()) {
        	 logging.log(Level.INFO, //LogChannels.LC_JSR238,
                           classname + " :" +
                           "grouping used " + symbols.groupingUsed + "\n" +
                           "grouping separator \"" + grouping + "\"\n" +
                           "decimal separator \"" + decimal + "\"\n" +
                           "digit count " + count);
        }

        int sizeBeforeIntegerPart = result.length();
        for (int i = count - 1; i >= 0; --i) {
            if (i < digitList.decimalAt && digitIndex < digitList.count) {
                //  Output a real digit
                result.append((char) (digitList.digits[digitIndex++] +
                                      zeroDelta));
            } else {
                //  Output a leading zero
                result.append(zero);
            }

            //  Output grouping separator if necessary.  Don't output a
            //  grouping separator if i==0 though; that's at the end of
            //  the integer part.
            if (symbols.groupingUsed && i > 0 &&
                    (symbols.groupingCount != 0) &&
                    (i % symbols.groupingCount == 0)) {
                int gStart = result.length();
                result.append(grouping);
                if (logging.getLevel().intValue() <= Level.INFO.intValue()) {
               	 logging.log(Level.INFO, //LogChannels.LC_JSR238,
                                   classname + ": " +
                                   "add grouping at " + (digitIndex-1));
                }
            }
        }// for

        boolean fractionPresent = (symbols.minimumFractionDigits[style] > 0) ||
                (!isInteger && digitIndex < digitList.count);

        if (!fractionPresent && result.length() == sizeBeforeIntegerPart) {
            result.append(zero);
        }
        //  Output the decimal separator if we always do so.
        int sStart = result.length();
        if (symbols.decimalSeparatorAlwaysShown || fractionPresent) {
            result.append(decimal);
        }

        for (int i = 0; i < symbols.maximumFractionDigits[style]; ++i) {
            if (i >= symbols.minimumFractionDigits[style] &&
                    (isInteger || digitIndex >= digitList.count)) {
                break;
            }

            if (-1 - i > (digitList.decimalAt - 1)) {
                result.append(zero);
                continue;
            }

            if (!isInteger && digitIndex < digitList.count) {
                result.append((char) (digitList.digits[digitIndex++] +
                                      zeroDelta));
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
    
    /**
     * Parses text from a string to produce a <code>Number</code>.
     * <p>
     * The method attempts to parse text starting at the index given by
     * <code>pos</code>.
     * If parsing succeeds, then the index of <code>pos</code> is updated
     * to the index after the last character used (parsing does not necessarily
     * use all characters up to the end of the string), and the parsed
     * number is returned. The updated <code>pos</code> can be used to
     * indicate the starting point for the next call to this method.
     * If an error occurs, then the index of <code>pos</code> is not
     * changed, the error index of <code>pos</code> is set to the index of
     * the character where the error occurred, and null is returned.
     * <p>
     * The subclass returned depends on the value of {@link #isParseBigDecimal}
     * as well as on the string being parsed.
     * <ul>
     *   <li>If <code>isParseBigDecimal()</code> is false (the default),
     *       most integer values are returned as <code>Long</code>
     *       objects, no matter how they are written: <code>"17"</code> and
     *       <code>"17.000"</code> both parse to <code>Long(17)</code>.
     *       Values that cannot fit into a <code>Long</code> are returned as
     *       <code>Double</code>s. This includes values with a fractional part,
     *       infinite values, <code>NaN</code>, and the value -0.0.
     *       <code>DecimalFormat</code> does <em>not</em> decide whether to
     *       return a <code>Double</code> or a <code>Long</code> based on the
     *       presence of a decimal separator in the source string. Doing so
     *       would prevent integers that overflow the mantissa of a double,
     *       such as <code>"-9,223,372,036,854,775,808.00"</code>, from being
     *       parsed accurately.
     *       <p>
     *       Callers may use the <code>Number</code> methods
     *       <code>doubleValue</code>, <code>longValue</code>, etc., to obtain
     *       the type they want.
     *   <li>If <code>isParseBigDecimal()</code> is true, values are returned
     *       as <code>BigDecimal</code> objects. The values are the ones
     *       constructed by {@link java.math.BigDecimal#BigDecimal(String)}
     *       for corresponding strings in locale-independent format. The
     *       special cases negative and positive infinity and NaN are returned
     *       as <code>Double</code> instances holding the values of the
     *       corresponding <code>Double</code> constants.
     * </ul>
     * <p>
     * <code>DecimalFormat</code> parses all Unicode characters that represent
     * decimal digits, as defined by <code>Character.digit()</code>. In
     * addition, <code>DecimalFormat</code> also recognizes as digits the ten
     * consecutive characters starting with the localized zero digit defined in
     * the <code>DecimalFormatSymbols</code> object.
     *
     * @param text the string to be parsed
     * @param pos  A <code>ParsePosition</code> object with index and error
     *             index information as described above.
     * @return     the parsed value, or <code>null</code> if the parse fails
     * @exception  NullPointerException if <code>text</code> or
     *             <code>pos</code> is null.
     */
    public Number parse(String text, int index) {
        // special case NaN
        if (text.regionMatches(index, symbols.NaN, 0, symbols.NaN.length())) {
            index = index + symbols.NaN.length();
            return new Double(Double.NaN);
        }
        
        boolean[] status = new boolean[STATUS_LENGTH];
        if (!subparse(text, pos, positivePrefix, negativePrefix, digitList, false, status)) {
            return null;
        }

        // special case INFINITY
        if (status[STATUS_INFINITE]) {
            if (status[STATUS_POSITIVE] == (getMultiplier() >= 0)) {
                return new Double(Double.POSITIVE_INFINITY);
            } else {
                return new Double(Double.NEGATIVE_INFINITY);
            }
        }

        if (getMultiplier() == 0) {
            if (digitList.isZero()) {
                return new Double(Double.NaN);
            } else if (status[STATUS_POSITIVE]) {
                return new Double(Double.POSITIVE_INFINITY);
            } else {
                return new Double(Double.NEGATIVE_INFINITY);
            }
        }
    }
    
    /**
     * Parse the given text into a number.  The text is parsed beginning at
     * parsePosition, until an unparseable character is seen.
     * @param text The string to parse.
     * @param parsePosition The position at which to being parsing.  Upon
     * return, the first unparseable character.
     * @param digits The DigitList to set to the parsed value.
     * @param isExponent If true, parse an exponent.  This means no
     * infinite values and integer only.
     * @param status Upon return contains boolean status flags indicating
     * whether the value was infinite and whether it was positive.
     */
    private final boolean subparse(String text, int index,
                   String positivePrefix, String negativePrefix,
                   DigitList digits, boolean isExponent,
                   boolean status[]) {
        int position = index;
        int oldStart = index;
        int backup;
        boolean gotPositive, gotNegative;

        // check for positivePrefix; take longest
        gotPositive = text.regionMatches(position, positivePrefix, 0,
                                         positivePrefix.length());
        gotNegative = text.regionMatches(position, negativePrefix, 0,
                                         negativePrefix.length());

        if (gotPositive && gotNegative) {
            if (positivePrefix.length() > negativePrefix.length()) {
                gotNegative = false;
            } else if (positivePrefix.length() < negativePrefix.length()) {
                gotPositive = false;
            }
        }

        if (gotPositive) {
            position += positivePrefix.length();
        } else if (gotNegative) {
            position += negativePrefix.length();
        } else {
//            parsePosition.errorIndex = position;
            return false;
        }

        // process digits or Inf, find decimal position
        status[STATUS_INFINITE] = false;
        if (!isExponent && text.regionMatches(position,symbols.getInfinity(),0,
                          symbols.getInfinity().length())) {
            position += symbols.getInfinity().length();
            status[STATUS_INFINITE] = true;
        } else {
            // We now have a string of digits, possibly with grouping symbols,
            // and decimal points.  We want to process these into a DigitList.
            // We don't want to put a bunch of leading zeros into the DigitList
            // though, so we keep track of the location of the decimal point,
            // put only significant digits into the DigitList, and adjust the
            // exponent as needed.

            digits.decimalAt = digits.count = 0;
            char zero = symbols.getZeroDigit();
            char decimal = isCurrencyFormat ?
                symbols.getMonetaryDecimalSeparator() :
                symbols.getDecimalSeparator();
            char grouping = symbols.getGroupingSeparator();
            String exponentString = symbols.getExponentSeparator();
            boolean sawDecimal = false;
            boolean sawExponent = false;
            boolean sawDigit = false;
            int exponent = 0; // Set to the exponent value, if any

            // We have to track digitCount ourselves, because digits.count will
            // pin when the maximum allowable digits is reached.
            int digitCount = 0;

            backup = -1;
            for (; position < text.length(); ++position) {
                char ch = text.charAt(position);

                /* We recognize all digit ranges, not only the Latin digit range
                 * '0'..'9'.  We do so by using the Character.digit() method,
                 * which converts a valid Unicode digit to the range 0..9.
                 *
                 * The character 'ch' may be a digit.  If so, place its value
                 * from 0 to 9 in 'digit'.  First try using the locale digit,
                 * which may or MAY NOT be a standard Unicode digit range.  If
                 * this fails, try using the standard Unicode digit ranges by
                 * calling Character.digit().  If this also fails, digit will
                 * have a value outside the range 0..9.
                 */
                int digit = ch - zero;
                if (digit < 0 || digit > 9) {
                    digit = Character.digit(ch, 10);
                }

                if (digit == 0) {
                    // Cancel out backup setting (see grouping handler below)
                    backup = -1; // Do this BEFORE continue statement below!!!
                    sawDigit = true;

                    // Handle leading zeros
                    if (digits.count == 0) {
                        // Ignore leading zeros in integer part of number.
                        if (!sawDecimal) {
                            continue;
                        }

                        // If we have seen the decimal, but no significant
                        // digits yet, then we account for leading zeros by
                        // decrementing the digits.decimalAt into negative
                        // values.
                        --digits.decimalAt;
                    } else {
                        ++digitCount;
                        digits.append((char)(digit + '0'));
                    }
                } else if (digit > 0 && digit <= 9) { // [sic] digit==0 handled above
                    sawDigit = true;
                    ++digitCount;
                    digits.append((char)(digit + '0'));

                    // Cancel out backup setting (see grouping handler below)
                    backup = -1;
                } else if (!isExponent && ch == decimal) {
                    // If we're only parsing integers, or if we ALREADY saw the
                    // decimal, then don't parse this one.
                    if (style == INTEGERSTYLE) {
                        break;
                    }
                    digits.decimalAt = digitCount; // Not digits.count!
                    sawDecimal = true;
                } else if (!isExponent && ch == grouping && isGroupingUsed()) {
                    if (sawDecimal) {
                        break;
                    }
                    // Ignore grouping characters, if we are using them, but
                    // require that they be followed by a digit.  Otherwise
                    // we backup and reprocess them.
                    backup = position;
                } else if (!isExponent && text.regionMatches(position, exponentString, 0, exponentString.length())
                             && !sawExponent) {
                    // Process the exponent by recursively calling this method.
                     ParsePosition pos = new ParsePosition(position + exponentString.length());
                    boolean[] stat = new boolean[STATUS_LENGTH];
                    DigitList exponentDigits = new DigitList();

                    if (subparse(text, pos, "", Character.toString(symbols.getMinusSign()), exponentDigits, true, stat) &&
                        exponentDigits.fitsIntoLong(stat[STATUS_POSITIVE], true)) {
                        position = pos.index; // Advance past the exponent
                        exponent = (int)exponentDigits.getLong();
                        if (!stat[STATUS_POSITIVE]) {
                            exponent = -exponent;
                        }
                        sawExponent = true;
                    }
                    break; // Whether we fail or succeed, we exit this loop
                } else {
                    break;
                }
            }

            if (backup != -1) {
                position = backup;
            }

            // If there was no decimal point we have an integer
            if (!sawDecimal) {
                digits.decimalAt = digitCount; // Not digits.count!
            }

            // Adjust for exponent, if any
            digits.decimalAt += exponent;

            // If none of the text string was recognized.  For example, parse
            // "x" with pattern "#0.00" (return index and error index both 0)
            // parse "$" with pattern "$#0.00". (return index 0 and error
            // index 1).
            if (!sawDigit && digitCount == 0) {
                index = oldStart;
//                parsePosition.errorIndex = oldStart;
                return false;
            }
        }

        // check for suffix
        if (!isExponent) {
            if (gotPositive) {
                gotPositive = text.regionMatches(position,positiveSuffix,0,
                                                 positiveSuffix.length());
            }
            if (gotNegative) {
                gotNegative = text.regionMatches(position,negativeSuffix,0,
                                                 negativeSuffix.length());
            }

        // if both match, take longest
        if (gotPositive && gotNegative) {
            if (positiveSuffix.length() > negativeSuffix.length()) {
                gotNegative = false;
            } else if (positiveSuffix.length() < negativeSuffix.length()) {
                gotPositive = false;
            }
        }

        // fail if neither or both
        if (gotPositive == gotNegative) {
            parsePosition.errorIndex = position;
            return false;
        }

        index = position +
            (gotPositive ? positiveSuffix.length() : negativeSuffix.length()); // mark success!
        } else {
            index = position;
        }

        status[STATUS_POSITIVE] = gotPositive;
        if (index == oldStart) {
//            parsePosition.errorIndex = position;
            return false;
        }
        return true;
    }
}

