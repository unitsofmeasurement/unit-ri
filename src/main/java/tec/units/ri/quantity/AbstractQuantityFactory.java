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
package tec.units.ri.quantity;

import static tec.units.ri.util.SI.AMPERE;
import static tec.units.ri.util.SI.AMPERE_TURN;
import static tec.units.ri.util.SI.BECQUEREL;
import static tec.units.ri.util.SI.BIT;
import static tec.units.ri.util.SI.CANDELA;
import static tec.units.ri.util.SI.COULOMB;
import static tec.units.ri.util.SI.CUBIC_METRE;
import static tec.units.ri.util.SI.FARAD;
import static tec.units.ri.util.SI.GRAY;
import static tec.units.ri.util.SI.HENRY;
import static tec.units.ri.util.SI.HERTZ;
import static tec.units.ri.util.SI.JOULE;
import static tec.units.ri.util.SI.KATAL;
import static tec.units.ri.util.SI.KELVIN;
import static tec.units.ri.util.SI.KILOGRAM;
import static tec.units.ri.util.SI.LUMEN;
import static tec.units.ri.util.SI.LUX;
import static tec.units.ri.util.SI.METRE;
import static tec.units.ri.util.SI.METRES_PER_SECOND;
import static tec.units.ri.util.SI.METRES_PER_SQUARE_SECOND;
import static tec.units.ri.util.SI.MOLE;
import static tec.units.ri.util.SI.NEWTON;
import static tec.units.ri.util.SI.OHM;
import static tec.units.ri.util.SI.PASCAL;
import static tec.units.ri.util.SI.RADIAN;
import static tec.units.ri.util.SI.SECOND;
import static tec.units.ri.util.SI.SIEMENS;
import static tec.units.ri.util.SI.SIEVERT;
import static tec.units.ri.util.SI.SQUARE_METRE;
import static tec.units.ri.util.SI.STERADIAN;
import static tec.units.ri.util.SI.TESLA;
import static tec.units.ri.util.SI.VOLT;
import static tec.units.ri.util.SI.WATT;
import static tec.units.ri.util.SI.WEBER;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.function.QuantityFactory;
import javax.measure.quantity.Acceleration;
import javax.measure.quantity.AmountOfSubstance;
import javax.measure.quantity.Angle;
import javax.measure.quantity.Area;
import javax.measure.quantity.CatalyticActivity;
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.ElectricCapacitance;
import javax.measure.quantity.ElectricCharge;
import javax.measure.quantity.ElectricConductance;
import javax.measure.quantity.ElectricCurrent;
import javax.measure.quantity.ElectricInductance;
import javax.measure.quantity.ElectricPotential;
import javax.measure.quantity.ElectricResistance;
import javax.measure.quantity.Energy;
import javax.measure.quantity.Force;
import javax.measure.quantity.Frequency;
import javax.measure.quantity.Illuminance;
import javax.measure.quantity.Information;
import javax.measure.quantity.Length;
import javax.measure.quantity.LuminousFlux;
import javax.measure.quantity.LuminousIntensity;
import javax.measure.quantity.MagneticFlux;
import javax.measure.quantity.MagneticFluxDensity;
import javax.measure.quantity.MagnetomotiveForce;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Power;
import javax.measure.quantity.Pressure;
import javax.measure.quantity.RadiationDoseAbsorbed;
import javax.measure.quantity.RadiationDoseEffective;
import javax.measure.quantity.Radioactivity;
import javax.measure.quantity.SolidAngle;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Temperature;
import javax.measure.quantity.Time;
import javax.measure.quantity.Volume;

import tec.units.ri.AbstractQuantity;
import tec.units.ri.util.SI;


/**
 * A factory producing simple quantities instances (tuples {@link Number}/{@link Unit}).
 *
 * For example:<br/><code>
 *      Mass m = QuantityFactory.getInstance(Mass.class).create(23.0, KILOGRAM); // 23.0 kg<br/>
 *      Time m = QuantityFactory.getInstance(Time.class).create(124, MILLI(SECOND)); // 124 ms
 * </code>
 * @param <Q> The type of the quantity.
 *
 * @author  <a href="mailto:desruisseaux@users.sourceforge.net">Martin Desruisseaux</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 0.5.3, $Date: 2014-05-28 $
 */
