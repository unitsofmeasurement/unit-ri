/**
 * Unit-API - Units of Measurement API for Java
 * Copyright (c) 2005-2015, Jean-Marie Dautelle, Werner Keil, V2COM.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-363 nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
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

import static tec.units.ri.unit.Units.ONE;

import java.util.Map;

import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.IncommensurableException;
import javax.measure.UnconvertibleException;
import javax.measure.UnitConverter;
import tec.units.ri.format.SimpleUnitFormat;
import tec.units.ri.format.EBNFUnitFormat;
import tec.units.ri.function.AddConverter;
import tec.units.ri.function.MultiplyConverter;
import tec.units.ri.function.RationalConverter;
import tec.units.ri.quantity.QuantityDimension;
import tec.units.ri.unit.AlternateUnit;
import tec.units.ri.unit.AnnotatedUnit;
import tec.units.ri.unit.ProductUnit;
import tec.units.ri.unit.TransformedUnit;
import tec.units.ri.unit.Units;

/**
 * <p>
 * The class represents units founded on the seven SI base units for seven
 * base quantities assumed to be mutually independent.
 * </p>
 *
 * <p>
 * For all physics units, units conversions are symmetrical:
 * <code>u1.getConverterTo(u2).equals(u2.getConverterTo(u1).inverse())</code>.
 * Non-physical units (e.g. currency units) for which conversion is not
 * symmetrical should have their own separate class hierarchy and are considered
 * distinct (e.g. financial units), although they can always be combined with
 * physics units (e.g. "€/Kg", "$/h").
 * </p>
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.9.1, Jul 5, 2015
 */
public abstract class AbstractUnit<Q extends Quantity<Q>> implements Unit<Q> {

	/**
	 * 
	 */
	// private static final long serialVersionUID = -4344589505537030204L;

	/**
	 * Holds the name.
	 */
	protected String name;
	
	/**
	 * Holds the symbol.
	 */
	private String symbol;
	
	/**
	 * Default constructor.
	 */
	protected AbstractUnit() {
	}

	/**
	 * Indicates if this unit belongs to the set of coherent SI units (unscaled
	 * SI units).
	 * 
	 * The base and coherent derived units of the SI form a coherent set,
	 * designated the set of coherent SI units. The word coherent is used here
	 * in the following sense: when coherent units are used, equations between
	 * the numerical values of quantities take exactly the same form as the
	 * equations between the quantities themselves. Thus if only units from a
	 * coherent set are used, conversion factors between units are never
	 * required.
	 * 
	 * @return <code>equals(toSystemUnit())</code>
	 */
	public boolean isSystemUnit() {
		AbstractUnit<Q> si = this.toSystemUnit();
		return (this == si) || this.equals(si);
	}

	/**
	 * Returns the unscaled standard (SI) unit from which this unit is derived.
	 * 
	 * They SI unit can be be used to identify a quantity given the unit. For
	 * example:<code> static boolean isAngularVelocity(AbstractUnit<?> unit) {
	 * return unit.toSystemUnit().equals(RADIAN.divide(SECOND)); }
	 * assert(REVOLUTION.divide(MINUTE).isAngularVelocity()); // Returns true.
	 * </code>
	 *
	 * @return the unscaled metric unit from which this unit is derived.
	 */
	protected abstract AbstractUnit<Q> toSystemUnit();

	/**
	 * Returns the converter from this unit to its unscaled {@link #toSystemUnit standard}
	 * unit.
	 *
	 * @return <code>getConverterTo(this.toSystemUnit())</code>
	 * @see #toSystemUnit
	 */
	public abstract UnitConverter getSystemConverter();

