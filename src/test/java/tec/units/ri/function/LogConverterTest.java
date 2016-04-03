package tec.units.ri.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class LogConverterTest {

    private LogConverter logConverterBase10;

    @Before
    public void setUp() throws Exception {
        logConverterBase10 = new LogConverter(10.);
    }

    @Test
    public void testBaseUnmodified() {
        assertEquals(10., logConverterBase10.getBase(), 0.);
    }

    @Test
    public void testEqualityOfTwoLogConverter() {
        LogConverter logConverter = new LogConverter(10.);
        assertTrue(logConverter.equals(logConverterBase10));
        assertTrue(!logConverter.equals(null));
    }

    @Test
    public void testGetValueLogConverter() {
        LogConverter logConverter = new LogConverter(Math.E);
        assertEquals("Log(10.0)", logConverterBase10.getValue());
        assertEquals("ln", logConverter.getValue());
    }

    @Test
    public void isLinearOfLogConverterTest() {
        assertTrue(!logConverterBase10.isLinear());
    }

    @Test
    public void convertLogTest() {
        assertEquals(1, logConverterBase10.convert(10), 0.);
        assertEquals(Double.NaN, logConverterBase10.convert(-10), 0.);
        assertTrue(Double.isInfinite(logConverterBase10.convert(0)));
    }

    @Test
    public void inverseLogTest() {
        LogConverter logConverter = new LogConverter(Math.E);
        assertEquals(new ExpConverter(10.), logConverterBase10.inverse());
        assertEquals(new ExpConverter(Math.E), logConverter.inverse());
    }
}
