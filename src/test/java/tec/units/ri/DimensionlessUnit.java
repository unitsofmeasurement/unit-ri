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

import java.util.HashMap;
import java.util.Map;

import javax.measure.Dimension;
import javax.measure.IncommensurableException;
import javax.measure.Quantity;
import javax.measure.UnconvertibleException;
import javax.measure.Unit;
import javax.measure.UnitConverter;
import javax.measure.quantity.Dimensionless;

import tec.units.ri.quantity.QuantityDimension;
import tec.uom.lib.common.function.DoubleFactorSupplier;

/**
 * @author Werner Keil
 * @version 1.0
 */
enum DimensionlessUnit implements Unit<Dimensionless>, DoubleFactorSupplier {
  ONE("", 1.0); // reference Unit

  private final String description;
  private final double multFactor;

  private DimensionlessUnit(String name, double multF) {
    this.description = name;
    this.multFactor = multF;
  }

  public String getSymbol() {
    return description;
  }

  public double getFactor() {
    return multFactor;
  }

  public Unit<Dimensionless> getSystemUnit() {
    return ONE;
  }

  public Map<? extends Unit<?>, Integer> getProductUnits() {
    Map<Unit<Dimensionless>, Integer> prodUnits = new HashMap<Unit<Dimensionless>, Integer>();
    prodUnits.put(ONE, Integer.valueOf(1));
    return prodUnits;
  }

  public static DimensionlessUnit getByName(String symbol) {
    return ONE;
  }

  public UnitConverter getConverterTo(Unit<Dimensionless> that) throws UnconvertibleException {
    // currently unused
    return null;
  }

  public UnitConverter getConverterToAny(Unit<?> that) throws IncommensurableException, UnconvertibleException {
    // currently unused
    return null;
  }

  public Unit<Dimensionless> alternate(String s) {
    return null; // To change body of implemented methods use File | Settings | File TemplateBuilder.
  }

  public String getName() {
    return name();
  }

  public Dimension getDimension() {
    return QuantityDimension.NONE;
  }

  public Unit<?> inverse() {
    return this;
  }

  public Unit<Dimensionless> divide(double v) {
    return null; // To change body of implemented methods use File | Settings | File TemplateBuilder.
  }

  public Unit<?> divide(Unit<?> unit) {
    return null; // To change body of implemented methods use File | Settings | File TemplateBuilder.
  }

  public boolean isCompatible(Unit<?> that) {
    if (that instanceof DimensionlessUnit)
      return true;
    return false;
  }

  @SuppressWarnings("unchecked")
  public <T extends Quantity<T>> Unit<T> asType(Class<T> tClass) {
    Unit<T> metricUnit = (Unit<T>) ONE;
    if ((metricUnit == null) || metricUnit.isCompatible(this))
      return (Unit<T>) this;
    throw new ClassCastException("The unit: " + this //$NON-NLS-1$
        + " is not of parameterized type " + tClass); //$NON-NLS-1$
  }

  public Unit<Dimensionless> multiply(double factor) {
    return this;
  }

  public Unit<?> multiply(Unit<?> that) {
    return this;
  }

  public Unit<?> pow(int n) {
    return this;
  }

  public Unit<?> root(int n) {
    return this;
  }

  public Unit<Dimensionless> transform(UnitConverter operation) {
    return this;
  }

  public Unit<Dimensionless> shift(double v) {
    return this;
  }
}
