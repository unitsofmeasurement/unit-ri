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

import javolution.context.LogContext;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.unitsofmeasurement.service.SystemOfUnitsService;
import org.unitsofmeasurement.service.UnitFormatService;

/**
 * <p> The OSGi activator for the unit-ri bundle.</p>
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 5.1, April 8, 2014
 */
public class BundleActivatorImpl implements BundleActivator {

    public void start(BundleContext bc) throws Exception {
        Object name = bc.getBundle().getHeaders().get(Constants.BUNDLE_NAME);
        Object version = bc.getBundle().getHeaders().get(Constants.BUNDLE_VERSION);
        LogContext.info("Start Bundle: ", name, ", Version: ", version);
        
        // Publish SystemOfUnitsServices Implementation.
         bc.registerService(SystemOfUnitsService.class.getName(), new SystemOfUnitsServiceImpl(), null);

        // Publish UnitFormatService Implementation.
         bc.registerService(UnitFormatService.class.getName(), new UnitFormatServiceImpl(), null);
        
    }

    public void stop(BundleContext bc) throws Exception {
        Object name = bc.getBundle().getHeaders().get(Constants.BUNDLE_NAME);
        Object version = bc.getBundle().getHeaders().get(Constants.BUNDLE_VERSION);
        LogContext.info("Stop Bundle: ", name, ", Version: ", version);
    }
        
}
