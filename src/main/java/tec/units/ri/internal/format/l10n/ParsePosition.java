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
 * Portions Copyright  2000-2006 Sun Microsystems, Inc. All Rights
 * Reserved.  Use is subject to license terms.
 */

/*
 * (C) Copyright Taligent, Inc. 1996, 1997 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 */

/**
 * <code>ParsePosition</code> is a simple class used by <code>Format</code> and its subclasses to keep track of the current position during parsing.
 * The <code>parseObject</code> method in the various <code>Format</code> classes requires a <code>ParsePosition</code> object as an argument.
 *
 * <p>
 * By design, as you parse through a string with different formats, you can use the same <code>ParsePosition</code>, since the index parameter records
 * the current position.
 *
 * @author Mark Davis
 * @see Format
 */

public class ParsePosition {

  /**
   * Input: the place you start parsing. <br>
   * Output: position where the parse stopped. This is designed to be used serially, with each call setting index up for the next one.
   */
  int index = 0;
  int errorIndex = -1;

  /**
   * Retrieve the current parse position. On input to a parse method, this is the index of the character at which parsing will begin; on output, it is
   * the index of the character following the last character parsed.
   */
  public int getIndex() {
    return index;
  }

  /**
   * Set the current parse position.
   */
  public void setIndex(int index) {
    this.index = index;
  }

  /**
   * Create a new ParsePosition with the given initial index.
   */
  public ParsePosition(int index) {
    this.index = index;
  }

  /**
   * Set the index at which a parse error occurred. Formatters should set this before returning an error code from their parseObject method. The
   * default value is -1 if this is not set.
   * 
   * @since 1.2
   */
  public void setErrorIndex(int ei) {
    errorIndex = ei;
  }

  /**
   * Retrieve the index at which an error occurred, or -1 if the error index has not been set.
   * 
   * @since 1.2
   */
  public int getErrorIndex() {
    return errorIndex;
  }

  /**
   * Overrides equals
   */
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (!(obj instanceof ParsePosition))
      return false;
    ParsePosition other = (ParsePosition) obj;
    return (index == other.index && errorIndex == other.errorIndex);
  }

  /**
   * Returns a hash code for this ParsePosition.
   * 
   * @return a hash code value for this object
   */
  public int hashCode() {
    return (errorIndex << 16) | index;
  }

  /**
   * Return a string representation of this ParsePosition.
   * 
   * @return a string representation of this object
   */
  public String toString() {
    return getClass().getName() + "[index=" + index + ",errorIndex=" + errorIndex + ']';
  }
}
