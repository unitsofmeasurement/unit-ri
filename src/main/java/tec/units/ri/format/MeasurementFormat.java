/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright (c) 2005-2014, Jean-Marie Dautelle, Werner Keil, V2COM.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
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
package tec.units.ri.format;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

import javax.measure.Measurement;
import javax.measure.Unit;
import javax.measure.format.FormatBehavior;
import javax.measure.format.Parser;
import javax.measure.format.ParserException;
import javax.measure.format.UnitFormat;

import tec.units.ri.AbstractMeasurement;
import tec.units.ri.model.BaseMeasurement;
import tec.units.ri.util.SI;

/**
 * <p> This class provides the interface for formatting and parsing {@link AbstractMeasurement
 *     measurements}.</p>
 *
 * <p> Instances of this class should be able to format measurements stated in
 *     {@link CompoundUnit}. See {@link #formatCompound formatCompound(...)}.
 * </p>
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.4, $Date: 2014-09-18 $
 */
@SuppressWarnings("rawtypes")
public abstract class MeasurementFormat extends Format implements Parser<CharSequence, Measurement> {

	/**
	 *
	 */
	private static final long serialVersionUID = -4628006924354248662L;

	/**
	 * Holds the standard format instance.
	 */
	private static final Standard STANDARD = new Standard();

	/**
	 * Returns the measure format for the default locale. The default format
	 * assumes the measure is composed of a decimal number and a {@link Unit}
	 * separated by whitespace(s).
	 *
	 * @return <code>MeasureFormat.getInstance(NumberFormat.getInstance(), UnitFormat.getInstance())</code>
	 */
	public static MeasurementFormat getInstance() {
		return STANDARD;
	}

//	/**
//	 * Returns the measure format using the specified number format and unit
//	 * format (the number and unit are separated by one space).
//	 *
//	 * @param numberFormat the number format.
//	 * @param unitFormat the unit format.
//	 * @return the corresponding format.
//	 */
//	public static MeasurementFormat getInstance(NumberFormat numberFormat,
//			UnitFormat unitFormat) {
//		return new ValueSpaceUnit(numberFormat, unitFormat);
//	}

	/**
	 * Returns the culture invariant format based upon {@link BigDecimal}
	 * canonical format and the {@link UnitFormat#getStandardInstance() standard} unit
	 * format. This format <b>is not</b> locale-sensitive and can be used for
	 * unambiguous electronic communication of quantities together with their
	 * units without loss of information. For example:
	 * <code>"1.23456789 kg.m/s2"</code> returns
	 * <code>Measure.valueOf(new BigDecimal("1.23456789"), Unit.valueOf("kg.m/s2")));</code>
	 *
	 * @param style the format style to apply.
	 * @return the desired format.
	 */
	public static MeasurementFormat getInstance(FormatBehavior style) {
		switch (style) {
		default:
			return STANDARD;
		}
	}

	/**
	 * Formats the specified measure into an <code>Appendable</code>.
	 *
	 * @param measure the measure to format.
	 * @param dest the appendable destination.
	 * @return the specified <code>Appendable</code>.
	 * @throws IOException if an I/O exception occurs.
	 */
	public abstract Appendable format(Measurement<?> measure, Appendable dest)
			throws IOException;

	/**
	 * Parses a portion of the specified <code>CharSequence</code> from the
	 * specified position to produce an object. If parsing succeeds, then the
	 * index of the <code>cursor</code> argument is updated to the index after
	 * the last character used.
	 *
	 * @param csq the <code>CharSequence</code> to parse.
	 * @param cursor the cursor holding the current parsing index.
	 * @return the object parsed from the specified character sub-sequence.
	 * @throws IllegalArgumentException
	 *             if any problem occurs while parsing the specified character
	 *             sequence (e.g. illegal syntax).
	 */
	public abstract Measurement<?> parse(CharSequence csq, ParsePosition cursor)
			throws IllegalArgumentException, ParserException;

	/**
	 * Parses a portion of the specified <code>CharSequence</code> from the
	 * specified position to produce an object. If parsing succeeds, then the
	 * index of the <code>cursor</code> argument is updated to the index after
	 * the last character used.
	 *
	 * @param csq the <code>CharSequence</code> to parse.
	 * @param cursor the cursor holding the current parsing index.
	 * @return the object parsed from the specified character sub-sequence.
	 * @throws IllegalArgumentException
	 *             if any problem occurs while parsing the specified character
	 *             sequence (e.g. illegal syntax).
	 */
    public abstract Measurement<?> parse(CharSequence csq)
			throws IllegalArgumentException, ParserException;

