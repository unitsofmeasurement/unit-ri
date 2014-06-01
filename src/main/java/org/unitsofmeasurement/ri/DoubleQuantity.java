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
	public Measurement<?, Number> multiply(Number that) {
		return of(value * that.doubleValue(), getUnit());
	}

	@Override
	public Measurement<?, Number> divide(Measurement<?, Number> that) {
		return of(value / that.getValue().doubleValue(), getUnit().divide(that.getUnit()));
	}
	
	@Override
	public Measurement<?, Number> divide(Number that) {
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