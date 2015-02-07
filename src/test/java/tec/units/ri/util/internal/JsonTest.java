package tec.units.ri.util.internal;

import static org.junit.Assert.*;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

public class JsonTest {

	@Test
	public void test() {
		String json = "{name=\"json\",bool:true,int:1,double:2.2,func:function(a){ return a; },array:[1,2]}";  
		JSONObject jsonObject = JSONObject.fromObject( json );  
		Object bean = JSONObject.toBean( jsonObject );  
//		assertEquals( jsonObject.get( "name" ), PropertyUtils.getProperty( bean, "name" ) );  
//		assertEquals( jsonObject.get( "bool" ), PropertyUtils.getProperty( bean, "bool" ) );  
//		assertEquals( jsonObject.get( "int" ), PropertyUtils.getProperty( bean, "int" ) );  
//		assertEquals( jsonObject.get( "double" ), PropertyUtils.getProperty( bean, "double" ) );  
//		assertEquals( jsonObject.get( "func" ), PropertyUtils.getProperty( bean, "func" ) );  
		List expected = JSONArray.toList( jsonObject.getJSONArray( "array" ) );  
//		Assertions.assertListEquals( expected, (List) PropertyUtils.getProperty( bean, "array" ) );  
	}

}
