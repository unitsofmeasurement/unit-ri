package tec.units.ri.internal.format.l10n;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class AttributedStringTest {

	static AttributedString sut;
	
	@BeforeClass
	public static void init() {
		sut = new AttributedString("test");
	}
	
	@Test
	public void testGetIterator() {
		assertNotNull(sut.getIterator());
	}

	@Test
	public void testText() {
		assertEquals("test", sut.text);
	}
/*
	@Test
	public void testAttributedStringStringMap() {
		fail("Not yet implemented");
	}

	@Test
	public void testAttributedStringAttributedCharacterIterator() {
		fail("Not yet implemented");
	}

	@Test
	public void testAttributedStringAttributedCharacterIteratorIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAttributedStringAttributedCharacterIteratorIntIntAttributeArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddAttributeAttributeObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddAttributeAttributeObjectIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddAttributes() {
		sut.a
	}

	@Test
	public void testGetIteratorAttributeArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIteratorAttributeArrayIntInt() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testLength() {
		assertEquals(4, sut.length());
	}

}
