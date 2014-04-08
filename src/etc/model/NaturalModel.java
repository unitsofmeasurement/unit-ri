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
 * This class represents the natural model.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Planck_units">
 *      Wikipedia: Planck units</a>
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 5.0, October 12, 2010
 */
public class NaturalModel extends QuantumModel {


    /**
     * Default constructor.
     */
    public NaturalModel() {
    }

// TODO: Allow more conversion.
//		// H_BAR (SECOND * JOULE = SECOND * (KILOGRAM / C^2 )) = 1
//		// SPEED_OF_LIGHT (METRE / SECOND) = 1
//		// BOLTZMANN (JOULE / KELVIN = (KILOGRAM / C^2 ) / KELVIN) = 1
//		// MAGNETIC CONSTANT (NEWTON / AMPERE^2) = 1
//		// GRAVITATIONAL CONSTANT (METRE^3 / KILOGRAM / SECOND^2) = 1
//		SI.SECOND.setDimension(NONE, new MultiplyConverter((c * c)
//				* MathLib.sqrt(c / (hBar * G))));
//		SI.METRE.setDimension(NONE, new MultiplyConverter(c
//				* MathLib.sqrt(c / (hBar * G))));
//		SI.KILOGRAM.setDimension(NONE, new MultiplyConverter(MathLib.sqrt(G
//				/ (hBar * c))));
//		SI.KELVIN.setDimension(NONE, new MultiplyConverter(k
//				* MathLib.sqrt(G / (hBar * c)) / (c * c)));
//		SI.AMPERE.setDimension(NONE, new MultiplyConverter(MathLib.sqrt(µ0 * G)
//				/ (c * c)));
//		SI.MOLE.setDimension(AMOUNT_OF_SUBSTANCE, Converter.IDENTITY);
//		SI.CANDELA.setDimension(LUMINOUS_INTENSITY, Converter.IDENTITY);
}
