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
public class Locale {
    public static final Locale ROOT = new Locale("", "");
    private String language;
    private String country;
    private static Locale defaultLocale;
 
    /**
     * Constructs a locale using the specified language and country.
     */
    public Locale(String language, String country) {
        this.language = language;
        this.country = country;
        defaultLocale = null;
    }
 
    /**
     * Constructs a locale by parsing the language and country from the given
     * string.
     */
    public Locale(String locale) {
        defaultLocale = null;
        // Locale en_US will do if the given locale is invalid
        if (locale == null) {
            language = "en";
            country = "US";
            return;
        }
        language = locale;
        country = "";
        // Some devices separate locale components (language and country) from
        // each other by a hyphen instead of an underscore. Let's convert every
        // hyphen into an underscore for consistency.
        locale = locale.replace('-', '_');
        int separatorPos = locale.indexOf('_');
        if (separatorPos != -1) {
            language = locale.substring(0, separatorPos);
            locale = locale.substring(separatorPos + 1);
            separatorPos = locale.indexOf('_');
            if (separatorPos != -1) {
                country = locale.substring(0, separatorPos);
            } else {
                country = locale;
            }
        }
    }
 
    public String getLanguage() {
        return language;
    }
 
    public String getCountry() {
        return country;
    }
 
    public static Locale getDefault() {
        if (defaultLocale == null) {
            /*defaultLocale = new Locale(System.getProperty(
                "microedition.locale")); */
            defaultLocale = new Locale("en");
        }
        return defaultLocale;
    }
 
    /**
     * Returns this locale as a string representation, with the language and
     * country separated by underscores.
     * Examples: "en", "de_CH", "_US"
     */
    public String toString() {
        if (language.equals("") && country.equals("")) {
            return "";
        }
        StringBuffer localeString = new StringBuffer("");
        if (!country.equals("")) {
            localeString.append(language).append("_").append(country);
        } else if (!language.equals("")) {
            localeString.append(language);
        }
        return localeString.toString();
    }
}
