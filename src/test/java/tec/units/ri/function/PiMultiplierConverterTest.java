package tec.units.ri.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PiMultiplierConverterTest {

    private PiMultiplierConverter piMultiplierConverter;

    @Before
    public void setUp() throws Exception {
        piMultiplierConverter = new PiMultiplierConverter();
    }

    @Test
    public void testConvertMethod() {
        Assert.assertEquals(314.15, piMultiplierConverter.convert(100), 0.1);
        Assert.assertEquals(0, piMultiplierConverter.convert(0), 0.0);
        Assert.assertEquals(-314.15, piMultiplierConverter.convert(-100), 0.1);
    }

    @Test
    public void testEqualityOfTwoLogConverter() {
        assertTrue(!piMultiplierConverter.equals(null));
    }

    @Test
    public void testGetValuePiDivisorConverter() {
        assertEquals("(π)", piMultiplierConverter.getValue());
    }

    @Test
    public void isLinearOfLogConverterTest() {
        assertTrue(piMultiplierConverter.isLinear());
    }
}
