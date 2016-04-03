package tec.units.ri.unit;

import javax.measure.quantity.Length;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnnotatedUnitTest {

    private AnnotatedUnit<Length> annotatedUnit;
    private BaseUnit<Length> baseUnit;

    @Before
    public void setUp() throws Exception {
        baseUnit = new BaseUnit<Length>("m");
        annotatedUnit = new AnnotatedUnit<Length>(baseUnit, "Metre");
    }

    @Test
    public void testGetterUnmodified() {
        Assert.assertEquals(annotatedUnit.getAnnotation(), "Metre");
        Assert.assertEquals(annotatedUnit.getSymbol(), "m");
        Assert.assertEquals(annotatedUnit.getSystemUnit(), baseUnit);
        Assert.assertEquals(annotatedUnit.getActualUnit(), baseUnit);
    }

    @Test
    public void testEquality() {
        Assert.assertFalse(annotatedUnit.equals(baseUnit));
        Assert.assertFalse(baseUnit.equals(annotatedUnit));
        AnnotatedUnit<Length> anotherUnit = new AnnotatedUnit<Length>(baseUnit, "Metre");
        Assert.assertTrue(annotatedUnit.equals(anotherUnit));
        Assert.assertTrue(anotherUnit.equals(annotatedUnit));
        Assert.assertEquals(annotatedUnit.hashCode(), anotherUnit.hashCode());
        Assert.assertEquals(anotherUnit.hashCode(), annotatedUnit.hashCode());
        anotherUnit = new AnnotatedUnit<Length>(baseUnit, "KiloMetre");
        Assert.assertFalse(annotatedUnit.equals(anotherUnit));
        Assert.assertFalse(anotherUnit.equals(annotatedUnit));
    }
}
