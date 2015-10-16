package cz.mikealdo.parser;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Optional;

public abstract class FotbalCzHTMLParser {
	
	protected Document getDocumentFor(String html) {
		return Jsoup.parse(html);
	}

	protected Document getDocument(String url) {
		try {
			return Jsoup.connect(url).get();
		} catch (IOException e) {
			throw new IllegalArgumentException("Competition hash is not valid, given HTML is not parseable");
		}
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

	Element getSpanByClass(Document document, String cssClassName)
	{
		return document.select("span." + cssClassName).first();
	}

	Element getSpanWithTextContent(Document document, String textContent)
	{
        return document.getElementsContainingOwnText(textContent).first();
	}

	Elements getRowsFromTableByCssClass(Document document, String cssClassName) {
        return document.select("table." + cssClassName).first().children();
	}
	
	Elements getRowsFromTableByCssClass(Document document, String cssClassName, String content) {
        Elements matchingTables = document.select("table[class=" + cssClassName + "]");
        for (Element table : matchingTables) {
            Elements rows = table.getElementsByTag("tr");
            for (Element row : rows) {
                Element cell = row.child(0);
                if (cell.getElementsContainingText(content).size() > 0) {
                    return rows;
                }
            }
        }
		throw new IllegalArgumentException("Table with given css class name and given content is not in page");
	}

}
