package tec.units.ri.format.internal.l10n;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import tec.units.ri.format.internal.l10n.Locale;
import tec.units.ri.format.internal.l10n.MapResourceBundle;

public class ResourceBundleTest {

	@Test
	public void test() {
		Locale locale = new Locale("de");
		   
		MapResourceBundle   resources = MapResourceBundle.getBundle("Resources", locale);
		assertNotNull(resources);
		String title = resources.getString("title");
		assertEquals("Localization example", title);
	}
}
