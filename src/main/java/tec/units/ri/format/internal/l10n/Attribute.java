package tec.units.ri.format.internal.l10n;

import java.util.Hashtable;

/**
 * Defines attribute keys that are used to identify text attributes. These keys
 * are used in AttributedCharacterIterator and AttributedString.
 * 
 * @see AttributedCharacterIterator
 * @see AttributedString
 */
public class Attribute {

﻿  /**
﻿   * The name of this Attribute. The name is used primarily by readResolve to
﻿   * look up the corresponding predefined instance when deserializing an
﻿   * instance.
﻿   * 
﻿   * @serial
﻿   */
﻿  private String name;

﻿  // table of all instances in this class, used by readResolve
﻿  private static final Hashtable instanceMap = new Hashtable(7);

﻿  /**
﻿   * Constructs an Attribute with the given name.
﻿   */
﻿  protected Attribute(String name) {
﻿  ﻿  this.name = name;
﻿  ﻿  if (this.getClass() == Attribute.class) {
﻿  ﻿  ﻿  instanceMap.put(name, this);
﻿  ﻿  }
﻿  }

﻿  /**
﻿   * Compares two objects for equality. This version only returns true for
﻿   * <code>x.equals(y)</code> if <code>x</code> and <code>y</code> refer to
﻿   * the same object, and guarantees this for all subclasses.
﻿   */
﻿  public final boolean equals(Object obj) {
﻿  ﻿  return super.equals(obj);
﻿  }

﻿  /**
﻿   * Returns a hash code value for the object. This version is identical to
﻿   * the one in Object, but is also final.
﻿   */
﻿  public final int hashCode() {
﻿  ﻿  return super.hashCode();
﻿  }

﻿  /**
﻿   * Returns a string representation of the object. This version returns the
﻿   * concatenation of class name, "(", a name identifying the attribute and
﻿   * ")".
﻿   */
﻿  public String toString() {
﻿  ﻿  return getClass().getName() + "(" + name + ")";
﻿  }

﻿  /**
﻿   * Returns the name of the attribute.
﻿   */
﻿  protected String getName() {
﻿  ﻿  return name;
﻿  }

﻿  /**
﻿   * Attribute key for the language of some text.
﻿   * <p>
﻿   * Values are instances of Locale.
﻿   * 
﻿   * @see java.util.Locale
﻿   */
﻿  public static final Attribute LANGUAGE = new Attribute("language");

﻿  /**
﻿   * Attribute key for the reading of some text. In languages where the
﻿   * written form and the pronunciation of a word are only loosely related
﻿   * (such as Japanese), it is often necessary to store the reading
﻿   * (pronunciation) along with the written form.
﻿   * <p>
﻿   * Values are instances of Annotation holding instances of String.
﻿   * 
﻿   * @see Annotation
﻿   * @see java.lang.String
﻿   */
﻿  public static final Attribute READING = new Attribute("reading");

﻿  /**
﻿   * Attribute key for input method segments. Input methods often break up
﻿   * text into segments, which usually correspond to words.
﻿   * <p>
﻿   * Values are instances of Annotation holding a null reference.
﻿   * 
﻿   * @see Annotation
﻿   */
﻿  public static final Attribute INPUT_METHOD_SEGMENT = new Attribute(
﻿  ﻿  ﻿  "input_method_segment");

﻿  /* Declare serialVersionUID for interoperability */
﻿  private static final long serialVersionUID = -9142742483513960612L;

}