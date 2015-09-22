/*
 * Unit-API - Units of Measurement API for Java
 * Copyright (c) 2014-2015 Jean-Marie Dautelle, Werner Keil, V2COM
 * All rights reserved.
 *
 * See LICENSE.txt for details.
 */
package tec.units.ri.quantity;

import java.util.Map;

import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.UnitConverter;
import tec.units.ri.AbstractUnit;

/**
 * Building blocks on top of which all others units are created.
 * Base units are always unscaled metric units.
 *
 * <p>When using the standard model (default),
 * all seven base units are dimensionally independent.</p>
 *
 * @param <Q> The type of the quantity measured by this unit.
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.9
 *
 * @see <a href="http://en.wikipedia.org/wiki/SI_base_unit">Wikipedia: SI base unit</a>
 */
final class PseudoUnit<Q extends Quantity<Q>> extends AbstractUnit<Q> {
	
	private final Dimension dim;
	
    /**
     * Creates a base unit having the specified symbol.
     *
     * @param symbol the symbol of this base unit.
     * @throws IllegalArgumentException if the specified symbol is
     *         associated to a different unit.
     */
    public PseudoUnit(String symbol, String name, Dimension d) {
        setSymbol(symbol);
        setName(name);
        dim = d;
    }
    
    /**
     * Creates a base unit having the specified symbol.
     *
     * @param symbol the symbol of this base unit.
     * @throws IllegalArgumentException if the specified symbol is
     *         associated to a different unit.
     */
    public PseudoUnit(String symbol, String name) {
    	this(symbol, name, null);
    }

    /**
     * Creates a base unit having the specified symbol.
     *
     * @param symbol the symbol of this base unit.
     * @throws IllegalArgumentException if the specified symbol is
     *         associated to a different unit.
     */
    public PseudoUnit(String symbol, Dimension d) {
        this(symbol, null, d);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PseudoUnit<?>) {
        	PseudoUnit<?> other = (PseudoUnit<?>) obj;
        	return getSymbol() != null && getSymbol().equals(other.getSymbol());
        }
        return false;
    }

    @Override
    public int hashCode() {
        //return Objects.hashCode(symbol);
    	return 0;
    }

	@Override
	protected AbstractUnit<Q> toSystemUnit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnitConverter getSystemConverter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<? extends Unit<?>, Integer> getProductUnits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension getDimension() {
		return dim;
	}

	@Override
	public String toString() {
		if (getSymbol() != null && getSymbol().length()>0) {
			return getSymbol();
		}
		return "PseudoUnit [dim=" + dim + "]";
	}
}
