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

import org.unitsofmeasurement.ri.AbstractDimension;
import org.unitsofmeasurement.ri.function.AbstractConverter;
import org.unitsofmeasurement.ri.function.RationalConverter;

/**
 * This class represents the relativistic model.
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 5.0, October 12, 2010
 */
public class RelativisticModel extends StandardModel {
    
    /**
     * Holds the meter to time transform.
     */
    private static RationalConverter METRE_TO_TIME 
        = new RationalConverter(1, 299792458);
    
    /**
     * Default constructor.
     */
    public RelativisticModel() {
    }

    @Override
    public AbstractDimension getFundamentalDimension(AbstractDimension dimension) {
        if (dimension.equals(AbstractDimension.LENGTH)) return AbstractDimension.TIME;
        return super.getFundamentalDimension(dimension);
    }

    @Override
    public AbstractConverter getDimensionalTransform(AbstractDimension dimension) {
        if (dimension.equals(AbstractDimension.LENGTH)) return METRE_TO_TIME;
        return super.getDimensionalTransform(dimension);
    }

}