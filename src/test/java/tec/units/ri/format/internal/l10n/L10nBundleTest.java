package tec.units.ri.format.internal.l10n;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import tec.units.ri.format.internal.l10n.L10nResources;

public class L10nBundleTest {
	
	@Test
	@Ignore
	public void testL10() {
		L10nBundle resources = L10nResources.getBundle("de");
		assertNotNull(resources);
	}
	
	@Test
	public void testMapBundle() {
		Locale locale = new Locale("de");
		   
		L10nBundle resources = MapResourceBundle.getBundle("Resources", locale);
		assertNotNull(resources);
		String title = resources.getString("title");
		assertEquals("Localization example", title);
	}
}
