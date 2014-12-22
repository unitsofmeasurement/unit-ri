package tec.units.ri.format.internal;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public final class BundleToMapAdapter {
	public final static Map<String, String> toMap(final ResourceBundle resource) {
	    Map<String, String> map = new HashMap<>();

	    Enumeration<String> keys = resource.getKeys();
	    while (keys.hasMoreElements()) {
	      String key = keys.nextElement();
	      map.put(key, resource.getString(key));
	    }

	    return map;
	  }
}
