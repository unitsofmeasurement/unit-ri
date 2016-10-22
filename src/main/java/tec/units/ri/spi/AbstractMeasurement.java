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
package tec.units.ri.spi;

import javax.measure.Quantity;

/**
 * <p>
 * This class represents the immutable result of a measurement stated in a known quantity.
 * </p>
 * 
 * <p>
 * All instances of this class shall be immutable.
 * </p>
 * 
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.0, $Date: 2016-10-06 $
 * @since 1.0
 */
abstract class AbstractMeasurement<Q extends Quantity<Q>> implements Measurement<Q> {

  /**
	 * 
	 */
  // private static final long serialVersionUID = 2417644773551236879L;

  private final Quantity<Q> quantity;

  private final long timestamp;

  /**
   * constructor.
   */
  protected AbstractMeasurement(Quantity<Q> q, long t) {
    this.quantity = q;
    this.timestamp = t;
  }

  /**
   * constructor.
   */
  protected AbstractMeasurement(Quantity<Q> q) {
    this(q, System.currentTimeMillis());
  }

  /**
   * Returns the measurement quantity.
   *
   * @return the quantity.
   */
  public final Quantity<Q> getQuantity() {
    return quantity;
  }

  /**
   * Returns the measurement timestamp.
   *
   * @return the timestamp.
   */
  public final long getTimestamp() {
    return timestamp;
  }

  @SuppressWarnings({ "unchecked" })
  static final <Q extends Quantity<Q>> Measurement<Q> of(Quantity<Q> q) {
    return new Default<Q>(q);
  }

  @SuppressWarnings({ "unchecked" })
  static final <Q extends Quantity<Q>> Measurement<Q> of(Quantity<Q> q, long t) {
    return new Default<Q>(q, t);
  }

  /**
   * This class represents the default measurement.
   */
  @SuppressWarnings("rawtypes")
  private static final class Default<Q> extends AbstractMeasurement {

    @SuppressWarnings({ "unchecked" })
    protected <R extends Quantity<R>> Default(Quantity<R> q, long t) {
      super(q, t);
    }

    @SuppressWarnings("unchecked")
    protected <R extends Quantity<R>> Default(Quantity<R> q) {
      super(q);
    }

    public int compareTo(Object o) {
      // TODO Auto-generated method stub
      return 0;
    }
  }
}