	/**
	 * Annotates the specified unit. Annotation does not change the unit
	 * semantic. Annotations are often written between curly braces behind
	 * units. For example: <code> Unit<Volume> PERCENT_VOL =
	 * Units.PERCENT.annotate("vol"); // "%{vol}" AbstractUnit<Mass> KG_TOTAL =
	 * Units.KILOGRAM.annotate("total"); // "kg{total}" AbstractUnit<Dimensionless>
	 * RED_BLOOD_CELLS = ONE.annotate("RBC"); // "{RBC}" </code>
	 *
	 * Note: Annotations of system units are not considered themselves as system
	 * units.
	 *
	 * @param annotation
	 *            the unit annotation.
	 * @return the annotated unit.
	 */
	public AnnotatedUnit<Q> annotate(String annotation) {
		return new AnnotatedUnit<Q>(this, annotation);
	}

	/**
	 * Returns the physics unit represented by the specified characters.
	 *
	 * Locale-sensitive unit parsing may be handled using the OSGi
	 * {@link javax.measure.spi.UnitFormatService} or for non-OSGi applications
	 * instances of {@link SimpleUnitFormat}.
	 *
	 * <p>
	 * Note: The standard UCUM format supports dimensionless units.[code]
	 * AbstractUnit<Dimensionless> PERCENT =
	 * AbstractUnit.parse("100").inverse().asType(Dimensionless.class); [/code]
	 * </p>
	 *
	 * @param charSequence
	 *            the character sequence to parse.
	 * @return <code>SimpleUnitFormat.getInstance().parse(csq)</code>
	 * @throws ParserException
	 *             if the specified character sequence cannot be parsed correctly.
	 */
	public static Unit<?> parse(CharSequence charSequence) {
		return EBNFUnitFormat.getInstance().parse(charSequence);
	}

	/**
	 * Returns the standard {@link String} representation of this unit. The string produced for a given unit
	 * is always the same; it is not affected by the locale. It can be used as a
	 * canonical string representation for exchanging units, or as a key for a
	 * Map, Hashtable, etc.
	 *
	 * Locale-sensitive unit parsing should be handled using the OSGi
	 * {@link tec.units.ri.SimpleUnitFormat.service.UnitFormat} service (or
	 * {@link SimpleUnitFormat} for non-OSGi applications).
	 *
	 * @return <code>SimpleUnitFormat.getInstance().format(this)</code>
	 */
	@Override
	public String toString() {
		return SimpleUnitFormat.getInstance().format(this);
		/*
		final Appendable tmp = new StringBuilder();
		try {
			return SimpleUnitFormat.getInstance().format(this, tmp).toString();
		} catch (IOException ioException) {
			throw new Error(ioException); // Should never happen.
		} finally {
			// if (tmp!=null) tmp.clear();
		}
		*/
	}

	// ///////////////////////////////////////////////////////
	// Implements org.unitsofmeasurement.Unit<Q> interface //
	// ///////////////////////////////////////////////////////

	/**
	 * Returns the system unit (unscaled SI unit) from which this unit is
	 * derived. They can be be used to identify a quantity given the unit. For
	 * example:[code] static boolean isAngularVelocity(AbstractUnit<?> unit) {
	 * return unit.getSystemUnit().equals(RADIAN.divide(SECOND)); }
	 * assert(REVOLUTION.divide(MINUTE).isAngularVelocity()); // Returns true.
	 * [/code]
	 *
	 * @return the unscaled metric unit from which this unit is derived.
	 */
	@Override
	public final AbstractUnit<Q> getSystemUnit() {
		return toSystemUnit();
	}

	/**
	 * Indicates if this unit is compatible with the unit specified. To be
	 * compatible both units must be physics units having the same fundamental
	 * dimension.
	 *
	 * @param that
	 *            the other unit.
	 * @return <code>true</code> if this unit and that unit have equals
	 *         fundamental dimension according to the current physics model;
	 *         <code>false</code> otherwise.
	 */
	@Override
	public final boolean isCompatible(Unit<?> that) {
		if ((this == that) || this.equals(that))
			return true;
		if (!(that instanceof AbstractUnit))
			return false;
		Dimension thisDimension = this.getDimension();
		Dimension thatDimension = that.getDimension();
		if (thisDimension.equals(thatDimension))
			return true;
		DimensionalModel model = DimensionalModel.current(); // Use
																	// dimensional
																	// analysis
																	// model.
		return model.getFundamentalDimension(thisDimension).equals(
				model.getFundamentalDimension(thatDimension));
	}

