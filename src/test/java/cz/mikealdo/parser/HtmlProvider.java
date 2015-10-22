package cz.mikealdo.parser;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HtmlProvider {

    public Document getMatchStatistics() {
        return Jsoup.parse(getMatchStatisticsHTML());
    }

    public String getMatchStatisticsHTML() {
        try {
            return IOUtils.toString(
                    this.getClass().getResource("/html/match-statistics.html"),
                    "UTF-8"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("File for match statistics was not loaded.");
    }

    public Document getMatchSummary() {
        return Jsoup.parse(getMatchStatisticsHTML());
    }

    public String getMatchSummaryHTML() {
        try {
            return IOUtils.toString(
                    this.getClass().getResource("/html/match-summary.html"),
                    "UTF-8"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("File for match summary was not loaded.");
    }
}
