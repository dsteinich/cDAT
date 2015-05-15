package gov.cida.cdat.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

public class CharacterSeparatedValueTest {

	@Test
	public void constructorTest() {
		String msgPrefix = "Separators must provided for ";
		try {
			new CharacterSeparatedValue(null, null);
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
			assertTrue(e.getMessage().startsWith(msgPrefix));
		}

		try {
			new CharacterSeparatedValue(null, "");
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
			assertTrue(e.getMessage().startsWith(msgPrefix));
		}

		try {
			new CharacterSeparatedValue("", "");
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
			assertTrue(e.getMessage().startsWith(msgPrefix));
		}

		try {
			new CharacterSeparatedValue("", null);
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
			assertTrue(e.getMessage().startsWith(msgPrefix));
		}

		try {
			new CharacterSeparatedValue(null, "a");
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
			assertTrue(e.getMessage().startsWith(msgPrefix));
		}

		try {
			new CharacterSeparatedValue("a", "");
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
			assertTrue(e.getMessage().startsWith(msgPrefix));
		}

		try {
			new CharacterSeparatedValue("a", null);
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
			assertTrue(e.getMessage().startsWith(msgPrefix));
		}
		
		assertNotNull(new CharacterSeparatedValue("a", "b"));
	}
	
	@Test
	public void encodeCsvTest() {
		CharacterSeparatedValue t = new CharacterSeparatedValue(",", "\n");
		assertEquals("", t.encode(null));
		assertEquals("abc", t.encode("abc"));
		assertEquals("\"a,b,c\"", t.encode("a,b,c"));
		assertEquals("\"\"\"\"", t.encode("\""));
		assertEquals("\"\"\"a\"\"b\"\"c\"\"\"", t.encode("\"a\"b\"c\""));
		assertEquals("a\tb\tc", t.encode("a\tb\tc"));
		assertEquals("\"a\nb\nc\"", t.encode("a\nb\nc"));
		assertEquals("\"a\rb\rc\"", t.encode("a\rb\rc"));
	}
	
	@Test
	public void encodeTsvTest() {
		CharacterSeparatedValue t = new CharacterSeparatedValue("\t", "\n");
		assertEquals("", t.encode(null));
		assertEquals("abc", t.encode("abc"));
		assertEquals("a,b,c", t.encode("a,b,c"));
		assertEquals("\"", t.encode("\""));
		assertEquals("\"a\"b\"c\"", t.encode("\"a\"b\"c\""));
		assertEquals("abc", t.encode("a\tb\tc"));
		assertEquals("abc", t.encode("a\nb\nc"));
		assertEquals("abc", t.encode("a\rb\rc"));
	}
	
	@Test
	public void transformMapCsvTest() {
		CharacterSeparatedValue t = new CharacterSeparatedValue();

		Map<String, String> mapping = new LinkedHashMap<>();
		mapping.put("A", "colA");
		mapping.put("B", "colB");
		mapping.put("D", "colD");
		t.setFieldMappings(mapping);
		
		Map<String, Object> result = new HashMap<>();
    	result.put("A", null);
    	result.put("B", "2,5");
    	result.put("C", "3");

    	assertEquals(",\"2,5\",", t.transform(result));
    	
	}
	
	@Test
	public void transformMapTsvTest() {
		CharacterSeparatedValue t = new CharacterSeparatedValue("\t");

		Map<String, String> mapping = new LinkedHashMap<>();
		mapping.put("A", "colA");
		mapping.put("B", "colB");
		mapping.put("D", "colD");
		t.setFieldMappings(mapping);
		
		Map<String, Object> result = new HashMap<>();
    	result.put("A", null);
    	result.put("B", "2\n5");
    	result.put("C", "3");

    	assertEquals("\t25\t", t.transform(result));
    	
	}

	
	@Test
	public void doHeaderCsvTest() {
		CharacterSeparatedValue t = new CharacterSeparatedValue();

		Map<String, String> mapping = new LinkedHashMap<>();
		mapping.put("A", "colA");
		mapping.put("B", "col,B");
		mapping.put("D", "colD");
		
		Map<String, Object> result = new HashMap<>();
    	result.put("A", null);
    	result.put("B", "2,5");
    	result.put("C", "3");

    	assertEquals("A,B,C", t.doHeader(result));
    	
		t.setFieldMappings(mapping);
    	assertEquals("colA,\"col,B\",colD", t.doHeader(result));

	}
	
	@Test
	public void doHeaderTsvTest() {
		CharacterSeparatedValue t = new CharacterSeparatedValue("\t");

		Map<String, String> mapping = new LinkedHashMap<>();
		mapping.put("A", "colA");
		mapping.put("B", "col\nB");
		mapping.put("D", "colD");
		
		Map<String, Object> result = new HashMap<>();
    	result.put("A", null);
    	result.put("B", "2\n5");
    	result.put("C", "3");

    	assertEquals("A\tB\tC", t.doHeader(result));
    	
		t.setFieldMappings(mapping);
    	assertEquals("colA\tcolB\tcolD", t.doHeader(result));
    	
	}
	
	@Test
	public void transformObjectTest() {
		CharacterSeparatedValue t = new CharacterSeparatedValue();

		Map<String, String> mapping = new LinkedHashMap<>();
		mapping.put("A", "colA");
		mapping.put("B", "col,B");
		mapping.put("D", "colD");
		
		Map<String, Object> result = new HashMap<>();
    	result.put("A", null);
    	result.put("B", "2,5");
    	result.put("C", "3");

    	byte[] x = t.transform((Object)result);
    	assertEquals("A,B,C\n,\"2,5\",3", new String(x));
    	
		CharacterSeparatedValue u = new CharacterSeparatedValue();
		u.setFieldMappings(mapping);
		byte[] y = u.transform((Object)result);
    	assertEquals("colA,\"col,B\",colD\n,\"2,5\",", new String(y));

    	//don't write the headers again
		byte[] z = u.transform((Object)result);
    	assertEquals("\n,\"2,5\",", new String(z));

	}
	
}
