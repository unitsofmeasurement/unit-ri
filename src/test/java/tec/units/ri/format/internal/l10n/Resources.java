/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tec.units.ri.format.internal.l10n;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import tec.units.ri.format.internal.l10n.MapResourceBundle;
 
/**
 * Default resource bundle (English / United States).
 */
public class Resources extends MapResourceBundle {
    private Map<String, String> strings;
 
    public Resources() {
        strings = new HashMap<String, String>(30);
 
        strings.put("title", "Localization example");
        strings.put("exit", "Exit");
        strings.put("localeLbl", "Locale");
        strings.put("textLbl", "Text");
        strings.put("text", "Here's some text.");
    }
 
    public String handleGetString(String key) {
        return (String)strings.get(key);
    }
    
    /**
     * Returns an <code>Enumeration</code> of the keys contained in
     * this <code>ResourceBundle</code> and its parent bundles.
     *
     * @return an <code>Enumeration</code> of the keys contained in
     *         this <code>ResourceBundle</code> and its parent bundles.
     * @see #keySet()
     */
    public Iterator<String> getKeys() {
        // lazily load the lookup hashtable.
//        if (lookup == null) {
//            loadLookup();
//        }
//
//        ResourceBundle parent = this.parent;
        return strings.keySet().iterator();
    }

	@Override
	public Set<String> keySet() {
		return strings.keySet();
	}
}
