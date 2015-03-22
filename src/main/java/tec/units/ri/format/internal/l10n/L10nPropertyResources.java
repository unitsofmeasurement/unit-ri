package tec.units.ri.format.internal.l10n;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Map;

public class L10nPropertyResources implements L10nBundle {

    // Constants -----------------------------------------------------

  private static final String PROPERTIES_PCKG = "";
  private static final String PROPERTIES_PRFX = "messages";
    private static final String DEFAULT_LOCALE  = "";

  private static final String UNSET_LOCALE_MESSAGE
      = "Set locale before retrieving resources.";
  private static final String INVALID_STRING_FORMAT
      = "The String format is invalid.";
  private static final String LOCALE_NOT_FOUND
      = "No resource found for locale";
  private static final String INVALID_RESOURCE_FORMAT
      = "The resource file format is invalid.";
  private static final String ERROR_LOADING_RESOURCES
      = "Error loading resources.";
  private static final String UNABLE_TO_FIND_BINARY_RESOURCE
      = "Unable to find binary resource.";

  // Attributes ----------------------------------------------------

  private Map values;
  private String 	  locale;

    // Static --------------------------------------------------------

  /**
   * Gets an instance of L10nPropertyResources to access resources with
   * the specified locale.
   *
   * @param locale name of locale.
   * @return L10nPropertyResources instance.
   */
  public static L10nPropertyResources getBundle(String locale) {
    return new L10nPropertyResources(locale);
  }

    // Constructors --------------------------------------------------

  /**
   * Creates an instance of L10nPropertyResources to access resources
   * with the specified locale.
   *
   * @param locale name of locale.
   * @return L10nPropertyResources instance.
   */
  private L10nPropertyResources(String locale) {
    this.values = new Hashtable();
    this.setLocale(locale);
  }

    // Public --------------------------------------------------------


  /**
   * Sets the locale of the L10nPropertyResources instance.
   *
   * @param locale name of the locale.
   */
  public void setLocale(String locale) {
    if (locale == null || locale.length() == 0x00) {
      this.locale = System.getProperty("microedition.locale");
      if (this.locale == null) {
        this.locale = L10nPropertyResources.DEFAULT_LOCALE;
      }
    } else {
      this.locale = locale;
    }

    try {
      this.loadResources(this.locale);
    } catch (Exception exc) {
      exc.printStackTrace();
    }
  }


  /**
   * Gets the value for the specified key. For every parameter
   * on the params argument there must be an entry in the key
   * value in the format {x} where x is the index of the value
   * to be replaced on the formated string.
   *
   * @param key resource key.
   * @param params parameters to be formated.
   * @return the formated value.
   */
  public String getString(String key, String[] params) {
    String result = null;
    if (this.values == null) {
      throw new IllegalStateException(UNSET_LOCALE_MESSAGE);
    }
    result = (String) this.values.get(key);
    if (result == null){
       result = "!!" + key + "!!";
    }
    else {
      StringBuffer buffer = new StringBuffer(result);
      for (int i = 0; i < params.length; i++) {
        int index = result.indexOf("{" + i + "}");
        if (index == -1) {
          throw new IllegalArgumentException(INVALID_STRING_FORMAT);
        }

        buffer.delete(index, index + String.valueOf(i).length() + 2);
        buffer.insert(index, params[i]);
        result = buffer.toString();
        buffer.delete(0x00, buffer.length());
        buffer.append(result);
      }
    }
    return result;
  }


  /**
   * Gets the value for the specified key.
   *
   * @param key resource key.
   * @return String values associated to the
   * key.
   */
  public String getString(String key) {
    if (this.values == null) {
      throw new IllegalStateException(UNSET_LOCALE_MESSAGE);
    }
    String result = (String) this.values.get(key);
    if (result == null) {
      result = "!!" + key + "!!";
    }
    return result;
   }


  /**
   * Gets the binary data associated to the path
   * set as value of the specified key.
   *
   * @param key resource key.
   * @return the bytes of the binary data or null
   * if key has no associated value.
   * @throws IOException - If any error occurs.
   */
  public byte[] getData(String key) throws IOException {
    byte[] result = null;
    String value  = getString(key);
    if (value != null) {
      InputStream  stream = this.getClass().getResourceAsStream(value);
      if (stream == null) {
        throw new IOException(UNABLE_TO_FIND_BINARY_RESOURCE);
      }

      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      try {
        byte[] chunk = new byte[100];
        int	   read	 = -1;
        do {
          read = stream.read(chunk);
          if (read > 0x00) {
            buffer.write(chunk, 0x00, read);
          }
        } while (read != -1);
      } finally {
        stream.close();
      }
      result = buffer.toByteArray();
    }
    return result;
  }

    // Package protected ---------------------------------------------

    // Protected -----------------------------------------------------

    // Private -------------------------------------------------------

  private void loadResources(String locale) throws IOException {

    Reader reader = null;
    this.values.clear();
    try {
      reader = getResourceReader(locale);
      if (reader != null) {
        values = readResources(reader);
      }
    }  finally {
      if (reader != null) reader.close();
    }
  }

  private Reader getResourceReader(String locale) throws IOException {
    Reader reader = null;

    String resourePath = getResourcePath(locale);
    InputStream stream = this.getClass().getResourceAsStream(resourePath);
    if (stream == null) {
      throw new IOException(LOCALE_NOT_FOUND + " " + this.locale + ".");
    } else {
      reader = new InputStreamReader(stream);
    }

    return reader;
  }

  private Hashtable readResources(Reader reader) throws IOException {
    Hashtable	 values = new Hashtable();
    StringBuffer buffer = new StringBuffer();

    try {
      int letter = -1;
      while ((letter = reader.read()) != -1) {
        switch (letter) {
          case '\r':
          case '\n':
          break;
          default:
            buffer.append((char)letter);
          break;
        }

        if (letter == '\n') {
          String line   = buffer.toString();
          int separator = line.indexOf("=");
          if (separator < 0) {
            throw new IOException(INVALID_RESOURCE_FORMAT);
          }
          String key 	 = line.substring(0x00, separator);
          String value = line.substring(++separator, line.length());
          values.put(key, value);
          buffer.delete(0x00, buffer.length());
        }
      }
    } catch (Exception e) {
      throw new IOException(ERROR_LOADING_RESOURCES);
    }
    return values;
  }

  private String getResourcePath(String locale) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("/");
    if ( PROPERTIES_PCKG.length() > 0 ) {
      buffer.append(PROPERTIES_PCKG);
      buffer.append("/");
    }
    buffer.append(PROPERTIES_PRFX);
    if (locale.length() > 0) {
      buffer.append("_");
    }
    buffer.append(locale);
    buffer.append(".properties");
    return buffer.toString();
  }

}
