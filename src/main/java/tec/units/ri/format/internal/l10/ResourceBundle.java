/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tec.units.ri.format.internal.l10;

/**
 *
 * @author Werner
 */
 
import java.util.Vector;
 
public abstract class ResourceBundle {
    protected ResourceBundle parent;
 
    /**
     * Returns a resource bundle using the specified base name and target
     * locale.
     */
    public static ResourceBundle getBundle(String baseName, Locale targetLocale) {
        ResourceBundle parentBundle = null;
        ResourceBundle bundle = null;
        // Get a list of candidate locales for which resource bundles are
        // searched
        Vector candidateLocales = getCandidateLocales(targetLocale);
        // Go through every candidate locale and try to instantiate a
        // ResourceBundle using the base name and the candidate locale
        for (int i = candidateLocales.size() - 1; i >= 0; i--) {
            Locale locale = (Locale)candidateLocales.elementAt(i);
            // Bundle name consists of the base name plus an underscore and
            // locale if there is a locale. Otherwise, it will consist only of
            // the base name.
            String bundleName = baseName +
                ((locale.toString().equals("")) ? "" : ("_" + locale));
            try {
                // Try to instantiate a resource bundle using the name
                // constructed above
                //Class bundleClass = Class.forName("com.nokia.example.l10nMIDlet." + bundleName);
                Class bundleClass = Class.forName("l10nmidlet." + bundleName);
                bundle = (ResourceBundle)bundleClass.newInstance();
                // Set the parent bundle for this bundle. For the base bundle
                // (the one with the root locale, Locale.ROOT), the parent is
                // null.
                bundle.setParent(parentBundle);
                parentBundle = bundle;
            } catch (Exception ex) {
                // No need to do anything, just continue to the next bundle
            }
        }
        // If bundle is null even here, no resource bundle could be found.
        // This is an error situation.
        if (bundle == null) {
            throw new RuntimeException(
                "Can't find resource bundle for base name " + baseName + ".");
        }
        return bundle;
    }
 
    /**
     * Returns a list of Locales as candidate locales used in bundle
     * instantiation.
     * @param locale the locale for which a resource bundle is desired
     */
    private static Vector getCandidateLocales(Locale locale) {
        String language = locale.getLanguage();
        String country = locale.getCountry();
 
        Vector locales = new Vector(3);
        if (!country.equals("")) {
            locales.addElement(locale);
        }
        if (!language.equals("")) {
            locales.addElement(
                (locales.size() == 0) ? locale : new Locale(language, ""));
        }
        locales.addElement(Locale.ROOT);
        return locales;
    }
 
    protected void setParent(ResourceBundle parent) {
        this.parent = parent;
    }
 
    public final String getString(String key) {
        String string = handleGetString(key);
        if (string == null) {
            if (parent != null) {
                string = parent.getString(key);
            }
            if (string == null) {
                throw new RuntimeException("Can't find resource for bundle " +
                    this.getClass().getName() + " and key " + key + ".");
            }
        }
        return string;
    }
 
    /**
     * Gets a string for the given key from this resource bundle. Returns null
     * if this resource bundle does not contain a string for the given key.
     */
    protected abstract String handleGetString(String key);
}
