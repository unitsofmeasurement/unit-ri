package tec.units.ri.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PiDivisorConverterTest {

    private PiDivisorConverter piDivisorConverter;

    @Before
    public void setUp() throws Exception {
        piDivisorConverter = new PiDivisorConverter();
    }

    @Test
    public void testConvertMethod() {
        Assert.assertEquals(1000, piDivisorConverter.convert(3141), 0.2);
        Assert.assertEquals(0, piDivisorConverter.convert(0), 0.0);
        Assert.assertEquals(-1000, piDivisorConverter.convert(-3141), 0.2);
    }

    @Test
    public void testEqualityOfTwoLogConverter() {
        assertTrue(!piDivisorConverter.equals(null));
    }

    @Test
    public void testGetValuePiDivisorConverter() {
        assertEquals("(1/π)", piDivisorConverter.getValue());
    }

    @Test
    public void isLinearOfLogConverterTest() {
        assertTrue(piDivisorConverter.isLinear());
    }
}
