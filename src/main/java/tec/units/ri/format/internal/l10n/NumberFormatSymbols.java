package tec.units.ri.format.internal.l10n;

/*
 *   
 *
 * Portions Copyright  1990-2007 Sun Microsystems, Inc. All Rights Reserved.
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.measure.format.ParserException;


/**
 *  This class represents the set of symbols (such as the decimal separator,
 *  the grouping separator, and so on) needed by <code>NumberFormat</code> to
 *  format numbers. <code>NumberFormatSymbols</code> could be instantiated and
 *  setup by user but recommended is to get it from <code>ResourceManager
 *  </code> repectively <code>DevResourceManager.getNumberFormatSymbols()
 *  </code> already initialized. All fields are left public intentionaly.
 *
 */
class NumberFormatSymbols { //implements SerializableResource {

    /**
     *  Creates <code>DecimalFormatSymbols</code> instance uninitialized. All
     *  necessary number symbols and styles must be set before using the
     *  instance. Usually <code>DecimalFormatSymbols</code> is obtained using
     *  <code>DevResourceManager.getNumberFormatSymbols()</code>.
     */
    public NumberFormatSymbols() { }

    /**
     * The method clones resource.
     *
     * @return cloned resource or <code>null</code>
     * if cloning wasn't possible.
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
     *  supported currencies with codes. example: {"JPY", "Y"}
     */
    public String[][] currencies = new String[][]{{}};
            
    
    /**
     *  currency symbol.
     */
    public String currencySymbol = "$";

    /**
     *  international currency symbol "USD".
     */
    public String intlCurrencySymbol = "USD";

    /**
     *  monetary separator.
     */
    public char monetarySeparator = '.';

    /**
     *  zero digit.
     */
    public char zeroDigit = '0';

    /**
     *  grouping separator ','.
     */
    public char groupingSeparator = ',';

    /**
     *  decimal separator '.'.
     */
    public char decimalSeparator = '.';

    /**
     *  percent sign '%'.
     */
    public char percent = '%';

    /**
     *  permill sign.
     */
    public char perMille = '\u2030';

    /**
     *  minus sign.
     */
    public char minusSign = '-';

    /**
     *  infinity.
     */
    public String infinity = "\u221e";

    /**
     *  NaN string.
     */
    public String NaN = "\ufffd";

    /**
     *  always show decimal separator.
     */
    public boolean decimalSeparatorAlwaysShown = false;

    /**
     *  prefixes for positive numbers for all styles.
     */
    public String[] positivePrefix = new String[] {"", "", "", ""};

    /**
     *  suffixes for positive numbers for all styles.
     */
    public String[] positiveSuffix = new String[] {"", "", "", ""};

    /**
     *  prefixes for negative numbers for all styles.
     */
    public String[] negativePrefix = new String[] {"-", "-", "-", "-"};

    /**
     *  suffixes for negative numbers for all styles.
     */
    public String[] negativeSuffix = new String[] {"", "", "", ""};

    /**
     *  is grouping symbol used.
     */
    public boolean groupingUsed = false;

    /**
     *  count of digits int the group.
     */
    public int groupingCount = 3;

    /**
     *  prefix for all styles (always at least used for currency).
     */
    public String[] prefixes = new String[] {"", "", "", ""};

    /**
     *  suffix for all styles (always used for percentage style).
     */
    public String[] suffixes = new String[] {"", "", "%", ""};

    /**
     *  maximum integer digits <code>-1</code> stands for not specified (use
     *  maximum allowed).
     */
    public int[] maximumIntegerDigits = new int[] {-1, -1, -1, -1};

    /**
     *  minimum integer digits at least 1.
     */
    public int[] minimumIntegerDigits = new int[] {1, 1, 1, 1};

    /**
     *  maximum fraction digits <code>-1</code> stands for not specified (use
     *  maximum allowed) integer has always 0 percentage has ussualy 0.
     */
    public int[] maximumFractionDigits = new int[] {-1, -1, 0, 0};

    /**
     *  minimum fraction digits.
     */
    public int[] minimumFractionDigits = new int[] {0, 0, 0, 0};

    /**
     *  multiplier is always 1 but for percentage style is 100.
     */
    public int[] multiplier = new int[] {1, 1, 100, 1};

    /**
     *  locale of this symbols.
     */
    public String locale;


    /**
     *  Reads NumberFormatSymbol from input stream.
     *
     * @param  in                     input stream to read from
     * @throws  IOException        exception when read failed
     * @throws  ParserException  exception when resource cannot
     *                             be created
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
        for(int i = 0; i < currenciesLen; i++ ) {
            readStrings(currencies[i], dis);
        }
    }


    /**
     *  Write NumberFormatSymbols object into output stream.
     *
     * @param  out                    output stream
     * @throws  IOException        thrown when write failed
     * @throws  ParserException  thrown when problem with
     *                             resource occured
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
     * @param array object array to write
     * @param dous output stream to write to
     * @throws IOException thrown if writing fails
     */
    protected void writeStrings(String[] array, DataOutputStream dous) 
                        throws IOException {
        for (int i = 0; i < array.length; i++) {
            dous.writeUTF(array[i]);
        }
    }

    /**
     * Helper method reads array of objects.
     * @param array array to read strings into
     * @param dis input stream to read from
     * @throws IOException when reading fails
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
     * @param array array of integer
     * @param dous output stream to write to
     * @throws IOException thrown when writing fails
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
     * @param array array of integers
     * @param dis input stream to read from
     * @throws IOException thrown when reading fails
     */
    protected void readIntArray(int[] array, DataInputStream dis) 
                                        throws IOException {
        for (int i = 0; i < array.length; i++) {
            array[i] = dis.readInt();
        }
    }
}
