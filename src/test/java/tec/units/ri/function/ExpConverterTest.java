package tec.units.ri.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ExpConverterTest {

    private ExpConverter expConverterBase10;

    @Before
    public void setUp() throws Exception {
        expConverterBase10 = new ExpConverter(10.);
    }

    @Test
    public void testBaseUnmodified() {
        assertEquals(10., expConverterBase10.getBase(), 0.);
    }

    @Test
    public void testEqualityOfTwoLogConverter() {
        ExpConverter expConverter = new ExpConverter(10.);
        assertTrue(expConverter.equals(expConverterBase10));
        assertTrue(!expConverter.equals(null));
    }

    @Test
    public void testGetValueLogConverter() {
        ExpConverter expConverter = new ExpConverter(Math.E);
        assertEquals("Exp(10.0)", expConverterBase10.getValue());
        assertEquals("e", expConverter.getValue());
    }

    @Test
    public void isLinearOfLogConverterTest() {
        assertTrue(!expConverterBase10.isLinear());
    }

    @Test
    public void convertLogTest() {
        LogConverter logConverter = new LogConverter(10.);
        assertEquals(1.0, logConverter.convert(expConverterBase10.convert(1.0)), 0.);
        assertEquals(-10, logConverter.convert(expConverterBase10.convert(-10)), 0.);
    }

    @Test
    public void inverseLogTest() {
        ExpConverter expConverter = new ExpConverter(Math.E);
        assertEquals(new LogConverter(10.), expConverterBase10.inverse());
        assertEquals(new LogConverter(Math.E), expConverter.inverse());
    }
}
