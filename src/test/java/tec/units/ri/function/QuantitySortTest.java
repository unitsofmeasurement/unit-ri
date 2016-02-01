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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.measure.quantity.Time;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tec.units.ri.AbstractQuantity;
import tec.units.ri.quantity.NumberQuantity;
import tec.units.ri.unit.Units;

public class QuantitySortTest {
    // TODO currently sort() only works on AbstractQuantity. If we wanted to use Quantity, we probably had to consider adding Comparable to Quantity.
    private AbstractQuantity<Time> week;
	private AbstractQuantity<Time> day;
    private AbstractQuantity<Time> hours;
    private AbstractQuantity<Time> minutes;
    private AbstractQuantity<Time> seconds;

    @Before
    public void init() {
        minutes = NumberQuantity.of(15, Units.MINUTE);
        hours = NumberQuantity.of(18, Units.HOUR);
        day = NumberQuantity.of(1, Units.DAY);
        week = NumberQuantity.of(7, Units.DAY);
        seconds = NumberQuantity.of(100, Units.SECOND);
    }

    @Test
    public void sortNaturalTest() {
        List<AbstractQuantity<Time>> times = getTimes();
        Collections.sort(times);
        Assert.assertEquals(seconds, times.get(0));
        Assert.assertEquals(minutes, times.get(1));
        Assert.assertEquals(hours, times.get(2));
        Assert.assertEquals(day, times.get(3));
        Assert.assertEquals(week, times.get(4));
    }

    @Test
    public void sortNaturalDescTest() {
        List<AbstractQuantity<Time>> times = getTimes();
        Collections.sort(times);
        Collections.reverse(times);
        Assert.assertEquals(week, times.get(0));
        Assert.assertEquals(day, times.get(1));
        Assert.assertEquals(hours, times.get(2));
        Assert.assertEquals(minutes, times.get(3));
        Assert.assertEquals(seconds, times.get(4));
    }


    @SuppressWarnings("unchecked")
	private List<AbstractQuantity<Time>> getTimes() {
        return Arrays.asList(day, minutes, hours, week, seconds);
    }
}
