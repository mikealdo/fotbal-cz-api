package cz.mikealdo.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.apache.commons.io.output.NullOutputStream;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

public abstract class FotbalCzHTMLParser {
	
	protected Document getDOMDocument(String html) {
		Tidy tidy = createTidy();
		InputStream stream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
		return tidy.parseDOM(stream, new NullOutputStream());
	}

	protected Document getDOMDocument(URL url) {
		Tidy tidy = createTidy();
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
			Writer outputStream = new BufferedWriter(new OutputStreamWriter(new NullOutputStream()));
			return tidy.parseDOM(input, outputStream);
		} catch (IOException e) {
			throw new IllegalArgumentException("Competition hash is not valid, given HTML is not parseable");
		}
	}

	private Tidy createTidy() {
		Tidy tidy = new Tidy();
		tidy.setShowWarnings(false);
		tidy.setQuiet(true);
		tidy.setHideComments(true);
		tidy.setInputEncoding("UTF-8");
		tidy.setOutputEncoding("UTF-8");
		return tidy;
	}

	String getNodeValue(Node item) {
		if (item == null || item.getFirstChild() == null) {
			return "";
		}
		return item.getFirstChild().getNodeValue();
	}

	DateTime parseDateTime(String stringToParse) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm");
		return formatter.parseDateTime(stringToParse);
	}

	Optional<Integer> parseInteger(String nodeValue) {
		if (StringUtils.isBlank(nodeValue)) {
			return Optional.empty();
		}
		return Optional.of(Integer.parseInt(nodeValue));
	}

	Node getSpanByClass(Document document, String cssClassName)
	{
		NodeList spans = document.getElementsByTagName("span");
		for (int i = 0; i < spans.getLength(); i++) {
			Node span = spans.item(i);
			if (span.getAttributes().getNamedItem("class").getNodeValue().contains(cssClassName)) {
				return span;
			}
		}
		throw new IllegalArgumentException("No span with given css class name.");
	}

	Node getSpanWithTextContent(Document document, String textContent)
	{
		NodeList spans = document.getElementsByTagName("span");
		for (int i = 0; i < spans.getLength(); i++) {
			Node span = spans.item(i);
			String spanFirstChildText = span.getChildNodes().item(0).getNodeValue();
			if (spanFirstChildText.contains(textContent)) {
				return span;
			}
		}
		throw new IllegalArgumentException("No span with given text content.");
	}

	NodeList getRowsFromTableByCssClass(Document summaryDOMDocument, String cssClassName) {
		NodeList tables = summaryDOMDocument.getElementsByTagName("table");
		for (int i = 0; i < tables.getLength(); i++){
			Node table = tables.item(i);
			if (classNameMatches(cssClassName, table)) {
				return table.getChildNodes();
			}
		}
		throw new IllegalArgumentException("Table with given css class name is not in page");
	}
	
	NodeList getRowsFromTableByCssClass(Document summaryDOMDocument, String cssClassName, String content) {
		NodeList tables = summaryDOMDocument.getElementsByTagName("table");
		for (int i = 0; i < tables.getLength(); i++){
			Node table = tables.item(i);
			if (classNameMatches(cssClassName, table) && isContentPresent(content, table)) {
				return table.getChildNodes();
			}
		}
		throw new IllegalArgumentException("Table with given css class name and given content is not in page");
	}

	private boolean isContentPresent(String content, Node table) {
		NodeList childNodes = table.getChildNodes().item(0).getChildNodes().item(0).getChildNodes();
		return getNodeValue(childNodes.item(0)).contains(content);
	}

	private boolean classNameMatches(String cssClassName, Node table) {
		return table.getAttributes() != null && table.getAttributes().getNamedItem("class") != null && table.getAttributes().getNamedItem("class").getNodeValue().equals(cssClassName);
	}

}
