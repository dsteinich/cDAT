package gov.cida.cdat.transform;

import java.util.regex.Pattern;

public interface XmlConstants {
	
	String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	String BEGIN_OPEN_TAG = "<";
	String CLOSE_TAG = ">";
	String BEGIN_END_TAG = "</";
	String CLOSE_EMPTY_TAG = "/>";
	
	String  REGEX_NON_QNAME_SEPARATORS = "[()/@]";
	String  REGEX_NON_QNAME_CHARS = "[^-_.:0-9A-Za-z]";
	String  REGEX_ALL_NON_QNAME_CHARS = REGEX_NON_QNAME_SEPARATORS + REGEX_NON_QNAME_CHARS;
	Pattern NON_QNAME_SEPARATORS = Pattern.compile(REGEX_NON_QNAME_SEPARATORS);
	Pattern NON_QNAME_CHARS = Pattern.compile(REGEX_NON_QNAME_CHARS);
	Pattern LEFT_ANGLE_BRACKET = Pattern.compile("<");
	Pattern RIGHT_ANGLE_BRACKET = Pattern.compile(">");
	Pattern AMPERSAND = Pattern.compile("&");

	Pattern LESS_THAN = Pattern.compile("&lt;");
	Pattern GREATER_THAN = Pattern.compile("&gt;");
	Pattern QUOTE = Pattern.compile("&quot;");
	Pattern APOS = Pattern.compile("&apos;");
	Pattern AMP = Pattern.compile("&amp;");

}