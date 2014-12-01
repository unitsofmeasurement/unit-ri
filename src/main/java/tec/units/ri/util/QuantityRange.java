package tec.units.ri.util;

import java.util.Objects;

import javax.measure.Quantity;

/**
 * A Quantity Range is a pair of {@link Quantity} items that represent a range
 * of values.
 * <p>
 * Range limits MUST be presented in the same scale and have the same unit as
 * measured data values.<br/>
 * Subclasses of Range should be immutable.
 * 
 * @param <T>
 *            The value of the range.
 * 
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.1, November 29, 2014
 * @see <a
 *      href="http://www.botts-inc.com/SensorML_1.0.1/schemaBrowser/SensorML_QuantityRange.html">
 *      SensorML: QuantityRange</a>
 */
public class QuantityRange<Q extends Quantity<Q>> extends Range<Quantity<Q>> {
	private Quantity<Q> res;
	
	protected QuantityRange(Quantity<Q> min, Quantity<Q> max, Quantity<Q> resolution) {
		super(min, max);
		this.res = resolution;
	}

	protected QuantityRange(Quantity<Q> min, Quantity<Q> max) {
		super(min, max);
	}
	
	/**
	 * Returns an {@code Range} with the specified values.
	 *
	 * @param <T>
	 *            the class of the value
	 * @param minimum
	 *            The minimum value for the measurement range.
	 * @param maximum
	 *            The maximum value for the measurement range.
	 * @param resolution
	 *            The resolution of the measurement range.
	 * @return an {@code MeasurementRange} with the given values
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static QuantityRange of(Quantity minimum, Quantity maximum, Quantity resolution) {
		return new QuantityRange(minimum, maximum, resolution);
	}

/*
	/**
	 * Returns an {@code Range} with the specified values.
	 *
	 * @param <T>
	 *            the class of the value
	 * @param minimum
	 *            The minimum value for the quantity range.
	 * @param maximum
	 *            The maximum value for the quantity range.
	 * @return an {@code QuantityRange} with the given values
	 
	@SuppressWarnings({ "rawtypes", "unchecked" }) */
//	protected static QuantityRange of(Quantity minimum, Quantity maximum) {
//		return new QuantityRange(minimum, maximum);
//	}
	

	/**
	 * Returns the resolution of the measurement range. The value is the same as
	 * that given as the constructor parameter for the largest value.
	 * 
	 * @return resolution of the range, the value is the same as that given as
	 *         the constructor parameter for the resolution
	 */
	public Quantity<Q> getResolution() {
		return res;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see tec.units.ri.util.Range#contains()
	 */
	@Override
	public boolean contains(Quantity<Q> q) {
		if (q.getValue().doubleValue() >= getMinimum().getValue().doubleValue()) {
			return true;
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals()
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof QuantityRange<?>) {
			@SuppressWarnings("unchecked")
			final QuantityRange<Q> other = (QuantityRange<Q>) obj;
			return Objects.equals(getMinimum(), other.getMinimum()) &&
					Objects.equals(getMaximum(), other.getMaximum()) &&
					Objects.equals(getResolution(), other.getResolution());
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder().append("min= ")
				.append(getMinimum()).append(", max= ").append(getMaximum());
		if (res != null) {
			sb.append(", res= ").append(getResolution());
		}
		return sb.toString();
	}
}
