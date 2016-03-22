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

import static org.junit.Assert.*;

import org.junit.Test;

import tec.units.ri.internal.format.l10n.DecimalFormat;

public class NumberFormatTest {
	final String[] patterns = new String[]{"#,#00.00#", "0.0;(0.0)", "0.###E0"};

	@Test
	public void testDecimalFormat() {
		DecimalFormat format = (DecimalFormat) DecimalFormat
				.getNumberInstance();

		double value = -12.321;
		for (int i = 0; i < patterns.length; i++) {
			String pattern = patterns[i];
			format.applyPattern(pattern);
			String formated = format.format(value);
			String text = "Pattern: " + pattern + " Sample: " + formated + "\n";
			// System.out.println(text);
			switch (i) {
				case 0 :
					assertEquals("-12.321", formated);
					break;
				case 1 :
					assertEquals("(12.3)", formated);
					break;
				case 2 :
					assertEquals("-1.232E1", formated);
					break;
				default :
			}
		}
	}

	@Test
	public void testSmallNumbers() {
		DecimalFormat format = (DecimalFormat) DecimalFormat
				.getNumberInstance();
		format.applyPattern("#,#0.00#");
		double value = 0.05d;
		String formated = format.format(value);
		assertEquals("0.05", formated);
	}
}
