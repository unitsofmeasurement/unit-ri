/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright 2010-2014, Jean-Marie Dautelle, Werner Keil, V2COM and individual
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
package org.unitsofmeasurement.ri.internal.osgi;

import org.unitsofmeasurement.ri.AbstractDimension;
import org.unitsofmeasurement.quantity.Quantity;

/**
 * <p> This interface represents the service to retrieve the dimension
 *     given a quantity type.</p>
 * 
 * <p> Bundles providing new quantity types and/or dimensions should publish 
 *     instances of this class in order for the framework to be able
 *     to determinate the dimension associated to the new quantities.</p>
 * 
 * <p> When activated the unit-ri bundle publishes the dimensional 
 *     mapping of all quantities defined in the 
 *     <code>org.unitsofmeasurement.quantity</code> package.</p>

 * <p> Published instances are typically used to check the dimensional 
 *     consistency of physical quantities.</p>
 *     
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 5.1, April 7, 2014
 */
public class DimensionServiceImpl implements DimensionService {
// FIXME class,  not interface
    /**
     * Returns the dimension for the specified quantity or <code>null</code> if
     * unknown.
     *
     * @return the corresponding dimension or <code>null</code>
     */
    <Q extends Quantity<Q>> Dimension getDimension(Class<Q> quantityType);
    
}
