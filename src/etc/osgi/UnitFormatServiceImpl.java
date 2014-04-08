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

import java.util.Locale;

import org.unitsofmeasurement.ri.unit.format.LocalUnitFormat;
import org.unitsofmeasurement.ri.unit.format.UCUMFormat;
import org.unitsofmeasurement.service.UnitFormatService;
import org.unitsofmeasurement.unit.UnitFormat;

/**
 * UnitFormatService Implementation.
 */
class UnitFormatServiceImpl implements UnitFormatService {

    /**
     * Returns the UCUM instance.
     */
    public UnitFormat getUnitFormat() {
        return UCUMFormat.getCaseSensitiveInstance();
    }

    /**
     * Returns the format having the specified name.
     */
    public UnitFormat getUnitFormat(String name) {
        if (name.equals("UCUM")) return UCUMFormat.getCaseSensitiveInstance();
        return null;
    }

    /**
     * Returns the format for the specified locale.
     */
    public UnitFormat getUnitFormat(Locale locale) {
        return LocalUnitFormat.getInstance(locale);
    }
    
}
