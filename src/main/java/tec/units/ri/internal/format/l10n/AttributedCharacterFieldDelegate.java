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

import java.util.Vector;

/**
 * AttributedCharacterFieldDelegate combines the notifications from a Format into a resulting <code>AttributedCharacterIterator</code>. The resulting
 * <code>AttributedCharacterIterator</code> can be retrieved by way of the <code>getIterator</code> method.
 * 
 */
class AttributedCharacterFieldDelegate implements Format.FieldDelegate {
  /**
   * Array of AttributeStrings. Whenever <code>formatted</code> is invoked for a region > size, a new instance of AttributedString is added to
   * attributedStrings. Subsequent invocations of <code>formatted</code> for existing regions result in invoking addAttribute on the existing
   * AttributedStrings.
   */
  private final Vector attributedStrings;
  /**
   * Running count of the number of characters that have been encountered.
   */
  private int size;

  AttributedCharacterFieldDelegate() {
    attributedStrings = new Vector();
  }

  public void formatted(Format.Field attr, Object value, int start, int end, StringBuffer buffer) {
    if (start != end) {
      if (start < size) {
        // Adjust attributes of existing runs
        int index = size;
        int asIndex = attributedStrings.size() - 1;

        while (start < index) {
          AttributedString as = (AttributedString) attributedStrings.elementAt(asIndex--);
          int newIndex = index - as.length();
          int aStart = Math.max(0, start - newIndex);

          as.addAttribute(attr, value, aStart, Math.min(end - start, as.length() - aStart) + aStart);
          index = newIndex;
        }
      }
      if (size < start) {
        // Pad attributes
        attributedStrings.addElement(new AttributedString(buffer.toString().substring(size, start)));
        size = start;
      }
      if (size < end) {
        // Add new string
        int aStart = Math.max(start, size);
        AttributedString string = new AttributedString(buffer.toString().substring(aStart, end));

        string.addAttribute(attr, value);
        attributedStrings.addElement(string);
        size = end;
      }
    }
  }

  public void formatted(int fieldID, Format.Field attr, Object value, int start, int end, StringBuffer buffer) {
    formatted(attr, value, start, end, buffer);
  }

  /**
   * Returns an <code>AttributedCharacterIterator</code> that can be used to iterate over the resulting formatted String.
   * 
   * @pararm string Result of formatting.
   */
  public AttributedCharacterIterator getIterator(String string) {
    // Add the last AttributedCharacterIterator if necessary
    // assert(size <= string.length());
    if (string.length() > size) {
      attributedStrings.addElement(new AttributedString(string.substring(size)));
      size = string.length();
    }
    int iCount = attributedStrings.size();
    AttributedCharacterIterator iterators[] = new AttributedCharacterIterator[iCount];

    for (int counter = 0; counter < iCount; counter++) {
      iterators[counter] = ((AttributedString) attributedStrings.elementAt(counter)).getIterator();
    }
    return new AttributedString(iterators).getIterator();
  }
}
