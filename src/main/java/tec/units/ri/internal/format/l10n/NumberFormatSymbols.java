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
package tec.units.ri.internal.format.l10n;

/*
 *
 * Portions Copyright  1990-2008, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.measure.format.ParserException;

/**
 * This class represents the set of symbols (such as the decimal separator, the
 * grouping separator, and so on) needed by <code>NumberFormat</code> to format
 * numbers. <code>NumberFormatSymbols</code> could be instantiated and setup by
 * user but recommended is to get it from <code>ResourceManager
 *  </code> respectively <code>DevResourceManager.getNumberFormatSymbols()
 *  </code> already initialized. All fields are left public intentionally.
 * 
 * @deprecated
 */
final class NumberFormatSymbols {

	/**
	 * Creates <code>DecimalFormatSymbols</code> instance uninitialized. All
	 * necessary number symbols and styles must be set before using the
	 * instance. Usually <code>DecimalFormatSymbols</code> is obtained using
	 * <code>DevResourceManager.getNumberFormatSymbols()</code>.
	 */
	NumberFormatSymbols() {
	}

	/**
	 * The method clones resource.
	 *
	 * @return cloned resource or <code>null</code> if cloning wasn't possible.
	 */
	public java.lang.Object clone() {
		NumberFormatSymbols newNfs = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			write(baos);
			baos.close();
			byte[] buffer = baos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
			newNfs = new NumberFormatSymbols();
			newNfs.read(bais);

		} catch (IOException ioe) {
			// cannot clone resource
		}
		return newNfs;
	}

	/**
	 * supported currencies with codes. example: {"JPY", "Y"}
	 */
	String[][] currencies = new String[][] { {} };

	/**
	 * currency symbol.
	 */
	String currencySymbol = "$";

	/**
	 * international currency symbol "USD".
	 */
	public String intlCurrencySymbol = "USD";

	/**
	 * monetary separator.
	 */
	public char monetarySeparator = '.';

	/**
	 * zero digit.
	 */
	public char zeroDigit = '0';

	/**
	 * grouping separator ','.
	 */
	public char groupingSeparator = ',';

	/**
	 * decimal separator '.'.
	 */
	public char decimalSeparator = '.';

	/**
	 * percent sign '%'.
	 */
	public char percent = '%';

	/**
	 * permill sign.
	 */
	public char perMille = '\u2030';

	/**
	 * minus sign.
	 */
	public char minusSign = '-';

	/**
	 * infinity.
	 */
	public String infinity = "\u221e";

	/**
	 * NaN string.
	 */
	public String NaN = "\ufffd";

	/**
	 * always show decimal separator.
	 */
	public boolean decimalSeparatorAlwaysShown = false;

	/**
	 * prefixes for positive numbers for all styles.
	 */
	public String[] positivePrefix = new String[] { "", "", "", "" };

	/**
	 * suffixes for positive numbers for all styles.
	 */
	public String[] positiveSuffix = new String[] { "", "", "", "" };

	/**
	 * prefixes for negative numbers for all styles.
	 */
	public String[] negativePrefix = new String[] { "-", "-", "-", "-" };

	/**
	 * suffixes for negative numbers for all styles.
	 */
	public String[] negativeSuffix = new String[] { "", "", "", "" };

	/**
	 * is grouping symbol used.
	 */
	public boolean groupingUsed = false;

	/**
	 * count of digits int the group.
	 */
	public int groupingCount = 3;

	/**
	 * prefix for all styles (always at least used for currency).
	 */
	public String[] prefixes = new String[] { "", "", "", "" };

	/**
	 * suffix for all styles (always used for percentage style).
	 */
	public String[] suffixes = new String[] { "", "", "%", "" };

	/**
	 * maximum integer digits <code>-1</code> stands for not specified (use
	 * maximum allowed).
	 */
	public int[] maximumIntegerDigits = new int[] { -1, -1, -1, -1 };

	/**
	 * minimum integer digits at least 1.
	 */
	public int[] minimumIntegerDigits = new int[] { 1, 1, 1, 1 };

	/**
	 * maximum fraction digits <code>-1</code> stands for not specified (use
	 * maximum allowed) integer has always 0 percentage has ussualy 0.
	 */
	public int[] maximumFractionDigits = new int[] { -1, -1, 0, 0 };

	/**
	 * minimum fraction digits.
	 */
	public int[] minimumFractionDigits = new int[] { 0, 0, 0, 0 };

	/**
	 * multiplier is always 1 but for percentage style is 100.
	 */
	public int[] multiplier = new int[] { 1, 1, 100, 1 };

	/**
	 * locale of these symbols.
	 */
	String locale;

	/**
	 * Reads NumberFormatSymbol from input stream.
	 *
	 * @param in
	 *            input stream to read from
	 * @throws IOException
	 *             exception when read failed
	 * @throws ParserException
	 *             exception when resource cannot be created
	 */
	public void read(java.io.InputStream in) throws IOException,
			ParserException {
		DataInputStream dis = new DataInputStream(in);
		currencySymbol = dis.readUTF();
		intlCurrencySymbol = dis.readUTF();
		monetarySeparator = dis.readChar();
		zeroDigit = dis.readChar();
		groupingSeparator = dis.readChar();
		decimalSeparator = dis.readChar();
		percent = dis.readChar();
		perMille = dis.readChar();
		minusSign = dis.readChar();
		infinity = dis.readUTF();
		NaN = dis.readUTF();
		decimalSeparatorAlwaysShown = dis.readBoolean();
		readStrings(positivePrefix, dis);
		readStrings(positiveSuffix, dis);
		readStrings(negativePrefix, dis);
		readStrings(negativeSuffix, dis);
		groupingUsed = dis.readBoolean();
		groupingCount = dis.readInt();
		readStrings(prefixes, dis);
		readStrings(suffixes, dis);
		readIntArray(maximumIntegerDigits, dis);
		readIntArray(minimumIntegerDigits, dis);
		readIntArray(maximumFractionDigits, dis);
		readIntArray(minimumFractionDigits, dis);
		readIntArray(multiplier, dis);
		int currenciesLen = dis.readInt();
		currencies = new String[currenciesLen][2];
		for (int i = 0; i < currenciesLen; i++) {
			readStrings(currencies[i], dis);
		}
	}

	/**
	 * Write NumberFormatSymbols object into output stream.
	 *
	 * @param out
	 *            output stream
	 * @throws IOException
	 *             thrown when write failed
	 * @throws ParserException
	 *             thrown when problem with resource occured
	 */
	public void write(java.io.OutputStream out) throws IOException,
			ParserException {
		DataOutputStream dous = new DataOutputStream(out);
		dous.writeUTF(currencySymbol);
		dous.writeUTF(intlCurrencySymbol);
		dous.writeChar(monetarySeparator);
		dous.writeChar(zeroDigit);
		dous.writeChar(groupingSeparator);
		dous.writeChar(decimalSeparator);
		dous.writeChar(percent);
		dous.writeChar(perMille);
		dous.writeChar(minusSign);
		dous.writeUTF(infinity);
		dous.writeUTF(NaN);
		dous.writeBoolean(decimalSeparatorAlwaysShown);
		writeStrings(positivePrefix, dous);
		writeStrings(positiveSuffix, dous);
		writeStrings(negativePrefix, dous);
		writeStrings(negativeSuffix, dous);
		dous.writeBoolean(groupingUsed);
		dous.writeInt(groupingCount);
		writeStrings(prefixes, dous);
		writeStrings(suffixes, dous);
		writeIntArray(maximumIntegerDigits, dous);
		writeIntArray(minimumIntegerDigits, dous);
		writeIntArray(maximumFractionDigits, dous);
		writeIntArray(minimumFractionDigits, dous);
		writeIntArray(multiplier, dous);
		dous.writeInt(currencies.length);
		for (int i = 0; i < currencies.length; i++) {
			writeStrings(currencies[i], dous);
		}
	}

	/**
	 * Write array of strings.
	 * 
	 * @param array
	 *            object array to write
	 * @param dous
	 *            output stream to write to
	 * @throws IOException
	 *             thrown if writing fails
	 */
	protected void writeStrings(String[] array, DataOutputStream dous)
			throws IOException {
		for (int i = 0; i < array.length; i++) {
			dous.writeUTF(array[i]);
		}
	}

	/**
	 * Helper method reads array of objects.
	 * 
	 * @param array
	 *            array to read strings into
	 * @param dis
	 *            input stream to read from
	 * @throws IOException
	 *             when reading fails
	 */
	protected void readStrings(String[] array, DataInputStream dis)
			throws IOException {
		for (int i = 0; i < array.length; i++) {
			array[i] = dis.readUTF();
		}
	}

	/**
	 * Helper method writes integer array.
	 *
	 * @param array
	 *            array of integer
	 * @param dous
	 *            output stream to write to
	 * @throws IOException
	 *             thrown when writing fails
	 */
	protected void writeIntArray(int[] array, DataOutputStream dous)
			throws IOException {
		for (int i = 0; i < array.length; i++) {
			dous.writeInt(array[i]);
		}
	}

	/**
	 * Helper method reads array of integers.
	 *
	 * @param array
	 *            array of integers
	 * @param dis
	 *            input stream to read from
	 * @throws IOException
	 *             thrown when reading fails
	 */
	protected void readIntArray(int[] array, DataInputStream dis)
			throws IOException {
		for (int i = 0; i < array.length; i++) {
			array[i] = dis.readInt();
		}
	}
}
