package tec.units.ri.function;

import static org.junit.Assert.*;
import static tec.units.ri.util.SI.*;
import static tec.units.ri.util.SIPrefix.*;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.UnitConverter;
import javax.measure.quantity.Length;

import org.junit.Test;

import tec.units.ri.quantity.Quantities;

public class UnitConverterTest {
	private Unit<Length> sourceUnit = METRE;
	private Unit<Length> targetUnit = CENTI(METRE);
	
	@Test
	public void testDouble() {
		UnitConverter converter = sourceUnit.getConverterTo(targetUnit);
		double length1 = 4.0;
		double length2 = 6.0;
		double result1 = converter.convert(length1);
		double result2 = converter.convert(length2);
		assertEquals(400, result1, 0);
		assertEquals(600, result2, 0);
	}
	
	@Test
	public void testQuantity() {
		Quantity<Length> quantLength1 = Quantities.getQuantity(4.0, sourceUnit);
		Quantity<Length> quantLength2 = Quantities.getQuantity(6.0, targetUnit);
		Quantity<Length> quantResult1 = quantLength1.to(targetUnit);
		assertNotNull(quantResult1);
		assertEquals(Double.valueOf(400), quantResult1.getValue());
		assertEquals(targetUnit, quantResult1.getUnit());
	}

}
