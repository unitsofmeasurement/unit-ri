/**
 * Unit-API - Units of Measurement API for Java
 * Copyright (c) 2005-2015, Jean-Marie Dautelle, Werner Keil, V2COM.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-363 nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
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
package tec.units.ri.util.internal;

import java.io.InputStream;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class JsonParsing {

    public static void main(String[] args) throws Exception {
        InputStream is = 
                JsonParsing.class.getResourceAsStream( "/root/units.json");
        String jsonTxt = IOUtils.toString( is );

        JSONObject json = (JSONObject) JSONSerializer.toJSON( jsonTxt );        
//        Set entries = json.entrySet();
//        
//        for (Object o : entries) {
//        	System.out.println(o);
//        }
        JSONObject main = json.getJSONObject("main");
        JSONObject root = main.getJSONObject("root");
        JSONObject id = root.getJSONObject("identity");
        JSONObject units = root.getJSONObject("units");
//        System.out.println(units);
        JSONObject longUnits = units.getJSONObject("long");
//        System.out.println(longUnits);
	      Set entries = longUnits.entrySet();
	      
	      for (Object o : entries) {
	      	System.out.println(o);
	      }
//        JSONObject test = units.getJSONObject("acceleration-g-force");
    }
}
