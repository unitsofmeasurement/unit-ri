package tec.units.ri.format.internal.l10;

import static org.junit.Assert.*;

import org.junit.Test;

public class L10Test {

	@Test
	public void test() {
		Locale locale = new Locale("de");
		   
		ResourceBundle   resources = ResourceBundle.getBundle("Resources", locale);
		assertNotNull(resources);
		String title = resources.getString("title");
		assertEquals("Localization example", title);
	}

}