public abstract class AbstractQuantityFactory<Q extends Quantity<Q>> implements QuantityFactory<Q>  {

    /**
     * Holds the current instances.
     */
    @SuppressWarnings("rawtypes")
	private static final Map<Class, AbstractQuantityFactory> INSTANCES = new ConcurrentHashMap<>();

    private static final Logger logger = Logger.getLogger(AbstractQuantityFactory.class.getName());

    private static final Level LOG_LEVEL = Level.FINE;

    /**
     * Returns the default instance for the specified quantity type.
     *
     * @param <Q> The type of the quantity
     * @param type the quantity type
     * @return the quantity factory for the specified type
     */
    @SuppressWarnings("unchecked")
	public static <Q extends Quantity<Q>>  AbstractQuantityFactory<Q> getInstance(final Class<Q> type) {

         logger.log(LOG_LEVEL, "Type: " + type + ": " + type.isInterface());
         AbstractQuantityFactory<Q> factory;
         if (!type.isInterface()) {
        	 if (type != null && type.getInterfaces() != null & type.getInterfaces().length > 0) {
	        	 logger.log(LOG_LEVEL, "Type0: " + type.getInterfaces()[0]);
	             Class<?> type2 = type.getInterfaces()[0];

	            factory = INSTANCES.get(type2);
	            if (factory != null) return factory;
	            if (!AbstractQuantity.class.isAssignableFrom(type2))
	                // This exception is not documented because it should never happen if the
	                // user don't try to trick the Java generic types system with unsafe cast.
	                throw new ClassCastException();
	            factory = new Default<Q>((Class<Q>)type2);
	            INSTANCES.put(type2, factory);
        	 } else {
                 factory = INSTANCES.get(type);
                 if (factory != null) return factory;
                 if (!AbstractQuantity.class.isAssignableFrom(type))
                     // This exception is not documented because it should never happen if the
                     // user don't try to trick the Java generic types system with unsafe cast.
                     throw new ClassCastException();
                 factory = new Default<Q>(type);
                 INSTANCES.put(type, factory);
        	 }
         } else {
            factory = INSTANCES.get(type);
            if (factory != null) return factory;
            if (!Quantity.class.isAssignableFrom(type))
                // This exception is not documented because it should never happen if the
                // user don't try to trick the Java generic types system with unsafe cast.
                throw new ClassCastException();
            factory = new Default<Q>(type);
            INSTANCES.put(type, factory);
         }
        return factory;
    }

    /**
     * Overrides the default implementation of the factory for the specified
     * quantity type.
     *
     * @param <Q> The type of the quantity
     * @param type the quantity type
     * @param factory the quantity factory
     */
    protected static <Q extends Quantity<Q>>  void setInstance(final Class<Q> type, AbstractQuantityFactory<Q> factory) {
        if (!AbstractQuantity.class.isAssignableFrom(type))
            // This exception is not documented because it should never happen if the
            // user don't try to trick the Java generic types system with unsafe cast.
            throw new ClassCastException();
        INSTANCES.put(type, factory);
    }

    /**
     * Returns the quantity for the specified number stated in the specified unit.
     *
     * @param value the value stated in the specified unit
     * @param unit the unit
     * @return the corresponding quantity
     */
    public abstract <N extends Number, U extends Unit<Q>> Q create(N number, U unit);

    /**
     * Returns the metric unit for quantities produced by this factory
     * or <code>null</code> if unknown.
     *
     * @return the metric units for this factory quantities.
     */
    public abstract Unit<Q> getMetricUnit();

    /**
     * The default factory implementation. This factory provides
     * a default implementation for every {@link AbstractQuantity} sub-types.
     *
     * @param <Q> The type of the quantity
     */
    private static final class Default<Q extends Quantity<Q>>  extends AbstractQuantityFactory<Q> {

        /**
         * The type of the quantities created by this factory.
         */
        @SuppressWarnings("unused")
		private final Class<Q> type;

