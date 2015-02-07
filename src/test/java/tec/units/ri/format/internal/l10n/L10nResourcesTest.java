package tec.units.ri.format.internal.l10n;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import tec.units.ri.format.internal.l10n.L10nResources;

@Ignore
public class L10nResourcesTest {
	
	@Test
	public void test2() {
		L10nResources resources = L10nResources.getL10nResources("de");
		assertNotNull(resources);
	}
}
