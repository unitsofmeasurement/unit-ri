package tec.units.ri.format.internal.l10n;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import tec.units.ri.format.internal.l10n.Locale;
import tec.units.ri.format.internal.l10n.ResourceBundle;

public class ResourceBundleTest {

	@Test
	public void test() {
		Locale locale = new Locale("de");
		   
		ResourceBundle   resources = ResourceBundle.getBundle("Resources", locale);
		assertNotNull(resources);
		String title = resources.getString("title");
		assertEquals("Localization example", title);
	}
}
