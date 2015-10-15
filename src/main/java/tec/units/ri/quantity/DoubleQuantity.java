/*
 * Units of Measurement Reference Implementation
 * Copyright (c) 2005-2015, Jean-Marie Dautelle, Werner Keil, V2COM.
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
package tec.units.ri.quantity;

import javax.measure.Quantity;
import javax.measure.Unit;

import tec.units.ri.AbstractQuantity;
import tec.units.ri.format.QuantityFormat;
import tec.units.ri.internal.format.l10n.L10nNumberFormat;
import tec.units.ri.internal.format.l10n.NumberFormat;

/**
 * An amount of quantity, consisting of a double and a Unit. DoubleQuantity
 * objects are immutable.
 * 
 * @see AbstractQuantity
 * @see Quantity
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @author Otavio de Santana
 * @param <Q>
 *            The type of the quantity.
 * @version 0.4, $Date: 2015-07-07 $
 */
final class DoubleQuantity<Q extends Quantity<Q>> extends AbstractQuantity<Q> {

    final double value;

    public DoubleQuantity(double value, Unit<Q> unit) {
    	super(unit);
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }

    public double doubleValue(Unit<Q> unit) {
        return (super.getUnit().equals(unit)) ? value : super.getUnit().getConverterTo(unit).convert(value);
    }

	@Override
	public long longValue(Unit<Q> unit) {
        double result = doubleValue(unit);
        if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
            throw new ArithmeticException("Overflow (" + result + ")");
        }
        return (long) result;
	}

	@Override
	public Quantity<Q> add(Quantity<Q> that) {
		final Quantity<Q> converted = that.to(getUnit());
		return NumberQuantity.of(value + converted.getValue().doubleValue(), getUnit());
	}

	@Override
	public Quantity<Q> subtract(Quantity<Q> that) {
		final Quantity<Q> converted = that.to(getUnit());
		return NumberQuantity.of(value - converted.getValue().doubleValue(), getUnit());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Quantity<?> multiply(Quantity<?> that) {
		return new DoubleQuantity(value * that.getValue().doubleValue(), getUnit().multiply(that.getUnit()));
	}

	@Override
	public Quantity<Q> multiply(Number that) {
		return NumberQuantity.of(value * that.doubleValue(), getUnit());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Quantity<?> divide(Quantity<?> that) {
		return new DoubleQuantity(value / that.getValue().doubleValue(), getUnit().divide(that.getUnit()));
	}
	
	@Override
	public Quantity<Q> divide(Number that) {
		return NumberQuantity.of(value / that.doubleValue(), getUnit());
	}

	@SuppressWarnings("unchecked")
	@Override
	public AbstractQuantity<Q> inverse() {
		return (AbstractQuantity<Q>) NumberQuantity.of(1d / value, getUnit().inverse());
	}
	
	/**
	 * Returns the <code>String</code> representation of this quantity. The
	 * string produced for a given quantity is always the same; it is not
	 * affected by locale. This means that it can be used as a canonical string
	 * representation for exchanging quantity, or as a key for a Hashtable, etc.
	 * Locale-sensitive quantity formatting and parsing is handled by the
	 * {@link QuantityFormat} class and its subclasses.
	 *
	 * @return <code>UnitFormat.getInternational().format(this)</code>
	 */
	@Override
	public String toString() {
		return QuantityFormat.getInstance().format(this);
//		String numPart = NumberFormat.getInstance().format(value);
//		return numPart + " " + String.valueOf(getUnit());
//		return nosci(getValue()) + " " + String.valueOf(getUnit());
	}
	
	private static String nosci(double d) {
	    if(d < 0){
	        return "-" + nosci(-d);
	    }
	    String javaString = String.valueOf(d);
	    int indexOfE =javaString.indexOf("E"); 
	    if(indexOfE == -1){
	        return javaString;
	    }
	    StringBuffer sb = new StringBuffer();
	    if(d > 1){//big number
	        int exp = Integer.parseInt(javaString.substring(indexOfE + 1));
	        String sciDecimal = javaString.substring(2, indexOfE);
	        int sciDecimalLength = sciDecimal.length();
	        if(exp == sciDecimalLength){
	            sb.append(javaString.charAt(0));
	            sb.append(sciDecimal);              
	        }else if(exp > sciDecimalLength){
	            sb.append(javaString.charAt(0));
	            sb.append(sciDecimal);
	            for(int i = 0; i < exp - sciDecimalLength; i++){
	                sb.append('0');
	            }
	        }else if(exp < sciDecimalLength){
	            sb.append(javaString.charAt(0));
	            sb.append(sciDecimal.substring(0, exp));
	            sb.append('.');
	            for(int i = exp; i < sciDecimalLength ; i++){
	                sb.append(sciDecimal.charAt(i));
	            }
	        }
	      return sb.toString();
	    }else{
	        //for little numbers use the default or you will
	        //loose accuracy
	        return javaString;
	    }       
	}
}