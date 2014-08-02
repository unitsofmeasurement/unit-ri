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

import javax.measure.Quantity;
import javax.measure.Unit;

/**
 * An amount of quantity, consisting of a double and a Unit. FloatQuantity
 * objects are immutable.
 * 
 * @see AbstractQuantity
 * @see Quantity
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @author OtÃ¡vio GonÃ§alves de Santana
 * @param <Q>
 *            The type of the quantity.
 * @version 0.2, $Date: 2014-08-02 $
 */
final class FloatQuantity<T extends Quantity<T>> extends AbstractQuantity<T> {

	final float value;

    public FloatQuantity(float value, Unit<T> unit) {
    	super(unit);
        this.value = value;
    }

    @Override
    public Float getValue() {
        return value;
    }

    // Implements AbstractMeasurement
    public double doubleValue(Unit<T> unit) {
        return (super.getUnit().equals(unit)) ? value : super.getUnit().getConverterTo(unit).convert(value);
    }

	public long longValue(Unit<T> unit) {
        double result = doubleValue(unit);
        if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
            throw new ArithmeticException("Overflow (" + result + ")");
        }
        return (long) result;
	}

	@Override
	public AbstractQuantity<T> add(Quantity<T> that) {
		return of(value + that.getValue().floatValue(), getUnit()); // TODO use shift of the unit?
	}

	@Override
	public AbstractQuantity<T> subtract(Quantity<T> that) {
		return of(value - that.getValue().floatValue(), getUnit()); // TODO use shift of the unit?
	}

	@SuppressWarnings("unchecked")
	@Override
	public AbstractQuantity<T> multiply(Quantity<?> that) {
		return (AbstractQuantity<T>) of(value * that.getValue().floatValue(), 
				getUnit().multiply(that.getUnit()));
	}

	@Override
	public Quantity<T> multiply(Number that) {
		return of(value * that.floatValue(), 
				getUnit().multiply(that.doubleValue()));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Quantity<?> divide(Quantity<?> that) {
		return new FloatQuantity(value / that.getValue().floatValue(), getUnit().divide(that.getUnit()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Quantity<T> inverse() {
		return (AbstractQuantity<T>) of(value, getUnit().inverse());
	}

	@Override
	public boolean isBig() {
		return false;
	}

	@Override
	public Quantity<T> divide(Number that) {
		return of(value / that.floatValue(), getUnit());
	}
}