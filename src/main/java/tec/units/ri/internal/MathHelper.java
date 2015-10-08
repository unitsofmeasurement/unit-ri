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
package tec.units.ri.internal;

public final class MathHelper {
	/**
	 * Returns the negation of the argument, throwing an exception if the result
	 * exceeds a {@code double}.
	 *
	 * @param a
	 *            the value to negate
	 * @return the result
	 * @throws ArithmeticException
	 *             if the result overflows a double
	 */
	public static double negateExact(double a) {
		if (a == Double.MAX_VALUE || a == Double.MIN_VALUE) {
			throw new ArithmeticException("double overflow");
		}

		return -a;
	}

	public static double gcd(double a, double b) {
		if (b == 0)
			return a;
		return gcd(b, a % b);
	}
	
	public static final double pow(double x, double y)
	{
		return powTaylor(x,y);
	}
	
	private static final double powSqrt(double x, double y)
	{
		int den = 1024, num = (int)(y*den), iterations = 10;
		double n = Double.MAX_VALUE;
		
		while( n >= Double.MAX_VALUE && iterations > 1)
		{
			n = x;
			
			for( int i=1; i < num; i++ )n*=x;

			if( n >= Double.MAX_VALUE ) 
			{
				iterations--;
				den = (int)(den / 2);
				num = (int)(y*den);
			}
		}	

		for( int i = 0; i <iterations; i++ )n = Math.sqrt(n);
		
		return n;
	}

	private  static final double powDecay(double x, double y)
	{
		int num, den = 1001, s = 0;
		double n = x, z = Double.MAX_VALUE;
		
		for( int i = 1; i < s; i++)n *= x;
		
		while( z >= Double.MAX_VALUE )
		{
			den -=1;
			num = (int)(y*den);
			s = (num/den)+1;
			
			z = x; 
			for( int i = 1; i < num; i++ )z *= x;
		}
		
		while( n > 0 )
		{
			double a = n;

			for( int i = 1; i < den; i++ )a *= n;
			
			if( (a-z) < .00001 || (z-a) > .00001 ) return n;

			n *= .9999;                          
		}
		
		return -1.0;
	}
	
	private  static final double powTaylor(double a, double b)
	{
		boolean gt1 = (Math.sqrt((a-1)*(a-1)) <= 1)? false:true; 
		int oc = -1,iter = 30;
		double p = a, x, x2, sumX, sumY;
		
		if( (b-Math.floor(b)) == 0 )
		{
			for( int i = 1; i < b; i++ )p *= a;
			return p;
		}
		
		x = (gt1)?(a /(a-1)):(a-1);
		sumX = (gt1)?(1/x):x;
		
		for( int i = 2; i < iter; i++ )
		{
			p = x;
			for( int j = 1; j < i; j++)p *= x;
			
			double xTemp = (gt1)?(1/(i*p)):(p/i);
			
			sumX = (gt1)?(sumX+xTemp):(sumX+(xTemp*oc));
					
			oc *= -1;
		}
		
		x2 = b * sumX;
		sumY = 1+x2;
				
		for( int i = 2; i <= iter; i++ )
		{
			p = x2;
			for( int j = 1; j < i; j++)p *= x2;
			
			int yTemp = 2;
			for( int j = i; j > 2; j-- )yTemp *= j;
			
			sumY += p/yTemp;
		}
		
		return sumY;
	}
}
