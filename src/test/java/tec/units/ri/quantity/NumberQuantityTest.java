/*
 * Units of Measurement Reference Implementation
 * Copyright (c) 2005-2017, Jean-Marie Dautelle, Werner Keil, V2COM.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-363 nor the names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tec.units.ri.quantity;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.measure.Quantity;
import javax.measure.quantity.ElectricResistance;
import javax.measure.quantity.Length;
import javax.measure.quantity.Time;

import org.junit.Assert;
import org.junit.Test;

import tec.units.ri.AbstractQuantity;
import tec.units.ri.unit.Units;

public class NumberQuantityTest {

  @Test
  public void divideTest() {
    NumberQuantity<ElectricResistance> quantity1 = new NumberQuantity<ElectricResistance>(Double.valueOf(3).doubleValue(), Units.OHM);
    NumberQuantity<ElectricResistance> quantity2 = new NumberQuantity<ElectricResistance>(Double.valueOf(2).doubleValue(), Units.OHM);
    Quantity<?> result = quantity1.divide(quantity2);
    assertEquals(Double.valueOf(1.5f), result.getValue());
  }

  @Test
  public void divideNumberTest() {
    NumberQuantity<ElectricResistance> quantity1 = new NumberQuantity<ElectricResistance>(Double.valueOf(3).doubleValue(), Units.OHM);
    Quantity<?> result = quantity1.divide(Double.valueOf(2));
    assertEquals(Double.valueOf(1.5d), result.getValue());
  }

  @Test
  public void addTest() {
    NumberQuantity<ElectricResistance> quantity1 = new NumberQuantity<ElectricResistance>(Double.valueOf(1).doubleValue(), Units.OHM);
    NumberQuantity<ElectricResistance> quantity2 = new NumberQuantity<ElectricResistance>(Double.valueOf(2).doubleValue(), Units.OHM);
    Quantity<ElectricResistance> result = quantity1.add(quantity2);
    assertEquals(Double.valueOf(3f), result.getValue());
  }

  @Test
  public void subtractTest() {
    NumberQuantity<ElectricResistance> quantity1 = new NumberQuantity<ElectricResistance>(Double.valueOf(1).doubleValue(), Units.OHM);
    NumberQuantity<ElectricResistance> quantity2 = new NumberQuantity<ElectricResistance>(Double.valueOf(2).doubleValue(), Units.OHM);
    Quantity<ElectricResistance> result = quantity2.subtract(quantity1);
    assertEquals(Double.valueOf(1), result.getValue());
    assertEquals(Units.OHM, result.getUnit());
  }

  @Test
  public void multiplyQuantityTest() {
    NumberQuantity<ElectricResistance> quantity1 = new NumberQuantity<ElectricResistance>(Double.valueOf(3).doubleValue(), Units.OHM);
    NumberQuantity<ElectricResistance> quantity2 = new NumberQuantity<ElectricResistance>(Double.valueOf(2).doubleValue(), Units.OHM);
    Quantity<?> result = quantity1.multiply(quantity2);
    assertEquals(Double.valueOf(6L), result.getValue());
  }

  @Test
  public void multiplyQuantityByNumTest() {
    NumberQuantity<ElectricResistance> quantity1 = new NumberQuantity<ElectricResistance>(Double.valueOf(3).doubleValue(), Units.OHM);
    Quantity<?> result = quantity1.multiply(Double.valueOf(2));
    assertEquals(Double.valueOf(6L), result.getValue());
  }

  @Test
  public void doubleValueTest() {
    NumberQuantity<Time> day = new NumberQuantity<Time>(Double.valueOf(3), Units.DAY);
    double hours = day.doubleValue(Units.HOUR);
    assertEquals(72, hours, 0);
  }

  @Test
  public void intValueTest() {
    NumberQuantity<Time> day = new NumberQuantity<Time>(Double.valueOf(3), Units.DAY);
    int hours = day.intValue(Units.HOUR);
    assertEquals(72, hours, 0);
  }

  @Test
  public void ofTest() {
    AbstractQuantity<Length> l = NumberQuantity.of(Short.valueOf("10").shortValue(), Units.METRE);
    assertEquals(Short.valueOf("10"), l.getValue());
  }

  @Test
  public void inverseTest() {
    AbstractQuantity<Length> l = NumberQuantity.of(Double.valueOf(10d).doubleValue(), Units.METRE);
    assertEquals(Double.valueOf(1 / 10d), l.inverse().getValue());
  }

  @Test
  public void toTest() {
    Quantity<Time> day = Quantities.getQuantity(1D, Units.DAY);
    Quantity<Time> hour = day.to(Units.HOUR);
    Assert.assertEquals(Double.valueOf(24), hour.getValue());
    Assert.assertEquals(hour.getUnit(), Units.HOUR);

    Quantity<Time> dayResult = hour.to(Units.DAY);
    Assert.assertEquals(dayResult.getValue(), day.getValue());
    Assert.assertEquals(dayResult.getUnit(), day.getUnit());
  }

  @Test
  public void parseTest() {
    Quantity<?> l = NumberQuantity.parse("10 m");
    assertEquals(Double.valueOf(10d), l.getValue());
    assertEquals(Units.METRE, l.getUnit());
  }

  @Test
  public void equalsTest() {
    NumberQuantity<Time> day = new NumberQuantity<Time>(Double.valueOf(3), Units.DAY);
    NumberQuantity<Time> day2 = new NumberQuantity<Time>(Double.valueOf(3), Units.DAY);
    assertEquals(day, day2);
  }

  @Test
  public void inverseTestTime() {
    NumberQuantity<Time> day = new NumberQuantity<Time>(Double.valueOf(10), Units.DAY);
    assertEquals(Double.valueOf(1 / 10d), day.inverse().getValue());
  }

  @Test
  public void testEquality() throws Exception {
    Quantity<Length> value = Quantities.getQuantity(new Double(10), Units.METRE);
    Quantity<Length> anotherValue = Quantities.getQuantity(new Long(10), Units.METRE);
    assertEquals(value, anotherValue);
  }

  @Test
  public void testEqualityAtomic() throws Exception {
    Quantity<Length> value = Quantities.getQuantity(new AtomicInteger(10), Units.METRE);
    Quantity<Length> anotherValue = Quantities.getQuantity(new AtomicLong(10), Units.METRE);
    assertEquals(value, anotherValue);
  }

  @Test
  public void testEqualityFloat() throws Exception {
    Quantity<Length> value = Quantities.getQuantity(Integer.valueOf(20), Units.METRE);
    Quantity<Length> anotherValue = Quantities.getQuantity(Double.valueOf(20), Units.METRE);
    assertEquals(value, anotherValue);
  }

  @Test(expected = NullPointerException.class)
  public void testEqualityWithNull() throws Exception {
    Quantity<Length> value = Quantities.getQuantity(Integer.valueOf(20), Units.METRE);
    Quantity<Length> anotherValue = Quantities.getQuantity(null, Units.METRE);
    assertEquals(value, anotherValue);
  }
}
