package cz.mikealdo.pages;

import cz.mikealdo.football.domain.CompetitionDetails;
import cz.mikealdo.football.domain.Match;
import cz.mikealdo.football.domain.MatchResult;
import cz.mikealdo.football.domain.PairedTeam;
import org.joda.time.DateTime;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatchesStatisticsPage extends FotbalCzHTMLPage {

    public static final String COMPETITION_DETAIL_BASE_URL = "https://is.fotbal.cz/souteze/detail-souteze.aspx?req=";
    Logger logger = LoggerFactory.getLogger(MatchesStatisticsPage.class);
    private MatchSummaryPage summaryPage;

    public MatchesStatisticsPage(MatchSummaryPage summaryPage) {
        this.summaryPage = summaryPage;
    }

    public CompetitionDetails createCompetitionDetailsFrom(String competitionHash) {
		Document output = getMatchesDOMDocument(competitionHash);
		return createCompetitionDetails(output);
	}

    public Document getMatchesDOMDocument(final String competitionHash) {
        return getDocument(COMPETITION_DETAIL_BASE_URL + competitionHash);
    }

	public CompetitionDetails createCompetitionDetails(Document input) {
		CompetitionDetails competitionDetails = new CompetitionDetails();
        competitionDetails.setCompetitionName(input.getElementsByClass("nadpis-hlavni").get(0).textNodes().get(0).text().trim());
        competitionDetails.setCompetitionDescription(input.getElementsByClass("nadpis-hlavni").get(0).child(0).textNodes().get(0).text().trim());
        competitionDetails.setRoundDates(retrieveRounds(input));
		competitionDetails.setMatches(retrieveMatches(input, competitionDetails.getRoundDates()));
		competitionDetails.setTeams(retrieveTeams(competitionDetails.getMatches()));
		return competitionDetails;
	}

	private Map<Integer, DateTime> retrieveRounds(Document output) {
        Map<Integer, DateTime> rounds = new LinkedHashMap<>();
        Element competitionTable = output.select("table.soutez-kola").first();
        Elements rows = competitionTable.getElementsByTag("tr");
        for (Element row : rows) {
			Element cell = row.child(0);
            if (cell.hasClass("kolo")) {
                Elements h2 = cell.select("h2.nadpis");
                String roundText = h2.text();
                Integer round = Integer.parseInt(roundText.split("\\.")[0]);
                String date = h2.select("span").text().trim();
                rounds.put(round, parseDateTime(date));
            }
		}
		return rounds;
	}

    List<PairedTeam> retrieveTeams(List<Match> matches) {
        List<PairedTeam> teams = new LinkedList<>();
        for (Match match : matches) {
			if (!teams.contains(match.getHomeTeam())) {
				teams.add(match.getHomeTeam());
			}
			if (!teams.contains(match.getVisitorTeam())) {
				teams.add(match.getVisitorTeam());
			}
        }
        logger.info("Teams parsed.");
        List<PairedTeam> sortedTeams = teams.stream().sorted(Comparator.comparingInt(PairedTeam::getPairingId)).collect(Collectors.toList());
        sortedTeams.addAll(findFreeDraws(sortedTeams));
        return sortedTeams.stream().sorted(Comparator.comparingInt(PairedTeam::getPairingId)).collect(Collectors.toList());
    }

    private List<PairedTeam> findFreeDraws(List<PairedTeam> sortedTeams) {
        List<PairedTeam> freeDraws = new LinkedList<>();
        int lastPairingId = 0;
        for (PairedTeam team : sortedTeams) {
            int currentPairingId = team.getPairingId();
			if (currentPairingId > lastPairingId+1) {
                freeDraws.add(new PairedTeam(currentPairingId - 1, "volný los"));
            }
			lastPairingId = currentPairingId;
		}
		return freeDraws;
	}

    private List<Match> retrieveMatches(Document output, Map<Integer, DateTime> roundDates) {
        Elements tables = output.select("table.soutez-zapasy");
        List<Match> matches = new LinkedList<>();
		int round = 1;
        for (Element table : tables) {
            Elements rows = table.getElementsByTag("tr");
            for (Element row : rows) {
                if (row.getElementsByTag("th").size() > 0) {
                    continue;
                }
                Match match = new Match();
                Elements cells = row.children();
                match.setDate(parseDateTime(cells.get(0).text()));
                match.setHomeTeam(new PairedTeam(resolvePairingId(cells.get(1).text()), resolveTeamName(cells.get(1))));
                match.setVisitorTeam(new PairedTeam(resolvePairingId(cells.get(2).text()), resolveTeamName(cells.get(2))));
                Element cellWithPageLinkToMatchSummary = cells.get(6).child(0);
                if (hasMatchDetailsSpecified(cellWithPageLinkToMatchSummary)) {
                    String simpleResultText = cells.get(3).text();
                    MatchResult simpleResult = new MatchResult(simpleResultText);
                    if (simpleResult.isResultEntered()) {
                        Optional<MatchResult> detailedMatchResult = retrieveDetailedMatchResult(cellWithPageLinkToMatchSummary);
                        if (detailedMatchResult.isPresent()) {
                            MatchResult matchResult = detailedMatchResult.get();
                            if (matchResult.isResultEntered()) {
                                match.updateResult(matchResult);
                            } else {
                                match.updateResult(simpleResult);
                            }
                        }
                    }
                }
                match.setRound(round);
                logger.info(match.toString());
                matches.add(match);
            }
            round++;
        }
        logger.info("Matches parsed.");

        Stream<Match> sorted = matches.stream().sorted((o1, o2) -> {
            int firstPairingId = o1.getHomeTeam().getPairingId();
            int secondPairingId = o2.getHomeTeam().getPairingId();
            int result = o1.getRound() > o2.getRound() ? 1 : o1.getRound().equals(o2.getRound()) ? 0 : -1;
            if (result == 0) {
                result = firstPairingId > secondPairingId ? 1 : -1;
            }
            return result;
        });
        return sorted.collect(Collectors.toList());
	}

    private boolean hasMatchDetailsSpecified(Element cellWithPageLinkToMatchSummary) {
        return cellWithPageLinkToMatchSummary.hasClass("uzavren") || cellWithPageLinkToMatchSummary.hasClass("neuzavren");
    }

    private String resolveTeamName(Element element) {
        return element.text().replaceAll(" \\(.*\\)$", "");
    }

    private int resolvePairingId(String text) {
        Pattern pattern = Pattern.compile(".*\\((.*)\\)$");
        Matcher m = pattern.matcher(text);
        if (m.matches()) {
            return Integer.parseInt(m.group(1));
        }
        return 0;
    }

    protected Optional<MatchResult> retrieveDetailedMatchResult(Element link) {
        String linkToSummary = link.attr("abs:href");
        MatchResult matchResultFor = summaryPage.createMatchResultFor(linkToSummary);
        return Optional.ofNullable(matchResultFor);
    }
}
