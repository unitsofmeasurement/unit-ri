/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright 2013-2014, Jean-Marie Dautelle, Werner Keil, V2COM and individual
 *  contributors by the @author tag.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.unitsofmeasurement.ri;

import javax.measure.Measurement;
import javax.measure.Quantity;
import javax.measure.Unit;

class DoubleQuantity<T extends Quantity<T>> extends AbstractQuantity<T> {

    final double value;

    public DoubleQuantity(double value, Unit<T> unit) {
    	super(unit);
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }


    public double doubleValue(Unit<T> unit) {
        return (super.getUnit().equals(unit)) ? value : super.getUnit().getConverterTo(unit).convert(value);
    }

	@Override
	public long longValue(Unit<T> unit) {
        double result = doubleValue(unit);
        if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
            throw new ArithmeticException("Overflow (" + result + ")");
        }
        return (long) result;
	}

	@Override
	public Measurement<T, Number> add(Measurement<T, Number> that) {
		return of(value + that.getValue().doubleValue(), getUnit()); // TODO use shift of the unit?
	}

	@Override
	public Measurement<T, Number> substract(Measurement<T, Number> that) {
		return of(value - that.getValue().doubleValue(), getUnit()); // TODO use shift of the unit?
	}

	@Override
	public Measurement<?, Number> multiply(Measurement<?, Number> that) {
		return of(value * that.getValue().doubleValue(), getUnit().multiply(that.getUnit()));
	}

	@Override
	public Quantity<T> multiply(Number that) {
		return of(value * that.doubleValue(), getUnit());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Quantity<?> divide(Quantity<?> that) {
		return new DoubleQuantity(value / that.getValue().doubleValue(), getUnit().divide(that.getUnit()));
	}
	
	@Override
	public Quantity<T> divide(Number that) {
		return of(value / that.doubleValue(), getUnit());
	}

	@SuppressWarnings("unchecked")
	@Override
	public AbstractQuantity<T> inverse() {
		return (AbstractQuantity<T>) of(value, getUnit().inverse());
	}

	@Override
	public boolean isBig() {
		return false;
	}
}