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
import java.util.Hashtable;
 
/**
 * Default resource bundle (English / United States).
 */
public class Resources extends ResourceBundle {
    private Hashtable strings;
 
    public Resources() {
        strings = new Hashtable(30);
 
        strings.put("title", "Localization example");
        strings.put("exit", "Exit");
        strings.put("localeLbl", "Locale");
        strings.put("textLbl", "Text");
        strings.put("text", "Here's some text.");
    }
 
    public String handleGetString(String key) {
        return (String)strings.get(key);
    }
}