	/**
	 * Formats the specified value using {@link CompoundUnit} compound units}.
	 * The default implementation is locale sensitive and does not use space to
	 * separate units. For example:[code]
	 *     Unit<Length> FOOT_INCH = FOOT.compound(INCH);
	 *     Measure<Length> height = Measure.valueOf(1.81, METER);
	 *     System.out.println(height.to(FOOT_INCH));
	 *
	 *     > 5ft11,26in // French Local
	 *
	 *     Unit<Angle> DMS = DEGREE_ANGLE.compound(MINUTE_ANGLE).compound(SECOND_ANGLE);
	 *     Measure<Angle> rotation = Measure.valueOf(35.857497, DEGREE_ANGLE);
	 *     System.out.println(rotation.to(DMS));
	 *
	 *     > 35°51'26,989" // French Local
	 * [/code]
	 *
	 * @param value the value to format using compound units.
	 * @param unit the compound unit.
	 * @param dest the appendable destination.
	 * @return the specified <code>Appendable</code>.
	 * @throws IOException if an I/O exception occurs.
	 */
//	@SuppressWarnings("unchecked")
//	protected Appendable formatCompound(double value, CompoundUnit<?> unit,
//			Appendable dest) throws IOException {
//		Unit high = unit.getHigh();
//		Unit low = unit.getLow(); // The unit in which the value is stated.
//		long highValue = (long) low.getConverterTo(high).convert(value);
//		double lowValue = value - high.getConverterTo(low).convert(highValue);
//		if (high instanceof CompoundUnit)
//			formatCompound(highValue, (CompoundUnit) high, dest);
//		else {
//			dest.append(DEFAULT._numberFormat.format(highValue));
//			DEFAULT._unitFormat.format(high, dest);
//		}
//		dest.append(DEFAULT._numberFormat.format(lowValue));
//		return DEFAULT._unitFormat.format(low, dest);
//	}

	public final StringBuffer format(Object obj, final StringBuffer toAppendTo,
			FieldPosition pos) {
		if (!(obj instanceof AbstractMeasurement<?>))
			throw new IllegalArgumentException(
					"obj: Not an instance of Measure");
		if ((toAppendTo == null) || (pos == null))
			throw new NullPointerException();
		try {
			return (StringBuffer) format((AbstractMeasurement<?>) obj,
					(Appendable) toAppendTo);
		} catch (IOException ex) {
			throw new Error(ex); // Cannot happen.
		}
	}

	public final Measurement<?> parseObject(String source, ParsePosition pos) {
		try {
			return parse(source, pos);
		} catch (IllegalArgumentException | ParserException e) {
			return null; // Unfortunately the message why the parsing failed
		} // is lost; but we have to follow the Format spec.

	}

	/**
	 * Convenience method equivalent to {@link #format(AbstractMeasurement, Appendable)}
	 * except it does not raise an IOException.
	 *
	 * @param measure the measure to format.
	 * @param dest the appendable destination.
	 * @return the specified <code>StringBuilder</code>.
	 */
	public final StringBuilder format(AbstractMeasurement<?> measure, StringBuilder dest) {
		try {
			return (StringBuilder) this.format(measure, (Appendable) dest);
		} catch (IOException ex) {
			throw new RuntimeException(ex); // Should not happen.
		}
	}

	// Holds standard implementation.
	private static final class Standard extends MeasurementFormat {

		/**
		 *
		 */
		private static final long serialVersionUID = 2758248665095734058L;

		@SuppressWarnings("unchecked")
		public Appendable format(Measurement measurement, Appendable dest)
				throws IOException {
			final Unit unit = measurement.getUnit();
//			if (unit instanceof CompoundUnit)
//				return formatCompound(measure.doubleValue(unit),
//						(CompoundUnit) unit, dest);
//			else {

//				if (measure.isBig()) { // TODO SE only
//					BigDecimal decimal = measure.decimalValue(unit,
//						MathContext.UNLIMITED);
//					dest.append(decimal.toString());
//				} else {
					//Object o = measure.getValue();
//					dest.append(String.valueOf(o));
					dest.append(String.valueOf(measurement));
//				}
				if (measurement.getUnit().equals(SI.ONE))
					return dest;
				dest.append(' ');
				return LocalUnitFormat.getInstance().format(unit, dest);
//			}
		}

		@SuppressWarnings("unchecked")
		public Measurement<?> parse(CharSequence csq, ParsePosition cursor)
				throws ParserException {
			int startDecimal = cursor.getIndex();
			while ((startDecimal < csq.length())
					&& Character.isWhitespace(csq.charAt(startDecimal))) {
				startDecimal++;
			}
			int endDecimal = startDecimal + 1;
			while ((endDecimal < csq.length())
					&& !Character.isWhitespace(csq.charAt(endDecimal))) {
				endDecimal++;
			}
			Double decimal = new Double(csq.subSequence(startDecimal,
					endDecimal).toString());
			cursor.setIndex(endDecimal + 1);
			Unit unit = LocalUnitFormat.getInstance().parse(csq, cursor);
			return BaseMeasurement.of(decimal.doubleValue(), unit);
		}

        public Measurement<?> parse(CharSequence csq)
				throws ParserException {
			return parse(csq, new ParsePosition(0));
		}
	}
}