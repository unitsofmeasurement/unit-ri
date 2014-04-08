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
package org.unitsofmeasurement.ri.model;

/**
 * This class represents the quantum model.
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 5.0, October 12, 2010
 */
public class QuantumModel extends HighEnergyModel {

    /**
     * Default constructor.
     */
    public QuantumModel() {
    }
    
// TODO: Allow more conversion.
//        // ENERGY = m²·kg/s² = kg·c²
//        SI.KILOGRAM.setDimension(SI.GIGA(NonSI.ELECTRON_VOLT),
//                new MultiplyConverter(1E-9 * c * c / ePlus));
//
//        // H_BAR (SECOND * JOULE = SECOND * (KILOGRAM / C^2 )) = 1
//        SI.SECOND.setDimension(Unit.ONE.divide(SI.GIGA(NonSI.ELECTRON_VOLT)),
//                new MultiplyConverter(1E9 * ePlus / hBar));
//
//        // SPEED_OF_LIGHT (METRE / SECOND) = 1
//        SI.METRE.setDimension(Unit.ONE.divide(SI.GIGA(NonSI.ELECTRON_VOLT)),
//                new MultiplyConverter(1E9 * ePlus / (c * hBar)));
//
//        // BOLTZMANN (JOULE / KELVIN = (KILOGRAM / C^2 ) / KELVIN) = 1
//        SI.KELVIN.setDimension(SI.GIGA(NonSI.ELECTRON_VOLT),
//                new MultiplyConverter(1E-9 * k / ePlus));
//
//        // MAGNETIC CONSTANT (NEWTON / AMPERE^2) = 1
//        SI.AMPERE.setDimension(SI.GIGA(NonSI.ELECTRON_VOLT),
//                new MultiplyConverter(1E-9 * MathLib.sqrt(µ0 * c * hBar) / ePlus));
//
//        SI.MOLE.setDimension(SI.MOLE, Converter.IDENTITY);
//        SI.CANDELA.setDimension(SI.CANDELA, Converter.IDENTITY);
    
}