	/**
	 * Casts this unit to a parameterized unit of specified nature or throw a
	 * ClassCastException if the dimension of the specified quantity and this
	 * unit's dimension do not match (regardless whether or not the dimensions
	 * are independent or not).
	 *
	 * @param type
	 *            the quantity class identifying the nature of the unit.
	 * @throws ClassCastException
	 *             if the dimension of this unit is different from the
	 *             SI dimension of the specified type.
	 * @see Units#getUnit(Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final <T extends Quantity<T>> Unit<T> asType(Class<T> type) {
		Dimension typeDimension = QuantityDimension.getInstance(type);
		if ((typeDimension != null)
				&& (!this.getDimension().equals(typeDimension)))
			throw new ClassCastException("The unit: " + this
					+ " is not compatible with quantities of type " + type);
		return (Unit<T>) this;
	}

	@Override
	public abstract Map<? extends Unit<?>, Integer> getProductUnits();

	@Override
	public abstract Dimension getDimension();

	protected void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	protected void setSymbol(String s) {
		this.symbol = s;
	}

	@Override
	public final UnitConverter getConverterTo(Unit<Q> that)
			throws UnconvertibleException {
		if ((this == that) || this.equals(that))
			return AbstractConverter.IDENTITY; // Shortcut.
		Unit<Q> thisSystemUnit = this.getSystemUnit();
		Unit<Q> thatSystemUnit = that.getSystemUnit();
		if (!thisSystemUnit.equals(thatSystemUnit))
			try {
				return getConverterToAny(that);
			} catch (IncommensurableException e) {
				throw new UnconvertibleException(e);
			}
		UnitConverter thisToSI = this.getSystemConverter();
		UnitConverter thatToSI = that.getConverterTo(thatSystemUnit);
		return thatToSI.inverse().concatenate(thisToSI);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public final UnitConverter getConverterToAny(Unit<?> that)
			throws IncommensurableException, UnconvertibleException {
		if (!isCompatible(that))
			throw new IncommensurableException(this
					+ " is not compatible with " + that);
		AbstractUnit thatAbstr = (AbstractUnit) that; // Since both units are
														// compatible they must
														// be both abstract
														// units.
		DimensionalModel model = DimensionalModel.current();
		AbstractUnit thisSystemUnit = this.getSystemUnit();
		UnitConverter thisToDimension = model.getDimensionalTransform(
				thisSystemUnit.getDimension()).concatenate(
				this.getSystemConverter());
		AbstractUnit thatSystemUnit = thatAbstr.getSystemUnit();
		UnitConverter thatToDimension = model.getDimensionalTransform(
				thatSystemUnit.getDimension()).concatenate(
				thatAbstr.getSystemConverter());
		return thatToDimension.inverse().concatenate(thisToDimension);
	}

	@Override
	public final Unit<Q> alternate(String symbol) {
		return new AlternateUnit<Q>(this, symbol);
	}

	@Override
	public final AbstractUnit<Q> transform(UnitConverter operation) {
		AbstractUnit<Q> systemUnit = this.getSystemUnit();
		UnitConverter cvtr = this.getSystemConverter().concatenate(operation);
		if (cvtr.equals(AbstractConverter.IDENTITY))
			return systemUnit;
		return new TransformedUnit<Q>(systemUnit, cvtr);
	}

	@Override
	public final AbstractUnit<Q> shift(double offset) {
		if (offset == 0)
			return this;
		return transform(new AddConverter(offset));
	}

	@Override
	public final AbstractUnit<Q> multiply(double factor) {
		if (factor == 1)
			return this;
		if (isLongValue(factor))
			return transform(new RationalConverter((long) factor, 1));
		return transform(new MultiplyConverter(factor));
	}

	private static boolean isLongValue(double value) {
		return !((value < Long.MIN_VALUE) || (value > Long.MAX_VALUE))
				&& Math.floor(value) == value;
	}

	/**
	 * Returns the product of this unit with the one specified.
	 *
	 * <p>
	 * Note: If the specified unit (that) is not a physical unit, then
	 * <code>that.multiply(this)</code> is returned.
	 * </p>
	 *
	 * @param that
	 *            the unit multiplicand.
	 * @return <code>this * that</code>
	 */
	@Override
	public final Unit<?> multiply(Unit<?> that) {
		if (that instanceof AbstractUnit)
			return multiply((AbstractUnit<?>) that);
		return that.multiply(this); // Commutatif.
	}