        /**
         * The metric unit for quantities created by this factory.
         */
        private final Unit<Q> metricUnit;

        /**
         * Creates a new factory for quantities of the given type.
         *
         * @param type The type of the quantities created by this factory.
         */
        @SuppressWarnings("unchecked")
		Default(final Class<Q> type) {
            this.type = type;
            metricUnit = CLASS_TO_METRIC_UNIT.get(type);
        }
        @SuppressWarnings("rawtypes")
		static final Map<Class, Unit> CLASS_TO_METRIC_UNIT = new HashMap<Class, Unit>();
        static {
            CLASS_TO_METRIC_UNIT.put(Dimensionless.class, SI.ONE);
            CLASS_TO_METRIC_UNIT.put(ElectricCurrent.class, AMPERE);
            CLASS_TO_METRIC_UNIT.put(LuminousIntensity.class, CANDELA);
            CLASS_TO_METRIC_UNIT.put(Temperature.class, KELVIN);
            CLASS_TO_METRIC_UNIT.put(Mass.class, KILOGRAM);
            CLASS_TO_METRIC_UNIT.put(Length.class, METRE);
            CLASS_TO_METRIC_UNIT.put(AmountOfSubstance.class, MOLE);
            CLASS_TO_METRIC_UNIT.put(Time.class, SECOND);
            CLASS_TO_METRIC_UNIT.put(MagnetomotiveForce.class, AMPERE_TURN);
            CLASS_TO_METRIC_UNIT.put(Angle.class, RADIAN);
            CLASS_TO_METRIC_UNIT.put(SolidAngle.class, STERADIAN);
            CLASS_TO_METRIC_UNIT.put(Information.class, BIT);
            CLASS_TO_METRIC_UNIT.put(Frequency.class, HERTZ);
            CLASS_TO_METRIC_UNIT.put(Force.class, NEWTON);
            CLASS_TO_METRIC_UNIT.put(Pressure.class, PASCAL);
            CLASS_TO_METRIC_UNIT.put(Energy.class, JOULE);
            CLASS_TO_METRIC_UNIT.put(Power.class, WATT);
            CLASS_TO_METRIC_UNIT.put(ElectricCharge.class, COULOMB);
            CLASS_TO_METRIC_UNIT.put(ElectricPotential.class, VOLT);
            CLASS_TO_METRIC_UNIT.put(ElectricCapacitance.class, FARAD);
            CLASS_TO_METRIC_UNIT.put(ElectricResistance.class, OHM);
            CLASS_TO_METRIC_UNIT.put(ElectricConductance.class, SIEMENS);
            CLASS_TO_METRIC_UNIT.put(MagneticFlux.class, WEBER);
            CLASS_TO_METRIC_UNIT.put(MagneticFluxDensity.class, TESLA);
            CLASS_TO_METRIC_UNIT.put(ElectricInductance.class, HENRY);
            CLASS_TO_METRIC_UNIT.put(LuminousFlux.class, LUMEN);
            CLASS_TO_METRIC_UNIT.put(Illuminance.class, LUX);
            CLASS_TO_METRIC_UNIT.put(Radioactivity.class, BECQUEREL);
            CLASS_TO_METRIC_UNIT.put(RadiationDoseAbsorbed.class, GRAY);
            CLASS_TO_METRIC_UNIT.put(RadiationDoseEffective.class, SIEVERT);
            CLASS_TO_METRIC_UNIT.put(CatalyticActivity.class, KATAL);
            CLASS_TO_METRIC_UNIT.put(Speed.class, METRES_PER_SECOND);
            CLASS_TO_METRIC_UNIT.put(Acceleration.class, METRES_PER_SQUARE_SECOND);
            CLASS_TO_METRIC_UNIT.put(Area.class, SQUARE_METRE);
            CLASS_TO_METRIC_UNIT.put(Volume.class, CUBIC_METRE);
        }

        @SuppressWarnings("unchecked")
        public <N extends Number, U extends Unit<Q>> Q create(N number, U unit) {
            return (Q) new BaseQuantity<Q>(number, unit);
        }

        public Unit<Q> getMetricUnit() {
            return metricUnit;
        }
    }

}
