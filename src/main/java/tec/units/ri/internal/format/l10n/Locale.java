/*
 * Units of Measurement Reference Implementation
 * Copyright (c) 2005-2015, Jean-Marie Dautelle, Werner Keil, V2COM.
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

/**
 * A Java ME compliant backport of {@linkplain java.util.Locale}
 * @author Werner
 * @version 0.3
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
                "microedition.locale")); // TODO we have to determine if we're in ME or not and PERMISSION for System.getProperty() must exist, too 
                */
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
