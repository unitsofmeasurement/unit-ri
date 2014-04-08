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
package org.unitsofmeasurement.ri.internal.osgi;

import org.unitsofmeasurement.ri.unit.system.SI;
import org.unitsofmeasurement.ri.unit.system.UCUM;
import javax.measure.service.SystemOfUnitsService;
import javax.measure.SystemOfUnits;

/**
 * SystemOfUnitsService Implementation.
 */
class SystemOfUnitsServiceImpl implements SystemOfUnitsService {

    /**
     * Returns the SI instance.
     */
    public SystemOfUnits getSystemOfUnits() {
        return SI.getInstance();
    }

    /**
     * Returns the instance having the specified name.
     */
    public SystemOfUnits getSystemOfUnits(String name) {
        if (name.equals("SI")) return SI.getInstance();
        if (name.equals("UCUM")) return UCUM.getInstance();
        return null;
    }
    
}
