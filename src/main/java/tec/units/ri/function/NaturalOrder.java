/*
 * Units of Measurement Reference Implementation
 * Copyright (c) 2005-2016, Jean-Marie Dautelle, Werner Keil, V2COM.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-363 nor the names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
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
package tec.units.ri.function;

import java.util.Comparator;

import javax.measure.Quantity;

/**
 * Comparator to sort by natural order, looking both the unit and the value.
 * 
 * @author <a href="mailto:werner@uom.technology">Werner Keil</a>
 * @author <a href="mailto:otaviopolianasantana@gmail.com">Otavio Santana</a>
 * @version 1.0.1
 * 
 * @return <b>Given:</b>
 *         <p>
 *         Quantity&lt;Time&gt; day = Quantities.getQuantity(1, Units.DAY);
 *         </p>
 *         <p>
 *         Quantity&lt;Time&gt; hours = Quantities.getQuantity(18, Units.HOUR);
 *         </p>
 *         <p>
 *         Quantity&lt;Time&gt; minutes = Quantities.getQuantity(15, Units.HOUR);
 *         </p>
 *         <p>
 *         Quantity&lt;Time&gt; seconds = Quantities.getQuantity(100, Units.HOUR);
 *         </p>
 *         will return: seconds, minutes, hours, day
 * @since 1.0
 */
public class NaturalOrder<T extends Quantity<T>> implements Comparator<Quantity<T>> {

  @Override
  public int compare(Quantity<T> q1, Quantity<T> q2) {
    if (q1.getUnit().equals(q2.getUnit())) {
      return Double.compare(q1.getValue().doubleValue(), q2.getValue().doubleValue());
    }
    return Double.compare(q1.getValue().doubleValue(), q2.to(q1.getUnit()).getValue().doubleValue());
  }
}