	/**
	 * Returns the product of this physical unit with the one specified.
	 *
	 * @param that
	 *            the physical unit multiplicand.
	 * @return <code>this * that</code>
	 */
	public final Unit<?> multiply(AbstractUnit<?> that) {
		if (this.equals(ONE))
			return that;
		if (that.equals(ONE))
			return this;
		return ProductUnit.getProductInstance(this, that);
	}

	/**
	 * Returns the inverse of this physical unit.
	 *
	 * @return <code>1 / this</code>
	 */
	@Override
	public final Unit<?> inverse() {
		if (this.equals(ONE))
			return this;
		return ProductUnit.getQuotientInstance(ONE, this);
	}

	/**
	 * Returns the result of dividing this unit by the specifified divisor. If
	 * the factor is an integer value, the division is exact. For example:
	 * 
	 * <pre>
	 * <code>
	 *    QUART = GALLON_LIQUID_US.divide(4); // Exact definition.
	 * </code>
	 * </pre>
	 * 
	 * @param divisor
	 *            the divisor value.
	 * @return this unit divided by the specified divisor.
	 */
	@Override
	public final AbstractUnit<Q> divide(double divisor) {
		if (divisor == 1)
			return this;
		if (isLongValue(divisor))
			return transform(new RationalConverter(1, (long) divisor));
		return transform(new MultiplyConverter(1.0 / divisor));
	}

	/**
	 * Returns the quotient of this unit with the one specified.
	 *
	 * @param that
	 *            the unit divisor.
	 * @return <code>this.multiply(that.inverse())</code>
	 */
	@Override
	public final Unit<?> divide(Unit<?> that) {
		return this.multiply(that.inverse());
	}

	/**
	 * Returns a unit equals to the given root of this unit.
	 *
	 * @param n
	 *            the root's order.
	 * @return the result of taking the given root of this unit.
	 * @throws ArithmeticException
	 *             if <code>n == 0</code> or if this operation would result in
	 *             an unit with a fractional exponent.
	 */
	@Override
	public final Unit<?> root(int n) {
		if (n > 0)
			return ProductUnit.getRootInstance(this, n);
		else if (n == 0)
			throw new ArithmeticException("Root's order of zero");
		else
			// n < 0
			return ONE.divide(this.root(-n));
	}

	/**
	 * Returns a unit equals to this unit raised to an exponent.
	 *
	 * @param n
	 *            the exponent.
	 * @return the result of raising this unit to the exponent.
	 */
	@Override
	public final Unit<?> pow(int n) {
		if (n > 0)
			return this.multiply(this.pow(n - 1));
		else if (n == 0)
			return ONE;
		else
			// n < 0
			return ONE.divide(this.pow(-n));
	}

	// //////////////////////////////////////////////////////////////
	// Ensures that sub-classes implements hashCode/equals method.
	// //////////////////////////////////////////////////////////////

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object that);

}