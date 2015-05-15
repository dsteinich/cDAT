package gov.cida.cdat.transform;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Note that this class only handles comma and tab delimited files at this time.
 *
 */
public class CharacterSeparatedValue extends Transformer {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public static final Pattern QUOTE = Pattern.compile("\"");
	public static final String  COMMA = ",";
	public static final String  TAB = "\t";
	public static final String  NEW_LINE = "\n";
	public static final String  NEW_LINE_CR = "\r\n";
	
	private final String valueSeparator;
	private final String entrySeparator;

	boolean doHeader = true;
	
	Map<String,String> fieldMappings;
	
	public CharacterSeparatedValue() {
		this(COMMA, NEW_LINE);
	}
	
	public CharacterSeparatedValue(String valueSeparator) {
		this(valueSeparator, NEW_LINE);
	}
	
	public CharacterSeparatedValue(String valueSeparator, String entrySeparator) {
		if (StringUtils.isEmpty(valueSeparator) || StringUtils.isEmpty(entrySeparator)) {
			throw new RuntimeException("Separators must provided for " + getClass());
		}
		this.valueSeparator = valueSeparator;
		this.entrySeparator = entrySeparator;
		this.fieldMappings  = new LinkedHashMap<String, String>(); // default is no mappings (they will be populated from the data linkedHashMap)
	}
	
	
	public void setFieldMappings(Map<String, String> fieldMappings) {
		this.fieldMappings = fieldMappings;
	}
	
	
	@Override
	public <T> byte[] transform(T obj) {
		StringBuilder csv = new StringBuilder();
		if (obj instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String,Object>) obj;
			csv.append( prefix(map) );
			csv.append( transform(map) );
		}
		try {
			return csv.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			return csv.toString().getBytes();
		}
	}
	
	public String transform(Map<String,Object> map) {		
		log.trace("need entry: {}", map);

		StringBuilder entry = new StringBuilder();
		String sep = "";
		for (String column : fieldMappings.keySet()) {
			entry.append(sep);
			String initialValue = "";
			if (map.containsKey(column) && null != map.get(column) ) {
				initialValue = map.get(column).toString();
			}
			String encodeValue = encode(initialValue);
			entry.append(encodeValue);
			sep = valueSeparator;
		}
		return entry.toString();
	}
	

	String doHeader(Map<String,Object> headerEntry) {
		doHeader = false;
		Map<String,	Object> headerMap = new LinkedHashMap<String, Object>();
		if (fieldMappings.isEmpty()) {
			for (Map.Entry<String,Object> column : headerEntry.entrySet()) {
				fieldMappings.put(column.getKey(), column.getKey());
			}
		}
		headerMap.putAll(fieldMappings);
		return transform(headerMap);
	}
	
	
	@Override
	public String encode(String value) {
		if (value == null) {
			return "";
		}
		String processing = value;
		if (COMMA.equals(valueSeparator)) { // Currently handles commas and quotes.
			processing = StringEscapeUtils.escapeCsv(processing);
		} else {
			processing = processing.replaceAll("[\n\r\t]", ""); // Handles newlines and carriage returns, and tabs
		}
		return processing;
	}
	
	
	/**
	 * The record prefix is the header for the first row
	 * and the entrySeparator for subsequent rows.
	 */
	String prefix(Map<String,Object> entry) {
		String prefix = "";
		if (doHeader) {
			prefix = doHeader(entry);
		}
		prefix += entrySeparator;
		return prefix;
	}
	
	
	public String getValueSeparator() {
		return valueSeparator;
	}
	public String getEntrySeparator() {
		return entrySeparator;
	}
}