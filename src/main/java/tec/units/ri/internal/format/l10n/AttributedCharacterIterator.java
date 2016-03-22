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
 * Portions Copyright   Copyright  1990-2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

import java.util.Map;
import java.util.Vector;

/**
 * An AttributedCharacterIterator allows iteration through both text and related attribute information.
 * 
 * <p>
 * An attribute is a key/value pair, identified by the key. No two attributes on a given character can have the same key.
 * 
 * <p>
 * The values for an attribute are immutable, or must not be mutated by clients or storage. They are always passed by reference, and not cloned.
 * 
 * <p>
 * A <em>run with respect to an attribute</em> is a maximum text range for which:
 * <ul>
 * <li>the attribute is undefined or null for the entire range, or
 * <li>the attribute value is defined and has the same non-null value for the entire range.
 * </ul>
 * 
 * <p>
 * A <em>run with respect to a set of attributes</em> is a maximum text range for which this condition is met for each member attribute.
 * 
 * <p>
 * The returned indexes are limited to the range of the iterator.
 * 
 * <p>
 * The returned attribute information is limited to runs that contain the current character.
 * 
 * <p>
 * Attribute keys are instances of AttributedCharacterIterator.Attribute and its subclasses.
 * 
 * @see AttributedCharacterIterator.Attribute
 * @see AttributedString
 * @see Annotation
 */

interface AttributedCharacterIterator extends CharacterIterator {

  /**
   * Returns the index of the first character of the run with respect to all attributes containing the current character.
   */
  public int getRunStart();

  /**
   * Returns the index of the first character of the run with respect to the given attribute containing the current character.
   */
  public int getRunStart(Attribute attribute);

  /**
   * Returns the index of the first character of the run with respect to the given attributes containing the current character.
   */
  public int getRunStart(Vector attributes);

  /**
   * Returns the index of the first character following the run with respect to all attributes containing the current character.
   */
  public int getRunLimit();

  /**
   * Returns the index of the first character following the run with respect to the given attribute containing the current character.
   */
  public int getRunLimit(Attribute attribute);

  /**
   * Returns the index of the first character following the run with respect to the given attributes containing the current character.
   */
  public int getRunLimit(Vector attributes);

  /**
   * Returns a map with the attributes defined on the current character.
   */
  public Map getAttributes();

  /**
   * Returns the value of the named attribute for the current character. Returns null if the attribute is not defined.
   * 
   * @param attribute
   *          the key of the attribute whose value is requested.
   */
  public Object getAttribute(Attribute attribute);

  /**
   * Returns the keys of all attributes defined on the iterator's text range. The set is empty if no attributes are defined.
   */
  public Vector getAllAttributeKeys();
};