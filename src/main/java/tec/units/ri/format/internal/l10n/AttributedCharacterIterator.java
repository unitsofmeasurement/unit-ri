/*
 * @(#)AttributedCharacterIterator.java﻿  1.34 06/10/10
 *
 * Copyright  1990-2006 Sun Microsystems, Inc. All Rights Reserved.  
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
 *
 */

package tec.units.ri.format.internal.l10n;

import java.util.Hashtable;
import java.util.Vector;

/**
 * An AttributedCharacterIterator allows iteration through both text and related
 * attribute information.
 * 
 * <p>
 * An attribute is a key/value pair, identified by the key. No two attributes on
 * a given character can have the same key.
 * 
 * <p>
 * The values for an attribute are immutable, or must not be mutated by clients
 * or storage. They are always passed by reference, and not cloned.
 * 
 * <p>
 * A <em>run with respect to an attribute</em> is a maximum text range for
 * which:
 * <ul>
 * <li>the attribute is undefined or null for the entire range, or
 * <li>the attribute value is defined and has the same non-null value for the
 * entire range.
 * </ul>
 * 
 * <p>
 * A <em>run with respect to a set of attributes</em> is a maximum text range
 * for which this condition is met for each member attribute.
 * 
 * <p>
 * The returned indexes are limited to the range of the iterator.
 * 
 * <p>
 * The returned attribute information is limited to runs that contain the
 * current character.
 * 
 * <p>
 * Attribute keys are instances of AttributedCharacterIterator.Attribute and its
 * subclasses.
 * 
 * @see AttributedCharacterIterator.Attribute
 * @see AttributedString
 * @see Annotation
 * @since 1.2
 */

public interface AttributedCharacterIterator extends CharacterIterator {

﻿  /**
﻿   * Returns the index of the first character of the run with respect to all
﻿   * attributes containing the current character.
﻿   */
﻿  public int getRunStart();

﻿  /**
﻿   * Returns the index of the first character of the run with respect to the
﻿   * given attribute containing the current character.
﻿   */
﻿  public int getRunStart(Attribute attribute);

﻿  /**
﻿   * Returns the index of the first character of the run with respect to the
﻿   * given attributes containing the current character.
﻿   */
﻿  public int getRunStart(Vector attributes);

﻿  /**
﻿   * Returns the index of the first character following the run with respect
﻿   * to all attributes containing the current character.
﻿   */
﻿  public int getRunLimit();

﻿  /**
﻿   * Returns the index of the first character following the run with respect
﻿   * to the given attribute containing the current character.
﻿   */
﻿  public int getRunLimit(Attribute attribute);

﻿  /**
﻿   * Returns the index of the first character following the run with respect
﻿   * to the given attributes containing the current character.
﻿   */
﻿  public int getRunLimit(Vector attributes);

﻿  /**
﻿   * Returns a map with the attributes defined on the current character.
﻿   */
﻿  public Hashtable getAttributes();

﻿  /**
﻿   * Returns the value of the named attribute for the current character.
﻿   * Returns null if the attribute is not defined.
﻿   * 
﻿   * @param attribute
﻿   *            the key of the attribute whose value is requested.
﻿   */
﻿  public Object getAttribute(Attribute attribute);

﻿  /**
﻿   * Returns the keys of all attributes defined on the iterator's text range.
﻿   * The set is empty if no attributes are defined.
﻿   */
﻿  public Vector getAllAttributeKeys();
}