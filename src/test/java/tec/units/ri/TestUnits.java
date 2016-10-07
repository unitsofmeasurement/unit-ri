/*
 * Units of Measurement Reference Implementation
 * Copyright (c) 2005-2016, Jean-Marie Dautelle, Werner Keil, V2COM.
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
package tec.units.ri;

import static tec.units.ri.format.UnitStyle.LABEL;
import static tec.units.ri.format.UnitStyle.SYMBOL_AND_LABEL;
import static tec.units.ri.unit.Units.JOULE;
import static tec.units.ri.unit.Units.LITRE;
import static tec.units.ri.unit.Units.METRE;
import static tec.units.ri.unit.Units.KILOGRAM;

import javax.measure.Unit;
import javax.measure.quantity.Energy;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;

import tec.units.ri.AbstractSystemOfUnits;
import tec.units.ri.format.SimpleUnitFormat;
import tec.units.ri.format.UnitStyle;
import tec.units.ri.function.MultiplyConverter;
import tec.units.ri.function.RationalConverter;
import tec.units.ri.unit.TransformedUnit;

/**
 * <p>
 * This class contains test units of measurement.
 * </p>
 * <p>
 * 
 * @noextend This class is not intended to be extended by clients.
 * 
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.3, $Date: 2016-10-07 $
 * @see <a href="http://www.bier-entdecken.de/chubel/">Ich hätte gern ein Chübel</a>
 * @see <a href="https://en.wikipedia.org/wiki/Ma%C3%9F">Wikipedia: Maß</a>
 */
final class TestUnits extends AbstractSystemOfUnits {
  /**
   * Default constructor (prevents this class from being instantiated).
   */
  private TestUnits() {
  }

  /**
   * The singleton instance of {@code TestUnits}.
   */
  static final TestUnits INSTANCE = new TestUnits();

  @Override
  public String getName() {
    return "Test units of measurement";
  }

  /**
   * A unit of length equal to <code>0.3048 m</code> (standard name <code>ft</code>).
   */
  static final Unit<Length> FOOT = AbstractSystemOfUnits.Helper.addUnit(INSTANCE.units, METRE.multiply(3048).divide(10000), "Foot");

  /**
   * A unit of length equal to <code>0.9144 m</code> (standard name <code>yd</code>).
   */
  static final Unit<Length> YARD = AbstractSystemOfUnits.Helper.addUnit(INSTANCE.units, FOOT.multiply(3), "yd", LABEL);

  /**
   * A unit of length equal to <code>0.0254 m</code> (standard name <code>in</code>).
   */
  static final Unit<Length> INCH = AbstractSystemOfUnits.Helper.addUnit(INSTANCE.units, FOOT.divide(12), "Inch", "in", SYMBOL_AND_LABEL);

  /**
   * Ein Schoppen – zuerst ein niederdeutsches Wort, das ins Französische entlehnt und von dort ins Oberdeutsche rückentlehnt worden und verwandt mit
   * schöpfen ist – ist ursprünglich ein Gefäß für Flüssigkeiten, später ein Hohl- bzw. Raummaß für Getränke..
   * 
   * @see <a href="https://de.wikipedia.org/wiki/Schoppen">Wikipedia: Schoppen</a>
   */
  public static final Unit<Volume> SCHOPPEN_BAYERN = AbstractSystemOfUnits.Helper.addUnit(INSTANCE.units, LITRE.multiply(0.50), "Schoppen Bayern",
      "sb");

  /**
   * A mass unit accepted for use with SI units (standard name <code>t</code> ).
   */
  public static final Unit<Mass> TONNE = AbstractSystemOfUnits.Helper.addUnit(INSTANCE.units, new TransformedUnit<Mass>(KILOGRAM,
      new RationalConverter(1000, 1)), "Tonne", "t", UnitStyle.SYMBOL_AND_LABEL);

  /**
   * An energy unit accepted for use with SI units. The electronvolt is the kinetic energy acquired by an electron passing through a potential
   * difference of 1 V in vacuum. The value must be obtained by experiment, and is therefore not known exactly.
   */
  public static final Unit<Energy> ELECTRON_VOLT = AbstractSystemOfUnits.Helper.addUnit(INSTANCE.units, new TransformedUnit<Energy>(JOULE,
      new MultiplyConverter(1.602176487E-19)), "Electron Volt", null, UnitStyle.NAME);
  // CODATA 2006 - http://physics.nist.gov/cuu/Constants/codata.pdf

  // //////////////////////////////////////////////////////////////////////////
  // Label adjustments for Test system
  static {
    SimpleUnitFormat.getInstance().label(SCHOPPEN_BAYERN, "sch");
  }
}
