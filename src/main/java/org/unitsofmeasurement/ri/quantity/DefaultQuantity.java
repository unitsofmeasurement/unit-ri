package org.unitsofmeasurement.ri.quantity;

import javax.measure.Measurement;
import javax.measure.Quantity;
import javax.measure.Unit;

import org.unitsofmeasurement.ri.AbstractMeasurement;
import org.unitsofmeasurement.ri.AbstractUnit;

class DefaultQuantity <Q extends Quantity<Q>> implements Quantity<Q> {

	private final Unit<Q> unit;
	private  final Number value;

    DefaultQuantity(final Number value, final Unit<Q> unit) {
        this.unit = unit;
        this.value = value;
    }
	@Override
	public Measurement<Q, Number> add(Measurement<Q, Number> that) {
		throw new UnsupportedOperationException("method not implemented yet");
	}

	@Override
	public Measurement<Q, Number> substract(Measurement<Q, Number> that) {
		throw new UnsupportedOperationException("method not implemented yet");
	}

	@Override
	public Measurement<?, Number> multiply(Measurement<?, Number> that) {
		throw new UnsupportedOperationException("method not implemented yet");
	}

	@Override
	public Measurement<?, Number> multiply(Number that) {
		throw new UnsupportedOperationException("method not implemented yet");
	}

	@Override
	public Measurement<?, Number> divide(Measurement<?, Number> that) {
		throw new UnsupportedOperationException("method not implemented yet");
	}

	@Override
	public Measurement<?, Number> divide(Number that) {
		throw new UnsupportedOperationException("method not implemented yet");
	}

	@Override
	public Measurement<Q, Number> inverse() {
		throw new UnsupportedOperationException("method not implemented yet");
	}

	@Override
	public Unit<Q> getUnit() {
		 return unit;
	}

	@Override
	public Number getValue() {
		return value;
	}

	@Override
	public Measurement<Q, Number> to(Unit<Q> unit) {
		throw new UnsupportedOperationException("method not implemented yet");
	}
	
	@Override
	public String toString() {
		return String.valueOf(value) + ' ' + unit;
	}

	@Override
	public int hashCode() {
		return value.hashCode() * 31 + unit.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AbstractMeasurement)){
			return false;
		}
		final AbstractMeasurement<Q> that = (AbstractMeasurement<Q>) obj;
		if (!unit.isCompatible((AbstractUnit<?>) that.getUnit())){
			return false;
		}
		return value.doubleValue() == (that).doubleValue(unit);
	}

}
